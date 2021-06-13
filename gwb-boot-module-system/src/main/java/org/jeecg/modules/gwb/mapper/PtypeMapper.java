package org.jeecg.modules.gwb.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.gwb.entity.Ptype;
import org.jeecg.modules.gwb.entity.dto.ViewPtypeStockDto;

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
}
