package org.gwb.modules.gwb.service;

import java.io.IOException;

import org.gwb.modules.gwb.entity.DlySale;
import org.gwb.modules.gwb.entity.HisPtpyeSync;

import com.baomidou.mybatisplus.extension.service.IService;

public interface IDlySaleService extends IService<DlySale> {

    /**
     * 同步每日销售数据
     * @return
     */
    HisPtpyeSync syncDlySaleInfFromServer() throws IOException;

}
