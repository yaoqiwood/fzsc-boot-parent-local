package org.gwb.modules.gwb.service;

import org.gwb.modules.gwb.entity.Bakdly;

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
public interface IBakdlyService extends BaseService<Bakdly> {
    QueryWrapper<Bakdly> buildWrapper(Bakdly var);
}
