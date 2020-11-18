/**
 * 
 */
package com.config.validation.classLevelValidator;

import java.lang.reflect.InvocationTargetException;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.config.exception.common.ExceptionApplicationUtility;


/**
 * @author Sanjeev
 *
 */
public class ModuleRequiredWithTreeNodeTypeValidator
		implements ConstraintValidator<ModuleRequiredWithTreeNodeType, Object> {
	private static final Logger log=LoggerFactory.getLogger(ModuleRequiredWithTreeNodeTypeValidator.class);
	private String nodeType;
	private String moduleId;	
	private String message;

	@Override
	public void initialize(ModuleRequiredWithTreeNodeType moduleRequiredWithTreeNodeType) {
		this.nodeType = moduleRequiredWithTreeNodeType.nodeType();
		this.moduleId = moduleRequiredWithTreeNodeType.moduleId();		
		this.message = moduleRequiredWithTreeNodeType.message();
	}

	@Override
	public boolean isValid(Object object, ConstraintValidatorContext context) {
		final Object nodeTypeValue;
		final Object moduleIdValue;		
		boolean isValid = true;
		try {
			nodeTypeValue = BeanUtils.getProperty(object, this.nodeType);
			moduleIdValue = BeanUtils.getProperty(object, this.moduleId);
			log.info("moduleIdValue = "+moduleIdValue);
			try {
				// Wrie validation logic here
				if (nodeTypeValue.equals("L") && moduleIdValue.equals("")) 										
					isValid = false;
			   else 					
					isValid = true;				
				
			} catch (UnsupportedOperationException e) {
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
			context.buildConstraintViolationWithTemplate(this.message).addPropertyNode(this.moduleId)
						.addConstraintViolation();
		}
		return isValid;
	}
}