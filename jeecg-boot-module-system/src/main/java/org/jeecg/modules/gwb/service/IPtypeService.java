package org.jeecg.modules.gwb.service;

import java.io.IOException;
import java.util.Date;

import org.jeecg.modules.gwb.entity.Ptype;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.IService;

@DS("multi-datasource-gwb")
public interface IPtypeService extends IService<Ptype> {

    void test();

    void syncSendPtypeInfData2Server(Integer updateTag) throws IOException;

    void syncPriceInfData2Server(Date updateDate);
}
