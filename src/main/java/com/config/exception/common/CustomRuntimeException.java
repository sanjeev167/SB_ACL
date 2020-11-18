/**
 *
 */
package com.config.exception.common;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @ author ksanjeev
 * @ 24/8/2016
 * @ 2.20 p.m.
 */
public class CustomRuntimeException extends CustomBaseException implements Serializable {

    private static final long serialVersionUID = -6513206704156645127L;
    private ExceptionInfo exceptionInfo;
    private Throwable wrappedException;
    public CustomRuntimeException(ExceptionInfo exceptionInfo, Throwable wrappedException) {
        super(exceptionInfo, wrappedException);
        this.exceptionInfo = exceptionInfo;
        this.wrappedException = wrappedException;
        
       Logger logger = LoggerFactory.getLogger(exceptionInfo.getExceptionGeneratingClass());
        
        String sqlCode = exceptionInfo.sqlCode;
        if (sqlCode != null && !sqlCode.equals("")) {
            sqlCode = "[" + sqlCode + "]";
        }
        if (exceptionInfo.exceptionCategory.equalsIgnoreCase("customException") || exceptionInfo.exceptionCategory.equalsIgnoreCase("CustomRuntimeException") || exceptionInfo.exceptionCategory.equalsIgnoreCase("CustomBusinessException")) {
         //This case is avoiding duplicate logging.
        
        } else {
            logger.error("-->" + exceptionInfo.exceptionCategory +" "+ sqlCode + " in Line no[" + exceptionInfo.getExceptionAtLineNo() + "] "
                    + "of method " + exceptionInfo.getMethodName() + "() of class " + exceptionInfo.getExceptionGeneratingClass() + " [Main Cause-->" + exceptionInfo.exceptionCause + "]");
        }
    }

   

    public ExceptionInfo getExceptionInfo() {
        // TODO Auto-generated method stub
        return exceptionInfo;
    }

}//End of CustomRunTimeException
