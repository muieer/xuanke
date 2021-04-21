package com.yhh.xuanke.dto;

public class ExposerDTO {

    private Boolean exposed = false;

    private String md5;

    private Integer pno;

    //暴露秒杀接口
    public ExposerDTO(Boolean exposed, String md5) {
        this.exposed = exposed;
        this.md5 = md5;
    }

    public Boolean getExposed() {
        return exposed;
    }

    public void setExposed(Boolean exposed) {
        this.exposed = exposed;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public Integer getPno() {
        return pno;
    }

    public void setPno(Integer pno) {
        this.pno = pno;
    }

}
