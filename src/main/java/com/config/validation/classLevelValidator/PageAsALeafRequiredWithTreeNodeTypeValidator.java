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
public class PageAsALeafRequiredWithTreeNodeTypeValidator
		implements ConstraintValidator<PageAsALeafRequiredWithTreeNodeType, Object> {

	private static final Logger log=LoggerFactory.getLogger(PageAsALeafRequiredWithTreeNodeTypeValidator.class);
	private String nodeType;
	private String pageMasterId;	
	private String message;

	@Override
	public void initialize(PageAsALeafRequiredWithTreeNodeType pageAsALeafRequiredWithTreeNodeType) {
		this.nodeType = pageAsALeafRequiredWithTreeNodeType.nodeType();
		this.pageMasterId = pageAsALeafRequiredWithTreeNodeType.pageMasterId();		
		this.message = pageAsALeafRequiredWithTreeNodeType.message();
	}

	@Override
	public boolean isValid(Object object, ConstraintValidatorContext context) {
		final Object nodeTypeValue;
		final Object pageMasterIdValue;		
		boolean isValid = true;
		try {
			nodeTypeValue = BeanUtils.getProperty(object, this.nodeType);
			pageMasterIdValue = BeanUtils.getProperty(object, this.pageMasterId);
			log.info("pageMasterId = "+pageMasterIdValue);
			try {
				// Wrie validation logic here
				if (nodeTypeValue.equals("L") && pageMasterIdValue.equals("")) 										
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
			context.buildConstraintViolationWithTemplate(this.message).addPropertyNode(this.pageMasterId)
						.addConstraintViolation();
		}
		return isValid;
	}
}