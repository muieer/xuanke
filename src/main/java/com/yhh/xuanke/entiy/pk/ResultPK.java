package com.yhh.xuanke.entiy.pk;

import java.io.Serializable;

//构造联合主键类
public class ResultPK implements Serializable {

    private Integer pno;

    private Integer sno;

    public Integer getPno() {
        return pno;
    }

    public void setPno(Integer pno) {
        this.pno = pno;
    }

    public Integer getSno() {
        return sno;
    }

    public void setSno(Integer sno) {
        this.sno = sno;
    }

    @Override
    public String toString() {
        return "ResultPK{" +
                "pno=" + pno +
                ", sno=" + sno +
                '}';
    }
}
