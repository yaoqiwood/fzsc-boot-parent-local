package org.gwb.modules.gwb.service;

import java.io.IOException;

import org.gwb.modules.gwb.entity.TGoodsStocksGlide;

import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author yaoqi
 * @Description: 库存记录表
 */
public interface ITGoodsStocksGlideService extends IService<TGoodsStocksGlide> {

    /**
     * 同步库存信息
     * @return
     * @throws IOException
     */
    // HisPtpyeSync syncTGoodsStocksInfFromServer() throws IOException;
}
