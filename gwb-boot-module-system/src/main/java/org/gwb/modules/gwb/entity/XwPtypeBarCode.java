package org.gwb.modules.gwb.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data
@TableName("xw_PtypeBarCode")
public class XwPtypeBarCode {

    @TableId("PTypeId")
    private String pTypeId;

    @TableField("UnitID")
    private String unitID;

    @TableField("BarCode")
    private String barCode;

    @TableField("Ordid")
    private String ordid;

    @TableField("keyid")
    private String keyid;

    @TableField("kwtypeid")
    private String kwtypeid;

}
