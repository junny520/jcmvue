package com.haiyishuzi.haiyishuzijcm.api;

import androidx.annotation.NonNull;

import java.io.Serializable;

import javax.inject.Inject;

/**
 * 查询参数的通用封装
 *
 * @param <T>
 * @author Administrator
 */
public class QueryInfo<T> implements Serializable {


    @Inject
    public QueryInfo() {
    }

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private Integer pageNum = 1;
    // 默认0查询全部
    private int pageSize = 20;

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    //不允许为空，因为保存离线数据的时候查询条件为key，若为空会出现只有pagenume和pagesize的key
    @Inject
    T query;

    @NonNull
    public T getQuery() {
        return query;
    }

    public void setQuery(@NonNull T query) {
        this.query = query;
    }

}
