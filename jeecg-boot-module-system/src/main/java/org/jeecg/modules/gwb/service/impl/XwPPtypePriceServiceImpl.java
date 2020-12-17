package org.jeecg.modules.gwb.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.common.util.DateUtils;
import org.jeecg.common.util.HTTPUtil;
import org.jeecg.common.util.HttpXmlBuilderUtil;
import org.jeecg.modules.gwb.entity.XwPPtypePrice;
import org.jeecg.modules.gwb.mapper.XwPPtypePriceMapper;
import org.jeecg.modules.gwb.service.IXwPPtypePriceService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

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

    /**
     * 同步价格信息
     * @param updateDate
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void syncPPtypePriceInfData2Server(Date updateDate) throws IOException {
        QueryWrapper<XwPPtypePrice> queryWrapper = new QueryWrapper<>();
        String strDate = DateUtils.date2Str(updateDate, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        queryWrapper.lambda().gt(XwPPtypePrice::getUpdatedt, strDate);
        List<XwPPtypePrice> xwPPtypePriceList = list(queryWrapper);
        if (xwPPtypePriceList.size() == 0) {
            throw new JeecgBootException("没有检测到新的商品价格信息更新");
        }
        StringBuilder sendSBData = new StringBuilder(JSONArray.toJSONString(xwPPtypePriceList));
        log.info("查询数据封装完毕\n数据为：" + sendSBData.toString());
        StringBuilder sendStr = HttpXmlBuilderUtil.buildSendDataBuilder(sendSBData, new Date());

        BufferedReader reader = null;
        try {
            String builder = HTTPUtil.httpPostMethod(postDataUrl, filterCheckPublicKey, sendStr.toString(), reader);
            log.info("接收返回值:" + builder);
            Map<String, Object> retMessageMap = XmlUtil.xmlToMap(builder.toString());
            if (!String.valueOf(true).equals(retMessageMap.get("success"))) {
                throw new JeecgBootException("错误，同步不成功");
            }
            // TODO 同步信息记录历史
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
    }
}
