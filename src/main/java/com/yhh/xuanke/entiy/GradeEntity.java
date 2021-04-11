package com.yhh.xuanke.entiy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "grade")
public class GradeEntity {

    @Id
    @Column
    private Integer gno;

    @Column
    private Integer year;

    @Column
    private Integer term;

    @Column
    private String cno;

    @Column
    private String cname;

    @Column
    private String cnature;

    @Column
    private Float credit;

    @Column
    private Float grade;

    @Column
    private String bukao;

    @Column
    private String chongxiu;

    @Column
    private Integer sno;

    public Integer getGno() {
        return gno;
    }

    public void setGno(Integer gno) {
        this.gno = gno;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getTerm() {
        return term;
    }

    public void setTerm(Integer term) {
        this.term = term;
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

    public String getCnature() {
        return cnature;
    }

    public void setCnature(String cnature) {
        this.cnature = cnature;
    }

    public Float getCredit() {
        return credit;
    }

    public void setCredit(Float credit) {
        this.credit = credit;
    }

    public Float getGrade() {
        return grade;
    }

    public void setGrade(Float grade) {
        this.grade = grade;
    }

    public String getBukao() {
        return bukao;
    }

    public void setBukao(String bukao) {
        this.bukao = bukao;
    }

    public String getChongxiu() {
        return chongxiu;
    }

    public void setChongxiu(String chongxiu) {
        this.chongxiu = chongxiu;
    }

    public Integer getSno() {
        return sno;
    }

    public void setSno(Integer sno) {
        this.sno = sno;
    }

    @Override
    public String toString() {
        return "GradeEntity{" +
                "gno=" + gno +
                ", year=" + year +
                ", term=" + term +
                ", cno='" + cno + '\'' +
                ", cname='" + cname + '\'' +
                ", cnature='" + cnature + '\'' +
                ", credit=" + credit +
                ", grade=" + grade +
                ", bukao='" + bukao + '\'' +
                ", chongxiu='" + chongxiu + '\'' +
                ", sno=" + sno +
                '}';
    }
}
