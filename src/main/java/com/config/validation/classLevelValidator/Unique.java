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

import com.config.validation.interfaceForServices.FieldValueExists;

/**
 * @author Sanjeev
 *
 */
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
//@Target( {ElementType.FIELD, ElementType.METHOD})
@Retention( RetentionPolicy.RUNTIME)
@Constraint( validatedBy = UniqueValidator.class)
@Documented
public @interface Unique {
    
    String message() default "Duplicate entry";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    
    Class<? extends FieldValueExists> service();
    
    String serviceQualifier() default "";
    String fieldName();
    String id();
    
}