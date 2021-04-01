package org.jeecg.common.util;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

import org.jeecg.common.constant.DefConstants;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HTTPUtil {

    public static String httpPostMethod(String postDataUrl, String filterCheckPublicKey, String sendStr,
            BufferedReader reader) throws IOException {
        return httpMethod(postDataUrl, filterCheckPublicKey, "POST", sendStr, reader);
    }

    public static String httpGetMethod(String postDataUrl, String filterCheckPublicKey, String sendStr,
            BufferedReader reader) throws IOException {
        return httpMethod(postDataUrl, filterCheckPublicKey, "GET", sendStr, reader);
    }

    private static String httpMethod(String postDataUrl, String filterCheckPublicKey, String method, String sendStr,
            BufferedReader reader) throws IOException {
        String strMessage;
        StringBuilder builder = new StringBuilder();
        // 接报文的地址
        URL uploadServlet = new URL(postDataUrl);
        HttpURLConnection servletConnection = (HttpURLConnection) uploadServlet.openConnection();
        // 设置连接参数
        servletConnection.setRequestProperty("Charsert", "UTF-8");
        servletConnection.setRequestMethod(method);
        servletConnection.setRequestProperty(DefConstants.X_ACCESS_SIGN_CHECK, filterCheckPublicKey);
        servletConnection.setRequestProperty(DefConstants.CONTENT_TYPE, DefConstants.XML_CONTENT);
        servletConnection.setRequestProperty(DefConstants.CONTENT_LENGTH, Integer.toString(sendStr.length()));
        if (!("GET".equals(method))) {
            servletConnection.setDoOutput(true);
        }
        servletConnection.setDoInput(true);
        servletConnection.setAllowUserInteraction(true);

        // 开启流，写入XML数据
        OutputStream output = servletConnection.getOutputStream();
        log.info("正在发送报文......");

        output.write(sendStr.toString().getBytes());
        output.flush();
        output.close();

        // 获取返回的数据
        InputStream inputStream = servletConnection.getInputStream();
        reader = new BufferedReader(new InputStreamReader(inputStream));
        while ((strMessage = reader.readLine()) != null) {
            builder.append(strMessage);
        }
        return builder.toString();
    }
}
