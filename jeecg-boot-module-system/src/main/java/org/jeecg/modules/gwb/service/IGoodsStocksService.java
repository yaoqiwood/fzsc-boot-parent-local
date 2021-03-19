package org.jeecg.modules.gwb.service;

import org.jeecg.modules.gwb.entity.GoodsStocks;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Description: 商品库存表
 * @Author: jeecg-boot
 * @Date:   2020-07-11
 * @Version: V1.0
 */

@DS("multi-datasource-gwb")
public interface IGoodsStocksService extends IService<GoodsStocks> {

}
