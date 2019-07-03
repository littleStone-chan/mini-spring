package com.chan;

import com.chan.beans.BeanFactory;
import com.chan.controller.UserController;
import com.chan.starter.MiniApplication;

/**
 * @description:
 * @author: Chen
 * @create: 2019-06-23 14:05
 **/
public class Application {

    public static void main(String[] args) {
        System.out.println("Hello world");
        MiniApplication.run(Application.class,args);

        System.out.println(BeanFactory.getBean(UserController.class));
    }

}
