package cn.topicstudy.jhttp.common;

import cn.topicstudy.jutil.basic.error.BaseErrorCodeEnum;

public enum JhttpErrorCodeEnum implements BaseErrorCodeEnum {
    SYS_ERROR("TS-JHTTP-0-0", "系统错误"),

    //
    STATUS_CODE_NOT_200("U-TS-JHTTP-1-0","请求失败，{0}，{1}"),

    ;
    private String errorCode;
    private String errorMsg;

    JhttpErrorCodeEnum(String errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    @Override
    public String getErrorCode() {
        return errorCode;
    }

    @Override
    public String getErrorMsg() {
        return errorMsg;
    }
}
