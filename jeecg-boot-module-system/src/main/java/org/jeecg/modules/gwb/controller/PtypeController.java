package org.jeecg.modules.gwb.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.jeecg.common.api.vo.Result;
import org.jeecg.common.util.DateUtils;
import org.jeecg.modules.gwb.entity.HisPtpyeSync;
import org.jeecg.modules.gwb.service.IHisPtpyeSyncService;
import org.jeecg.modules.gwb.service.IPtypeService;
import org.jeecg.modules.gwb.service.IXwPPtypePriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/ptype")
public class PtypeController {

    @Autowired
    private IPtypeService ptypeService;

    @Autowired
    private IXwPPtypePriceService xwPPtypePriceService;

    @Autowired
    private IHisPtpyeSyncService hisPtpyeSyncService;

    /**
     * 同步商品信息
     * @return
     */
    @PostMapping(value = "syncSendPtypeInfData2Server")
    public Result<?> syncSendPtypeInfData2Server() { // @RequestParam("updateTag") Integer updateTag
        try {
            Integer updateTag = hisPtpyeSyncService.selectMaxUpdateTag();
            HisPtpyeSync hisPtpyeSync = ptypeService.syncSendPtypeInfData2Server();
            hisPtpyeSyncService.save(hisPtpyeSync);
            return Result.ok("同步商品信息成功");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return Result.error(e.getMessage());
        }
    }

    /**
     * 同步价格信息
     * @param updateDateStr
     * @return
     */
    @PostMapping(value = "syncPPtypePriceInfData2Server")
    public Result<?> syncPPtypePriceInfData2Server(@RequestParam("updateDate") String updateDateStr) {
        try {
            Date updateDate = DateUtils.str2Date(updateDateStr, new SimpleDateFormat("YYYY-MM-DD HH:MM:SS"));
            xwPPtypePriceService.syncPPtypePriceInfData2Server(updateDate);
            return Result.ok("同步价格信息成功");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return Result.error("同步价格信息失败");
        }
    }
}
