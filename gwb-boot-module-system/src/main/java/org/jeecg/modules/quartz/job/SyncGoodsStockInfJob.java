package org.jeecg.modules.quartz.job;

import java.io.IOException;

import org.jeecg.modules.gwb.entity.HisPtpyeSync;
import org.jeecg.modules.gwb.service.IGoodsStocksService;
import org.jeecg.modules.gwb.service.IHisPtpyeSyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class SyncGoodsStockInfJob {

    private IGoodsStocksService goodsStocksService;

    private IHisPtpyeSyncService hisPtpyeSyncService;

    @Autowired
    public void setGoodsStocksService(IGoodsStocksService goodsStocksService) {
        this.goodsStocksService = goodsStocksService;
    }

    @Autowired
    public void setHisPtpyeSyncService(IHisPtpyeSyncService hisPtpyeSyncService) {
        this.hisPtpyeSyncService = hisPtpyeSyncService;
    }

    // 第一位是秒 以此类推 频率改1分钟
    @Scheduled(fixedRate = 1000 * 60 * 1)
    @Transactional(rollbackFor = Exception.class)
    void process() {
        log.info("定时开始同步库存信息......");
        try {
            HisPtpyeSync hisPtpyeSync = this.goodsStocksService.syncGoodsStocksInfFromServer();
            this.hisPtpyeSyncService.save(hisPtpyeSync);
        } catch (IOException e) {
            log.error("定时同步任务失败", e);
        }
    }

}
