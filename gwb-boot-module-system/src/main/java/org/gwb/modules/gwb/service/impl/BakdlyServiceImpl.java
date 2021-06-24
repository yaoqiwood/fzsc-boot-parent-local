package org.gwb.modules.gwb.service.impl;

import org.gwb.modules.gwb.entity.Bakdly;
import org.gwb.modules.gwb.mapper.BakdlyMapper;
import org.gwb.modules.gwb.service.IBakdlyService;
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
public class BakdlyServiceImpl extends ServiceImpl<BakdlyMapper, Bakdly> implements IBakdlyService {
    public QueryWrapper<Bakdly> buildWrapper(Bakdly var) {
        return null;
    }
}
