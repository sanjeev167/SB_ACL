/**
 * 
 */
package com.pvt.sec.env.dto;

import javax.validation.constraints.NotEmpty;


import com.config.validation.classLevelValidator.SecondChildUniqueWithParentId;
import com.config.validation.classLevelValidator.UniqueWithParentId;

import com.pvt.sec.env.service.PageService;
/**
 * @author Sanjeev
 *
 */
@UniqueWithParentId(parentId="moduleNameId", fieldName = "pageName", service = PageService.class, message = "{page.unique.pageNameValue.violation}",
id="id")
@SecondChildUniqueWithParentId(parentId="moduleNameId", fieldName = "baseurl", service = PageService.class, 
message = "{page.unique.baseUrlValue.violation}", id="id")
public class PageMasterDTO {
	
	private Integer id;
	private String departmentName;
	private String moduleName;
	private String pageNameUniqueness;
	@NotEmpty(message = "Select App. Context")
	private String departmentNameId;
	
	@NotEmpty(message = "Select a module")
	private String moduleNameId;
	
	@NotEmpty(message = "Page name is required.")	
	private String pageName;
	@NotEmpty(message = "Base url is required.")
	private String baseurl;
	
	
	private Integer totalrecords;

	//Will be utilized by @Valid
	public PageMasterDTO() {}
	
	//Will be utilized by grid
	public PageMasterDTO(Integer id, String departmentName, String moduleName, String pageName,String baseurl, Integer totalrecords) {
		this.id = id;
		this.departmentName = departmentName;
		this.moduleName = moduleName;
		this.pageName = pageName;
		this.baseurl=baseurl;
		this.totalrecords = totalrecords;
	}
	
	//Will be utilized by grid
		public PageMasterDTO(Integer id, String departmentNameId, String moduleNameId, String pageName,String baseurl) {
			this.id = id;
			this.departmentNameId = departmentNameId;
			this.moduleNameId = moduleNameId;
			this.pageName = pageName;
			this.baseurl=baseurl;
		}

		/**
		 * @return the id
		 */
		public Integer getId() {
			return id;
		}

		/**
		 * @param id the id to set
		 */
		public void setId(Integer id) {
			this.id = id;
		}

		/**
		 * @return the departmentName
		 */
		public String getDepartmentName() {
			return departmentName;
		}

		/**
		 * @param departmentName the departmentName to set
		 */
		public void setDepartmentName(String departmentName) {
			this.departmentName = departmentName;
		}

		/**
		 * @return the moduleName
		 */
		public String getModuleName() {
			return moduleName;
		}

		/**
		 * @param moduleName the moduleName to set
		 */
		public void setModuleName(String moduleName) {
			this.moduleName = moduleName;
		}

		/**
		 * @return the departmentNameId
		 */
		public String getDepartmentNameId() {
			return departmentNameId;
		}

		/**
		 * @param departmentNameId the departmentNameId to set
		 */
		public void setDepartmentNameId(String departmentNameId) {
			this.departmentNameId = departmentNameId;
		}

		/**
		 * @return the moduleNameId
		 */
		public String getModuleNameId() {
			return moduleNameId;
		}

		/**
		 * @param moduleNameId the moduleNameId to set
		 */
		public void setModuleNameId(String moduleNameId) {
			this.moduleNameId = moduleNameId;
		}

		/**
		 * @return the pageName
		 */
		public String getPageName() {
			return pageName;
		}

		/**
		 * @param pageName the pageName to set
		 */
		public void setPageName(String pageName) {
			this.pageName = pageName;
		}

		
		
		/**
		 * @return the baseurl
		 */
		public String getBaseurl() {
			return baseurl;
		}

		/**
		 * @param baseurl the baseurl to set
		 */
		public void setBaseurl(String baseurl) {
			this.baseurl = baseurl;
		}

		
		
		
		/**
		 * @return the pageNameUniqueness
		 */
		public String getPageNameUniqueness() {
			return pageNameUniqueness;
		}

		/**
		 * @param pageNameUniqueness the pageNameUniqueness to set
		 */
		public void setPageNameUniqueness(String pageNameUniqueness) {
			this.pageNameUniqueness = pageNameUniqueness;
		}

		/**
		 * @return the totalrecords
		 */
		public Integer getTotalrecords() {
			return totalrecords;
		}

		/**
		 * @param totalrecords the totalrecords to set
		 */
		public void setTotalrecords(Integer totalrecords) {
			this.totalrecords = totalrecords;
		}
	
		

}
