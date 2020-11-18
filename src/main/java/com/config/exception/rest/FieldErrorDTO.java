/**
 * 
 */
package com.config.exception.rest;

import javax.validation.Path;

/**
 * @author Sanjeev
 *
 */
public class FieldErrorDTO {
	    private String field;	 
	    private String message;	
	   
	    public FieldErrorDTO(String field, String message) {
	        this.field = field;
	        this.message = message;
	    }
		public FieldErrorDTO(Path propertyPath, String message) {				
			this.field=propertyPath.toString().split("[.]", 0)[1];
			this.message=message;
		}
		/**
		 * @return the field
		 */
		public String getField() {
			return field;
		}
		/**
		 * @param field the field to set
		 */
		public void setField(String field) {
			
			this.field = field;
			
		}
		/**
		 * @return the message
		 */
		public String getMessage() {
			return message;
		}
		/**
		 * @param message the message to set
		 */
		public void setMessage(String message) {
			this.message = message;
		}
	    
	    
	    
}
