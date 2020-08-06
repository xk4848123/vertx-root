package com.wanke.common.annotion;

import java.lang.annotation.*;

/**
 * @Author: chendi
 * @Description: 组件
 * @Date: 2020/8/6 8:54
 * @Version: 1.0
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Component {
}
