package cn.exceptioncode.yapi.client.util;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ClassUtils;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.lang.instrument.ClassDefinition;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;


@Slf4j
public class YapiClassUtils {

    private YapiClassUtils(){

    }


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


    /**
     * 判断返回值是否 `Rector` 序列
     *
     * @param method
     * @return
     */
    public static boolean returnTypeIsReactor(Method method) {
        if (method != null) {
            String typeName = method.getReturnType().getTypeName();
            try{
                String monoTypeName = Mono.class.getTypeName();
                String fluxTypeName = Flux.class.getTypeName();
                if (monoTypeName.equals(typeName) || fluxTypeName.equals(typeName)) {
                    return true;
                } else {
                    return false;
                }
            }catch (NoClassDefFoundError e){
                log.error("NoClassDefFoundError e:{}",e.getMessage());
                return false;
            }
        } else {
            return false;
        }
    }


    // TODO: 19-7-2 方法待完善 判断一个Class是否是一个泛型类

    /**
     * 获取具体响应类型及泛型 没有申明的泛型默认赋值 `Object`
     *
     * @param method
     * @return
     */
     public static Map<Class, Class> getResponseReturnType(Method method) {
        Map<Class, Class> classMap = new HashMap<>();
        Class returnTypeClass = method.getReturnType();
        if (returnTypeIsReactor(method)) {
            String genericReturnTypeName = method.getGenericReturnType().getTypeName();
            // 获取 `Reactor` 序列 `Mono`、`Flux` 元素中申明的泛型 genericTypeName
            String genericTypeName = getFirstGenericTypeName(genericReturnTypeName);
            if (genericTypeName != null) {
                String classTypeName = getClassByGenericTypeName(genericTypeName);
                // 序列元素申明了具体泛型
                genericTypeName = getFirstGenericTypeName(genericTypeName);
                if (genericTypeName != null) {
                    try {
                        // TODO: 19-7-2 泛型中申明的泛型暂时不解析
                        genericTypeName = getClassByGenericTypeName(genericTypeName);
                        classMap.put(Class.forName(classTypeName), Class.forName(genericTypeName));
                    } catch (ClassNotFoundException e) {
                        log.error("ClassNotFoundException message:{}", e.getMessage());
                        classMap.put(Object.class, Object.class);
                    }
                } else {
                    try {
                        classMap.put(Class.forName(classTypeName), Object.class);
                    } catch (ClassNotFoundException e) {
                        log.error("ClassNotFoundException message:{}", e.getMessage());
                        classMap.put(Object.class, Object.class);
                    }

                }
            } else {
                // 序列中没有具体申明泛型
                classMap.put(Object.class, Object.class);
            }
        } else {
            String genericReturnTypeName = method.getGenericReturnType().getTypeName();
            String genericTypeName = getFirstGenericTypeName(genericReturnTypeName);
            try {
                if (genericTypeName != null) {
                    // TODO: 19-7-2 泛型中申明的泛型暂时不解析
                    genericTypeName = getClassByGenericTypeName(genericTypeName);
                    classMap.put(returnTypeClass, Class.forName(genericTypeName));
                } else {
                    classMap.put(returnTypeClass, Object.class);
                }
            } catch (ClassNotFoundException e) {
                log.error("ClassNotFoundException message:{}", e.getMessage());
                classMap.put(returnTypeClass, Object.class);
            }
        }
        return classMap;
    }


    /**
     * 通过类泛型全类名获取其中具体的泛型全类名
     * reactor.core.publisher.Mono<cn.exceptioncode.common.dto.DogDTO>
     * ->
     * cn.exceptioncode.common.dto.DogDTO
     *
     * @param genericClassTypeName
     * @return
     */
    public static String getFirstGenericTypeName(String genericClassTypeName) {
        if (!StringUtils.isEmpty(genericClassTypeName) && genericClassTypeName.contains("<")) {
            String genericTypeNameStr = genericClassTypeName.substring(genericClassTypeName.indexOf("<") + 1, genericClassTypeName.lastIndexOf(">"));
            String[] genericTypeNames = genericTypeNameStr.split(",");
            return genericTypeNames[0];
        } else {
            return null;
        }
    }

    /**
     * 通过泛型全类名获取类对应的全类名
     * reactor.core.publisher.Mono<cn.exceptioncode.common.dto.DogDTO>
     * ->
     * reactor.core.publisher.Mono
     *
     * @param genericTypeName
     * @return
     */
    public static String getClassByGenericTypeName(String genericTypeName) {
        if (!StringUtils.isEmpty(genericTypeName)) {
            if (genericTypeName.contains("<")) {
                return genericTypeName.substring(0, genericTypeName.indexOf("<"));
            } else {
                return genericTypeName;
            }
        } else {
            return null;
        }
    }


//    Mono reactor1() {
//        return Mono.just(new DogDTO());
//    }
//
//
//    @SneakyThrows
//    public static void main(String[] args) {
//        Method method = YapiClassUtils.class.getDeclaredMethod("reactor1");
//        Type genericReturnType = method.getGenericReturnType();
//        Type returnType = method.getReturnType();
//        String genericReturnTypeName = genericReturnType.getTypeName();
//        String returnTypeName = returnType.getTypeName();
//        System.out.println(genericReturnTypeName);
//        System.out.println(returnTypeName);
//        System.out.println(getClassByGenericTypeName(genericReturnTypeName));
//        System.out.println(getClassByGenericTypeName(returnTypeName));
//
//    }


}
