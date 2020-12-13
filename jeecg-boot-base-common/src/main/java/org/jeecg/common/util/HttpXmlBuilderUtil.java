package org.jeecg.common.util;

import java.util.Date;

public class HttpXmlBuilderUtil {
    public static StringBuilder buildSendDataBuilder(StringBuilder sbd, Date requestDate) {
        StringBuilder sendStr = new StringBuilder();
        sendStr.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        sendStr.append("<sync_data>");
        sendStr.append("<request_data>").append(sbd).append("</request_data>");
        sendStr.append("<request_time>").append(requestDate).append("</request_time>");
        sendStr.append("<request_param>");
        sendStr.append("</request_param>");
        sendStr.append("</sync_data>");
        return sendStr;
    }
}
