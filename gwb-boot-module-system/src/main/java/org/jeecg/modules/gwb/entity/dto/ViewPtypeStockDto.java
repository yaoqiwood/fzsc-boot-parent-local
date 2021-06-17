package org.jeecg.modules.gwb.entity.dto;

import lombok.Data;

@Data
public class ViewPtypeStockDto {
    private String ptypeid;

    private String pfullname;

    private String pusercode;

    private String type;

    private Integer qty;

    private String unit;

    private Integer draftCount;

    private Integer saleCount;

    private Integer draftSum;

    private Integer saleSum;
}
