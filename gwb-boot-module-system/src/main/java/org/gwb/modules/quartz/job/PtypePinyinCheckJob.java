package org.gwb.modules.quartz.job;

import org.gwb.modules.gwb.service.IPtypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class PtypePinyinCheckJob {

    private IPtypeService ptypeService;

    @Autowired
    public void setPtypeService(IPtypeService ptypeService) {
        this.ptypeService = ptypeService;
    }

    @Scheduled(fixedRate = 1000 * 60 * 5)
    @Transactional(rollbackFor = Exception.class)
    void process() {
        try {
            this.ptypeService.changePtypeHZPinyinName2Xz();
        } catch (Exception e) {
            log.error("定时任务同步失败：", e);
        }
    }
}
