package com.chan.controller;

import com.chan.service.UserBean;
import com.chan.beans.AutoWired;
import com.chan.web.mvc.Controller;
import com.chan.web.mvc.RequestMapping;
import com.chan.web.mvc.RequestParam;

/**
 * @description:
 * @author: Chen
 * @create: 2019-07-03 00:27
 **/
@Controller
public class UserController {

    @AutoWired
    UserBean userBean;

    @RequestMapping(value = "hello")
    public String hello(@RequestParam(value = "name") String name){
        return userBean.say() + "," + name;
    }

}
