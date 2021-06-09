package org.jeecg.modules.gwb.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.jeecg.common.enumerate.EnumSyncPtypeStatus;
import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.common.util.HTTPUtil;
import org.jeecg.common.util.HttpXmlBuilderUtil;
import org.jeecg.common.util.SnowflakeUtil;
import org.jeecg.modules.gwb.entity.GoodsStocks;
import org.jeecg.modules.gwb.entity.HisPtpyeSync;
import org.jeecg.modules.gwb.entity.dto.GoodsStockDto;
import org.jeecg.modules.gwb.mapper.GoodsStocksMapper;
import org.jeecg.modules.gwb.service.IGoodsStocksService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import cn.hutool.core.util.XmlUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @Description: 商品库存表
 * @Date:   2020-07-11
 */
@Service
@Slf4j
public class GoodsStocksServiceImpl extends ServiceImpl<GoodsStocksMapper, GoodsStocks> implements IGoodsStocksService {

    @Value("${ptype.syncGoodsStocksFromLocalUrl}")
    private String syncGoodsStocksFromLocalUrl;

    @Value("${ptype.filter.check.public.key}")
    private String filterCheckPublicKey;

    @Value("${ptype.remoteSearchMaxSaveTimeUrl}")
    private String remoteSearchMaxSaveTimeUrl;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public HisPtpyeSync syncGoodsStocksInfFromServer() throws IOException {
        BufferedReader reader = null;
        try {
            log.info("查询最大库存更新ID开始......URL:\n" + remoteSearchMaxSaveTimeUrl);
            String maxSaveDate = HttpXmlBuilderUtil.httpGetSyncMethodFromServer(remoteSearchMaxSaveTimeUrl, reader,
                    filterCheckPublicKey);
            List<GoodsStockDto> goodsStockDtoList = this.baseMapper.searchGoodsStocksUpdateInf(maxSaveDate);

            if (goodsStockDtoList.size() == 0) {
                throw new JeecgBootException("没有检测到新的库存信息更新");
            }

            // 数据重新封装
            List<GoodsStocks> goodsStocksList = new ArrayList<>();
            goodsStockDtoList.forEach(item -> {
                GoodsStocks temp = new GoodsStocks();
                temp.setPtypeid(item.getPtypeid());
                temp.setQty(item.getQty());
                temp.setPrice(item.getGPrice());
                temp.setTotal(item.getGTotal());
                temp.setVchcode(item.getVchcode());
                temp.setSaveTime(item.getSavedate());
                goodsStocksList.add(temp);
            });
            StringBuilder sendSBData = new StringBuilder(JSONArray.toJSONString(goodsStocksList));
            log.info("查询数据封装完毕\n");
            StringBuilder sendStr = HttpXmlBuilderUtil.buildSendDataBuilder(sendSBData, new Date());

            String builder = HTTPUtil.httpPostMethod(syncGoodsStocksFromLocalUrl, filterCheckPublicKey,
                    sendStr.toString(), reader);
            log.info("接收返回值:" + builder);
            Map<String, Object> retMessageMap = XmlUtil.xmlToMap(builder.toString());
            if (!String.valueOf(true).equals(retMessageMap.get("response_success"))) {
                throw new JeecgBootException("错误，库存信息同步不成功");
            }
            HisPtpyeSync hisPtpyeSync = new HisPtpyeSync();
            hisPtpyeSync.setHpsId(SnowflakeUtil.getNum().toString());
            hisPtpyeSync.setStatus(EnumSyncPtypeStatus.T_GOODS_STOCKS.getCode());
            hisPtpyeSync.setAffectNum(goodsStockDtoList.size());
            hisPtpyeSync.setCreateTime(new Date());
            return hisPtpyeSync;
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
    }
}
