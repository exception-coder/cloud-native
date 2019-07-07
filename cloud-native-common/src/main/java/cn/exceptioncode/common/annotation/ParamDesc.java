package cn.exceptioncode.common.annotation;

import java.lang.annotation.*;

@Target({ElementType.PARAMETER,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ParamDesc {


    /**
     *
     * 备注
     *
     * @return
     */
    String desc() default "";


    /**
     *
     *
     * 示例
     *
     * @return
     */
    String example() default "";

    /**
     *
     * 是否必填
     *
     * @return
     */
    boolean required() default true;

}
