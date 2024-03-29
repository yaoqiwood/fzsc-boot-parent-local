package org.gwb.modules.gwb.controller;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.ValidationException;

import org.gwb.common.api.vo.Result;
import org.gwb.common.system.base.controller.JeecgController;
import org.gwb.modules.gwb.entity.HisPtpyeSync;
import org.gwb.modules.gwb.entity.Ptype;
import org.gwb.modules.gwb.entity.dto.ViewPtypeInf2ModifyDto;
import org.gwb.modules.gwb.service.IHisPtpyeSyncService;
import org.gwb.modules.gwb.service.IPtypeService;
import org.gwb.modules.gwb.service.IXwPPtypePriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Strings;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/ptype")
public class PtypeController extends JeecgController<Ptype, IPtypeService> {

    @Autowired
    private IXwPPtypePriceService xwPPtypePriceService;

    @Autowired
    private IHisPtpyeSyncService hisPtpyeSyncService;

    /**
     * 同步商品信息
     * @return
     */
    @PostMapping(value = "syncSendPtypeInfData2Server")
    public Result<?> syncSendPtypeInfData2Server() { // @RequestParam("updateTag") Integer updateTag
        try {
            // Integer updateTag = hisPtpyeSyncService.selectMaxUpdateTag();
            HisPtpyeSync hisPtpyeSync = this.service.syncSendPtypeInfData2Server();
            hisPtpyeSyncService.save(hisPtpyeSync);
            return Result.ok("同步商品信息成功");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return Result.error(e.getMessage());
        }
    }

    /**
     * 同步价格信息
     * @return
     */
    @PostMapping(value = "syncPPtypePriceInfData2Server")
    public Result<?> syncPPtypePriceInfData2Server() {
        try {
            HisPtpyeSync hisPtpyeSync = xwPPtypePriceService.syncPPtypePriceInfData2Server();
            hisPtpyeSyncService.save(hisPtpyeSync);
            return Result.ok("同步价格信息成功");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return Result.error("同步价格信息失败");
        }
    }

    /**
     * initPtypeSyncPage
     * @return
     */
    @PostMapping({ "initPtypeSyncPage" })
    public Result<?> initPtypeSyncPage() {
        try {
            return Result.ok(hisPtpyeSyncService.initPtypeSyncPage());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return Result.error(e.getMessage());
        }
    }

    /**
     * changeAllE2CATName
     * @return
     */
    @PostMapping({ "changeAllE2CATName" })
    public Result<?> changeAllE2CATName() {
        Integer size = this.service.changeAllE2CATName();
        return Result.ok("变更成功，共变更： " + size + " 条数据！");
    }

    /**
     * viewSearchStockPtypeOverall
     * @param params
     * @return
     */
    @PostMapping({ "viewSearchStockPtypeOverall" })
    public Result<?> viewSearchStockPtypeOverall(@RequestBody JSONObject params) {
        return Result.ok(this.service.viewSearchStockPtypeOverall(params));
    }

    /**
     * findViewPtypeOverallByPtypeId
     * @param ptypeid
     * @return
     */
    @GetMapping({ "findViewPtypeOverallByPtypeId" })
    public Result<?> findViewPtypeOverallByPtypeId(@RequestParam String ptypeid) {
        return Result.ok(this.service.findViewPtypeOverallByPtypeId(ptypeid));
    }

    /**
     * findViewVchDraftQtyOrderDtoListByPtypeId
     * @param ptypeid
     * @return
     */
    @GetMapping({ "findViewVchDraftQtyOrderDtoListByPtypeId" })
    public Result<?> findViewVchDraftQtyOrderDtoListByPtypeId(@RequestParam String ptypeid) {
        return Result.ok(this.service.findViewVchDraftQtyOrderDtoListByPtypeId(ptypeid));
    }

    /**
     * findViewVchSaleQtyOrderDtoListByPtypeId
     * @param ptypeid
     * @return
     */
    @GetMapping({ "findViewVchSaleQtyOrderDtoListByPtypeId" })
    public Result<?> findViewVchSaleQtyOrderDtoListByPtypeId(@RequestParam String ptypeid) {
        return Result.ok(this.service.findViewVchSaleQtyOrderDtoListByPtypeId(ptypeid));
    }

    /**
     * listWarehouseByNumRangeAndPage
     * @param object
     * @return
     */
    @PostMapping({ "listWarehouseByNumRangeAndPage" })
    public Result<?> listWarehouseByNumRangeAndPage(@RequestBody JSONObject object) {
        return Result.ok(this.service.findWarehouseByNumRangeAndPage(object));
    }

    /**
     * changeContainerNoByPtypeId
     * @param ptype
     * @return
     * @throws ValidationException
     */
    @PostMapping({ "modifyContainerInfoByPtypeId" })
    public Result<?> modifyContainerInfoByPtypeId(@RequestBody ViewPtypeInf2ModifyDto ptype)
            throws ValidationException {
        if (Strings.isNullOrEmpty(ptype.getPtypeid()) || Strings.isNullOrEmpty(ptype.getBarcode())
                || Strings.isNullOrEmpty(ptype.getPfullname())) {
            throw new ValidationException("错误");
        }
        this.service.modifyContainerInfoByPtypeId(ptype.getPtypeid(), ptype.getBarcode(), ptype.getPfullname());
        return Result.ok("修改成功");
    }

    /**
     * batchModifyContainerInfoByPtypeId
     * @param params
     * @return
     * @throws ValidationException
     */
    @PostMapping({ "batchModifyContainerInfoByPtypeId" })
    public Result<?> batchModifyContainerInfoByPtypeId(@RequestBody JSONObject params) throws ValidationException {
        List<String> batchPtypeIdList = new ArrayList<>();
        if (Strings.isNullOrEmpty(params.getString("batchPtypeId"))
                || Strings.isNullOrEmpty(params.getString("containerNo"))) {
            throw new ValidationException("错误");
        }
        batchPtypeIdList = JSONArray.parseArray(params.getString("batchPtypeId"), String.class);
        if (batchPtypeIdList.size() == 0) {
            throw new ValidationException("错误，参数有误");
        }
        this.service.batchModifyContainerInfoByPtypeId(batchPtypeIdList, params.getString("containerNo"));
        return Result.ok("修改成功");
    }

    /**
     * findPtypeById
     * @param ptypeId
     * @return
     */
    @GetMapping({ "findPtypeById" })
    public Result<?> findPtypeById(@RequestParam String ptypeId) {
        return Result.ok(this.service.findPtypeById(ptypeId));
    }
}
