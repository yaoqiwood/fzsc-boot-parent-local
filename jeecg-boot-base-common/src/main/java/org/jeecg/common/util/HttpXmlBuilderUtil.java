package org.jeecg.common.util;

import java.util.Date;

public class HttpXmlBuilderUtil {
    public static StringBuilder buildSendDataBuilder(StringBuilder sbd, Date requestDate) {
        StringBuilder sendStr = new StringBuilder();
        sendStr.append("<sync_data>");
        sendStr.append("<request_data>").append(XMLTransferUtil.transferXML(sbd.toString())).append("</request_data>");
        sendStr.append("<request_time>").append(requestDate).append("</request_time>");
        sendStr.append("</sync_data>");
        return sendStr;
    }
}
