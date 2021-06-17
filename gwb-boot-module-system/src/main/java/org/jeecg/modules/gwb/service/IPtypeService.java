package org.jeecg.modules.gwb.service;

import java.io.IOException;
import java.util.List;

import org.jeecg.modules.gwb.entity.HisPtpyeSync;
import org.jeecg.modules.gwb.entity.Ptype;
import org.jeecg.modules.gwb.entity.dto.ViewPtypeStockDto;
import org.jeecg.modules.gwb.entity.dto.ViewVchDraftQtyOrderDto;

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

    ViewPtypeStockDto findViewPtypeOverallByPtypeId(String ptypeid);

    List<ViewVchDraftQtyOrderDto> findViewVchDraftQtyOrderDtoListByPtypeId(String ptypeid);

}
