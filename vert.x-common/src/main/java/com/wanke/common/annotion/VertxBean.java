package com.wanke.common.annotion;

import java.lang.annotation.*;

/**
 * @Author: chendi
 * @Description:
 * @Date: 2020/8/7 14:04
 * @Version: 1.0
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface VertxBean {
}
