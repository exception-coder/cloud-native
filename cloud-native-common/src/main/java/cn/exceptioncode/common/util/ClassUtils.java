package cn.exceptioncode.common.util;

import cn.exceptioncode.common.dto.BaseResponse;
import lombok.Data;
import lombok.SneakyThrows;

import java.lang.reflect.*;
import java.util.*;

public class ClassUtils extends org.apache.commons.lang3.ClassUtils {


    public BaseResponse<BaseResponse<String>> baseResponse() {
        return null;
    }


    @SneakyThrows
    public static void main(String[] args) {
        Method method = ClassUtils.class.getMethod("baseResponse");
        Class returnType = method.getReturnType();
        TypeVariable[] typeVariables = returnType.getTypeParameters();
        for (TypeVariable typeVariable : typeVariables) {
            System.out.println(typeVariable.getName());
            Type type = method.getGenericReturnType();
            if (type instanceof ParameterizedType) {
                // 泛型类
                ParameterizedType parameterizedType = (ParameterizedType) type;
                for (Type actualTypeArgument : parameterizedType.getActualTypeArguments()) {
                    if(actualTypeArgument instanceof ParameterizedType){
                        System.out.println(actualTypeArgument.getTypeName());
                        System.out.println(((ParameterizedType) actualTypeArgument).getRawType());
                    }

                }
            }
        }


        List<String> stringList = new ArrayList();

        List<String> linkedList = new LinkedList();

        if (stringList instanceof Collection) {
            System.out.println("stringList instanceof Collection");
        }

        if (linkedList instanceof Collection) {
            System.out.println("linkedList instanceof Collection");
        }

        if (Collection.class.isAssignableFrom(stringList.getClass())) {
            System.out.println("Collection.class.isAssignableFrom(stringList.getClass())");
        }

        Field[] fields = DemoClass.class.getDeclaredFields();
        for (Field field : fields) {
            if (isAssignableFromCollection(field)) {
                System.out.println(field.getType().getName());
            }

        }
    }

    @Data
    class DemoClass {

        private List<String> stringList;

        private ArrayList<String> arrayList;

        private LinkedList<String> linkedList;

        private Map<String, String> stringStringMap;
    }

    /**
     * Determines if the collection represented by this
     *
     * @param field
     * @return
     */
    public static boolean isAssignableFromCollection(Field field) {
        Class clazz = field.getType();
        return Collection.class.isAssignableFrom(clazz);
    }


}
