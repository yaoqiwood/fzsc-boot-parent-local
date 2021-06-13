package org.jeecg.common.enumerate;

import lombok.Getter;

@Getter
public enum EnumRedisKeyType {
    USERNAME_KEY("账号key", "_username_key"), IP_CAPTCHA_KEY("验证码key", "_captcha_key");

    EnumRedisKeyType(String name, String code) {
        this.name = name;
        this.code = code;
    }

    private final String name;

    private final String code;
}
