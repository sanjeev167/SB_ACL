package com.pvt.sec.rbac.entity;

import java.io.Serializable;

import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Table;


import com.pvt.sec.rbac.dto.AppUserGroupDTO;




/**
 * The persistent class for the user_group database table.
 * 
 */
@Entity
@Table(name="user_group")
@NamedQuery(name="UserGroup.findAll", query="SELECT u FROM UserGroup u")
@SqlResultSetMapping(
        name = "AppUserGroupDTOMapping",
        classes = @ConstructorResult(
                targetClass = AppUserGroupDTO.class,
                columns = { @ColumnResult(name = "id", type = Integer.class), 
                            @ColumnResult(name = "departmentName"), 
                            @ColumnResult(name = "groupName"), 
                            @ColumnResult(name = "categoryName"),                             
                            @ColumnResult(name = "userName"), 
                            @ColumnResult(name = "totalrecords", type = Integer.class),
                            }))
public class UserGroup implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	//bi-directional many-to-one association to AppGroup
	@ManyToOne
	@JoinColumn(name="app_group_id")
	private AppGroup appGroup;

	//bi-directional many-to-one association to UserReg
	@ManyToOne
	@JoinColumn(name="user_id")
	private UserReg userReg;

	//bi-directional many-to-one association to UserReg
		@ManyToOne
		@JoinColumn(name="web_user_id")
		private WebUser webUser;
	
	
	public UserGroup() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public AppGroup getAppGroup() {
		return this.appGroup;
	}

	public void setAppGroup(AppGroup appGroup) {
		this.appGroup = appGroup;
	}

	public UserReg getUserReg() {
		return this.userReg;
	}

	public void setUserReg(UserReg userReg) {
		this.userReg = userReg;
	}

	/**
	 * @return the webUser
	 */
	public WebUser getWebUser() {
		return webUser;
	}

	/**
	 * @param webUser the webUser to set
	 */
	public void setWebUser(WebUser webUser) {
		this.webUser = webUser;
	}
	
	

}