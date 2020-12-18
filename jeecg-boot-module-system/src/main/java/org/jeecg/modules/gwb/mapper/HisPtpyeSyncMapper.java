package org.jeecg.modules.gwb.mapper;

import org.jeecg.modules.gwb.entity.HisPtpyeSync;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @author yaoqi
 */

@DS("master")
public interface HisPtpyeSyncMapper extends BaseMapper<HisPtpyeSync> {

    Integer selectMaxUpdateTag();
}
