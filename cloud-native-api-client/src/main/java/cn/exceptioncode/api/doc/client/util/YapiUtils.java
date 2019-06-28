package cn.exceptioncode.api.doc.client.util;

import org.apache.commons.lang3.ClassUtils;

import java.util.Collection;

public class YapiUtils {


    public static String getPropertiesType(Class clazz) {
        if(clazz.isPrimitive()){
            System.out.println(clazz);
            clazz.getClassLoader();
        }else {
            if(ClassUtils.isAssignable(clazz,Number.class)){
                if(ClassUtils.isAssignable(clazz,Integer.class)){
                    return "integer";
                }
                return "number";
            }
            if(ClassUtils.isAssignable(clazz,Boolean.class)){
                return "boolean";
            }
            if(ClassUtils.isAssignable(clazz,String.class)){
                return "string";
            }
            if (ClassUtils.isAssignable(clazz, Collection.class)||clazz.isArray()){
                return "array";
            }

        }

        return "object";
    }


    public static void main(String[] args) {
        System.out.println(Integer.class.isPrimitive());
    }
}
