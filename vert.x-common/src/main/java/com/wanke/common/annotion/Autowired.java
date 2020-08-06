package com.wanke.common.annotion;

import java.lang.annotation.*;

/**
 * @Author: chendi
 * @Description:
 * @Date: 2020/8/6 9:01
 * @Version: 1.0
 */
@Target({ElementType.TYPE, ElementType.METHOD,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Autowired {
}
