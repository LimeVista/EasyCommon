package me.limeice.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PACKAGE;
import static java.lang.annotation.RetentionPolicy.CLASS;

/**
 * 标记检查不支持接口
 */
@Documented
@Retention(CLASS)
@Target({METHOD, PACKAGE})
public @interface UnSupported {
    //
}
