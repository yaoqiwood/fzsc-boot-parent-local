package org.jeecg.modules.gwb.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.ConnectException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.jeecg.common.enumerate.EnumSyncPtypeStatus;
import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.common.util.HTTPUtil;
import org.jeecg.common.util.HttpXmlBuilderUtil;
import org.jeecg.common.util.SnowflakeUtil;
import org.jeecg.modules.gwb.entity.HisPtpyeSync;
import org.jeecg.modules.gwb.entity.Ptype;
import org.jeecg.modules.gwb.mapper.PtypeMapper;
import org.jeecg.modules.gwb.service.IHisPtpyeSyncService;
import org.jeecg.modules.gwb.service.IPtypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import cn.hutool.core.util.XmlUtil;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PtypeServiceImpl extends ServiceImpl<PtypeMapper, Ptype> implements IPtypeService {

    @Value("${ptype.postDataUrl}")
    private String postDataUrl;

    @Value("${ptype.filter.check.public.key}")
    private String filterCheckPublicKey;

    @Autowired
    private IHisPtpyeSyncService hisPtpyeSyncService;

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

    /**
     * syncSendPtypeInfData2Server
     * @return
     * @throws IOException
     */
    @Override
    public HisPtpyeSync syncSendPtypeInfData2Server() throws IOException {
        Integer updateTag = hisPtpyeSyncService.selectMaxUpdateTag();
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
            String builder = HTTPUtil.httpPostMethod(postDataUrl, filterCheckPublicKey, sendStr.toString(), reader);
            log.info("接收返回值:" + builder);
            Map<String, Object> retMessageMap = XmlUtil.xmlToMap(builder.toString());
            if (!String.valueOf(true).equals(retMessageMap.get("response_success"))) {
                throw new JeecgBootException("错误，同步不成功");
            }
            // TODO 同步信息记录历史
            HisPtpyeSync hisPtpyeSync = new HisPtpyeSync();
            hisPtpyeSync.setHpsId(SnowflakeUtil.getNum().toString());
            hisPtpyeSync.setUpdateTag(updateTag.toString());
            hisPtpyeSync.setStatus(EnumSyncPtypeStatus.PTYPE.getCode());
            hisPtpyeSync.setCreateTime(new Date());
            hisPtpyeSync.setReceiveTime(new Date());
            hisPtpyeSync.setRemoteHost(postDataUrl);
            return hisPtpyeSync;
            // saveHisInf(hisPtpyeSync);
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
        return null;
    }

    // @DS("master")
    // private void saveHisInf(HisPtpyeSync hisPtpyeSync4Save) {
    // hisPtpyeSyncService.save(hisPtpyeSync4Save);
    // }
}
