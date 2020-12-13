package org.jeecg.modules.gwb.controller;

import org.jeecg.modules.gwb.service.IPtypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/ptype")
public class PtypeController {

    @Autowired
    private IPtypeService ptypeService;

    // @PostMapping(value = "test"){
    //
    // }

}
