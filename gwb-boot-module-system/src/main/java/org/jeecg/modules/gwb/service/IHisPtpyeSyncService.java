package org.jeecg.modules.gwb.service;

import java.util.Date;
import java.util.Map;

import org.jeecg.modules.gwb.entity.HisPtpyeSync;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.IService;

@DS("master")
public interface IHisPtpyeSyncService extends IService<HisPtpyeSync> {

    Integer selectMaxUpdateTag();

    Date selectMaxUpdatePriceTime();

    HisPtpyeSync selectMaxUpdateTagRecord();

    HisPtpyeSync selectMaxUpdatePriceTimeRecord();

    Map<String, Object> initPtypeSyncPage();
}
