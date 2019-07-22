package cn.exceptioncode.common.util;

import lombok.Data;

import java.lang.reflect.Field;
import java.util.*;

public class ClassUtils extends org.apache.commons.lang3.ClassUtils {


    public static void main(String[] args) {
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

        private Map<String,String> stringStringMap;
    }

    /**
     * isAssignableFromCollection
     *
     * @param field
     * @return
     */
    public static boolean isAssignableFromCollection(Field field) {
        Class clazz = field.getType();
        return Collection.class.isAssignableFrom(clazz);
    }
}
