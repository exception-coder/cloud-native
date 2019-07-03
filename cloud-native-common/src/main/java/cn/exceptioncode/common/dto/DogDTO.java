package cn.exceptioncode.common.dto;


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

    public String name;

    private Integer age;

    String color;

}
