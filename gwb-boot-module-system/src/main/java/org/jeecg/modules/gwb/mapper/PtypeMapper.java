package org.jeecg.modules.gwb.mapper;

import java.util.List;

import org.jeecg.modules.gwb.entity.Ptype;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

public interface PtypeMapper extends BaseMapper<Ptype> {

    List<Ptype> findContainENamePtype();

}
