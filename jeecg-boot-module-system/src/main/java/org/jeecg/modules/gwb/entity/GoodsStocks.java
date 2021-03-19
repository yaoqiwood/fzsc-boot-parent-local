package org.jeecg.modules.gwb.entity;

import java.util.Date;

import org.jeecgframework.poi.excel.annotation.Excel;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Description: 商品库存表
 * @Author: jeecg-boot
 * @Date:   2020-07-11
 * @Version: V1.0
 */
@Data
@TableName("GoodsStocks")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "GoodsStocks对象", description = "商品库存表")
public class GoodsStocks {

    /**ptypeid*/
    @TableId
    @Excel(name = "ptypeid", width = 15)
    @ApiModelProperty(value = "ptypeid")
    private String ptypeid;

    /**ktypeid*/
    @Excel(name = "ktypeid", width = 15)
    @ApiModelProperty(value = "ktypeid")
    private String ktypeid;

    /**jobnumber*/
    @Excel(name = "jobnumber", width = 15)
    @ApiModelProperty(value = "jobnumber")
    private String jobnumber;

    /**outfactorydate*/
    @Excel(name = "outfactorydate", width = 15)
    @ApiModelProperty(value = "outfactorydate")
    private String outfactorydate;

    /**qty*/
    @Excel(name = "qty", width = 15)
    @ApiModelProperty(value = "qty")
    private java.math.BigDecimal qty;

    /**price*/
    @Excel(name = "price", width = 15)
    @ApiModelProperty(value = "price")
    private java.math.BigDecimal price;

    /**total*/
    @Excel(name = "total", width = 15)
    @ApiModelProperty(value = "total")
    private java.math.BigDecimal total;

    /**goodsorder*/
    @Excel(name = "goodsorder", width = 15)
    @ApiModelProperty(value = "goodsorder")
    private Integer goodsorder;

    /**goodsorderid*/
    @Excel(name = "goodsorderid", width = 15)
    @ApiModelProperty(value = "goodsorderid")
    private Integer goodsorderid;

    /**pgholinqty*/
    @Excel(name = "pgholinqty", width = 15)
    @ApiModelProperty(value = "pgholinqty")
    private Integer pgholinqty;

    /**goodsbatchid*/
    @Excel(name = "goodsbatchid", width = 15)
    @ApiModelProperty(value = "goodsbatchid")
    private String goodsbatchid;

    /**btypeid*/
    @Excel(name = "btypeid", width = 15)
    @ApiModelProperty(value = "btypeid")
    private String btypeid;

    /**buydate*/
    @Excel(name = "buydate", width = 15)
    @ApiModelProperty(value = "buydate")
    private String buydate;

    /**usefulenddate*/
    @Excel(name = "usefulenddate", width = 15)
    @ApiModelProperty(value = "usefulenddate")
    private String usefulenddate;

    /**
     * 保存时间
     */
    private Date saveDate;
}
