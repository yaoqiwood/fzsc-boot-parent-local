package org.jeecg.modules.gwb.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
*
* @author Mikko
* @since 2021-06-09
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("Dlyndx")
public class Dlyndx implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableField("Vchcode")
    private Double vchcode;

    @TableField("Date")
    private String date;

    @TableField("Number")
    private String number;

    @TableField("VchType")
    private Double vchtype;

    @TableField("summary")
    private String summary;

    @TableField("Comment")
    private String comment;

    @TableField("btypeid")
    private String btypeid;

    @TableField("etypeid")
    private String etypeid;

    @TableField("ktypeid")
    private String ktypeid;

    @TableField("ktypeid2")
    private String ktypeid2;

    @TableField("ifcheck")
    private String ifcheck;

    @TableField("checke")
    private String checke;

    @TableField("period")
    private Integer period;

    @TableField("RedWord")
    private String redword;

    @TableField("RedOld")
    private String redold;

    @TableField("accounte")
    private String accounte;

    @TableField("InputNo")
    private String inputno;

    @TableField("draft")
    private Integer draft;

    @TableField("cashover")
    private Integer cashover;

    @TableField("attach")
    private Integer attach;

    @TableField("Total")
    private Double total;

    @TableField("BillType")
    private Integer billtype;

    @TableField("GatheringDate")
    private String gatheringdate;

    @TableField("DeptID")
    private String deptid;

    @TableField("AVchCode")
    private Integer avchcode;

    @TableField("counttype")
    private Integer counttype;

    @TableField("InputN")
    private Integer inputn;

    @TableField("InputNO1")
    private String inputno1;

    @TableField("InputNO2")
    private String inputno2;

    @TableField("InputNO3")
    private String inputno3;

    @TableField("InputNO4")
    private String inputno4;

    @TableField("InputNO5")
    private String inputno5;

    @TableField("InvoceTag")
    private Integer invocetag;

    @TableField("VipCardID")
    private Integer vipcardid;

    @TableField("VipDiscount")
    private Double vipdiscount;

    @TableField("cwFlag")
    private Integer cwflag;

    @TableField("lsFull")
    private Integer lsfull;

    @TableField("ChangeTotal")
    private Double changetotal;

    @TableField("lsRetail")
    private Integer lsretail;

    @TableField("PostNo")
    private String postno;

    @TableField("FromOrderCode")
    private Integer fromordercode;

    @TableField("SaveCardID")
    private Integer savecardid;

    @TableField("FeeBtypeID")
    private String feebtypeid;

    @TableField("FeeTotal")
    private Double feetotal;

    @TableField("YearPeriod")
    private Integer yearperiod;

    @TableField("Comment1")
    private String comment1;

    @TableField("Comment2")
    private String comment2;

    @TableField("Comment3")
    private String comment3;

    @TableField("Defdiscount")
    private Double defdiscount;

    @TableField("atypeid")
    private String atypeid;

    @TableField("ModiNo")
    private String modino;

    @TableField("ModiDate")
    private String modidate;

    @TableField("Savedate")
    private Date savedate;

    @TableField("uniqueID")
    private String uniqueid;

    @TableField("MenID")
    private Integer menid;

    @TableField("isAccept")
    private Integer isaccept;

    @TableField("isFaxed")
    private Integer isfaxed;

    @TableField("GatherBtypeId")
    private String gatherbtypeid;

    @TableField("VchtypeState")
    private Integer vchtypestate;

    @TableField("IsPOS")
    private Integer ispos;

    @TableField("SaveMenid")
    private Integer savemenid;

    @TableField("BillBalanceMode")
    private Integer billbalancemode;

    @TableField("IsAutoJMD")
    private Integer isautojmd;

    @TableField("MdTypeid")
    private Integer mdtypeid;

    @TableField("DataSource")
    private String datasource;

    @TableField("isUpLoad")
    private Integer isupload;

    @TableField("alBillNumber")
    private String albillnumber;

    @TableField("isFacePayState")
    private Integer isfacepaystate;

    @TableField("isreject")
    private Integer isreject;

    @TableField("NoInvoice")
    private Integer noinvoice;

    @TableField("isGenerated")
    private Integer isgenerated;

    @TableField("fctypeid")
    private String fctypeid;

    @TableField("fcrate")
    private Double fcrate;

    @TableField("fctotal")
    private Double fctotal;

    @TableField("fcfeetotal")
    private Double fcfeetotal;

    @TableField("isFcAuto")
    private Integer isfcauto;

}
