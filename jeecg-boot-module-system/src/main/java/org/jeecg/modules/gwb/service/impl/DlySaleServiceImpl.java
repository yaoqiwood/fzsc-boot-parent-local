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
import org.jeecg.common.util.SnowflakeUtil;
import org.jeecg.modules.gwb.entity.DlySale;
import org.jeecg.modules.gwb.entity.HisPtpyeSync;
import org.jeecg.modules.gwb.mapper.DlySaleMapper;
import org.jeecg.modules.gwb.service.IDlySaleService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import cn.hutool.core.util.XmlUtil;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DlySaleServiceImpl extends ServiceImpl<DlySaleMapper, DlySale> implements IDlySaleService {

    @Value("${ptype.remoteSearchMaxDlySaleVchcodeUrl}")
    private String remoteSearchMaxDlySaleVchcodeUrl;

    @Value("${ptype.syncDlySaleFromLocalUrl}")
    private String syncDlySaleFromLocalUrl;

    @Value("${ptype.filter.check.public.key}")
    private String filterCheckPublicKey;

    @DS("multi-datasource-gwb")
    @Override
    public HisPtpyeSync syncDlySaleInfFromServer() throws IOException {
        BufferedReader reader = null;
        try {
            log.info("销量表获取服务器最大Vchcode开始......URL:\n" + remoteSearchMaxDlySaleVchcodeUrl);
            String maxVchcode = HttpXmlBuilderUtil.httpGetSyncMethodFromServer(remoteSearchMaxDlySaleVchcodeUrl, reader,
                    filterCheckPublicKey);
            QueryWrapper<DlySale> dlySaleQueryWrapper = new QueryWrapper<>();
            dlySaleQueryWrapper.lambda().gt(DlySale::getVchcode, maxVchcode);
            List<DlySale> dlySaleList = this.list(dlySaleQueryWrapper);
            if (dlySaleList.size() == 0) {
                throw new JeecgBootException("没有检测到新的销量表更新信息......");
            }
            StringBuilder sb = new StringBuilder(JSONArray.toJSONString(dlySaleList));
            StringBuilder sendStr = HttpXmlBuilderUtil.buildSendDataBuilder(sb, new Date());
            log.info("查询数据封装完毕\n正在同步信息，URL:\n" + syncDlySaleFromLocalUrl);
            String builder = HTTPUtil.httpPostMethod(syncDlySaleFromLocalUrl, filterCheckPublicKey, sendStr.toString(),
                    reader);
            log.info("接受返回值:" + builder);
            Map<String, Object> retMessageMap = XmlUtil.xmlToMap(builder.toString());
            if (!String.valueOf(true).equals(retMessageMap.get("response_success"))) {
                throw new JeecgBootException("错误，销售表同步不成功");
            }
            HisPtpyeSync hisPtpyeSync = new HisPtpyeSync();
            hisPtpyeSync.setHpsId(SnowflakeUtil.getNum().toString());
            hisPtpyeSync.setStatus(EnumSyncPtypeStatus.T_GOODS_STOCKS.getCode());
            hisPtpyeSync.setAffectNum(dlySaleList.size());
            hisPtpyeSync.setCreateTime(new Date());
            return hisPtpyeSync;
        } finally {
            if (reader != null) {
                reader.close();
            }
        }

    }
}
