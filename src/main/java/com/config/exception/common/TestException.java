/**
 *
 */
package com.config.exception.common;

/**
 * @author ksanjeev
 *
 */
public class TestException {

    public TestException() {
    }

    // Set up a simple configuration that logs on the console.
    /**
     * @param args
     * @throws CustomRuntimeException
     * @throws Exception
     */
    /*public static void main(String[] args) {
        try {
            exceptionCrossCheck();
        } catch (CustomRuntimeException e) {

        } catch (CustomBusinessException e) {
            // TODO Auto-generated catch block			
            e.printStackTrace();
        }
    }//End of main*/

    public static void exceptionCrossCheck() throws CustomRuntimeException, CustomBusinessException {
        int j = 3;
        int y;
        try {
            y = 21 / j;
            // Write the code to be examined under exception strategy
            if (y < 3) {
                //Do some business process here            	
            } else {
                //Now prepare the business exception to be thrown           
                throw (ExceptionApplicationUtility.wrapBusinessException(new CustomBusinessException("be_1.error")));
            }
        } catch (Exception e) {
           
            throw (ExceptionApplicationUtility.wrapRunTimeException(e));
        }
    }//End of exceptionCrossCheck
}
