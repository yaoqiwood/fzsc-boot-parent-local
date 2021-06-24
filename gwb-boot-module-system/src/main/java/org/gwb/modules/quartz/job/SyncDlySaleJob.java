package org.gwb.modules.quartz.job;

import java.io.IOException;

import org.gwb.modules.gwb.entity.HisPtpyeSync;
import org.gwb.modules.gwb.service.IDlySaleService;
import org.gwb.modules.gwb.service.IHisPtpyeSyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class SyncDlySaleJob {

    private IDlySaleService dlySaleService;

    private IHisPtpyeSyncService hisPtpyeSyncService;

    @Autowired
    public void setDlySaleService(IDlySaleService dlySaleService) {
        this.dlySaleService = dlySaleService;
    }

    @Autowired
    public void setHisPtpyeSyncService(IHisPtpyeSyncService hisPtpyeSyncService) {
        this.hisPtpyeSyncService = hisPtpyeSyncService;
    }

    // 第一位是秒 以此类推 频率改1分钟
    @Scheduled(fixedRate = 1000 * 60 * 1)
    @Transactional(rollbackFor = Exception.class)
    void process() {
        try {
            HisPtpyeSync hisPtpyeSync = dlySaleService.syncDlySaleInfFromServer();
            this.hisPtpyeSyncService.save(hisPtpyeSync);
        } catch (IOException e) {
            log.error("定时任务同步失败：", e);
        }
    }

}
