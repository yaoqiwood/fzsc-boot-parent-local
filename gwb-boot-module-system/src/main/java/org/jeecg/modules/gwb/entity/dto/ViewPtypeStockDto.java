package org.jeecg.modules.gwb.entity.dto;

import lombok.Data;

@Data
public class ViewPtypeStockDto {
    private String ptypeid;

    private String pfullname;

    private String type;

    private Integer qty;

    private String unit;

    private Integer draftCount;

    private Integer saleCount;
}
