package org.jeecg.modules.quartz.job;

import java.io.IOException;

import org.jeecg.common.util.DateUtils;
import org.jeecg.modules.gwb.entity.HisPtpyeSync;
import org.jeecg.modules.gwb.service.IHisPtpyeSyncService;
import org.jeecg.modules.gwb.service.IPtypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class SyncPtypeInfJob {

    @Autowired
    private IPtypeService ptypeService;

    @Autowired
    private IHisPtpyeSyncService hisPtpyeSyncService;

    // 第一位是秒 以此类推
    @Scheduled(fixedRate = 1000 * 60 * 10)
    @Transactional(rollbackFor = Exception.class)
    void process() {
        log.info("定时开始同步商品信息任务执行，现在时间为：" + DateUtils.now() + "\n");
        try {
            HisPtpyeSync hisPtpyeSync = ptypeService.syncSendPtypeInfData2Server();
            hisPtpyeSyncService.save(hisPtpyeSync);
        } catch (IOException e) {
            log.error("执行失败,原因：" + e.getMessage());
        }
    }
}
