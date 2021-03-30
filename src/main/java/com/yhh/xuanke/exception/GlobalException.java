package com.yhh.xuanke.exception;

import com.yhh.xuanke.common.CodeMsg;

public class GlobalException extends RuntimeException {

    private final CodeMsg codeMsg;

    public GlobalException(CodeMsg codeMsg) {
        super(codeMsg.toString());
        this.codeMsg = codeMsg;
    }

    public CodeMsg getCodeMsg() {
        return codeMsg;
    }

}
