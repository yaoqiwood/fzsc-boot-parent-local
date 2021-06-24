package org.gwb.modules.gwb.entity;

import java.math.BigDecimal;

import org.jeecgframework.poi.excel.annotation.Excel;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Description: 销量表
 * @Author: jeecg-boot
 * @Date:   2020-07-22
 * @Version: V1.0
 */
@Data
@TableName("DlySale")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "DlySale对象", description = "销量表")
public class DlySale {

    /**vchcode*/
    @Excel(name = "vchcode", width = 15)
    @ApiModelProperty(value = "vchcode")
    private BigDecimal vchcode;

    /**atypeid*/
    @Excel(name = "atypeid", width = 15)
    @ApiModelProperty(value = "atypeid")
    private String atypeid;

    /**btypeid*/
    @Excel(name = "btypeid", width = 15)
    @ApiModelProperty(value = "btypeid")
    private String btypeid;

    /**etypeid*/
    @Excel(name = "etypeid", width = 15)
    @ApiModelProperty(value = "etypeid")
    private String etypeid;

    /**ktypeid2*/
    @Excel(name = "ktypeid2", width = 15)
    @ApiModelProperty(value = "ktypeid2")
    private String ktypeid2;

    /**ktypeid*/
    @Excel(name = "ktypeid", width = 15)
    @ApiModelProperty(value = "ktypeid")
    private String ktypeid;

    /**ptypeid*/
    @Excel(name = "ptypeid", width = 15)
    @ApiModelProperty(value = "ptypeid")
    private String ptypeid;

    /**qty*/
    @Excel(name = "qty", width = 15)
    @ApiModelProperty(value = "qty")
    private BigDecimal qty;

    /**discount*/
    @Excel(name = "discount", width = 15)
    @ApiModelProperty(value = "discount")
    private BigDecimal discount;

    /**discountprice*/
    @Excel(name = "discountprice", width = 15)
    @ApiModelProperty(value = "discountprice")
    private BigDecimal discountprice;

    /**costtotal*/
    @Excel(name = "costtotal", width = 15)
    @ApiModelProperty(value = "costtotal")
    private BigDecimal costtotal;

    /**costprice*/
    @Excel(name = "costprice", width = 15)
    @ApiModelProperty(value = "costprice")
    private BigDecimal costprice;

    /**blockno*/
    @Excel(name = "blockno", width = 15)
    @ApiModelProperty(value = "blockno")
    private String blockno;

    /**goodsno*/
    // private Integer goodsno;

    /**price*/
    @Excel(name = "price", width = 15)
    @ApiModelProperty(value = "price")
    private BigDecimal price;

    /**total*/
    @Excel(name = "total", width = 15)
    @ApiModelProperty(value = "total")
    private BigDecimal total;

    /**prodate*/
    @Excel(name = "prodate", width = 15)
    @ApiModelProperty(value = "prodate")
    private String prodate;

    /**taxprice*/
    @Excel(name = "taxprice", width = 15)
    @ApiModelProperty(value = "taxprice")
    private BigDecimal taxprice;

    /**taxtotal*/
    @Excel(name = "taxtotal", width = 15)
    @ApiModelProperty(value = "taxtotal")
    private BigDecimal taxtotal;

    /**comment*/
    @Excel(name = "comment", width = 15)
    @ApiModelProperty(value = "comment")
    private String comment;

    /**date*/
    @Excel(name = "date", width = 15)
    @ApiModelProperty(value = "date")
    private String date;

    /**usedtype*/
    @Excel(name = "usedtype", width = 15)
    @ApiModelProperty(value = "usedtype")
    private String usedtype;

    /**period*/
    @Excel(name = "period", width = 15)
    @ApiModelProperty(value = "period")
    private Integer period;

    /**taxTotal*/
    @Excel(name = "taxTotal", width = 15)
    @ApiModelProperty(value = "taxTotal")
    private BigDecimal taxTotal;

    /**tax*/
    @Excel(name = "tax", width = 15)
    @ApiModelProperty(value = "tax")
    private BigDecimal tax;

    /**discounttotal*/
    @Excel(name = "discounttotal", width = 15)
    @ApiModelProperty(value = "discounttotal")
    private BigDecimal discounttotal;

    /**vchtype*/
    @Excel(name = "vchtype", width = 15)
    @ApiModelProperty(value = "vchtype")
    private Integer vchtype;

    /**redword*/
    @Excel(name = "redword", width = 15)
    @ApiModelProperty(value = "redword")
    private String redword;

    /**ptyperec*/
    @Excel(name = "ptyperec", width = 15)
    @ApiModelProperty(value = "ptyperec")
    private Integer ptyperec;

    /**costmode*/
    @Excel(name = "costmode", width = 15)
    @ApiModelProperty(value = "costmode")
    private Integer costmode;

    /**unit*/
    @Excel(name = "unit", width = 15)
    @ApiModelProperty(value = "unit")
    private Integer unit;

    /**ordercode*/
    @Excel(name = "ordercode", width = 15)
    @ApiModelProperty(value = "ordercode")
    private Integer ordercode;

    /**orderdlycode*/
    @Excel(name = "orderdlycode", width = 15)
    @ApiModelProperty(value = "orderdlycode")
    private Integer orderdlycode;

