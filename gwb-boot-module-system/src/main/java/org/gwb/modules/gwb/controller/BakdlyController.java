package org.gwb.modules.gwb.controller;

import org.gwb.modules.gwb.entity.Bakdly;
import org.gwb.modules.gwb.service.IBakdlyService;
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
@RequestMapping("/gwb/bakdly")
public class BakdlyController extends BaseController<Bakdly, IBakdlyService> {
    public BakdlyController() {
        super(Bakdly.class);
    }
}
