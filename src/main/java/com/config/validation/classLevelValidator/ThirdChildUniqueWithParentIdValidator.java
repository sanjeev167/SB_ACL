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
import com.config.exception.common.ExceptionApplicationUtility;
import com.config.validation.interfaceForServices.FieldValueWithParentIdExists;

/**
 * @author Sanjeev
 *
 */
public class ThirdChildUniqueWithParentIdValidator implements ConstraintValidator<ThirdChildUniqueWithParentId, Object> {
	@Autowired
	private ApplicationContext applicationContext;

	private FieldValueWithParentIdExists service;
	private String fieldName;
	private String parentId;
	private String id;
	private String message;
	

	@Override
	public void initialize(ThirdChildUniqueWithParentId thirdChildUniqueWithParentId) {
		Class<? extends FieldValueWithParentIdExists> clazz = thirdChildUniqueWithParentId.service();

		this.fieldName = thirdChildUniqueWithParentId.fieldName();
		this.parentId = thirdChildUniqueWithParentId.parentId();
		this.id=thirdChildUniqueWithParentId.id();
        this.message=thirdChildUniqueWithParentId.message();
        
		String serviceQualifier = thirdChildUniqueWithParentId.serviceQualifier();
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
					isValid = !this.service.ThirdChildValueWithParentIdExists(parentIdValue, this.parentId, fieldvalue,
							this.fieldName, idValue,this.id);
				} catch (UnsupportedOperationException e) {
					// TODO Auto-generated catch block
					ExceptionApplicationUtility.wrapRunTimeException(e);
				} catch (CustomRuntimeException e) {
					// TODO Auto-generated catch block
					ExceptionApplicationUtility.wrapRunTimeException(e);
				}			
		} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
			// TODO Auto-generated catch block
			ExceptionApplicationUtility.wrapRunTimeException(e);
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