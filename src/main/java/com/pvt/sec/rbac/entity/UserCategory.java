package com.pvt.sec.rbac.entity;

import java.io.Serializable;
import javax.persistence.*;



import com.pvt.sec.env.entity.DepartmentMaster;
import com.pvt.sec.rbac.dto.UserCategoryDTO;


import java.util.List;


/**
 * The persistent class for the user_category database table.
 * 
 */
@Entity
@Table(name="user_category")
@NamedQuery(name="UserCategory.findAll", query="SELECT u FROM UserCategory u")
@SqlResultSetMapping(
        name = "UserCategoryDTOMapping",
        classes = @ConstructorResult(
                targetClass = UserCategoryDTO.class,
                columns = { @ColumnResult(name = "id", type = Integer.class), 
                            @ColumnResult(name = "departmentName"), 
                            @ColumnResult(name = "categoryName"),                             
                            @ColumnResult(name = "totalrecords", type = Integer.class),
                            }))
public class UserCategory implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	private String name;

	//bi-directional many-to-one association to DepartmentMaster
	@ManyToOne
	@JoinColumn(name="department_id")
	private DepartmentMaster departmentMaster;

	//bi-directional many-to-one association to UserReg
	@OneToMany(mappedBy="userCategory")
	private List<UserReg> userRegs;

	//bi-directional many-to-one association to WebUser
	@OneToMany(mappedBy="userCategory")
	private List<WebUser> webUsers;

	public UserCategory() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public DepartmentMaster getDepartmentMaster() {
		return this.departmentMaster;
	}

	public void setDepartmentMaster(DepartmentMaster departmentMaster) {
		this.departmentMaster = departmentMaster;
	}

	public List<UserReg> getUserRegs() {
		return this.userRegs;
	}

	public void setUserRegs(List<UserReg> userRegs) {
		this.userRegs = userRegs;
	}

	public UserReg addUserReg(UserReg userReg) {
		getUserRegs().add(userReg);
		userReg.setUserCategory(this);

		return userReg;
	}

	public UserReg removeUserReg(UserReg userReg) {
		getUserRegs().remove(userReg);
		userReg.setUserCategory(null);

		return userReg;
	}

	public List<WebUser> getWebUsers() {
		return this.webUsers;
	}

	public void setWebUsers(List<WebUser> webUsers) {
		this.webUsers = webUsers;
	}

	public WebUser addWebUser(WebUser webUser) {
		getWebUsers().add(webUser);
		webUser.setUserCategory(this);

		return webUser;
	}

	public WebUser removeWebUser(WebUser webUser) {
		getWebUsers().remove(webUser);
		webUser.setUserCategory(null);

		return webUser;
	}

}