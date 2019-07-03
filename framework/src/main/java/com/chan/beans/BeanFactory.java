package com.chan.beans;

import com.chan.web.mvc.Controller;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @description: bean 工厂
 * @author: Chen
 * @create: 2019-07-03 00:03
 **/
public class BeanFactory {

    /**
     * 配置bean容器 存放bean
     */
    private static Map<Class<?>,Object> beanMap = new ConcurrentHashMap<>();

    /**
     * 根据类  获取bean
     * @param clz
     * @return
     */
    public static Object getBean(Class<?> clz){return beanMap.get(clz);}

    /**
     * 根据扫描到到类  依次按照规则注入到bean容器中
     * @param classList
     * @throws Exception
     */
    public static void init(List<Class<?>> classList) throws Exception {

        List<Class<?>> toCreate = new ArrayList<>(classList);

        /**
         * 将满足注入条件循环注入bean容器
         */
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


    /**
     * 判断是否是需要注入到bean
     * 如果不是 直接返回true  并且添加到bean容器
     * 如果是，但是当时不满足注入条件  返回false  等到下次循环再调用
     * 直至满足条件，添加到bean容器中
     * @param clz
     * @return
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
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
