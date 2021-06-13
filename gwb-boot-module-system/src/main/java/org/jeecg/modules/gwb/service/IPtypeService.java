package org.jeecg.modules.gwb.service;

import java.io.IOException;

import org.jeecg.modules.gwb.entity.HisPtpyeSync;
import org.jeecg.modules.gwb.entity.Ptype;
import org.jeecg.modules.gwb.entity.dto.ViewPtypeStockDto;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

public interface IPtypeService extends IService<Ptype> {

    void test();

    /**
     * syncSendPtypeInfData2Server
     * @return
     * @throws IOException
     */
    HisPtpyeSync syncSendPtypeInfData2Server() throws IOException;

    Integer changeAllE2CATName();

    Page<ViewPtypeStockDto> viewSearchStockPtypeOverall(JSONObject params);
}
