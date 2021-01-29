package org.anonymous.beans;

import java.util.List;

/**
 * @author child
 * 2019/3/31 14:04
 * 当前页的数据: list
 * 当前页 : pageNumber
 * 总条数 : totalCount
 * 总页数 : totalPages
 * 每页要显示的条数 : pageSize
 */
public class PageBean {
    private List<Contact> list;
    private Integer pageNumber;
    private Integer totalCount;
    private Integer totalPages;
    private Integer pageSize;

    public PageBean() {
    }

    public PageBean(List<Contact> list, Integer pageNumber, Integer totalCount, Integer totalPages, Integer pageSize) {
        this.list = list;
        this.pageNumber = pageNumber;
        this.totalCount = totalCount;
        this.totalPages = totalPages;
        this.pageSize = pageSize;
    }

    public List<Contact> getList() {
        return list;
    }

    public void setList(List<Contact> list) {
        this.list = list;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
