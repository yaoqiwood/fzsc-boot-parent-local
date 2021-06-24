package org.gwb.modules.gwb.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data
@TableName("his_ptpye_sync")
public class HisPtpyeSync {

    @TableId(value = "hps_id")
    private String hpsId;

    private String remoteHost;

    private Date receiveTime;

    private Date updatePriceTime;

    private String updateTag;

    private Integer affectNum;

    private String status;

    private Date createTime;

    private String remark;
}
