/**
 * 
 */
package com.config.validation.fieldLevelValidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author Sanjeev
 *
 */

public class ShouldIntegerValidator implements ConstraintValidator<ShouldInteger, String> {

	// public ShouldIntegerValidator() { }

	@Override
	public void initialize(ShouldInteger constraint) {
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		boolean checkWhetherIntegerOrNot = false;
		try {
			//log.info("Reched for checking int validation");
			if (value != "") {
				Integer.parseInt(value);
				checkWhetherIntegerOrNot = true;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return checkWhetherIntegerOrNot;
	}

}
