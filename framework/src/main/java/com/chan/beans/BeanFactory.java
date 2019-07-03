package com.chan.beans;

import com.chan.web.mvc.Controller;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @description:
 * @author: Chen
 * @create: 2019-07-03 00:03
 **/
public class BeanFactory {

    private static Map<Class<?>,Object> beanMap = new ConcurrentHashMap<>();

    public static Object getBean(Class<?> clz){return beanMap.get(clz);}

    public static void init(List<Class<?>> classList) throws Exception {

        List<Class<?>> toCreate = new ArrayList<>(classList);

        while (toCreate.size()!=0){

            int remainSize = toCreate.size();

            for (int i=0;i<toCreate.size();i++){
                if (finishCreate(toCreate.get(i))){
                    toCreate.remove(i);
                }
            }

            if (remainSize==toCreate.size()){
                throw new Exception("cycle dependency");
            }

        }

    }

    private static boolean finishCreate(Class<?> clz) throws IllegalAccessException, InstantiationException {

        if (!clz.isAnnotationPresent(Controller.class)&&!clz.isAnnotationPresent(Bean.class)){
            return true;
        }

        Object bean = clz.newInstance();
        for (Field field : clz.getDeclaredFields()){
            if (field.isAnnotationPresent(AutoWired.class)){
                Class<?> fieldType = field.getType();
                Object filedTypeObj = BeanFactory.getBean(fieldType);
                if (filedTypeObj==null){
                    return false;
                }

                field.setAccessible(true);
                field.set(bean,filedTypeObj);
            }
        }

        beanMap.put(clz,bean);
        return true;
    }

}
