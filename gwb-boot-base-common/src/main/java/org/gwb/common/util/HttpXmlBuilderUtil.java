package org.gwb.common.util;

import cn.hutool.core.util.XmlUtil;
import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.gwb.common.exception.JeecgBootException;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

@Slf4j
public class HttpXmlBuilderUtil {
    public static StringBuilder buildSendDataBuilder(StringBuilder sbd, Date requestDate) {
        StringBuilder sendStr = new StringBuilder();
        sendStr.append("<sync_data>");
        sendStr.append("<request_data>").append(XMLTransferUtil.transferXML(sbd.toString())).append("</request_data>");
        sendStr.append("<request_time>").append(requestDate).append("</request_time>");
        sendStr.append("</sync_data>");
        return sendStr;
    }

    public static String httpGetSyncMethodFromServer(String url, BufferedReader reader, String filterCheckPublicKey) throws IOException {
        String builder = HTTPUtil.httpPostMethod(url, filterCheckPublicKey, "", reader);
        Map<String, Object> retMessageMap = XmlUtil.xmlToMap(builder);
        log.info("接收返回值：" + builder);
        if (!String.valueOf(true).equals(retMessageMap.get("response_success"))) {
            throw new JeecgBootException("错误，同步失败，获取数据错误");
        }
        if (Strings.isNullOrEmpty(retMessageMap.get("response_return_data").toString())) {
            throw new JeecgBootException("错误，数据为空且错误");
        }
        return retMessageMap.get("response_return_data").toString();
    }
}
