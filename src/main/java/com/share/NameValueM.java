/**
 * 
 */
package com.share;

/**
 * @author Sanjeev
 *
 */
public class NameValueM {
	 private Integer id;
	  private String name;
	  private String allReadyAssigned="";
	  private Integer accessCount=0;
	  private Integer pageCount=0;
	 /**
	 * @return the accessCount
	 */
	public Integer getAccessCount() {
		return accessCount;
	}

	/**
	 * @param accessCount the accessCount to set
	 */
	public void setAccessCount(Integer accessCount) {
		this.accessCount = accessCount;
	}

	public NameValueM(Integer id, String name) {
		 this.id=id;
		 this.name=name;
		 
	 }

	public NameValueM(Integer id, String name,Integer accessCount) {
		 this.id=id;
		 this.name=name;
		 this.accessCount=accessCount;
		 
		 
	}
	public NameValueM(Integer id, String name,Integer accessCount,Integer pageCount) {
		 this.id=id;
		 this.name=name;
		 this.accessCount=accessCount;
		 this.pageCount=pageCount;
		 
	}

	public NameValueM(Integer id, String name,String  allReadyAssigned) {
		 this.id=id;
		 this.name=name;
		 this.allReadyAssigned=allReadyAssigned;
		 	 
	}




	public String getAllReadyAssigned() {
		return allReadyAssigned;
	}

	public void setAllReadyAssigned(String allReadyAssigned) {
		this.allReadyAssigned = allReadyAssigned;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public Integer getPageCount() {
		return pageCount;
	}

	public void setPageCount(Integer pageCount) {
		this.pageCount = pageCount;
	}  
	  
	}
