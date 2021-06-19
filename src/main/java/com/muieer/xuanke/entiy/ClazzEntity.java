package com.muieer.xuanke.entiy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "clazz")
public class ClazzEntity {

    @Id
    @Column
    private String cno;

    @Column
    private String cname;
    @Column
    private Float credit;

    @Column
    private Byte xueshi;

    @Column
    private String starttoend;

    @Column
    private String testtype;

    @Column
    private String cnature;

    @Column
    private Integer naturecode;

    @Column
    private String college;

    public String getCno() {
        return cno;
    }

    public void setCno(String cno) {
        this.cno = cno;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public Float getCredit() {
        return credit;
    }

    public void setCredit(Float credit) {
        this.credit = credit;
    }

    public Byte getXueshi() {
        return xueshi;
    }

    public void setXueshi(Byte xueshi) {
        this.xueshi = xueshi;
    }

    public String getStarttoend() {
        return starttoend;
    }

    public void setStarttoend(String starttoend) {
        this.starttoend = starttoend;
    }

    public String getTesttype() {
        return testtype;
    }

    public void setTesttype(String testtype) {
        this.testtype = testtype;
    }

    public String getCnature() {
        return cnature;
    }

    public void setCnature(String cnature) {
        this.cnature = cnature;
    }

    public Integer getNaturecode() {
        return naturecode;
    }

    public void setNaturecode(Integer naturecode) {
        this.naturecode = naturecode;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    @Override
    public String toString() {
        return "ClazzEntity{" +
                "cno='" + cno + '\'' +
                ", cname='" + cname + '\'' +
                ", credit=" + credit +
                ", xueshi=" + xueshi +
                ", starttoend='" + starttoend + '\'' +
                ", testtype='" + testtype + '\'' +
                ", cnature='" + cnature + '\'' +
                ", naturecode=" + naturecode +
                ", college='" + college + '\'' +
                '}';
    }
}
