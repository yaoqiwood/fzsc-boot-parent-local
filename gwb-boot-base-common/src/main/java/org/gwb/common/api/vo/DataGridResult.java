package org.gwb.common.api.vo;

import java.io.Serializable;

import lombok.Data;

@Data
public class DataGridResult<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private boolean result;

    private T data;

    private String message;

    private DataGridPageResult pager;

    public DataGridResult() {
    }

    public static DataGridResult<Object> buildPagerResult(Object data, long page, long recTotal, long recPerPage) {
        DataGridResult<Object> dataGridResult = new DataGridResult<>();
        dataGridResult.setResult(true);
        dataGridResult.setData(data);
        dataGridResult.setMessage("");
        DataGridPageResult dataGridPageResult = new DataGridPageResult();
        dataGridPageResult.setPageNo(page);
        dataGridPageResult.setTotal(recTotal);
        dataGridPageResult.setPageSize(recPerPage);
        dataGridResult.setPager(dataGridPageResult);
        return dataGridResult;
    }
}
