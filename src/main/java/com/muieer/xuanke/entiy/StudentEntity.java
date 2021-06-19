package com.muieer.xuanke.entiy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "student")
public class StudentEntity {

    @Column
    @Id
    private Integer sno;

    @Column
    private String sname;

    @Column
    private String gender;

    @Column
    private Integer classno;

    @Column
    private String college;

    @Column
    private String major;

    @Column
    private Integer grade;

    @Column
    private String password;

    @Column
    private String salt;

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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getClassno() {
        return classno;
    }

    public void setClassno(Integer classno) {
        this.classno = classno;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    @Override
    public String toString() {
        return "StudentEntity{" +
                "sno=" + sno +
                ", sname='" + sname + '\'' +
                ", gender='" + gender + '\'' +
                ", classno=" + classno +
                ", college='" + college + '\'' +
                ", major='" + major + '\'' +
                ", grade=" + grade +
                ", password='" + password + '\'' +
                ", salt='" + salt + '\'' +
                '}';
    }
}
