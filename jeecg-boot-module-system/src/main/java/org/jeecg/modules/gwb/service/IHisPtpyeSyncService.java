package org.jeecg.modules.gwb.service;

import org.jeecg.modules.gwb.entity.HisPtpyeSync;

import com.baomidou.mybatisplus.extension.service.IService;

public interface IHisPtpyeSyncService extends IService<HisPtpyeSync> {

    Integer selectMaxUpdateTag();
}
