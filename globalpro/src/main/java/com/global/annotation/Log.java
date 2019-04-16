package com.global.annotation;

import java.lang.annotation.*;

/**
 * @Author yanghanjin
 * @Description:
 * @Date 2019/2/28
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {

    String value()  default "";

}
