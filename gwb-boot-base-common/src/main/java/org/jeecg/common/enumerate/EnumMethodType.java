package org.jeecg.common.enumerate;

import lombok.Getter;

@Getter
public enum EnumMethodType {
    PLUS("+", "plus"), REDUCE("-", "reduce"), OTHER("other", "other");

    EnumMethodType(String name, String code) {
        this.name = name;
        this.code = code;
    }

    private final String name;

    private final String code;
}
