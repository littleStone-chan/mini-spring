package com.chan.beans;

import java.lang.annotation.*;

/**
 * @description:
 * @author: Chen
 * @create: 2019-07-02 21:50
 **/
@Documented
@Target(value = ElementType.FIELD)
@Retention(value = RetentionPolicy.RUNTIME)
public @interface AutoWired {
}
