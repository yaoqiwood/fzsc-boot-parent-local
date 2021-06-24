package org.gwb.modules.gwb.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.gwb.modules.gwb.entity.TGoodsStocksGlide;

/**
 * @Description: 库存记录表
 */
public interface TGoodsStocksGlideMapper extends BaseMapper<TGoodsStocksGlide> {
    /**
     * 远程选取最大GoodsId
     * @return
     */
    Integer remoteSearchMaxGoodsId();
}
