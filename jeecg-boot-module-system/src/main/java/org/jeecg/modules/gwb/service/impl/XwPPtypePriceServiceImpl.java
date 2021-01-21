package org.jeecg.modules.gwb.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.jeecg.common.enumerate.EnumSyncPtypeStatus;
import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.common.util.DateUtils;
import org.jeecg.common.util.HTTPUtil;
import org.jeecg.common.util.HttpXmlBuilderUtil;
import org.jeecg.common.util.SnowflakeUtil;
import org.jeecg.modules.gwb.entity.HisPtpyeSync;
import org.jeecg.modules.gwb.entity.XwPPtypePrice;
import org.jeecg.modules.gwb.mapper.XwPPtypePriceMapper;
import org.jeecg.modules.gwb.service.IHisPtpyeSyncService;
import org.jeecg.modules.gwb.service.IXwPPtypePriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.base.Strings;

import cn.hutool.core.util.XmlUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class XwPPtypePriceServiceImpl extends ServiceImpl<XwPPtypePriceMapper, XwPPtypePrice>
        implements IXwPPtypePriceService {

    @Value("${ptype.pricePostDataUrl}")
    private String postDataUrl;

    @Value("${ptype.filter.check.public.key}")
    private String filterCheckPublicKey;

    @Value("${ptype.getSyncMaxUpdateDateFromServerUrl}")
    private String getSyncMaxUpdateDateFromServerUrl;

    @Autowired
    private IHisPtpyeSyncService hisPtpyeSyncService;

    /**
     * 同步价格信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public HisPtpyeSync syncPPtypePriceInfData2Server() throws IOException {
        BufferedReader reader = null;
        try {
            log.info("查询最大更新日期开始......");
            String strDate = httpGetSyncMethodFromServer(getSyncMaxUpdateDateFromServerUrl, reader);
            QueryWrapper<XwPPtypePrice> queryWrapper = new QueryWrapper<>();
            Date updateDate = DateUtils.parseDate(strDate, "yyyy-MM-dd HH:mm:ss");
            log.info("最大更新日期为：" + DateUtils.parseDate(strDate, "yyyy-MM-dd HH:mm:ss"));
            // String strDate = DateUtils.date2Str(updateDate, new
            // SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
            queryWrapper.lambda().gt(XwPPtypePrice::getUpdatedt, strDate);
            List<XwPPtypePrice> xwPPtypePriceList = list(queryWrapper);
            if (xwPPtypePriceList.size() == 0) {
                throw new JeecgBootException("没有检测到新的商品价格信息更新");
            }
            StringBuilder sendSBData = new StringBuilder(JSONArray.toJSONString(xwPPtypePriceList));
            log.info("查询数据封装完毕\n");
            StringBuilder sendStr = HttpXmlBuilderUtil.buildSendDataBuilder(sendSBData, new Date());

            String builder = HTTPUtil.httpPostMethod(postDataUrl, filterCheckPublicKey, sendStr.toString(), reader);
            log.info("接收返回值:" + builder);
            Map<String, Object> retMessageMap = XmlUtil.xmlToMap(builder.toString());
            if (!String.valueOf(true).equals(retMessageMap.get("response_success"))) {
                throw new JeecgBootException("错误，同步不成功");
            }
            HisPtpyeSync hisPtpyeSync = new HisPtpyeSync();
            hisPtpyeSync.setHpsId(SnowflakeUtil.getNum().toString());
            hisPtpyeSync.setStatus(EnumSyncPtypeStatus.PRICE.getCode());
            hisPtpyeSync.setUpdatePriceTime(updateDate);
            hisPtpyeSync.setAffectNum(xwPPtypePriceList.size());
            // hisPtpyeSync.setRemoteHost(postDataUrl);
            hisPtpyeSync.setCreateTime(new Date());
            return hisPtpyeSync;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
        return null;
    }

    private String httpGetSyncMethodFromServer(String url, BufferedReader reader) throws IOException {
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
}
