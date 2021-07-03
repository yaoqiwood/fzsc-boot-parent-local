package org.gwb.modules.gwb.service;

import org.gwb.modules.gwb.entity.XwBaseupdatetag;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import meme.cat.basic.service.BaseService;

/**
* <p>
	*  服务类
	* </p>
*
* @author Mikko
* @since 2021-06-24
*/
public interface IXwBaseupdatetagService extends BaseService<XwBaseupdatetag> {
    QueryWrapper<XwBaseupdatetag> buildWrapper(XwBaseupdatetag var);
}
