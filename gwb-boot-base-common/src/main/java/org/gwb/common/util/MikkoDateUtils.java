package org.gwb.common.util;

import cn.hutool.core.date.DateUtil;

public class MikkoDateUtils {
    public static String getBefore7DaysBeginTime() {
        return DateUtil.beginOfDay(DateUtil.lastWeek()).toString();
    }

    public static String getNowTime() {
        return DateUtil.now();
    }
}
