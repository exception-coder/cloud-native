package cn.exceptioncode.api.doc.client.util;

import com.google.common.collect.Maps;

import java.util.Map;

public class ClassUtils {


    private static final Map<Class,Class> primmitiveToPrimmitiveWrapperMap = Maps.newHashMap();
    static {
        primmitiveToPrimmitiveWrapperMap.put(boolean.class,Boolean.class);
        primmitiveToPrimmitiveWrapperMap.put(char.class,Character.class);
        primmitiveToPrimmitiveWrapperMap.put(byte.class,Byte.class);
        primmitiveToPrimmitiveWrapperMap.put(short.class,Short.class);
        primmitiveToPrimmitiveWrapperMap.put(int.class,Integer.class);
        primmitiveToPrimmitiveWrapperMap.put(long.class,Long.class);
        primmitiveToPrimmitiveWrapperMap.put(float.class,Float.class);
        primmitiveToPrimmitiveWrapperMap.put(double.class,Double.class);
        primmitiveToPrimmitiveWrapperMap.put(void.class,Void.class);
    }

    /**
     *
     * 根据基本数据类型 获取对应的包装类
     *
     * @param clazz 基本数据类型
     * @return
     */
    public static Class getWrapperByPrimitive(Class clazz){
        if(clazz.isPrimitive()){
            return primmitiveToPrimmitiveWrapperMap.get(clazz);
        }else {
            return clazz;
        }

    }

}
