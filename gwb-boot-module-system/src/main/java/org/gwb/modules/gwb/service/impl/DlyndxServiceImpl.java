package org.gwb.modules.gwb.service.impl;

import org.gwb.modules.gwb.entity.Dlyndx;
import org.gwb.modules.gwb.mapper.DlyndxMapper;
import org.gwb.modules.gwb.service.IDlyndxService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
* <p>
	*  服务实现类
	* </p>
*
* @author Mikko
* @since 2021-06-09
*/
@Service
public class DlyndxServiceImpl extends ServiceImpl<DlyndxMapper, Dlyndx> implements IDlyndxService {
    public QueryWrapper<Dlyndx> buildWrapper(Dlyndx var) {
        return null;
    }
}
