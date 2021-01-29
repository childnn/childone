package org.anonymous.beans;

import java.util.List;

/**
 * @author child
 * 2019/4/1 18:31
 * 分页数据
 * 信息总条数: totalCount -- 数据库
 * 总页数: totalPages -- 总条数/每页数据数
 * 每页数据条数: pageSize -- 自定义
 * 当前页: pageNumber -- 页面带回
 * 当前页数据: list<T> -- 数据库
 */
public class PageBean1 {
    private final Integer totalCount;
    private final Integer totalPages;
    private final Integer pageSize;
    private final List<Contact> list;
    private Integer pageNumber = 1;

    public PageBean1(Integer totalCount, Integer totalPages, Integer pageSize, Integer pageNumber, List<Contact> list) {
        this.totalCount = totalCount;
        this.totalPages = totalPages;
        this.pageSize = pageSize;
        this.pageNumber = pageNumber;
        this.list = list;
    }

    //el 表达式调用 get 方法
    public Integer getTotalCount() {
        return totalCount;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public List<Contact> getList() {
        return list;
    }

    @Override
    public String toString() {
        return "PageBean{" +
                "totalCount=" + totalCount +
                ", totalPages=" + totalPages +
                ", pageSize=" + pageSize +
                ", pageNumber=" + pageNumber +
                ", list=" + list +
                '}';
    }
}
