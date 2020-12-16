package org.jeecg.modules.gwb.service;

import java.util.Date;

import org.jeecg.modules.gwb.entity.XwPPtypePrice;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.IService;

@DS("multi-datasource-gwb")
public interface IXwPPtypePriceService extends IService<XwPPtypePrice> {

    /**
     * 同步价格信息
     * @param updateDate
     */
    void syncPPtypePriceInfData2Server(Date updateDate);
}
