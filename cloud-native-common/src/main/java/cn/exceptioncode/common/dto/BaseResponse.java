package cn.exceptioncode.common.dto;

import cn.exceptioncode.common.annotation.ParamDesc;
import lombok.Data;

import java.io.Serializable;

/**
 * 接口返回对象的固定格式
 *
 * @param <T>
 */
@Data
public class BaseResponse<T> implements Serializable {

    @ParamDesc(desc = "响应码",example = "200")
    private int code;

    @ParamDesc(desc = "响应信息描述",example = "success")
    private String message;

    @ParamDesc(desc = "响应信息描述",example = "success",required = false)
    private T data;

    public BaseResponse() {
        this.setStatus(DefaultStatusEnum.SUCCESS);
    }

    public void setStatus(StatusEnum statusEnum) {
        this.setCode(statusEnum.code());
        this.setMessage(statusEnum.message());
    }

    public void setStatus(StatusEnum statusEnum, T data) {
        this.setStatus(statusEnum);
        this.setData(data);
    }

    public static BaseResponse success() {
        return BaseResponse.with(DefaultStatusEnum.SUCCESS);
    }

    public static <T> BaseResponse<T> success(T data) {
        return BaseResponse.with(DefaultStatusEnum.SUCCESS, data);
    }

    public static BaseResponse error() {
        return BaseResponse.with(DefaultStatusEnum.SYSTEM_ERROR);
    }

    public static <T> BaseResponse<T> error(T data) {
        return BaseResponse.with(DefaultStatusEnum.SYSTEM_ERROR, data);
    }

    public static BaseResponse with(StatusEnum statusEnum) {
        BaseResponse response = new BaseResponse();
        response.setCode(statusEnum.code());
        response.setMessage(statusEnum.message());
        return response;
    }

    public static <T> BaseResponse<T> with(StatusEnum statusEnum, T data) {
        BaseResponse response = BaseResponse.with(statusEnum);
        response.setData(data);
        return response;
    }
}
