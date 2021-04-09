package com.yhh.xuanke.dto;

import java.util.List;

public class ListDTO<T> {

    private List<T> list;
    private int page;
    private int size;
    //总页数
    private long totalPages;

    public ListDTO(List<T> list, int page, int size, long totalPages) {
        this.list = list;
        this.page = page;
        this.size = size;
        this.totalPages = totalPages;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public long getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}
