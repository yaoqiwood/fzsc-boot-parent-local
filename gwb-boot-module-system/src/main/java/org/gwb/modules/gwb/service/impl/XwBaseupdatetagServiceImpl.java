package org.gwb.modules.gwb.service.impl;

import org.gwb.modules.gwb.entity.XwBaseupdatetag;
import org.gwb.modules.gwb.mapper.XwBaseupdatetagMapper;
import org.gwb.modules.gwb.service.IXwBaseupdatetagService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
* <p>
	*  服务实现类
	* </p>
*
* @author Mikko
* @since 2021-06-24
*/
@Service
public class XwBaseupdatetagServiceImpl extends ServiceImpl<XwBaseupdatetagMapper, XwBaseupdatetag>
        implements IXwBaseupdatetagService {
    public QueryWrapper<XwBaseupdatetag> buildWrapper(XwBaseupdatetag var) {
        return null;
    }
}
