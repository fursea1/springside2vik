package org.vicalloy.codegen.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/*******************************************************************************
 * 为对象模型添加主权限说明信息，协助代码生成器工作
 * 
 * @author vicalloy
 * 
 */
@Target( { ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface MasterFunctionId__ {
	String masterFunctionId() default "";
}
