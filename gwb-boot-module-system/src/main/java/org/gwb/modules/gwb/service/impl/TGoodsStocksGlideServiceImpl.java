package org.gwb.modules.gwb.service.impl;

import org.gwb.modules.gwb.entity.TGoodsStocksGlide;
import org.gwb.modules.gwb.mapper.TGoodsStocksGlideMapper;
import org.gwb.modules.gwb.service.ITGoodsStocksGlideService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import lombok.extern.slf4j.Slf4j;

/**
 * @author yaoqi
 * @Description: 库存记录表
 */
@Service
@Slf4j
public class TGoodsStocksGlideServiceImpl extends ServiceImpl<TGoodsStocksGlideMapper, TGoodsStocksGlide>
        implements ITGoodsStocksGlideService {

    // @Value("${ptype.syncPostStocksDataUrl}")
    // private String syncPostStocksDataUrl;
    //
    // @Value("${ptype.filter.check.public.key}")
    // private String filterCheckPublicKey;
    //
    // @Value("${ptype.remoteSearchMaxGoodsIdFromServerUrl}")
    // private String remoteSearchMaxGoodsIdFromServerUrl;

    // @Override
    // @Transactional(rollbackFor = Exception.class)
    // public HisPtpyeSync syncTGoodsStocksInfFromServer() throws IOException {
    // BufferedReader reader = null;
    // try {
    // log.info("查询最大库存更新ID开始......URL:\n" + remoteSearchMaxGoodsIdFromServerUrl);
    // String maxIndex =
    // HttpXmlBuilderUtil.httpGetSyncMethodFromServer(remoteSearchMaxGoodsIdFromServerUrl,
    // reader, filterCheckPublicKey);
    // QueryWrapper<TGoodsStocksGlide> queryWrapper = new QueryWrapper<>();
    // // String strDate = DateUtils.date2Str(updateDate, new
    // // SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
    // queryWrapper.lambda().gt(TGoodsStocksGlide::getGoodsid, maxIndex);
    // List<TGoodsStocksGlide> tGoodsStocksGlideList = list(queryWrapper);
    // if (tGoodsStocksGlideList.size() == 0) {
    // throw new JeecgBootException("没有检测到新的库存信息更新");
    // }
    // StringBuilder sendSBData = new
    // StringBuilder(JSONArray.toJSONString(tGoodsStocksGlideList));
    // log.info("查询数据封装完毕\n");
    // StringBuilder sendStr = HttpXmlBuilderUtil.buildSendDataBuilder(sendSBData,
    // new Date());
    //
    // String builder = HTTPUtil.httpPostMethod(syncPostStocksDataUrl,
    // filterCheckPublicKey, sendStr.toString(),
    // reader);
    // log.info("接收返回值:" + builder);
    // Map<String, Object> retMessageMap = XmlUtil.xmlToMap(builder.toString());
    // if (!String.valueOf(true).equals(retMessageMap.get("response_success"))) {
    // throw new JeecgBootException("错误，同步不成功");
    // }
    // HisPtpyeSync hisPtpyeSync = new HisPtpyeSync();
    // hisPtpyeSync.setHpsId(SnowflakeUtil.getNum().toString());
    // hisPtpyeSync.setStatus(EnumSyncPtypeStatus.T_GOODS_STOCKS.getCode());
    // hisPtpyeSync.setAffectNum(tGoodsStocksGlideList.size());
    // hisPtpyeSync.setCreateTime(new Date());
    // return hisPtpyeSync;
    // } finally {
    // if (reader != null) {
    // reader.close();
    // }
    // }
    // }
}
