package com.yhh.xuanke.dto;

import java.util.List;

public class ListDTO<T> {

    private List<T> list;
    private int page;
    private int size;
    private long totalNum;

    public ListDTO(List<T> list, int page, int size, long totalNum) {
        this.list = list;
        this.page = page;
        this.size = size;
        this.totalNum = totalNum;
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

    public long getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(int totalNum) {
        this.totalNum = totalNum;
    }
}
