package com.chan.starter;

import com.chan.beans.BeanFactory;
import com.chan.core.ClassScanner;
import com.chan.web.handler.HandlerManage;
import com.chan.web.server.TomcatServer;
import org.apache.catalina.LifecycleException;

import java.io.IOException;
import java.util.List;

/**
 * @description: 项目的入口
 * @author: Chen
 * @create: 2019-06-23 14:22
 **/
public class MiniApplication {

    public static void run(Class<?> clz,String[] agrs){
        try {
            //根据传进来的clz所在的包取扫描包下的数据
            List<Class<?>> classList = ClassScanner.scanClasses(clz.getPackage().getName());
            try {
                //根据扫描到到文件，初始化bean工厂。
                BeanFactory.init(classList);
                //根据@Controller @RequestMapping 注解，处理映射关系。
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
            //启动tomcat
            TomcatServer tomcatServer = new TomcatServer(agrs);
            tomcatServer.startServer();
        } catch (LifecycleException e) {
            e.printStackTrace();
        }
    }

}
