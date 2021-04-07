package com.yhh.xuanke.entiy;

import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "result")
public class ResultEntity {


    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer rno;

    @Column
    private Integer pno;

    @Column
    private Integer sno;

    @CreatedDate
    @Column(name = "createtime")
    private Date createTime;

    //实际表中并没有该字段
    @OneToOne
    @JoinColumn(name = "pno", insertable = false, updatable = false)
    private PlanEntity planEntity;

    public Integer getRno() {
        return rno;
    }

    public void setRno(Integer rno) {
        this.rno = rno;
    }

    public PlanEntity getPlanEntity() {
        return planEntity;
    }

    public void setPlanEntity(PlanEntity planEntity) {
        this.planEntity = planEntity;
    }

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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "ResultEntity{" +
                "rno=" + rno +
                ", pno=" + pno +
                ", sno=" + sno +
                ", createTime=" + createTime +
                ", planEntity=" + planEntity +
                '}';
    }
}
