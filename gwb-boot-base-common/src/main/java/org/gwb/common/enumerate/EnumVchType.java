package org.gwb.common.enumerate;

import lombok.Getter;

@Getter
public enum EnumVchType {
    // PURCHASE("进货订单", "7"), SALE_ORDER("销售订单", "8")
    // 以下类型根据统计后查明可以影响库存数量变化
    STOCK_RETURN("进货退货", "6", "reduce"), DAMAGE("报损", "9", "reduce"), SALE("销售单", "11", "reduce"), OVERFLOW("报溢", "14",
            "plus"), DISASSEMBLE("拆装单", "16",
                    "other"), PURCHASE("进货单", "34", "plus"), SALE_RETURN("销售退货", "45", "plus");

    private String name;

    private String code;

    private String method;

    private static final String USED_TYPE_REDUCE = "1";

    private static final String USED_TYPE_PLUS = "2";

    private static final String OUT_WAREHOUSE = "出库";

    private static final String IN_WAREHOUSE = "入库";

    EnumVchType(String name, String code, String method) {
        this.name = name;
        this.code = code;
        this.method = method;
    }

    public static String getMethodByCode(String type) {
        for (EnumVchType value : EnumVchType.values()) {
            if (value.getCode().equals(type)) {
                return value.getMethod();
            }
        }
        return null;
    }

    public static String getNameByCode(String type) {
        for (EnumVchType value : EnumVchType.values()) {
            if (value.getCode().equals(type)) {
                return value.getName();
            }
        }
        return null;
    }

    public static String getPlusOrReduceName(String type) {
        for (EnumVchType value : EnumVchType.values()) {
            if (value.getCode().equals(type)) {
                if (EnumVchType.PURCHASE.getMethod().equals(value.getMethod())) {
                    return IN_WAREHOUSE;
                } else if (EnumVchType.SALE.getMethod().equals(value.getMethod())) {
                    return OUT_WAREHOUSE;
                }
            }
        }
        return null;
    }

    public static String getUsedTypeName(String usedType) {
        switch (usedType) {
        case USED_TYPE_REDUCE:
            return "拆（出库）";
        case USED_TYPE_PLUS:
            return "装(入库）";
        }
        return null;
    }
}
