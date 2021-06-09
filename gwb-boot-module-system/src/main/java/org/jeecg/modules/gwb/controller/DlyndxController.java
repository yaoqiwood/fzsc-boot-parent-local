package org.jeecg.modules.gwb.controller;

import org.jeecg.modules.gwb.entity.Dlyndx;
import org.jeecg.modules.gwb.service.IDlyndxService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import meme.cat.basic.controller.BaseController;

/**
* <p>
*  前端控制器
* </p>
*
* @author Mikko
* @since 2021-06-09
*/
@RestController
@RequestMapping("/gwb/dlyndx")
public class DlyndxController extends BaseController<Dlyndx, IDlyndxService> {
    public DlyndxController() {
        super(Dlyndx.class);
    }
}
