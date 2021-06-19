package com.muieer.xuanke.common;

public enum CodeMsg {
    PASSWORD_WRONG(500212, "密码错误"),
    STUDENT_NUM_NOT_EXISTS(500213, "学号不存在"),
    ACCOUNT_LOCKED(500213, "账户被锁定"),

    PlAN_OVER(500214, "此课程已无余量"),
    CHOOSE_END(1, "提交成功，请到信息查询栏中的学生选课情况查询页面查看选课结果！选课结果以此为准！"),
    CHOOSE_REPEAT(-2, "重复选课！"),
    TIME_HIT(-1, "上课时间冲突"),

    COUNT_OVER(-1, "频繁请求，请稍后再试！"),
    NO_PLAN(-1, "授课计划不存在！"),
    LESS_START_TIME(-1, "尚未到选课时间！"),
    TIME_END(-1, "选课已经结束！"),
    LINK_ERROR(-3, "请求链接错误"),

    RESULT_NOT_EXIST(-3, "此条选课记录不存在"),
    PLAN_NUM_ERROR(-4, "余量不能大于容量"),
    RESULT_NO_CHOOSE_SUCCESS(2,"退课成功！")
    ;




    private final int code;
    private final String msg;

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
