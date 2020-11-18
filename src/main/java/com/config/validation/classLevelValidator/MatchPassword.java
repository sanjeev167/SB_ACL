/**
 * 
 */
package com.config.validation.classLevelValidator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * @author Sanjeev
 *
 */

@Documented
@Constraint(validatedBy = MatchPasswordConstraintValidator.class)
@Target({ ElementType.TYPE, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface MatchPassword {
	 String message() default "Passwords don't match.";
	    Class<?>[] groups() default {};
	    Class<? extends Payload>[] payload() default {};	   
	    String password();
	    String passwordConf();  
	
}