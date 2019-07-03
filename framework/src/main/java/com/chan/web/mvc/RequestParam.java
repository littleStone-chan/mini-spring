package com.chan.web.mvc;

import java.lang.annotation.*;

/**
 * @description:
 * @author: Chen
 * @create: 2019-07-02 22:28
 **/
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(value = ElementType.PARAMETER)
public @interface RequestParam {

    String value();

}
