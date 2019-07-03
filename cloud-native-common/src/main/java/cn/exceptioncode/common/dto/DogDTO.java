package cn.exceptioncode.common.dto;


import cn.exceptioncode.common.annotations.ParamDesc;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 *
 *
 * @author zhangkai
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Data
public class DogDTO {

    @ParamDesc(desc = "呢称",example = "jock")
    private String name;

    @ParamDesc(desc = "年龄",example = "1",required = false)
    private Integer age;

    String color;

}
