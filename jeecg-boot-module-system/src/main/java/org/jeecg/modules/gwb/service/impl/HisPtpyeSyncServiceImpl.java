package org.jeecg.modules.gwb.service.impl;

import org.jeecg.modules.gwb.entity.HisPtpyeSync;
import org.jeecg.modules.gwb.mapper.HisPtpyeSyncMapper;
import org.jeecg.modules.gwb.service.IHisPtpyeSyncService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @author yaoqi
 */

@Service
public class HisPtpyeSyncServiceImpl extends ServiceImpl<HisPtpyeSyncMapper, HisPtpyeSync>
        implements IHisPtpyeSyncService {

    /**
     * selectMaxUpdateTag
     * @return
     */
    @Override
    public Integer selectMaxUpdateTag() {
        return baseMapper.selectMaxUpdateTag();
    }
}
