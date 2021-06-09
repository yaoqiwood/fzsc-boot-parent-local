package org.jeecg.modules.gwb.service;

import org.jeecg.modules.gwb.entity.Dlyndx;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import meme.cat.basic.service.BaseService;

/**
* <p>
	*  服务类
	* </p>
*
* @author Mikko
* @since 2021-06-09
*/
public interface IDlyndxService extends BaseService<Dlyndx> {
    QueryWrapper<Dlyndx> buildWrapper(Dlyndx var);
}
