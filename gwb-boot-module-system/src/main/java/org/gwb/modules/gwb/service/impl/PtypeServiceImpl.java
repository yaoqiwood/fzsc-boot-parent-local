package org.gwb.modules.gwb.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.gwb.common.constant.MikkoConstants;
import org.gwb.common.enumerate.EnumMethodType;
import org.gwb.common.enumerate.EnumSyncPtypeStatus;
import org.gwb.common.enumerate.EnumVchType;
import org.gwb.common.exception.JeecgBootException;
import org.gwb.common.util.*;
import org.gwb.modules.gwb.entity.HisPtpyeSync;
import org.gwb.modules.gwb.entity.Ptype;
import org.gwb.modules.gwb.entity.XwBaseupdatetag;
import org.gwb.modules.gwb.entity.XwPtypeBarCode;
import org.gwb.modules.gwb.entity.dto.*;
import org.gwb.modules.gwb.mapper.PtypeMapper;
import org.gwb.modules.gwb.mapper.XwBaseupdatetagMapper;
import org.gwb.modules.gwb.service.IHisPtpyeSyncService;
import org.gwb.modules.gwb.service.IPtypeService;
import org.gwb.modules.gwb.service.IXwPtypeBarCodeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    private XwBaseupdatetagMapper baseupdatetagMapper;

    private IXwPtypeBarCodeService barCodeService;

    @Autowired
    public void setBaseupdatetagMapper(XwBaseupdatetagMapper baseupdatetagMapper) {
        this.baseupdatetagMapper = baseupdatetagMapper;
    }

    @Autowired
    public void setBarCodeService(IXwPtypeBarCodeService barCodeService) {
        this.barCodeService = barCodeService;
    }

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
        if (null != object.getInteger("lessThanQtyNum") && 0 != object.getInteger("lessThanQtyNum")) {
            lessThanQtyNum = object.getInteger("lessThanQtyNum");
        }
        if (null != object.getInteger("biggerThanQtyNum")) {
            biggerThanQtyNum = object.getInteger("biggerThanQtyNum");
        }

        List<PtypeWareHouse> wareHouseList = this.baseMapper.findWarehouseByNumRange(pageSize, pageNoMSize,
                lessThanQtyNum, biggerThanQtyNum);
        for (PtypeWareHouse item : wareHouseList) {
            item.setQty(item.getQty() != null ? item.getQty() : 0);
        }
        Long count = this.baseMapper.countWarehouseByNumRange(lessThanQtyNum, biggerThanQtyNum);
        Page<PtypeWareHouse> wareHousePage = new Page<>();
        wareHousePage.setRecords(wareHouseList);
        wareHousePage.setTotal(count);
        wareHousePage.setCurrent(pageNo);
        wareHousePage.setSize(pageSize);
        wareHousePage.setPages(count / pageSize + (count % pageSize > 0 ? 1 : 0));
        return wareHousePage;
    }

    @DS("multi-datasource-gwb")
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void changePtypeHZPinyinName2Xz() {
        log.info("行走错误拼音定时修改任务开始......");
        QueryWrapper<Ptype> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().like(Ptype::getPnamepy, "hz");
        queryWrapper.lambda().like(Ptype::getPfullname, "行走");
        queryWrapper.lambda().or(sql -> sql.like(Ptype::getPnamepy, "dh").like(Ptype::getPfullname, "弹簧"));
        queryWrapper.lambda().or(sql -> sql.like(Ptype::getPnamepy, "cc").like(Ptype::getPfullname, "刹车"));
        List<Ptype> ptypeList = this.list(queryWrapper);
        Integer sum = ptypeList.size();
        Integer count = 0;
        for (Ptype item : ptypeList) {
            if (item.getPnamepy().equals(PingYinUtil.getFirstSpell(item.getPfullname()))) {
                continue;
            }
            // 创建更新类（产品信息）
            Ptype ptype4Update = new Ptype();
            Integer newUpdateTag = this.baseupdatetagMapper.selectNewMaxUpdateTag();
            ptype4Update.setPnamepy(PingYinUtil.getFirstSpell(item.getPfullname()));
            QueryWrapper<Ptype> updateWrapper = new QueryWrapper<>();
            updateWrapper.lambda().eq(Ptype::getPtypeid, item.getPtypeid());
            // 创建更新类（更新最大id
            XwBaseupdatetag xwBaseupdatetag4Update = new XwBaseupdatetag();
            // updateTagId
            xwBaseupdatetag4Update.setUpdatetag(newUpdateTag);
            ptype4Update.setUpdatetag(newUpdateTag);
            QueryWrapper<XwBaseupdatetag> baseUpdateTagWrapper = new QueryWrapper<>();
            baseUpdateTagWrapper.lambda().eq(XwBaseupdatetag::getBasetype, MikkoConstants.TableType);
            this.baseupdatetagMapper.update(xwBaseupdatetag4Update, baseUpdateTagWrapper);
            this.baseMapper.update(ptype4Update, updateWrapper);
            log.info("更新UpdateTag为：" + newUpdateTag);
            count++;
        }
        log.info("行走错误拼音定时修改任务结束：" + MikkoDateUtils.getNowTime() + "\n 查询数: " + sum + "  更新数量:" + count);
    }

    @DS("multi-datasource-gwb")
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void modifyContainerInfoByPtypeId(String ptypeId, String containerNo, String pfullName) {
        Integer newUpdateTag = this.baseupdatetagMapper.selectNewMaxUpdateTag();
        QueryWrapper<Ptype> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Ptype::getPtypeid, ptypeId);
        Ptype ptype4Update = new Ptype();
        XwPtypeBarCode xwPtypeBarCode4Update = new XwPtypeBarCode();
        // 查找当前是否有一个数量大于1的barcode记录
        xwPtypeBarCode4Update.setPTypeId(ptypeId);
        xwPtypeBarCode4Update.setBarCode(containerNo);
        int barCount = this.barCodeService
                .count(new QueryWrapper<XwPtypeBarCode>().lambda().eq(XwPtypeBarCode::getPTypeId, ptypeId));
        if (barCount > 0) {
            this.barCodeService.updateById(xwPtypeBarCode4Update);
        } else {
            xwPtypeBarCode4Update.setUnitID("0");
            xwPtypeBarCode4Update.setOrdid("0");
            xwPtypeBarCode4Update.setKeyid(SnowflakeUtil.getNum().toString());
            this.barCodeService.save(xwPtypeBarCode4Update);
        }
        XwBaseupdatetag xwBaseupdatetag4Update = new XwBaseupdatetag();
        // ptype4Update.setPusercode(containerNo);
        ptype4Update.setPfullname(pfullName);
        ptype4Update.setUpdatetag(newUpdateTag);
        xwBaseupdatetag4Update.setUpdatetag(newUpdateTag);
        QueryWrapper<XwBaseupdatetag> updateTagWrapper = new QueryWrapper<>();
        updateTagWrapper.lambda().eq(XwBaseupdatetag::getBasetype, MikkoConstants.TableType);
        this.baseupdatetagMapper.update(xwBaseupdatetag4Update, updateTagWrapper);
        this.baseMapper.update(ptype4Update, queryWrapper);
    }

    /**
     * 批量修改货柜号
     * @param ptypeIdArray
     * @param containerNo
     */
    @DS("multi-datasource-gwb")
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void batchModifyContainerInfoByPtypeId(List<String> ptypeIdArray, String containerNo) {
        // maxTagId + 1
        Integer newUpdateTag = this.baseupdatetagMapper.selectNewMaxUpdateTag();
        // baseBarcode
        // 遍历ptypeId List
        for (String ptypeId : ptypeIdArray) {
            XwPtypeBarCode ptypeBarCode4Update = new XwPtypeBarCode();
            // 准备更新PtypeId 对应ptype表
            Ptype ptype4Update = new Ptype();
            ptype4Update.setPtypeid(ptypeId);
            ptype4Update.setUpdatetag(newUpdateTag);

            // 准备更新barcode表对应 ptypeid 的货柜No
            ptypeBarCode4Update.setPTypeId(ptypeId);
            ptypeBarCode4Update.setBarCode(containerNo);

            // 查询barcode表对应id是否有一个或没有的记录 有则更新 无则插入
            int barcodeCount = this.barCodeService
                    .count(new QueryWrapper<XwPtypeBarCode>().lambda().eq(XwPtypeBarCode::getPTypeId, ptypeId));
            if (barcodeCount > 0) {
                this.barCodeService.updateById(ptypeBarCode4Update);
            } else {
                ptypeBarCode4Update.setUnitID("0");
                ptypeBarCode4Update.setOrdid("0");
                ptypeBarCode4Update.setKeyid(SnowflakeUtil.getNum().toString());
                this.barCodeService.save(ptypeBarCode4Update);
            }
            // 更新ptype对应记录信息（maxUpdateTag)
            this.baseMapper.update(ptype4Update, new QueryWrapper<Ptype>().lambda().eq(Ptype::getPtypeid, ptypeId));
        }
        // 更新基本tag表信息
        XwBaseupdatetag xwBaseupdatetag4Update = new XwBaseupdatetag();
        xwBaseupdatetag4Update.setUpdatetag(newUpdateTag);
        QueryWrapper<XwBaseupdatetag> updateTagWrapper = new QueryWrapper<>();
        updateTagWrapper.lambda().eq(XwBaseupdatetag::getBasetype, MikkoConstants.TableType);
        this.baseupdatetagMapper.update(xwBaseupdatetag4Update, updateTagWrapper);
    }

    @DS("multi-datasource-gwb")
    @Transactional(rollbackFor = Exception.class)
    @Override
    public ViewPtypeInf2ModifyDto findPtypeById(String ptypeId) {
        return this.baseMapper.viewPtypeInf2Modify(ptypeId);
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
