package cn.exceptioncode.api.doc.client.util;

import cn.exceptioncode.common.annotations.ParamDesc;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.ClassUtils;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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


    /**
     *
     * 构建 yapi json元素描述对象
     *
     * @param name 键
     * @param clazz 类类型
     * @param paramDesc 对象或字段描述注解
     * @param properties yapi json元素描述对象 properties 对应的 map
     * @param required 必填属性集合
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
        if(required==null){
            jsonMap.put("required", Lists.newArrayList(10));
        }else {
            jsonMap.put("required", required);
        }

        return jsonMap;
    }


}
