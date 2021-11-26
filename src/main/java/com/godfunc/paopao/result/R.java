package com.godfunc.paopao.result;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class R<T> implements Serializable {

    private static final String SUCCESS_MSG = "执行成功";
    private static final long SUCCESS_CODE = 0;

    private static final String ERROR_MSG = "操作失败";
    private static final long ERROR_CODE = -1;
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * 业务错误码
     */
    private long code;
    /**
     * 结果集
     */
    private T data;
    /**
     * 描述
     */
    private String msg;

    public R() {
        // to do nothing
    }

    public static <T> R<T> ok(T data) {
        return restResult(data, SUCCESS_CODE, SUCCESS_MSG);
    }

    public static <T> R<T> failed(String msg) {
        return restResult(null, ERROR_CODE, msg);
    }


    private static <T> R<T> restResult(T data, long code, String msg) {
        R<T> apiResult = new R<>();
        apiResult.setCode(code);
        apiResult.setData(data);
        apiResult.setMsg(msg);
        return apiResult;
    }
}