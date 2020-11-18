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
import com.config.validation.interfaceForServices.FieldValueWithParentIdExists;

/**
 * @author Sanjeev
 *
 */
public class FirstChildUniqueWithParentIdValidator implements ConstraintValidator<FirstChildUniqueWithParentId, Object> {
	@Autowired
	private ApplicationContext applicationContext;

	private FieldValueWithParentIdExists service;
	private String fieldName;
	private String parentId;
	private String id;
	private String message;
	

	@Override
	public void initialize(FirstChildUniqueWithParentId firstChildUniqueWithParentId) {
		Class<? extends FieldValueWithParentIdExists> clazz = firstChildUniqueWithParentId.service();

		this.fieldName = firstChildUniqueWithParentId.fieldName();
		this.parentId = firstChildUniqueWithParentId.parentId();
		this.id=firstChildUniqueWithParentId.id();
        this.message=firstChildUniqueWithParentId.message();
        
		String serviceQualifier = firstChildUniqueWithParentId.serviceQualifier();
		if (!serviceQualifier.equals("")) {
			this.service = this.applicationContext.getBean(serviceQualifier, clazz);
		} else {
			this.service = this.applicationContext.getBean(clazz);
		}
	}

	@Override
	public boolean isValid(Object object, ConstraintValidatorContext context) {
		final Object parentIdValue;
		final Object fieldvalue;
		final Object idValue;
		
		boolean isValid = true;		
		try {
			parentIdValue = BeanUtils.getProperty(object, this.parentId);
			fieldvalue = BeanUtils.getProperty(object, this.fieldName);	
			idValue=BeanUtils.getProperty(object, this.id);		
			
				try {
					isValid = !this.service.FirstChildValueWithParentIdExists(parentIdValue, this.parentId, fieldvalue,
							this.fieldName, idValue,this.id);
				} catch (UnsupportedOperationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (CustomRuntimeException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}			
		} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Set the error reporting path
		if (!isValid) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(this.message).addPropertyNode(this.fieldName)
					.addConstraintViolation();
		}

		
		return isValid;
	}
}