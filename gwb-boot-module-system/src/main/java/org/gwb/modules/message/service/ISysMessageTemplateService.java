package org.gwb.modules.message.service;

import java.util.List;

import org.gwb.common.system.base.service.JeecgService;
import org.gwb.modules.message.entity.SysMessageTemplate;

/**
 * @Description: 消息模板
 * @Author: jeecg-boot
 * @Date:  2019-04-09
 * @Version: V1.0
 */
public interface ISysMessageTemplateService extends JeecgService<SysMessageTemplate> {
    List<SysMessageTemplate> selectByCode(String code);
}
