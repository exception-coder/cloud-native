package cn.exceptioncode.yapi.doc.client.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ApiPropertiesDTO {

    public ApiPropertiesDTO(String type, String description, Mock mock) {
        this.type = type;
        this.description = description;
        this.mock = mock;
    }

    private Object properties;

    private String type;

    private String description;

    private Mock mock;

    @Data
    static class Mock{
        private String mock;
    }

}
