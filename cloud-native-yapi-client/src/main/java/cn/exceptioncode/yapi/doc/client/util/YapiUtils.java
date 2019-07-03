package cn.exceptioncode.yapi.doc.client.util;

import cn.exceptioncode.yapi.doc.client.dto.ApiDTO;
import cn.exceptioncode.common.annotations.ParamDesc;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.ClassUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhangkai
 */
public class YapiUtils {

    // `RequestMapping` 派生注解集合
    private static final List<Class> REQUESTMAPPING_DERIVED = Lists.newArrayList(GetMapping.class, PutMapping.class, DeleteMapping.class, PatchMapping.class, PostMapping.class);


    /**
     * 获取类对应的 yapi 元素类型
     *
     * @param clazz
     * @return
     */
    public static String getPropertiesType(Class clazz) {
        if (clazz.isPrimitive()) {
            System.out.println(clazz);
            clazz.getClassLoader();
        } else {
            if (ClassUtils.isAssignable(clazz, Number.class)) {
                if (ClassUtils.isAssignable(clazz, Integer.class)) {
                    return "integer";
                }
                return "number";
            }
            if (ClassUtils.isAssignable(clazz, Boolean.class)) {
                return "boolean";
            }
            if (ClassUtils.isAssignable(clazz, String.class)) {
                return "string";
            }
            if (ClassUtils.isAssignable(clazz, Collection.class) || clazz.isArray()) {
                return "array";
            }

        }

        return "object";
    }


    /**
     * 构建 yapi 元素描述对象
     *
     * @param name       键
     * @param clazz      类类型
     * @param paramDesc  对象或字段描述注解
     * @param properties yapi json元素描述对象 properties 对应的 map
     * @param required   必填属性集合
     * @return
     */
    public static Map<String, Object> jsonProperties(String name, Class clazz, ParamDesc paramDesc, Map<String, Object> properties, List required) {
        Map<String, Object> jsonMap = new HashMap<>(10);
        jsonMap.put("name", name);
        jsonMap.put("type", YapiUtils.getPropertiesType(clazz));
        if (paramDesc != null) {
            jsonMap.put("example", paramDesc.example());
            jsonMap.put("description", paramDesc.desc());
        } else {
            jsonMap.put("example", name);
            jsonMap.put("description", name);
        }
        if (properties == null) {
            properties = new HashMap<>(1);
        }
        jsonMap.put("properties", properties);
        if (required == null) {
            jsonMap.put("required", Lists.newArrayList(10));
        } else {
            jsonMap.put("required", required);
        }

        return jsonMap;
    }


    /**
     * 赋值请求方法及请求路径
     *
     * @param pathPrefix
     * @param apiDTO
     * @param method
     * @return
     */
    public static RequestMapping getRequestMapping(String pathPrefix, ApiDTO apiDTO, Method method) {
        if (pathPrefix == null) {
            pathPrefix = "";
        }
        // 接口路径
        String path = null;
        // 接口名称
        String apiName = null;
        RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
        if (requestMapping == null) {
            // 尝试从 `RequestMapping` 派生注解中获取 `RequestMapping` 注解
            for (Class aClass : REQUESTMAPPING_DERIVED) {
                Annotation annotation = method.getAnnotation(aClass);
                if (annotation != null) {
                    Object pathObject = AnnotationUtils.getValue(annotation);
                    if (pathObject != null) {
                        if (pathObject instanceof String[]) {
                            path = ((String[]) pathObject)[0];
                        }
                    }
                    pathObject = AnnotationUtils.getValue(annotation, "name");
                    if (pathObject != null) {
                        if (pathObject instanceof String) {
                            apiName = ((String) pathObject);
                        }
                    }
                    // 从`RequestMapping`派生注解上获取 `RequestMapping`注解
                    requestMapping = AnnotationUtils.findAnnotation(annotation.getClass(), RequestMapping.class);
                    break;
                }
            }
        }
        if(requestMapping!=null){
            // 获取接口路径
            String[] pathValue = path == null ? requestMapping.value() : new String[]{path};
            // 获取接口名称
            apiName = apiName == null ? requestMapping.name() : apiName;
            if (pathValue != null && pathValue.length > 0) {
                // TODO: 2019/6/21 path为数组 可能存在多个请求路径
                apiDTO.setPath(pathPrefix + pathValue[0]);
                if (StringUtils.isEmpty(apiName)) {
                    apiDTO.setTitle(apiDTO.getPath());
                } else {
                    apiDTO.setTitle(apiName);
                }
            }
            // 获取请求方法
            RequestMethod[] requestMethods = requestMapping.method();
            if (requestMethods != null && requestMethods.length == 1) {
                apiDTO.setMethod(requestMethods[0].name());
            } else {
                // 存在多个请求method 或者获取不到 直接使用 GET method
                apiDTO.setMethod(RequestMethod.GET.name());
            }
            return requestMapping;
        }else {
            return null;
        }

    }

}
