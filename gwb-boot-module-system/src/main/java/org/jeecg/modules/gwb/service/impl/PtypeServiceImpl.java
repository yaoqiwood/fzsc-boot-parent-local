package org.jeecg.modules.gwb.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.jeecg.common.enumerate.EnumMethodType;
import org.jeecg.common.enumerate.EnumSyncPtypeStatus;
import org.jeecg.common.enumerate.EnumVchType;
import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.common.util.*;
import org.jeecg.modules.gwb.entity.HisPtpyeSync;
import org.jeecg.modules.gwb.entity.Ptype;
import org.jeecg.modules.gwb.entity.dto.*;
import org.jeecg.modules.gwb.mapper.PtypeMapper;
import org.jeecg.modules.gwb.service.IHisPtpyeSyncService;
import org.jeecg.modules.gwb.service.IPtypeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.base.Strings;

import cn.hutool.core.util.XmlUtil;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PtypeServiceImpl extends ServiceImpl<PtypeMapper, Ptype> implements IPtypeService {

    @Value("${ptype.postDataUrl}")
    private String postDataUrl;

    @Value("${ptype.filter.check.public.key}")
    private String filterCheckPublicKey;

    @Value("${ptype.getSyncMaxUpdateTagFromServerUrl}")
    private String getSyncMaxUpdateTagFromServerUrl;

    @Autowired
    private IHisPtpyeSyncService hisPtpyeSyncService;

    private final String USED_TYPE_REDUCE = "1";

    private final String USED_TYPE_PLUS = "2";

    @Override
    public void test() {
        Integer index = 800;
        QueryWrapper<Ptype> ptypeQueryWrapper = new QueryWrapper<>();
        ptypeQueryWrapper.lambda().gt(Ptype::getRowindex, index);
        List<Ptype> ptypeList = this.list(ptypeQueryWrapper);
        ptypeList.forEach(item -> {
            log.info(item.getPfullname());
        });
    }

    /**
     * syncSendPtypeInfData2Server
     *
     * @return
     * @throws IOException
     */
    @DS("multi-datasource-gwb")
    @Override
    public HisPtpyeSync syncSendPtypeInfData2Server() throws IOException {
        BufferedReader reader = null;
        try {
            log.info("查询最大Tag开始......\n");
            String maxTag = this.httpGetSyncMethodFromServer(getSyncMaxUpdateTagFromServerUrl, reader);
            log.info("查询封装传输数据开始......MaxTag:" + maxTag);
            QueryWrapper<Ptype> queryWrapper4LookUp = new QueryWrapper<>();
            queryWrapper4LookUp.lambda().gt(Ptype::getUpdatetag, maxTag);
            List<Ptype> ptypeList = list(queryWrapper4LookUp);
            if (ptypeList.size() == 0) {
                throw new JeecgBootException("没有检测到新的商品信息更新");
            }
            StringBuilder sendSBData = new StringBuilder(JSONArray.toJSONString(ptypeList));
            log.info("查询数据封装完毕，正在向URL发送请求数据:\n" + postDataUrl);
            StringBuilder sendStr = HttpXmlBuilderUtil.buildSendDataBuilder(sendSBData, new Date());
            String builder = HTTPUtil.httpPostMethod(postDataUrl, filterCheckPublicKey, sendStr.toString(), reader);
            log.info("接收返回值:" + builder);
            Map<String, Object> retMessageMap = XmlUtil.xmlToMap(builder);
            if (!String.valueOf(true).equals(retMessageMap.get("response_success"))) {
                throw new JeecgBootException("错误，同步不成功");
            }
            // 同步信息记录历史
            HisPtpyeSync hisPtpyeSync = new HisPtpyeSync();
            hisPtpyeSync.setHpsId(SnowflakeUtil.getNum().toString());
            hisPtpyeSync.setUpdateTag(maxTag);
            hisPtpyeSync.setStatus(EnumSyncPtypeStatus.PTYPE.getCode());
            hisPtpyeSync.setCreateTime(new Date());
            hisPtpyeSync.setReceiveTime(new Date());
            hisPtpyeSync.setAffectNum(ptypeList.size());
            // hisPtpyeSync.setRemoteHost(postDataUrl);
            return hisPtpyeSync;
            // saveHisInf(hisPtpyeSync);
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
    }

    @DS("multi-datasource-gwb")
    @Override
    public Integer changeAllE2CATName() {
        List<Ptype> ptypeList = this.baseMapper.findContainENamePtype();
        Integer size = 0;
        for (Ptype ptype : ptypeList) {
            Ptype ptype4Update = new Ptype();
            String name = ptype.getPfullname();
            String replaceName = name.replaceAll("E", "CAT");
            replaceName = replaceName.replaceAll("e", "CAT");
            ptype4Update.setPtypeid(ptype.getPtypeid());
            ptype4Update.setPfullname(replaceName);
            ptype4Update.setPnamepy(PingYinUtil.getFirstSpell(replaceName));
            QueryWrapper<Ptype> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().eq(Ptype::getPtypeid, ptype4Update.getPtypeid());
            this.baseMapper.update(ptype4Update, queryWrapper);
            size++;
        }
        return size;
    }

    /**
     * viewSearchStockPtypeOverall
     *
     * @param params
     * @return
     */
    @DS("multi-datasource-gwb")
    @Override
    public Page<ViewPtypeStockDto> viewSearchStockPtypeOverall(JSONObject params) {
        Integer pageNo = params.getInteger("pageNo");
        Integer pageSize = params.getInteger("pageSize");
        String saleDateBegin = MikkoDateUtils.getBefore7DaysBeginTime();
        String saleDateEnd = MikkoDateUtils.getNowTime();
        if (!Strings.isNullOrEmpty(params.getString("saleDateBegin"))) {
            saleDateBegin = params.getString("saleDateBegin");
        }
        if (!Strings.isNullOrEmpty(params.getString("saleDateEnd"))) {
            saleDateEnd = params.getString("saleDateEnd");
        }

        String viewDeleted = "";
        String viewStop = "";
        if (!Strings.isNullOrEmpty(params.getString("viewDeleted"))) {
            viewDeleted = params.getString("viewDeleted");
        }
        if (!Strings.isNullOrEmpty(params.getString("viewStop"))) {
            viewStop = params.getString("viewStop");
        }

        String insertSql = "";
        if (!Strings.isNullOrEmpty(params.getString("params"))) {
            String paramsStr = params.getJSONArray("params").toJSONString();
            List<String> paramsArray = JSONArray.parseArray(paramsStr, String.class);
            insertSql = this.sqlTemplateRet(paramsArray);
        }
        List<ViewPtypeStockDto> viewPtypeStockDtoList = this.baseMapper.viewSearchStockPtypeOverall(pageSize,
                pageNo * pageSize, saleDateBegin, saleDateEnd, viewDeleted, viewStop, insertSql);
        long count = this.baseMapper.countSearchStockPtypeOverall(viewDeleted, viewStop, insertSql);
        for (ViewPtypeStockDto item : viewPtypeStockDtoList) {
            if (item.getQty() == null) {
                item.setQty(0);
            }
        }
        Page<ViewPtypeStockDto> page = new Page<>();
        page.setRecords(viewPtypeStockDtoList);
        page.setPages(count / pageSize + (count % pageSize > 0 ? 1 : 0));
        page.setSize(pageSize);
        page.setCurrent(pageNo);
        page.setTotal(count);
        return page;
    }

    @DS("multi-datasource-gwb")
    @Override
    public ViewPtypeStockDto findViewPtypeOverallByPtypeId(String ptypeid) {
        ViewPtypeStockDto ptypeStockDto = this.baseMapper.findViewPtypeOverallByPtypeId(ptypeid);
        List<ViewVchDraftQtyOrder> draftQtyOrderList = this.baseMapper.viewVchDraftQtyOrderById(ptypeid);
        List<ViewVchDraftQtyOrder> dlySaleQtyOrderList = this.baseMapper.viewVchDlySaleQtyOrderByIdAndDate(
                MikkoDateUtils.getBefore7DaysBeginTime(), MikkoDateUtils.getNowTime(), ptypeid);
        System.out.println(MikkoDateUtils.getBefore7DaysBeginTime());
        System.out.println(MikkoDateUtils.getNowTime());
        Integer draftSum = 0;
        Integer saleSum = 0;
        for (ViewVchDraftQtyOrder draftQtyOrder : draftQtyOrderList) {
            if (EnumMethodType.PLUS.getCode().equals(EnumVchType.getMethodByCode(draftQtyOrder.getVchType()))) {
                draftSum += draftQtyOrder.getQty();
            } else if (EnumMethodType.REDUCE.getCode()
                    .equals(EnumVchType.getMethodByCode(draftQtyOrder.getVchType()))) {
                draftSum -= draftQtyOrder.getQty();
            } else {
                switch (draftQtyOrder.getUsedType()) {
                case USED_TYPE_REDUCE:
                    draftSum -= draftQtyOrder.getQty();
                    break;
                case USED_TYPE_PLUS:
                    draftSum += draftQtyOrder.getQty();
                    break;
                }
            }
        }

        for (ViewVchDraftQtyOrder saleQtyOrder : dlySaleQtyOrderList) {
            saleSum += saleQtyOrder.getQty();
        }

        ptypeStockDto.setDraftSum(draftSum);
        ptypeStockDto.setSaleSum(saleSum);
        return ptypeStockDto;
    }

    @DS("multi-datasource-gwb")
    @Override
    public List<ViewVchDraftQtyOrderDto> findViewVchDraftQtyOrderDtoListByPtypeId(String ptypeid) {
        List<ViewVchDraftQtyOrderDto> vchDraftQtyOrderDtoList = this.baseMapper
                .findViewVchDraftQtyOrderDtoListByPtypeId(ptypeid);
        for (ViewVchDraftQtyOrderDto viewVchDraftQtyOrderDto : vchDraftQtyOrderDtoList) {
            viewVchDraftQtyOrderDto.setVchTypeName(EnumVchType.getNameByCode(viewVchDraftQtyOrderDto.getVchType()));
            // 检测当前是入库单亦或者出库单
            viewVchDraftQtyOrderDto
                    .setUsedTypeName(EnumVchType.getPlusOrReduceName(viewVchDraftQtyOrderDto.getVchType()));
            // 检查是否拆装单
            if (EnumVchType.DISASSEMBLE.getCode().equals(viewVchDraftQtyOrderDto.getVchType())) {
                viewVchDraftQtyOrderDto
                        .setUsedTypeName(EnumVchType.getUsedTypeName(viewVchDraftQtyOrderDto.getUsedType()));
            }
        }
        return vchDraftQtyOrderDtoList;
    }

    @DS("multi-datasource-gwb")
    @Override
    public List<ViewVchSaleQtyOrderDto> findViewVchSaleQtyOrderDtoListByPtypeId(String ptypeid) {
        List<ViewVchDraftQtyOrder> draftQtyOrderList = this.baseMapper.viewVchDlySaleQtyOrderByIdAndDate(
                MikkoDateUtils.getBefore7DaysBeginTime(), MikkoDateUtils.getNowTime(), ptypeid);
        List<ViewVchSaleQtyOrderDto> saleQtyOrderDtoList = new ArrayList<>();
        for (ViewVchDraftQtyOrder draftQtyOrder : draftQtyOrderList) {
            ViewVchSaleQtyOrderDto saleQtyOrderDto = new ViewVchSaleQtyOrderDto();
            BeanUtils.copyProperties(draftQtyOrder, saleQtyOrderDto);
            saleQtyOrderDto.setVchTypeName(EnumVchType.getNameByCode(saleQtyOrderDto.getVchType()));
            // 检测当前是入库单亦或者出库单
            saleQtyOrderDto.setUsedTypeName(EnumVchType.getPlusOrReduceName(saleQtyOrderDto.getVchType()));
            // 检查是否拆装单
            if (EnumVchType.DISASSEMBLE.getCode().equals(saleQtyOrderDto.getVchType())) {
                saleQtyOrderDto.setUsedTypeName(EnumVchType.getUsedTypeName(saleQtyOrderDto.getUsedType()));
            }
            saleQtyOrderDtoList.add(saleQtyOrderDto);
        }
        return saleQtyOrderDtoList;
    }

    @DS("multi-datasource-gwb")
    @Override
    public Page<PtypeWareHouse> findWarehouseByNumRangeAndPage(JSONObject object) {
        if (null == object.getInteger("pageNo") || null == object.getInteger("pageSize")) {
            return new Page<>();
        }
        Integer pageNo = object.getInteger("pageNo");
        Integer pageSize = object.getInteger("pageSize");
        Integer lessThanQtyNum = 1;
        Integer biggerThanQtyNum = null;
        Integer pageNoMSize = pageNo * pageSize;
        if (null != object.getInteger("lessThanQtyNum")) {
            lessThanQtyNum = object.getInteger("lessThanQtyNum");
        }
        if (null != object.getInteger("biggerThanQtyNum")) {
            biggerThanQtyNum = object.getInteger("biggerThanQtyNum");
        }

        List<PtypeWareHouse> wareHouseList = this.baseMapper.findWarehouseByNumRange(pageNoMSize, pageSize,
                lessThanQtyNum, biggerThanQtyNum);
        Long count = this.baseMapper.countWarehouseByNumRange(lessThanQtyNum, biggerThanQtyNum);
        Page<PtypeWareHouse> wareHousePage = new Page<>();
        wareHousePage.setRecords(wareHouseList);
        wareHousePage.setTotal(count);
        wareHousePage.setCurrent(pageNo);
        wareHousePage.setSize(pageSize);
        wareHousePage.setPages(count / pageSize + (count % pageSize > 0 ? 1 : 0));
        return wareHousePage;
    }

    private String httpGetSyncMethodFromServer(String url, BufferedReader reader) throws IOException {
        log.info("发送URL:" + url);
        String builder = HTTPUtil.httpPostMethod(url, filterCheckPublicKey, "", reader);
        Map<String, Object> retMessageMap = XmlUtil.xmlToMap(builder);
        log.info("接收返回值：" + builder);
        if (!String.valueOf(true).equals(retMessageMap.get("response_success"))) {
            throw new JeecgBootException("错误，同步失败，获取数据错误");
        }
        if (Strings.isNullOrEmpty(retMessageMap.get("response_return_data").toString())) {
            throw new JeecgBootException("错误，数据为空且错误");
        }
        return retMessageMap.get("response_return_data").toString();
    }

    private String sqlTemplateRet(List<String> advancedSearch) {
        String sqlTemplate = " AND (P.pfullname LIKE '%#searchParam#%' OR P.pnamepy LIKE '%#searchParam#%' OR P.type LIKE '%#searchParam#%' OR P.pusercode LIKE '%#searchParam#%' OR P.ptypeid = '#searchParam#') ";
        StringBuilder joinSql = new StringBuilder();
        for (String param : advancedSearch) {
            if (!Strings.isNullOrEmpty(param)) {
                joinSql.append(sqlTemplate.replaceAll("#searchParam#", param));
            }
        }
        return joinSql.toString();
    }

}
