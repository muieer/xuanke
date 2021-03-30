package com.yhh.xuanke.entiy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user")
public class UserEntity {

    @Id
    @Column
    private int sno;

    @Column
    private String name;

    @Column
    private String password;

    @Column
    private Integer grade;

    @Column
    private Integer major;

    @Column
    private Integer clazz;

    @Column
    private String salt;

    public int getSno() {
        return sno;
    }

    public void setSno(int sno) {
        this.sno = sno;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public Integer getMajor() {
        return major;
    }

    public void setMajor(Integer major) {
        this.major = major;
    }

    public Integer getClazz() {
        return clazz;
    }

    public void setClazz(Integer clazz) {
        this.clazz = clazz;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    @Override
    public String toString() {
        return "User{" +
                "sno=" + sno +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", grade=" + grade +
                ", major=" + major +
                ", clazz=" + clazz +
                ", salt='" + salt + '\'' +
                '}';
    }
}
