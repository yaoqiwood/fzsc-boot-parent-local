package org.gwb.common.api.vo;

import lombok.Data;

@Data
public class DataGridPageResult {
    private long pageNo;

    private long total;

    private long pageSize;
}
