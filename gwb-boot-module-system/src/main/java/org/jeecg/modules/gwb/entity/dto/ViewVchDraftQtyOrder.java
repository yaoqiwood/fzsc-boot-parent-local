package org.jeecg.modules.gwb.entity.dto;

import lombok.Data;

@Data
public class ViewVchDraftQtyOrder {
    private String vchcode;

    private String date;

    private String number;

    private String vchType;

    private String summary;

    private String comment;

    private Integer qty;

    private String usedType;

    private String ptypeid;

    private String pfullname;
}
