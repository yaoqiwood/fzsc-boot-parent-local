package org.jeecg.modules.gwb.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.jeecg.common.enumerate.EnumSyncPtypeStatus;
import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.common.util.HTTPUtil;
import org.jeecg.common.util.HttpXmlBuilderUtil;
import org.jeecg.common.util.PingYinUtil;
import org.jeecg.common.util.SnowflakeUtil;
import org.jeecg.modules.gwb.entity.HisPtpyeSync;
import org.jeecg.modules.gwb.entity.Ptype;
import org.jeecg.modules.gwb.mapper.PtypeMapper;
import org.jeecg.modules.gwb.service.IHisPtpyeSyncService;
import org.jeecg.modules.gwb.service.IPtypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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

    // @DS("master")
    // private void saveHisInf(HisPtpyeSync hisPtpyeSync4Save) {
    // hisPtpyeSyncService.save(hisPtpyeSync4Save);
    // }
}
