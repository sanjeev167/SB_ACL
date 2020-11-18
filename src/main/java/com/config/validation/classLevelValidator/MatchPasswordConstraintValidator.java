/**
 * 
 */
package com.config.validation.classLevelValidator;

import java.lang.reflect.InvocationTargetException;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.beanutils.BeanUtils;

import com.config.exception.common.ExceptionApplicationUtility;


/**
 * @author Sanjeev
 *
 */
public class MatchPasswordConstraintValidator implements ConstraintValidator<MatchPassword, Object> {

	private String password;
	private String passwordConf;  
	private String message;

	@Override
	public void initialize(MatchPassword matchPassword) {		
		this.password = matchPassword.password();
		this.passwordConf = matchPassword.passwordConf();		
        this.message=matchPassword.message();
	}

	@Override
	public boolean isValid(Object object, ConstraintValidatorContext context) {
		
		final Object passwordValue;
		final Object passwordConfvalue;		
		boolean isValid = true;			
			try {
				passwordValue = BeanUtils.getProperty(object, this.password);
				passwordConfvalue = BeanUtils.getProperty(object, this.passwordConf);				
			    isValid = passwordValue.toString().equals(passwordConfvalue.toString());			    
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				ExceptionApplicationUtility.wrapRunTimeException(e);
				
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				ExceptionApplicationUtility.wrapRunTimeException(e);
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				ExceptionApplicationUtility.wrapRunTimeException(e);
			}
			
			// Set the error reporting path
			if (!isValid) {
				context.disableDefaultConstraintViolation();
				context.buildConstraintViolationWithTemplate(this.message).addPropertyNode(this.passwordConf)
						.addConstraintViolation();
			}
			
		return isValid;
	}

	
}