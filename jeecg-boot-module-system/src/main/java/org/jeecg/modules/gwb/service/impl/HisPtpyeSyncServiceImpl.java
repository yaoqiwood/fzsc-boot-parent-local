package org.jeecg.modules.gwb.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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

    @Override
    public Date selectMaxUpdatePriceTime() {
        return baseMapper.selectMaxUpdatePriceTime();
    }

    @Override
    public HisPtpyeSync selectMaxUpdateTagRecord() {
        return baseMapper.selectMaxUpdateTagRecord();
    }

    @Override
    public HisPtpyeSync selectMaxUpdatePriceTimeRecord() {
        return baseMapper.selectMaxUpdatePriceTimeRecord();
    }

    @Override
    public Map<String, Object> initPtypeSyncPage() {
        Map<String, Object> map = new HashMap<>(16);
        map.put("maxUpdateTagRecord", selectMaxUpdateTagRecord());
        map.put("maxUpdatePriceTimeRecord", selectMaxUpdatePriceTimeRecord());
        return map;
    }
}
