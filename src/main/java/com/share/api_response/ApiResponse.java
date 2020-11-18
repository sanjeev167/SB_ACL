/**
 * 
 */
package com.share.api_response;

/**
 * @author Sanjeev
 *
 */
public class ApiResponse {

	
	    //This will be used by the client for making a decision whether he has to read the error details or actual data.
	    private String apiResponse="error";//"success" when the http response is 200
		//This response object will hold one of the following
		// [1] Actual data in json format that was requested for
		// [2] It may contain an error view object which will contain the details of the error 
		private Object responseObj;
		
		
		//This will tell the client about the meta data of api.
		private ApiDetails apiDetails;
		

		
		public ApiResponse(String apiResponse,Object responseObj,ApiDetails apiDetails) {			
			this.apiResponse=apiResponse;
			this.responseObj=responseObj;
			this.apiDetails=apiDetails;
			
		}
		
		
		public String getApiResponse() {
			return apiResponse;
		}

		/**
		 * @param apiResponse the apiResponse to set
		 */
		public void setApiResponse(String apiResponse) {
			this.apiResponse = apiResponse;
		}

		/**
		 * @return the responseObj
		 */
		public Object getResponseObj() {
			return responseObj;
		}

		/**
		 * @param responseObj the responseObj to set
		 */
		public void setResponseObj(Object responseObj) {
			this.responseObj = responseObj;
		}

		/**
		 * @return the apiDetails
		 */
		public ApiDetails getApiDetails() {
			return apiDetails;
		}

		/**
		 * @param apiDetails the apiDetails to set
		 */
		public void setApiDetails(ApiDetails apiDetails) {
			this.apiDetails = apiDetails;
		}

			
}//End of ApiResponse
