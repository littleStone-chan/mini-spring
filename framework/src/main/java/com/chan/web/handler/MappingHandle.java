package com.chan.web.handler;

import com.chan.beans.BeanFactory;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @description: 映射处理类
 * @author: Chen
 * @create: 2019-07-03 00:35
 **/
public class MappingHandle {

    String uri;

    Method method;

    Class<?> controller;

    String[] agrs;

    public MappingHandle(String uri,Method method,Class<?> controller,String[] agrs){
        this.uri = uri;
        this.method = method;
        this.controller = controller;
        this.agrs = agrs;
    }

    /**
     * 将扫描到到RequestMapping的路径  进行处理
     * 通过反射调用 调用该方法
     * @param req
     * @param res
     * @return
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     * @throws IOException
     */
    public boolean handle(ServletRequest req, ServletResponse res) throws InvocationTargetException, IllegalAccessException, IOException {

        String requestURI = ((HttpServletRequest)req).getRequestURI();

        System.out.println("uri:"+uri);
        System.out.println("requestURI:"+requestURI.substring(1));
        if (!uri.equals(requestURI.substring(1))){
            return false;
        }

        String[] params = new String[agrs.length];
        for (int i =0;i< params.length;i++){
            params[i] = req.getParameter(agrs[i]);
        }

        Object obj = BeanFactory.getBean(controller);
        System.out.println(obj);
        if (obj==null){
            return false;
        }

        Object ret = method.invoke(obj,params);
        System.out.println("ret:"+ret.toString());
        res.getWriter().println(ret.toString());
        return true;
    }

}
