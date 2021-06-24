package org.gwb.modules.gwb.entity.dto;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

@Data
public class GoodsStockDto {
    private String vchcode;

    private String date;

    private String vchType;

    private Date savedate;

    private String ptypeid;

    private BigDecimal tQty;

    private BigDecimal tPrice;

    private BigDecimal gPrice;

    private BigDecimal gTotal;

    private BigDecimal qty;
}
