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

import com.config.validation.interfaceForServices.FieldValueWithParentIdExists;

/**
 * @author Sanjeev
 *
 */
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
//@Target( {ElementType.FIELD, ElementType.METHOD})
@Retention( RetentionPolicy.RUNTIME)
@Constraint( validatedBy = UniqueWithParentIdValidator.class)
@Documented
public @interface UniqueWithParentId {
    String message() default "Duplicate entry";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    Class<? extends FieldValueWithParentIdExists> service();
    String serviceQualifier() default "";
    String parentId();
    String id();
    String fieldName();
   
    
}