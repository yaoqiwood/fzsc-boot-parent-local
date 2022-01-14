package org.gwb;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONArray;

public class ToolsTest {
    public static void main(String[] args) {
        // System.out.println(MikkoDateUtils.getBefore7DaysBeginTime());
        // long a = 1.1;
        // System.out.println();
        // System.out.println(EnumVchType.getMethodByCode("16"));
        // System.out.println(PingYinUtil.getFirstSpell("刹车"));
        String arrayStr = "[\"122\",\"233\",\"344\"]";
        List<String> strArr = new ArrayList<>();
        strArr = JSONArray.parseArray(arrayStr, String.class);
        for (String s : strArr) {
            System.out.println(s);
        }
    }
}
