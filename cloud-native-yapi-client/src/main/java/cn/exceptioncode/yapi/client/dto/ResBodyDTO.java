package cn.exceptioncode.yapi.client.dto;

import lombok.Data;

@Data
public class ResBodyDTO {

    private String errcode;

    private String errmsg;

    private Object data;
}
