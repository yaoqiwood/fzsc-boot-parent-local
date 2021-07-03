package org.gwb.modules.gwb.mapper;

import org.gwb.modules.gwb.entity.XwBaseupdatetag;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Mikko
 * @since 2021-06-24
 */
public interface XwBaseupdatetagMapper extends BaseMapper<XwBaseupdatetag> {

    /**
     * selectNewMaxUpdateTag
     * @return
     */
    Integer selectNewMaxUpdateTag();
}
