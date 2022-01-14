package org.gwb.modules.gwb.service;

import java.io.IOException;
import java.util.List;

import org.gwb.modules.gwb.entity.HisPtpyeSync;
import org.gwb.modules.gwb.entity.Ptype;
import org.gwb.modules.gwb.entity.dto.*;

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

    List<ViewVchSaleQtyOrderDto> findViewVchSaleQtyOrderDtoListByPtypeId(String ptypeid);

    Page<PtypeWareHouse> findWarehouseByNumRangeAndPage(JSONObject object);

    /**
     * 修改拼音首字母索引
     */
    void changePtypeHZPinyinName2Xz();

    /**
     * 修改货柜号
     * @param ptypeId
     * @param containerNo
     */
    void modifyContainerInfoByPtypeId(String ptypeId, String containerNo, String pfullName);

    /**
     * 批量修改货柜号
     * @param ptypeIdArray
     * @param containerNo
     */
    void batchModifyContainerInfoByPtypeId(List<String> ptypeIdArray, String containerNo);

    /**
     * 查找产品通过id
     * @param ptypeId
     * @return
     */
    ViewPtypeInf2ModifyDto findPtypeById(String ptypeId);

}
