package com.wanke.common.annotion;

import java.lang.annotation.*;


/**
 * @author chendi
 * 
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface BusController {

}
