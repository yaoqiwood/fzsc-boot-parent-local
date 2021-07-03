package org.gwb.modules.gwb.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
* <p>
* 
* </p>
*
* @author Mikko
* @since 2021-06-24
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("xw_baseupdatetag")
public class XwBaseupdatetag implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableField("basetype")
    private String basetype;

    @TableField("updatetag")
    private Integer updatetag;

}
