package org.gwb.modules.demo.test.service;

import org.gwb.common.system.base.service.JeecgService;
import org.gwb.modules.demo.test.entity.JeecgDemo;

import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * @Description: jeecg 测试demo
 * @Author: jeecg-boot
 * @Date:  2018-12-29
 * @Version: V1.0
 */
public interface IJeecgDemoService extends JeecgService<JeecgDemo> {
	
	public void testTran();
	
	public JeecgDemo getByIdCacheable(String id);
	
	/**
	 * 查询列表数据 在service中获取数据权限sql信息
	 * @param pageSize
	 * @param pageNo
	 * @return
	 */
	IPage<JeecgDemo> queryListWithPermission(int pageSize,int pageNo);
}
