package org.jeecg.modules.gwb.service;

import java.io.IOException;

import org.jeecg.modules.gwb.entity.Ptype;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.IService;

@DS("multi-datasource-gwb")
public interface IPtypeService extends IService<Ptype> {

    void test();

    void syncSendData2Server(Integer rowIndex) throws IOException;
}
