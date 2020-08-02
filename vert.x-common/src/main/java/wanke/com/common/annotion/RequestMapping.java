package wanke.com.common.annotion;


import java.lang.annotation.*;

/**
 * @author chendi
 *
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestMapping {

    String value() default "";
}
