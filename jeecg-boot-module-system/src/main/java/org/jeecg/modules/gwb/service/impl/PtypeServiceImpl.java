package org.jeecg.modules.gwb.service.impl;

import java.io.*;
import java.net.*;
import java.util.Date;
import java.util.List;

import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.common.util.HttpXmlBuilderUtil;
import org.jeecg.modules.gwb.entity.Ptype;
import org.jeecg.modules.gwb.mapper.PtypeMapper;
import org.jeecg.modules.gwb.service.IPtypeService;
import org.jeecg.modules.shiro.vo.DefContants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PtypeServiceImpl extends ServiceImpl<PtypeMapper, Ptype> implements IPtypeService {

    @Value("${ptype.postDataUrl}")
    private String postDataUrl;

    @Value("${ptype.filter.check.public.key}")
    private String filterCheckPublicKey;

    @Override
    public void test() {
        Integer index = 800;
        QueryWrapper<Ptype> ptypeQueryWrapper = new QueryWrapper<>();
        ptypeQueryWrapper.lambda().gt(Ptype::getRowindex, index);
        List<Ptype> ptypeList = this.list(ptypeQueryWrapper);
        ptypeList.forEach(item -> {
            log.info(item.getPfullname());
        });
    }

    @Override
    public void syncSendData2Server(Integer updateTag) throws IOException {
        log.info("查询封装传输数据开始......");
        QueryWrapper<Ptype> queryWrapper4LookUp = new QueryWrapper<>();
        queryWrapper4LookUp.lambda().gt(Ptype::getUpdatetag, updateTag);
        List<Ptype> ptypeList = list(queryWrapper4LookUp);
        if (ptypeList.size() == 0) {
            throw new JeecgBootException("没有检测到新的商品信息更新");
        }
        StringBuilder sendSBData = new StringBuilder(JSONArray.toJSONString(ptypeList));
        log.info("查询数据封装完毕\n数据为：" + sendSBData.toString());

        StringBuilder sendStr = HttpXmlBuilderUtil.buildSendDataBuilder(sendSBData, new Date());
        BufferedReader reader = null;
        try {
            String strMessage;
            StringBuilder builder = new StringBuilder();
            // 接报文的地址
            URL uploadServlet = new URL(postDataUrl);
            HttpURLConnection servletConnection = (HttpURLConnection) uploadServlet.openConnection();
            // 设置连接参数
            servletConnection.setRequestMethod("POST");
            servletConnection.setRequestProperty(DefContants.X_ACCESS_SIGN_CHECK, filterCheckPublicKey);
            servletConnection.setDoOutput(true);
            servletConnection.setDoInput(true);
            servletConnection.setAllowUserInteraction(true);

            // 开启流，写入XML数据
            OutputStream output = servletConnection.getOutputStream();
            log.info("发送的报文：");
            log.info(sendStr.toString());

            output.write(sendStr.toString().getBytes());
            output.flush();
            output.close();

            // 获取返回的数据
            InputStream inputStream = servletConnection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(inputStream));
            while ((strMessage = reader.readLine()) != null) {
                builder.append(strMessage);
            }
            log.info("接收返回值:" + builder);

        } catch (ConnectException | MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
    }
}
