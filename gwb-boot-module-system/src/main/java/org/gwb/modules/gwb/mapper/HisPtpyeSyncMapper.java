package org.gwb.modules.gwb.mapper;

import java.util.Date;

import org.apache.ibatis.annotations.Select;
import org.gwb.modules.gwb.entity.HisPtpyeSync;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @author yaoqi
 */

@DS("master")
public interface HisPtpyeSyncMapper extends BaseMapper<HisPtpyeSync> {

    /**
     * 选取最大的 UpdateTag
     * @return
     */
    Integer selectMaxUpdateTag();

    /**
     * 选取最大的价格更新时间
     * @return
     */
    Date selectMaxUpdatePriceTime();

    @Select("SELECT * FROM his_ptpye_sync WHERE status = 'ptype' ORDER BY update_tag DESC LIMIT 1")
    HisPtpyeSync selectMaxUpdateTagRecord();

    @Select("SELECT * FROM his_ptpye_sync WHERE status = 'price' ORDER BY update_price_time DESC LIMIT 1")
    HisPtpyeSync selectMaxUpdatePriceTimeRecord();
}
