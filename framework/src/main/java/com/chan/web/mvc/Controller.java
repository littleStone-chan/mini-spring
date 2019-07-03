package com.chan.web.mvc;

import java.lang.annotation.*;

/**
 * @description:
 * @author: Chen
 * @create: 2019-07-02 22:23
 **/
@Documented
@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = ElementType.TYPE)
public @interface Controller {
}
