package org.jeecg.modules.gwb.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableId;

import lombok.Data;

@Data
public class XwPPtypePrice {

    /**ptypeid*/
    private String ptypeid;

    /**unitid*/
    private String unitid;

    /**prtypeid*/
    private String prtypeid;

    /**price*/
    private java.math.BigDecimal price;

    /**updatedt*/
    private Date updatedt;

    /**keyid*/
    @TableId
    private String keyid;
}
