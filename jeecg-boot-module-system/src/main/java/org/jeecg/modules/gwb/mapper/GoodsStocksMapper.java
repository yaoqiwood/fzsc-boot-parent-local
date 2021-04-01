package org.jeecg.modules.gwb.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.gwb.entity.GoodsStocks;
import org.jeecg.modules.gwb.entity.dto.GoodsStockDto;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: 商品库存表
 * @Author: jeecg-boot
 * @Date: 2020-07-11
 * @Version: V1.0
 */
public interface GoodsStocksMapper extends BaseMapper<GoodsStocks> {

    /**
     * 搜索最大仓库单据更新值
     * @param maxVchCode
     * @return
     */
    List<GoodsStockDto> searchGoodsStocksUpdateInf(@Param("maxVchCode") Integer maxVchCode);

}
