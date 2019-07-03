package com.chan.starter;

import com.chan.beans.BeanFactory;
import com.chan.core.ClassScanner;
import com.chan.web.handler.HandlerManage;
import com.chan.web.server.TomcatServer;
import org.apache.catalina.LifecycleException;

import java.io.IOException;
import java.util.List;

/**
 * @description:
 * @author: Chen
 * @create: 2019-06-23 14:22
 **/
public class MiniApplication {

    public static void run(Class<?> clz,String[] agrs){
        TomcatServer tomcatServer = new TomcatServer(agrs);
        try {
            List<Class<?>> classList = ClassScanner.scanClasses(clz.getPackage().getName());
            try {
                BeanFactory.init(classList);
                HandlerManage.resolveMappingHandleList(classList);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            tomcatServer.startServer();
        } catch (LifecycleException e) {
            e.printStackTrace();
        }
    }

}
