package org.gwb.modules.quartz.job;

import java.io.IOException;
import java.text.ParseException;

import org.gwb.common.util.DateUtils;
import org.gwb.modules.gwb.entity.HisPtpyeSync;
import org.gwb.modules.gwb.service.IHisPtpyeSyncService;
import org.gwb.modules.gwb.service.IXwPPtypePriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class SyncPtypePriceInfJob {

    @Autowired
    private IXwPPtypePriceService pPtypePriceService;

    @Autowired
    private IHisPtpyeSyncService hisPtpyeSyncService;

    // 第一位是秒 以此类推
    @Scheduled(fixedRate = 1000 * 60 * 1)
    @Transactional(rollbackFor = Exception.class)
    void process() {
        log.info("定时更新商品价格任务开始执行，现在时间为：" + DateUtils.now());
        try {
            HisPtpyeSync hisPtpyeSync = pPtypePriceService.syncPPtypePriceInfData2Server();
            hisPtpyeSyncService.save(hisPtpyeSync);
        } catch (IOException e) {
            log.error("执行失败,原因：" + e.getMessage());
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
