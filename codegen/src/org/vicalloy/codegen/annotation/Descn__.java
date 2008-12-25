package org.vicalloy.codegen.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/*******************************************************************************
 * 为对象添加说明信息，协助代码生成器工作
 * 
 * @author vicalloy
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Descn__ {
	String descn() default "";
}
