package com.muieer.xuanke.entiy;

import com.muieer.xuanke.entiy.pk.ResultPK;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@IdClass(ResultPK.class)
@Table(name = "result")
public class ResultEntity implements Serializable {

    @Id
    @Column(name = "pno")
    private Integer pno;

    @Id
    @Column(name = "sno")
    private Integer sno;

    @CreatedDate
    @Column(name = "createtime")
    private Date createTime;

    //实际表中并没有该字段
    @OneToOne
    @JoinColumn(name = "pno", insertable = false, updatable = false)
    private PlanEntity planEntity;


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
                "pno=" + pno +
                ", sno=" + sno +
                ", createTime=" + createTime +
                ", planEntity=" + planEntity +
                '}';
    }
}
