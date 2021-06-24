package org.gwb.modules.gwb.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * @Description: 库存记录表
 */
@Data
@TableName("T_GoodsStocksGlide")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "T_GoodsStocksGlide对象", description = "库存记录表")
public class TGoodsStocksGlide {

    @TableId
    private Integer goodsid;

    private Integer dlyorder;

    private String ptypeid;

    private String ktypeid;

    private String goodsdate;

    private Integer goodsorder;

    private String jobnumber;

    private String outfactorydate;

    private BigDecimal qty;

    private BigDecimal price;

    private BigDecimal total;

    private Integer vchcode;

    private BigDecimal pgholqty;

    private Integer pgholinqty;

    private String goodsbatchid;

    private String kwtypeid;

    private String btypeid;

    private String buydate;

    private String usefulenddate;

    private String sltypeid;

    private Integer goodsorderid;
}
