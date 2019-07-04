package com.chan.web.handler;

import com.chan.web.mvc.Controller;
import com.chan.web.mvc.RequestMapping;
import com.chan.web.mvc.RequestParam;
import com.sun.glass.events.mac.NpapiEvent;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

/**
 * @description: 映射处理管理中心
 * @author: Chen
 * @create: 2019-07-03 00:34
 **/
public class HandlerManage {

    /**
     * 保存需要映射的列表
     */
    public static List<MappingHandle> mappingHandleList = new ArrayList<>();

    public static void resolveMappingHandleList(List<Class<?>> classList){

        for (Class<?> clz :classList){

            if (clz.isAnnotationPresent(Controller.class)){
                parseHandlerFromController(clz);
            }

        }

    }

    private static void parseHandlerFromController(Class<?> clz) {

        Method[] methods = clz.getMethods();

        for (Method method:methods){

            if (!method.isAnnotationPresent(RequestMapping.class)){
                continue;
            }

            String uri = method.getDeclaredAnnotation(RequestMapping.class).value();
            List<String> paramNameList = new ArrayList<>();

            for (Parameter parameter:method.getParameters()){
                if (parameter.isAnnotationPresent(RequestParam.class)){
                    paramNameList.add(parameter.getDeclaredAnnotation(RequestParam.class).value());
                }
            }
            String[] args = paramNameList.toArray(new String[paramNameList.size()]);
            MappingHandle mappingHandle = new MappingHandle(uri,method,clz,args);
            HandlerManage.mappingHandleList.add(mappingHandle);
        }

    }

}
