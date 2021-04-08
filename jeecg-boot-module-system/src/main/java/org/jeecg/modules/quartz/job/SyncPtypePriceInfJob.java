package org.jeecg.modules.quartz.job;

import java.io.IOException;
import java.text.ParseException;

import org.jeecg.common.util.DateUtils;
import org.jeecg.modules.gwb.entity.HisPtpyeSync;
import org.jeecg.modules.gwb.service.IHisPtpyeSyncService;
import org.jeecg.modules.gwb.service.IXwPPtypePriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

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
    private void process() {
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
