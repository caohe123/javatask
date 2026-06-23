package com.library.model;

import java.util.List;

public class PageBean<T> {
    // 统一字段，同时适配BookService、BorrowService
    private Integer totalCount;    // 总条数
    private Integer currentPage;   // 当前页码（BookService用）
    private Integer pageNum;      // 当前页码（BorrowService用，兼容旧代码）
    private Integer pageSize;      // 每页条数
    private Integer totalPage;     // 总页数
    private List<T> list;         // 分页数据（BookService用）
    private List<T> data;          // 分页数据（BorrowService用，兼容旧代码）

    // 无参构造
    public PageBean() {}

    // ===== 适配BookService的set/get =====
    public Integer getTotalCount() {
        return totalCount;
    }
    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }
    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
        this.pageNum = currentPage; // 同步给pageNum，两边通用
    }

    public Integer getPageSize() {
        return pageSize;
    }
    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getTotalPage() {
        return totalPage;
    }
    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public List<T> getList() {
        return list;
    }
    public void setList(List<T> list) {
        this.list = list;
        this.data = list; // 同步给data，BorrowService不用改
    }

    // ===== 适配BorrowService的旧兼容set/get =====
    public Integer getPageNum() {
        return pageNum;
    }
    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
        this.currentPage = pageNum;
    }

    public List<T> getData() {
        return data;
    }
    public void setData(List<T> data) {
        this.data = data;
        this.list = data;
    }

    // 计算总页数方法（原有保留）
    public void calcPageCount() {
        if (totalCount == null || pageSize == null || pageSize == 0) {
            this.totalPage = 0;
            return;
        }
        this.totalPage = totalCount % pageSize == 0 ? totalCount / pageSize : totalCount / pageSize + 1;
    }
}