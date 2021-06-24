package org.gwb.modules.gwb.service;

import java.util.Date;
import java.util.Map;

import org.gwb.modules.gwb.entity.HisPtpyeSync;

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
