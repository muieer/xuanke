package com.yhh.xuanke.entiy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "kaoshi")
public class KaoShiEntity {

    @Id
    @Column
    private Integer kno;

    @Column
    private String cno;

    @Column
    private String cname;

    @Column
    private String sname;

    @Column
    private String time;

    @Column
    private String location;

    @Column
    private String type;

    @Column
    private Integer seat;

    @Column
    private Integer sno;

    public Integer getKno() {
        return kno;
    }

    public void setKno(Integer kno) {
        this.kno = kno;
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getSeat() {
        return seat;
    }

    public void setSeat(Integer seat) {
        this.seat = seat;
    }

    public Integer getSno() {
        return sno;
    }

    public void setSno(Integer sno) {
        this.sno = sno;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    @Override
    public String toString() {
        return "KaoShiEntity{" +
                "kno=" + kno +
                ", cno='" + cno + '\'' +
                ", cname='" + cname + '\'' +
                ", sname='" + sname + '\'' +
                ", time='" + time + '\'' +
                ", location='" + location + '\'' +
                ", type='" + type + '\'' +
                ", seat=" + seat +
                ", sno=" + sno +
                '}';
    }
}
