package org.gwb.modules.gwb.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
* <p>
* 
* </p>
*
* @author Mikko
* @since 2021-06-09
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("BakDly")
public class Bakdly implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableField("Vchcode")
    private Double vchcode;

    @TableField("atypeid")
    private String atypeid;

    @TableField("btypeid")
    private String btypeid;

    @TableField("etypeid")
    private String etypeid;

    @TableField("ktypeid2")
    private String ktypeid2;

    @TableField("ktypeid")
    private String ktypeid;

    @TableField("PtypeId")
    private String ptypeid;

    @TableField("Qty")
    private Double qty;

    @TableField("discount")
    private Double discount;

    @TableField("DiscountPrice")
    private Double discountprice;

    @TableField("costtotal")
    private Double costtotal;

    @TableField("costprice")
    private Double costprice;

    @TableField("Blockno")
    private String blockno;

    @TableField("goodsorderID")
    private Integer goodsorderid;

    @TableField("price")
    private Double price;

    @TableField("total")
    private Double total;

    @TableField("ProDate")
    private String prodate;

    @TableField("TaxPrice")
    private Double taxprice;

    @TableField("TaxTotal")
    private Double taxtotal;

    @TableField("comment")
    private String comment;

    @TableField("date")
    private String date;

    @TableField("usedtype")
    private String usedtype;

    @TableField("period")
    private Integer period;

    @TableField("tax_total")
    private Double taxTotal;

    @TableField("tax")
    private Double tax;

    @TableField("discounttotal")
    private Double discounttotal;

    @TableField("Vchtype")
    private Integer vchtype;

    @TableField("ProjectID")
    private String projectid;

    @TableField("costmode")
    private Integer costmode;

    @TableField("redword")
    private String redword;

    @TableField("unit")
    private Integer unit;

    @TableField("OrderCode")
    private Integer ordercode;

    @TableField("OrderDlyCode")
    private Integer orderdlycode;

    @TableField("WldzGroup")
    private Integer wldzgroup;

    @TableField("DlyOrder")
    private Integer dlyorder;

    @TableField("RetailPrice")
    private Double retailprice;

    @TableField("InvoceTotal")
    private Double invocetotal;

    @TableField("Memo")
    private String memo;

    @TableField("InvoceTag")
    private Integer invocetag;

    @TableField("PDetail")
    private Integer pdetail;

    @TableField("DeptID")
    private String deptid;

    @TableField("DETAILGROUP")
    private Integer detailgroup;

    @TableField("HandZeroCost")
    private Integer handzerocost;

    @TableField("PStutas")
    private Integer pstutas;

    @TableField("VipCardId")
    private Integer vipcardid;

    @TableField("AssQty")
    private Double assqty;

    @TableField("AssPrice")
    private Double assprice;

    @TableField("AssDiscountPrice")
    private Double assdiscountprice;

    @TableField("AssTaxPrice")
    private Double asstaxprice;

    @TableField("UnitRate")
    private Double unitrate;

    @TableField("PromoVchcode")
    private Double promovchcode;

    @TableField("FeeTotal")
    private Double feetotal;

    @TableField("InPrice")
    private Double inprice;

    @TableField("InTotal")
    private Double intotal;

    @TableField("YearPeriod")
    private Integer yearperiod;

    @TableField("SideQty")
    private Double sideqty;

    @TableField("pgholqty")
    private Double pgholqty;

    @TableField("pgholInqty")
    private Double pgholinqty;

    @TableField("CopySdlyorder")
    private Integer copysdlyorder;

    @TableField("BillOrder")
    private Integer billorder;

    @TableField("OrderuniqueID")
    private String orderuniqueid;

    @TableField("ColRowNo")
    private Integer colrowno;

    @TableField("ptypeidtype")
    private Integer ptypeidtype;

    @TableField("stuffTotal")
    private Double stufftotal;

    @TableField("MenTotal")
    private Double mentotal;

    @TableField("ProdureTotal")
    private Double produretotal;

    @TableField("Erpdlyorder")
    private Integer erpdlyorder;

    @TableField("UsefulEndDate")
    private String usefulenddate;

    @TableField("Physicaltoqty")
    private Double physicaltoqty;

    @TableField("orderVchType")
    private Integer ordervchtype;

    @TableField("upNumber")
    private Integer upnumber;

    @TableField("GoodsBatchID")
    private String goodsbatchid;

    @TableField("kwtypeid")
    private String kwtypeid;

    @TableField("UBarCode")
    private String ubarcode;

    @TableField("AcceptQty")
    private Double acceptqty;

    @TableField("Acceptpgholqty")
    private Double acceptpgholqty;

    @TableField("AcceptpgholInqty")
    private Double acceptpgholinqty;

    @TableField("AssAcceptQty")
    private Double assacceptqty;

    @TableField("AcceptSideQty")
    private Double acceptsideqty;

    @TableField("uniqueID")
    private String uniqueid;

    @TableField("Sltypeid")
    private String sltypeid;

    @TableField("Sltypeid2")
    private String sltypeid2;

    @TableField("BackQty")
    private Double backqty;

    @TableField("ProdPrice")
    private Double prodprice;

    @TableField("OtherTotal")
    private Double othertotal;

    @TableField("ptypetype")
    private Integer ptypetype;

    @TableField("fctypeid")
    private String fctypeid;

    @TableField("fcrate")
    private Double fcrate;

    @TableField("fctotal")
    private Double fctotal;

}
