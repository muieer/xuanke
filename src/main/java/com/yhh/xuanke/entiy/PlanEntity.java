package com.yhh.xuanke.entiy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "plan")
public class PlanEntity {

    @Column
    @Id
    private Integer pno;

    @Column
    private String cno;

    @Column
    private String cname;

    @Column
    private String cteacher;

    @Column
    private String studytime;

    @Column
    private String studylocation;

    @Column
    private Float credit;

    @Column
    private Byte xueshi;

    @Column
    private String starttoend;

    @Column
    private String cnature;

    @Column
    private Integer naturecode;

    @Column
    private String college;

    @Column
    private Integer capacity;

    @Column
    private Integer num;

    public Integer getPno() {
        return pno;
    }

    public void setPno(Integer pno) {
        this.pno = pno;
    }

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

    public String getCteacher() {
        return cteacher;
    }

    public void setCteacher(String cteacher) {
        this.cteacher = cteacher;
    }

    public String getStudytime() {
        return studytime;
    }

    public void setStudytime(String studytime) {
        this.studytime = studytime;
    }

    public String getStudylocation() {
        return studylocation;
    }

    public void setStudylocation(String studylocation) {
        this.studylocation = studylocation;
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

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    @Override
    public String toString() {
        return "PlanEntity{" +
                "pno=" + pno +
                ", cno='" + cno + '\'' +
                ", cname='" + cname + '\'' +
                ", cteacher='" + cteacher + '\'' +
                ", studytime='" + studytime + '\'' +
                ", studylocation='" + studylocation + '\'' +
                ", credit=" + credit +
                ", xueshi=" + xueshi +
                ", starttoend='" + starttoend + '\'' +
                ", cnature='" + cnature + '\'' +
                ", naturecode=" + naturecode +
                ", college='" + college + '\'' +
                ", capacity=" + capacity +
                ", num=" + num +
                '}';
    }
}
