/**
 * 
 */
package com.config.validation.fieldLevelValidator;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * @author Sanjeev
 *
 */
@Target( {ElementType.FIELD, ElementType.METHOD})
@Retention( RetentionPolicy.RUNTIME)
@Constraint( validatedBy = ShouldIntegerValidator.class)


public @interface ShouldInteger {
	//String message() default "{com.dolszewski.blog.UniqueLogin.message}";
	
	String message() default "It should be an integer only.";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
