/**
 * 
 */
package com.config.validation.classLevelValidator;

import java.lang.reflect.InvocationTargetException;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.config.exception.common.CustomRuntimeException;
import com.config.validation.interfaceForServices.FieldValueExists;

/**
 * @author Sanjeev
 *
 */
public class UniqueValidator implements ConstraintValidator<Unique, Object> {
    @Autowired
    private ApplicationContext applicationContext;

    private FieldValueExists service;
    private String fieldName;
    private String id;
    private String message;

    @Override
    public void initialize(Unique unique) {
        Class<? extends FieldValueExists> clazz = unique.service();
        this.fieldName = unique.fieldName();
        this.id = unique.id();
        this.message=unique.message();
        String serviceQualifier = unique.serviceQualifier();

        if (!serviceQualifier.equals("")) {
            this.service = this.applicationContext.getBean(serviceQualifier, clazz);
        } else {
            this.service = this.applicationContext.getBean(clazz);
        }
    }

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext constraintValidatorContext) {
        boolean isValid=false;
        Object fieldValue;
        final Object idValue;
    	try {
    		try {
    			fieldValue=BeanUtils.getProperty(obj, this.fieldName);    			
    			idValue=BeanUtils.getProperty(obj, this.id);    			
				isValid=!this.service.fieldValueExists(fieldValue, this.fieldName,idValue,this.id);	
				// Set the error reporting path
				if (!isValid) {
					constraintValidatorContext.disableDefaultConstraintViolation();
					constraintValidatorContext.buildConstraintViolationWithTemplate(this.message).addPropertyNode(this.fieldName)
							.addConstraintViolation();
				}
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}    		
			
		} catch (CustomRuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	return isValid;
    }
}