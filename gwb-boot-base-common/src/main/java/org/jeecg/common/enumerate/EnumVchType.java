package org.jeecg.common.enumerate;

import lombok.Getter;

@Getter
public enum EnumVchType {
    // PURCHASE("进货订单", "7"), SALE_ORDER("销售订单", "8")
    // 以下类型根据统计后查明可以影响库存数量变化
    STOCK_RETURN("进货退货", "6"), DAMAGE("报损", "9"), SALE("销售单", "11"), OVERFLOW("报溢", "14"), DISASSEMBLE("拆装单",
            "16"), PURCHASE("进货单", "34"), SALE_RETURN("销售退货", "45");

    private String name;

    private String code;

    EnumVchType(String name, String code) {
        this.name = name;
        this.code = code;
    }

}
