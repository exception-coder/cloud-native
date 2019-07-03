package cn.exceptioncode.yapi.doc.client.enums;


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
