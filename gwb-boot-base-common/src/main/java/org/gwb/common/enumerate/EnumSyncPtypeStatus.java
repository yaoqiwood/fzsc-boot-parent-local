package org.gwb.common.enumerate;

import lombok.Getter;

@Getter
public enum EnumSyncPtypeStatus {
    PTYPE("商品", "ptype"), PRICE("价格", "price"),T_GOODS_STOCKS("库存","stocks");

    EnumSyncPtypeStatus(String name, String code) {
        this.name = name;
        this.code = code;
    }

    private String name;

    private String code;
}
