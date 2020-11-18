package com.pvt.sec.rbac.entity;

import java.io.Serializable;
import java.util.List;

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



import com.pvt.sec.rbac.dto.AppAdminUserDTO;
import com.pvt.sec.rbac.entity.UserCategory;
import com.pvt.sec.rbac.entity.UserGroup;


/**
 * The persistent class for the user_reg database table.
 * 
 */
@Entity
@Table(name="user_reg")
@NamedQuery(name="UserReg.findAll", query="SELECT u FROM UserReg u")
@SqlResultSetMapping(
        name = "AppAdminUserDTOMapping",
        classes = @ConstructorResult(
                targetClass = AppAdminUserDTO.class,
                columns = { @ColumnResult(name = "id", type = Integer.class), 
                            @ColumnResult(name = "departmentName"), 
                            @ColumnResult(name = "categoryName"), 
                            @ColumnResult(name = "name"), 
                            @ColumnResult(name = "userLoginId"), 
                            @ColumnResult(name = "totalrecords", type = Integer.class),
                            }))
public class UserReg implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	private String name;
	private String email;

	private String password;

	//bi-directional many-to-one association to UserGroup
	@OneToMany(mappedBy="userReg")
	private List<UserGroup> userGroups;

	//bi-directional many-to-one association to UserCategory
	@ManyToOne
	@JoinColumn(name="user_category_id")
	private UserCategory userCategory;

	//bi-directional many-to-one association to UserAddress
	//@OneToMany(mappedBy="userReg")
	//private List<UserAddress> userAddresses;

	//bi-directional many-to-one association to UserJobDetail
	//@OneToMany(mappedBy="userReg")
	//private List<UserJobDetail> userJobDetails;

	//bi-directional many-to-one association to UserPersonelDetail
	//@OneToMany(mappedBy="userReg")
	//private List<UserPersonelDetail> userPersonelDetails;
	
	
	public UserReg() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<UserGroup> getUserGroups() {
		return this.userGroups;
	}

	public void setUserGroups(List<UserGroup> userGroups) {
		this.userGroups = userGroups;
	}

	public UserGroup addUserGroup(UserGroup userGroup) {
		getUserGroups().add(userGroup);
		userGroup.setUserReg(this);

		return userGroup;
	}

	public UserGroup removeUserGroup(UserGroup userGroup) {
		getUserGroups().remove(userGroup);
		userGroup.setUserReg(null);

		return userGroup;
	}

	public UserCategory getUserCategory() {
		return this.userCategory;
	}

	public void setUserCategory(UserCategory userCategory) {
		this.userCategory = userCategory;
	}

	/*public List<UserAddress> getUserAddresses() {
		return this.userAddresses;
	}

	public void setUserAddresses(List<UserAddress> userAddresses) {
		this.userAddresses = userAddresses;
	}

	public UserAddress addUserAddress(UserAddress userAddress) {
		getUserAddresses().add(userAddress);
		userAddress.setUserReg(this);

		return userAddress;
	}

	public UserAddress removeUserAddress(UserAddress userAddress) {
		getUserAddresses().remove(userAddress);
		userAddress.setUserReg(null);

		return userAddress;
	}

	public List<UserJobDetail> getUserJobDetails() {
		return this.userJobDetails;
	}

	public void setUserJobDetails(List<UserJobDetail> userJobDetails) {
		this.userJobDetails = userJobDetails;
	}

	public UserJobDetail addUserJobDetail(UserJobDetail userJobDetail) {
		getUserJobDetails().add(userJobDetail);
		userJobDetail.setUserReg(this);

		return userJobDetail;
	}

	public UserJobDetail removeUserJobDetail(UserJobDetail userJobDetail) {
		getUserJobDetails().remove(userJobDetail);
		userJobDetail.setUserReg(null);

		return userJobDetail;
	}

	public List<UserPersonelDetail> getUserPersonelDetails() {
		return this.userPersonelDetails;
	}

	public void setUserPersonelDetails(List<UserPersonelDetail> userPersonelDetails) {
		this.userPersonelDetails = userPersonelDetails;
	}

	public UserPersonelDetail addUserPersonelDetail(UserPersonelDetail userPersonelDetail) {
		getUserPersonelDetails().add(userPersonelDetail);
		userPersonelDetail.setUserReg(this);

		return userPersonelDetail;
	}

	public UserPersonelDetail removeUserPersonelDetail(UserPersonelDetail userPersonelDetail) {
		getUserPersonelDetails().remove(userPersonelDetail);
		userPersonelDetail.setUserReg(null);

		return userPersonelDetail;
	}*/

	

}