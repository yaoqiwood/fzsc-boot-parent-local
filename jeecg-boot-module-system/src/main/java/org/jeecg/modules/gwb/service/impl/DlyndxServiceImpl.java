package org.jeecg.modules.gwb.service.impl;

import org.jeecg.modules.gwb.entity.Dlyndx;
import org.jeecg.modules.gwb.mapper.DlyndxMapper;
import org.jeecg.modules.gwb.service.IDlyndxService;
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
