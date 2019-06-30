package cn.exceptioncode.api.doc.client.util;

import cn.exceptioncode.common.dto.DogDTO;
import com.google.common.collect.Maps;
import lombok.SneakyThrows;
import reactor.core.publisher.Mono;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Map;

public class ClassUtils {


    private static final Map<Class, Class> primmitiveToPrimmitiveWrapperMap = Maps.newHashMap();

    static {
        primmitiveToPrimmitiveWrapperMap.put(boolean.class, Boolean.class);
        primmitiveToPrimmitiveWrapperMap.put(char.class, Character.class);
        primmitiveToPrimmitiveWrapperMap.put(byte.class, Byte.class);
        primmitiveToPrimmitiveWrapperMap.put(short.class, Short.class);
        primmitiveToPrimmitiveWrapperMap.put(int.class, Integer.class);
        primmitiveToPrimmitiveWrapperMap.put(long.class, Long.class);
        primmitiveToPrimmitiveWrapperMap.put(float.class, Float.class);
        primmitiveToPrimmitiveWrapperMap.put(double.class, Double.class);
        primmitiveToPrimmitiveWrapperMap.put(void.class, Void.class);
    }

    /**
     * 根据基本数据类型 获取对应的包装类
     *
     * @param clazz 基本数据类型
     * @return
     */
    public static Class getWrapperByPrimitive(Class clazz) {
        if (clazz.isPrimitive()) {
            return primmitiveToPrimmitiveWrapperMap.get(clazz);
        } else {
            return clazz;
        }

    }


    public Mono reactor() {
        return Mono.just(new DogDTO());
    }


    @SneakyThrows
    public static void main(String[] args) {
        Method method = ClassUtils.class.getMethod("reactor");
        Type genericReturnType = method.getGenericReturnType();
        System.out.println(genericReturnType.getTypeName());
    }


}
