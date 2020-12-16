package org.jeecg.modules.gwb.service.impl;

import java.util.Date;
import java.util.List;

import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.modules.gwb.entity.XwPPtypePrice;
import org.jeecg.modules.gwb.mapper.XwPPtypePriceMapper;
import org.jeecg.modules.gwb.service.IXwPPtypePriceService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

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
    public void syncPPtypePriceInfData2Server(Date updateDate) {
        QueryWrapper<XwPPtypePrice> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().gt(XwPPtypePrice::getUpdatedt, updateDate);
        List<XwPPtypePrice> xwPPtypePriceList = list(queryWrapper);
        if (xwPPtypePriceList.size() == 0) {
            throw new JeecgBootException("没有检测到新的商品价格信息更新");
        }
        StringBuilder sendSBData = new StringBuilder(JSONArray.toJSONString(xwPPtypePriceList));
        log.info("查询数据封装完毕\n数据为：" + sendSBData.toString());

    }
}
