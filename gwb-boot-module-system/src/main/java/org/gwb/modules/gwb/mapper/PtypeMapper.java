package org.gwb.modules.gwb.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.gwb.modules.gwb.entity.Ptype;
import org.gwb.modules.gwb.entity.dto.PtypeWareHouse;
import org.gwb.modules.gwb.entity.dto.ViewPtypeStockDto;
import org.gwb.modules.gwb.entity.dto.ViewVchDraftQtyOrder;
import org.gwb.modules.gwb.entity.dto.ViewVchDraftQtyOrderDto;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

public interface PtypeMapper extends BaseMapper<Ptype> {

    List<Ptype> findContainENamePtype();

    /**
     * viewSearchStockPtypeOverall
     * @param pageSize
     * @return
     */
    List<ViewPtypeStockDto> viewSearchStockPtypeOverall(@Param("pageSize") Integer pageSize,
            @Param("pageNoMSize") Integer pageNoMSize, @Param("saleDateBegin") String saleDateBegin,
            @Param("saleDateEnd") String saleDateEnd, @Param("viewDeleted") String viewDeleted,
            @Param("viewStop") String viewStop, @Param("searchParams") String searchParams);

    /**
     * countSearchStockPtypeOverall
     * @param searchParams
     * @return
     */
    long countSearchStockPtypeOverall(@Param("viewDeleted") String viewDeleted, @Param("viewStop") String viewStop,
            @Param("searchParams") String searchParams);

    /**
     * findViewPtypeOverallByPtypeId
     * @param ptypeid
     * @return
     */
    ViewPtypeStockDto findViewPtypeOverallByPtypeId(@Param("ptypeid") String ptypeid);

    /**
     * viewVchDraftQtyOrderById
     * @param ptypeid
     * @return
     */
    List<ViewVchDraftQtyOrder> viewVchDraftQtyOrderById(@Param("ptypeid") String ptypeid);

    /**
     * findViewVchDraftQtyOrderDtoListByPtypeId
     * @param ptypeid
     * @return
     */
    List<ViewVchDraftQtyOrderDto> findViewVchDraftQtyOrderDtoListByPtypeId(@Param("ptypeid") String ptypeid);

    /**
     * viewVchDlySaleQtyOrderByIdAndDate
     * @param dateBegin
     * @param dateEnd
     * @param ptypeid
     * @return
     */
    List<ViewVchDraftQtyOrder> viewVchDlySaleQtyOrderByIdAndDate(@Param("dateBegin") String dateBegin,
            @Param("dateEnd") String dateEnd, @Param("ptypeid") String ptypeid);

    /**
     * findWarehouseByNumRange
     * @param pageSize
     * @param pageNoMSize
     * @param lessThanQtyNum
     * @param biggerThanQtyNum
     * @return
     */
    List<PtypeWareHouse> findWarehouseByNumRange(@Param("pageSize") Integer pageSize,
            @Param("pageNoMSize") Integer pageNoMSize, @Param("lessThanQtyNum") Integer lessThanQtyNum,
            @Param("biggerThanQtyNum") Integer biggerThanQtyNum);

    /**
     * countWarehouseByNumRange
     * @param lessThanQtyNum
     * @param biggerThanQtyNum
     * @return
     */
    Long countWarehouseByNumRange(@Param("lessThanQtyNum") Integer lessThanQtyNum,
            @Param("biggerThanQtyNum") Integer biggerThanQtyNum);

}
