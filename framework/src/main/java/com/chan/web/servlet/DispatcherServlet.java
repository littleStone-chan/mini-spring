package com.chan.web.servlet;

import com.chan.web.handler.HandlerManage;
import com.chan.web.handler.MappingHandle;

import javax.servlet.*;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 * @description: serlet适配器，所有的url都会进入到此处，
 * 在此处根据@RequestMaping所映射到方法进行操作。
 * @author: Chen
 * @create: 2019-06-23 15:15
 **/
public class DispatcherServlet implements Servlet {
    @Override
    public void init(ServletConfig config) throws ServletException {

    }

    @Override
    public ServletConfig getServletConfig() {
        return null;
    }

    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {

        System.out.println("HandlerManage.mappingHandleList:"+HandlerManage.mappingHandleList.size());

        for (MappingHandle mappingHandle : HandlerManage.mappingHandleList){
            try {
                if (mappingHandle.handle(req,res)){
                    return;
                }else {
                    System.out.println("false");
                }
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public String getServletInfo() {
        return null;
    }

    @Override
    public void destroy() {

    }
}
