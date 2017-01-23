package com.bytecompute.tankms.annotation;

import java.lang.annotation.*;

/**
 * Created by Longer on 2016/12/1.
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TopicRouter {

    String name();

}
