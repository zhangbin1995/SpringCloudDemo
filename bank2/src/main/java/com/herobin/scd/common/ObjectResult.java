package com.herobin.scd.common;

import lombok.Data;

import java.io.Serializable;

@Data
public class ObjectResult<T> implements Serializable {

    private static final long serialVersionUID = 3343725580779102777L;
    private String code;
    private String msg;
    private T data;

    public static final ObjectResult SUCCESS = new ObjectResult("200", "操作成功");
    public static final ObjectResult ERROR = new ObjectResult("400", "操作失败");
    public static final ObjectResult EXCEPTION = new ObjectResult("500", "服务异常");

    public ObjectResult() {
    }

    public ObjectResult(String code, String msg) {
        this(code, msg, (T)null);
    }

    public ObjectResult(String code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static ObjectResult error(String msg) {
        return new ObjectResult(ERROR.code, msg);
    }

    public static ObjectResult success(Object object) {
        ObjectResult r = new ObjectResult("200", "操作成功");
        r.setData(object);
        return r;
    }

    @Override
    public String toString() {
        return "ObjectResult(code=" + this.getCode() + ", msg=" + this.getMsg() + ", data=" + this.getData() + ")";
    }
}
