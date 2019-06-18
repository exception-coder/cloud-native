package cn.exceptioncode.api.doc.client.dto;

import lombok.Data;

/**
 *
 *
 *
 * @author zhangkai
 */
@Data
public class ParamDTO {

    public ParamDTO() {
    }

    public ParamDTO(String name, String type, String example, String desc, String required) {
        this.name = name;
        this.type = type;
        this.example = example;
        this.desc = desc;
        this.required = required;
    }

    private String name;

    private String type;

    private String example;

    private String desc;

    private String required;


}
