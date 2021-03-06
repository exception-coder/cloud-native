package cn.exceptioncode.common.util;

import cn.exceptioncode.common.dto.BaseResponse;
import lombok.Data;
import lombok.SneakyThrows;
import org.apache.commons.lang3.reflect.TypeUtils;

import java.lang.reflect.*;
import java.util.*;

public class ClassUtils extends org.apache.commons.lang3.ClassUtils {


    public BaseResponse<List<String>> baseResponse() {
        return null;
    }


    public static List<Map<TypeVariable<?>, Type>> tmp(ParameterizedType parameterizedType ,List<Map<TypeVariable<?>, Type>> typeArgumentsList){
        Map<TypeVariable<?>, Type> typeVariableTypeMap = TypeUtils.getTypeArguments(parameterizedType);
        typeArgumentsList.add(typeVariableTypeMap);
        for (Map.Entry<TypeVariable<?>, Type> typeVariableTypeEntry : typeVariableTypeMap.entrySet()) {
            if( typeVariableTypeEntry.getValue() instanceof  ParameterizedType){
                tmp((ParameterizedType)  typeVariableTypeEntry.getValue(),typeArgumentsList);
            }
        }
        return typeArgumentsList;
    }

    @SneakyThrows
    public static void main(String[] args) {



        LinkedHashMap<String, LinkedHashMap<String, String>> map = new LinkedHashMap<>();
        Method method = ClassUtils.class.getMethod("baseResponse");
        Type type = method.getGenericReturnType();

        List<Map<TypeVariable<?>, Type>> typeArgumentsList = new ArrayList<>(10);

        if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;

            System.out.println(tmp(parameterizedType,typeArgumentsList));
            map = getTypeVariableWithParamType(parameterizedType, map);
        }
        System.out.println(map);


//        List<String> stringList = new ArrayList();
//
//        List<String> linkedList = new LinkedList();
//
//        if (stringList instanceof Collection) {
//            System.out.println("stringList instanceof Collection");
//        }
//
//        if (linkedList instanceof Collection) {
//            System.out.println("linkedList instanceof Collection");
//        }
//
//        if (Collection.class.isAssignableFrom(stringList.getClass())) {
//            System.out.println("Collection.class.isAssignableFrom(stringList.getClass())");
//        }
//
//        Field[] fields = DemoClass.class.getDeclaredFields();
//        for (Field field : fields) {
//            if (isAssignableFromCollection(field)) {
//                System.out.println(field.getType().getName());
//            }
//
//        }
    }


    /**
     *
     * 获取类泛型标识符与具体泛型参数映射关系集合
     *
     * @param parameterizedType
     * @param map
     * @return
     * @throws ClassNotFoundException
     */
    public static LinkedHashMap<String, LinkedHashMap<String, String>> getTypeVariableWithParamType(ParameterizedType parameterizedType, LinkedHashMap<String, LinkedHashMap<String, String>> map) throws ClassNotFoundException {
        String parameterizedTypeName = parameterizedType.getRawType().getTypeName();
        Class clazz = Class.forName(parameterizedTypeName);
        TypeVariable[] typeVariables = clazz.getTypeParameters();
        for (TypeVariable typeVariable : typeVariables) {
            String typeVariableName = typeVariable.getName();
            LinkedHashMap<String, String> typeVariableNameWithParameterizedTypeName;
            typeVariableNameWithParameterizedTypeName = map.get(typeVariableName);
            if (typeVariableNameWithParameterizedTypeName == null) {
                typeVariableNameWithParameterizedTypeName = new LinkedHashMap<>();
            }
            map.put(parameterizedTypeName, typeVariableNameWithParameterizedTypeName);
            // 泛型类
            for (Type actualTypeArgument : parameterizedType.getActualTypeArguments()) {
                String actualTypeArgumentTypeName = actualTypeArgument.getTypeName();
                typeVariableNameWithParameterizedTypeName.put(typeVariableName, actualTypeArgumentTypeName);
                if (actualTypeArgument instanceof ParameterizedType) {
                    actualTypeArgumentTypeName = ((ParameterizedType) actualTypeArgument).getRawType().getTypeName();
                    typeVariableNameWithParameterizedTypeName.put(typeVariableName, actualTypeArgumentTypeName);
                        getTypeVariableWithParamType((ParameterizedType) actualTypeArgument, map);
                }
            }
        }
        return map;
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
