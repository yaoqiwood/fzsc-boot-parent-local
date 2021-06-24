package org.gwb.modules.gwb.service;

import java.io.IOException;

import org.gwb.modules.gwb.entity.GoodsStocks;
import org.gwb.modules.gwb.entity.HisPtpyeSync;

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

    /**
     * 同步库存信息
     * @return
     * @throws IOException
     */
    HisPtpyeSync syncGoodsStocksInfFromServer() throws IOException;

}
