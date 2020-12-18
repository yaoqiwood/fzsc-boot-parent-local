package org.jeecg.modules.gwb.service;

import java.io.IOException;

import org.jeecg.modules.gwb.entity.HisPtpyeSync;
import org.jeecg.modules.gwb.entity.Ptype;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.IService;

@DS("multi-datasource-gwb")
public interface IPtypeService extends IService<Ptype> {

    void test();

    /**
     * syncSendPtypeInfData2Server
     * @return
     * @throws IOException
     */
    HisPtpyeSync syncSendPtypeInfData2Server() throws IOException;

}
