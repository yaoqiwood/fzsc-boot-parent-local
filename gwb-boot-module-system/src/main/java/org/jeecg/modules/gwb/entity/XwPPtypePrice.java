package org.jeecg.modules.gwb.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data
@TableName("xw_P_PtypePrice")
public class XwPPtypePrice {

    /**ptypeid*/
    @TableField("PTypeId")
    private String ptypeid;

    /**unitid*/
    @TableField("UnitID")
    private String unitid;

    /**prtypeid*/
    @TableField("PRTypeId")
    private String prtypeid;

    /**price*/
    @TableField("Price")
    private BigDecimal price;

    /**updatedt*/
    @TableField("UpDateDt")
    private Date updatedt;

    /**keyid*/
    @TableId
    @TableField("KeyId")
    private String keyid;
}
