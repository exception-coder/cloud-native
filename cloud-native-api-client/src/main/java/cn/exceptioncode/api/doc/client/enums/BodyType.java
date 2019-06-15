package cn.exceptioncode.api.doc.client.enums;

import lombok.Data;

/**
 * @author zhangkai
 */
public enum BodyType {

    RAW("raw"),
    FORM("form"),
    JSON("json");

    private String code;


    BodyType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
