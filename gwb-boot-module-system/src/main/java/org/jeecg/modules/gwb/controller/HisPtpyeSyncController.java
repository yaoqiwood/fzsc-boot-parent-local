package org.jeecg.modules.gwb.controller;

import org.jeecg.common.api.vo.DataGridResult;
import org.jeecg.modules.gwb.entity.HisPtpyeSync;
import org.jeecg.modules.gwb.service.IHisPtpyeSyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/hisPtypeSync")
public class HisPtpyeSyncController {

    @Autowired
    private IHisPtpyeSyncService hisPtpyeSyncService;

    @GetMapping(value = "data-grid.json")
    public DataGridResult<Object> dataGridJson(@RequestParam Integer pageNo, @RequestParam Integer pageSize,
            String search, @RequestParam String sortBy, @RequestParam String order) {
        Page<HisPtpyeSync> ptpyeSyncPage = new Page<>(pageNo, pageSize);
        QueryWrapper<HisPtpyeSync> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().orderBy(true, "desc".equals(order), HisPtpyeSync::getCreateTime);
        IPage<HisPtpyeSync> hisPtpyeSyncIpage = hisPtpyeSyncService.page(ptpyeSyncPage, queryWrapper);
        return DataGridResult.buildPagerResult(hisPtpyeSyncIpage.getRecords(), hisPtpyeSyncIpage.getCurrent(),
                hisPtpyeSyncIpage.getTotal(), hisPtpyeSyncIpage.getSize());
    }
}
