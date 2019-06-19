package cn.exceptioncode.api.doc.client.dto;

import lombok.Data;

@Data
public class ResBodyDTO {

    private String errcode;

    private String errmsg;

    private Object data;
}
