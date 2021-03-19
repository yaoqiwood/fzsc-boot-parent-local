package org.jeecg.common.util;

public class XMLTransferUtil {
    public static String transferXML(String xmlStr) {
        xmlStr = xmlStr.replaceAll("<", "&lt;");
        xmlStr = xmlStr.replaceAll(">", "&gt;");
        xmlStr = xmlStr.replaceAll("&", "&amp;");
        xmlStr = xmlStr.replaceAll("'", "&apos;");
        xmlStr = xmlStr.replaceAll("\"", "&quot;");
        return xmlStr;
    }
}
