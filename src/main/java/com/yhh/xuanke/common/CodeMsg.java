package com.yhh.xuanke.common;

public enum CodeMsg {
    PASSWORD_WRONG(500212, "密码错误"),
    STUDENT_NUM_NOT_EXISTS(500213, "学号不存在"),
    ACCOUNT_LOCKED(500213, "账户被锁定");


    private int code;
    private String msg;

    CodeMsg(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public static CodeMsg fromCode(int code) {
        for (CodeMsg codeMsg : CodeMsg.values()) {
            if (code == codeMsg.getCode()) {
                return codeMsg;
            }
        }
        return null;
    }

}
