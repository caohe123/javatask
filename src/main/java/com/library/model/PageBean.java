package com.library.model;

import java.util.List;

// 通用分页工具类，支持所有类型的数据
public class PageBean<T> {
    // 1. 当前页的数据列表
    private List<T> list;
    // 2. 总记录数
    private int totalCount;
    // 3. 当前页码
    private int currentPage;
    // 4. 每页显示条数
    private int pageSize;
    // 5. 总页数
    private int totalPage;

    // 生成getter和setter方法
    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
}