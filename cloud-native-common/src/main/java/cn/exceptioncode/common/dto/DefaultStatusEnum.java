package cn.exceptioncode.common.dto;

/**
 * 返回码，默认枚举对象
 */
public enum DefaultStatusEnum implements StatusEnum {

    SUCCESS(100, "操作成功"),
    SYSTEM_ERROR(101, "系统开了点小差，请稍后再试"),
    PARAM_ERROR(102, "参数绑定失败"),
    PARAM_VALIDATED_ERROR(103, "请求参数验证不通过"),
    PARAM_INVALID(104, "参数无效，请确认");


    private int code;
    private String message;

    DefaultStatusEnum(int code, String message){
        this.code = code;
        this.message = message;
    }

    @Override
    public int code(){
        return this.code;
    }

    @Override
    public String message() {
        return this.message;
    }

    @Override
    public String toString() {
        return this.code + "_" + this.message;

    }
}
