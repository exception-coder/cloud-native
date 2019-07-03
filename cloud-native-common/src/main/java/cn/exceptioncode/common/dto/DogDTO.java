package cn.exceptioncode.common.dto;


import cn.exceptioncode.common.annotations.ParamDesc;
import lombok.AllArgsConstructor;
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
public class DogDTO {

    @ParamDesc(desc = "呢称",example = "jock")
    private String name;

    @ParamDesc(desc = "年龄",example = "1",required = false)
    private Integer age;

    String color;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
