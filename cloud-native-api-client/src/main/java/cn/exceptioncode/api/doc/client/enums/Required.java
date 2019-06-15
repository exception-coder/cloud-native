package cn.exceptioncode.api.doc.client.enums;

/**
 * @author zhangkai
 */
public enum Required {

    TRUE(1,"必填"),

    FALSE(2,"非必填");

    private Integer code;

    private String desc;

    Required(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