    /**wldzgroup*/
    @Excel(name = "wldzgroup", width = 15)
    @ApiModelProperty(value = "wldzgroup")
    private Integer wldzgroup;

    /**dlyorder*/
    @TableId
    @ApiModelProperty(value = "dlyorder")
    private Integer dlyorder;

    /**retailprice*/
    @Excel(name = "retailprice", width = 15)
    @ApiModelProperty(value = "retailprice")
    private BigDecimal retailprice;

    /**invocetotal*/
    @Excel(name = "invocetotal", width = 15)
    @ApiModelProperty(value = "invocetotal")
    private BigDecimal invocetotal;

    /**memo*/
    @Excel(name = "memo", width = 15)
    @ApiModelProperty(value = "memo")
    private String memo;

    /**invocetag*/
    @Excel(name = "invocetag", width = 15)
    @ApiModelProperty(value = "invocetag")
    private Integer invocetag;

    /**pdetail*/
    @Excel(name = "pdetail", width = 15)
    @ApiModelProperty(value = "pdetail")
    private Integer pdetail;

    /**deptid*/
    @Excel(name = "deptid", width = 15)
    @ApiModelProperty(value = "deptid")
    private String deptid;

    /**detailgroup*/
    @Excel(name = "detailgroup", width = 15)
    @ApiModelProperty(value = "detailgroup")
    private Integer detailgroup;

    /**pstutas*/
    @Excel(name = "pstutas", width = 15)
    @ApiModelProperty(value = "pstutas")
    private Integer pstutas;

    /**vipcardid*/
    @Excel(name = "vipcardid", width = 15)
    @ApiModelProperty(value = "vipcardid")
    private Integer vipcardid;

    /**assqty*/
    @Excel(name = "assqty", width = 15)
    @ApiModelProperty(value = "assqty")
    private BigDecimal assqty;

    /**assprice*/
    @Excel(name = "assprice", width = 15)
    @ApiModelProperty(value = "assprice")
    private BigDecimal assprice;

    /**assdiscountprice*/
    @Excel(name = "assdiscountprice", width = 15)
    @ApiModelProperty(value = "assdiscountprice")
    private BigDecimal assdiscountprice;

    /**asstaxprice*/
    @Excel(name = "asstaxprice", width = 15)
    @ApiModelProperty(value = "asstaxprice")
    private BigDecimal asstaxprice;

    /**unitrate*/
    @Excel(name = "unitrate", width = 15)
    @ApiModelProperty(value = "unitrate")
    private BigDecimal unitrate;

    /**promovchcode*/
    @Excel(name = "promovchcode", width = 15)
    @ApiModelProperty(value = "promovchcode")
    private BigDecimal promovchcode;

    /**yearperiod*/
    @Excel(name = "yearperiod", width = 15)
    @ApiModelProperty(value = "yearperiod")
    private Integer yearperiod;

    /**sideqty*/
    @Excel(name = "sideqty", width = 15)
    @ApiModelProperty(value = "sideqty")
    private BigDecimal sideqty;

    /**pgholqty*/
    @Excel(name = "pgholqty", width = 15)
    @ApiModelProperty(value = "pgholqty")
    private BigDecimal pgholqty;

    /**pgholinqty*/
    @Excel(name = "pgholinqty", width = 15)
    @ApiModelProperty(value = "pgholinqty")
    private BigDecimal pgholinqty;

    /**copysdlyorder*/
    @Excel(name = "copysdlyorder", width = 15)
    @ApiModelProperty(value = "copysdlyorder")
    private Integer copysdlyorder;

    /**billorder*/
    @Excel(name = "billorder", width = 15)
    @ApiModelProperty(value = "billorder")
    private Integer billorder;

    /**feetotal*/
    @Excel(name = "feetotal", width = 15)
    @ApiModelProperty(value = "feetotal")
    private BigDecimal feetotal;

    /**usefulenddate*/
    @Excel(name = "usefulenddate", width = 15)
    @ApiModelProperty(value = "usefulenddate")
    private String usefulenddate;

    /**physicaltoqty*/
    @Excel(name = "physicaltoqty", width = 15)
    @ApiModelProperty(value = "physicaltoqty")
    private BigDecimal physicaltoqty;

    /**ordervchtype*/
    @Excel(name = "ordervchtype", width = 15)
    @ApiModelProperty(value = "ordervchtype")
    private Integer ordervchtype;

    /**goodsbatchid*/
    @Excel(name = "goodsbatchid", width = 15)
    @ApiModelProperty(value = "goodsbatchid")
    private String goodsbatchid;

    /**kwtypeid*/
    @Excel(name = "kwtypeid", width = 15)
    @ApiModelProperty(value = "kwtypeid")
    private String kwtypeid;

    /**ubarcode*/
    @Excel(name = "ubarcode", width = 15)
    @ApiModelProperty(value = "ubarcode")
    private String ubarcode;

    /**sltypeid*/
    @Excel(name = "sltypeid", width = 15)
    @ApiModelProperty(value = "sltypeid")
    private String sltypeid;

    /**sltypeid2*/
    @Excel(name = "sltypeid2", width = 15)
    @ApiModelProperty(value = "sltypeid2")
    private String sltypeid2;

    /**isunion*/
    @Excel(name = "isunion", width = 15)
    @ApiModelProperty(value = "isunion")
    private Integer isunion;
}
