package org.jeecg.modules.gwb.service;

import java.io.IOException;
import java.text.ParseException;

import org.jeecg.modules.gwb.entity.HisPtpyeSync;
import org.jeecg.modules.gwb.entity.XwPPtypePrice;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.IService;

@DS("multi-datasource-gwb")
public interface IXwPPtypePriceService extends IService<XwPPtypePrice> {

    /**
     * 同步价格信息
     */
    HisPtpyeSync syncPPtypePriceInfData2Server() throws IOException, ParseException;
}
