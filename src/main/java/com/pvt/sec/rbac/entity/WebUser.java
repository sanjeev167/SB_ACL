package com.pvt.sec.rbac.entity;

import java.io.Serializable;
import java.sql.Time;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Table;

import com.pvt.sec.rbac.dto.WebUserDTO;
import com.pvt.sec.rbac.entity.UserCategory;
import com.pvt.sec.rbac.entity.UserGroup;




/**
 * The persistent class for the web_user database table.
 * 
 */
@Entity
@Table(name="web_user")
@NamedQuery(name="WebUser.findAll", query="SELECT w FROM WebUser w")

//@NamedQuery(name = "WebUser.findByEmail", query = "select u from WebUser u where u.email = ?1")


@SqlResultSetMapping(
        name = "WebUserDTOMapping",
        classes = @ConstructorResult(
                targetClass = WebUserDTO.class,
                columns = { @ColumnResult(name = "id", type = Long.class), 
                            @ColumnResult(name = "departmentName"), 
                            @ColumnResult(name = "categoryName"), 
                            @ColumnResult(name = "name"),                            
                            @ColumnResult(name = "userLoginId"), 
                            @ColumnResult(name = "authprovider"), 
                            @ColumnResult(name = "activeOrNot"), 
                            
                            @ColumnResult(name = "totalrecords", type = Integer.class),
                            }))
public class WebUser implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@Column(name="active_c")
	private String activeC="Y";

	@Column(name="created_on")
	private Time createdOn;

	private String email;

	private String name;
	
	private String password;
	
	private String authprovider;
	
	//bi-directional many-to-one association to UserGroup
		@OneToMany(mappedBy="webUser", cascade = CascadeType.REMOVE)
		private List<UserGroup> userGroups;

	//bi-directional many-to-one association to UserCategory
	@ManyToOne
	@JoinColumn(name="user_category_id")
	private UserCategory userCategory;

	public WebUser() {
	}

	

	

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getActiveC() {
		return this.activeC;
	}

	public void setActiveC(String activeC) {
		this.activeC = activeC;
	}

	public Time getCreatedOn() {
		return this.createdOn;
	}

	public void setCreatedOn(Time createdOn) {
		this.createdOn = createdOn;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the signupas
	 */
	public String getAuthprovider() {
		return authprovider;
	}

	/**
	 * @param signupas the signupas to set
	 */
	public void setAuthprovider(String authprovider) {
		this.authprovider = authprovider;
	}

	public UserCategory getUserCategory() {
		return this.userCategory;
	}

	public void setUserCategory(UserCategory userCategory) {
		this.userCategory = userCategory;
	}
	
	
	public List<UserGroup> getUserGroups() {
		return this.userGroups;
	}

	public void setUserGroups(List<UserGroup> userGroups) {
		this.userGroups = userGroups;
	}

	public UserGroup addUserGroup(UserGroup userGroup) {
		getUserGroups().add(userGroup);
		userGroup.setWebUser(this);

		return userGroup;
	}

	public UserGroup removeUserGroup(UserGroup userGroup) {
		getUserGroups().remove(userGroup);
		userGroup.setWebUser(null);

		return userGroup;
	}

	
	
		
	
}