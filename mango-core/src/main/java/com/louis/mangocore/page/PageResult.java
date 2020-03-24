package com.louis.mangocore.page;

import java.util.List;

/*
* 对分页查询的结果进行了统一的封装，
* 结果返回业务数据和分页数据
* */
public class PageResult {
    private int pageNum; //当前页码
    private int pageSize;  //每页数量
    private long totalSize;  //记录总数
    private int totalPages;
    private List<?> content;

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public long getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(long totalSize) {
        this.totalSize = totalSize;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public List<?> getContent() {
        return content;
    }

    public void setContent(List<?> content) {
        this.content = content;
    }
}
