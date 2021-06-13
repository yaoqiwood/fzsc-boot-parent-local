package org.jeecg.common.enumerate;

import lombok.Getter;

@Getter
public enum EnumPtypeStatus {
    ENABLE("启用", "1"), UNABLE("停用", "0");

    EnumPtypeStatus(String name, String code) {
        this.name = name;
        this.code = code;
    }

    private final String name;

    private final String code;
}
