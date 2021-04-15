package com.yhh.xuanke.exception;

import com.yhh.xuanke.common.CodeMsg;

public class GlobalException extends RuntimeException {

    private CodeMsg codeMsg;

    public GlobalException(CodeMsg codeMsg) {
        super(codeMsg.getMsg());
        this.codeMsg = codeMsg;
    }

    public CodeMsg getCodeMsg() {
        return codeMsg;
    }

}
