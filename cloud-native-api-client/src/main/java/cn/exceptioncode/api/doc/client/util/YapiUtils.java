package cn.exceptioncode.api.doc.client.util;

import org.apache.commons.lang3.ClassUtils;

public class YapiUtils {
    public static String getPropertiesType(Class clazz) {
        if(clazz.isPrimitive()){
        }else {
            if(ClassUtils.isAssignable(clazz,Number.class)){
                return "number";
            }
            if(ClassUtils.isAssignable(clazz,Boolean.class)){
                return "boolean";
            }

        }

        return "object";
    }
    
    public static void main(String[] args) {

        System.out.println(Integer.class.isPrimitive());
    }
}
