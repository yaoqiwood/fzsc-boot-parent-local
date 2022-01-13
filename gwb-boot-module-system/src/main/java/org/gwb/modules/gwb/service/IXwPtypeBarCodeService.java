package org.gwb.modules.gwb.service;

import org.gwb.modules.gwb.entity.XwPtypeBarCode;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.IService;

@DS("multi-datasource-gwb")
public interface IXwPtypeBarCodeService extends IService<XwPtypeBarCode> {
}
