package cn.exceptioncode.api.doc.client.annotations;

import java.lang.annotation.*;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ParamDesc {


    /**
     *
     * 参数描述
     *
     * @return
     */
    String desc() default "";


    /**
     *
     *
     * 参数样例
     *
     * @return
     */
    String example() default "";


}
