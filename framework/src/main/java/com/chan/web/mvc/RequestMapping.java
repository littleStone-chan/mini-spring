package com.chan.web.mvc;

import java.lang.annotation.*;

/**
 * @description:
 * @author: Chen
 * @create: 2019-07-02 22:25
 **/
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.METHOD})
public @interface RequestMapping {

    String value();


}
