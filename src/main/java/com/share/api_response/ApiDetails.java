/**
 * 
 */
package com.share.api_response;

/**
 * @author Sanjeev
 *
 */
public class ApiDetails {
	
	private String api_version;
	private String api_type="public";//Secured[pvt] or unsecured[pub]
	private String api_url;
	private String method;
	private String input_data_type;
	private String output_data_type;
	/**
	 * @return the api_version
	 */
	public String getApi_version() {
		return api_version;
	}
	/**
	 * @param api_version the api_version to set
	 */
	public void setApi_version(String api_version) {
		this.api_version = api_version;
	}
	/**
	 * @return the api_type
	 */
	public String getApi_type() {
		return api_type;
	}
	/**
	 * @param api_type the api_type to set
	 */
	public void setApi_type(String api_type) {
		this.api_type = api_type;
	}
	/**
	 * @return the api_url
	 */
	public String getApi_url() {
		return api_url;
	}
	/**
	 * @param api_url the api_url to set
	 */
	public void setApi_url(String api_url) {
		this.api_url = api_url;
	}
	/**
	 * @return the method
	 */
	public String getMethod() {
		return method;
	}
	/**
	 * @param method the method to set
	 */
	public void setMethod(String method) {
		this.method = method;
	}
	/**
	 * @return the input_data_type
	 */
	public String getInput_data_type() {
		return input_data_type;
	}
	/**
	 * @param input_data_type the input_data_type to set
	 */
	public void setInput_data_type(String input_data_type) {
		this.input_data_type = input_data_type;
	}
	/**
	 * @return the output_data_type
	 */
	public String getOutput_data_type() {
		return output_data_type;
	}
	/**
	 * @param output_data_type the output_data_type to set
	 */
	public void setOutput_data_type(String output_data_type) {
		this.output_data_type = output_data_type;
	}
	
	
}
