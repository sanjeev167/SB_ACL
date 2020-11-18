package com.pvt.sec.rbac.entity;

import java.io.Serializable;
import javax.persistence.*;

import com.pvt.sec.env.entity.DepartmentMaster;
import com.pvt.sec.rbac.dto.RoleHierarchyDTO;

import java.sql.Time;
import java.util.List;


/**
 * The persistent class for the role_hierarchy database table.
 * 
 */
@Entity
@Table(name="role_hierarchy")
@NamedQuery(name="RoleHierarchy.findAll", query="SELECT r FROM RoleHierarchy r")

@SqlResultSetMapping(
        name = "RoleHierarchyDTOMapping",
        classes = @ConstructorResult(
                targetClass = RoleHierarchyDTO.class,
                columns = { @ColumnResult(name = "id", type = Integer.class), 
                	    @ColumnResult(name = "contextName", type = String.class), 
                	    @ColumnResult(name = "parentName", type = String.class), 
                	    @ColumnResult(name = "childName", type = String.class), 
                	    @ColumnResult(name = "parentId", type = String.class),                	    
                	    @ColumnResult(name = "contents", type = String.class),                	   
                	    @ColumnResult(name = "activeC", type = String.class),                             
                	    @ColumnResult(name = "totalrecords", type = Integer.class)
                            }))

public class RoleHierarchy implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private Integer id;

	@Column(name="active_c", length=1)
	private String activeC;

	@Column(length=100)
	private String contents;
	
	@Column(length=50)
	private String head;
	
	@Column(name="created_by")
	private Integer createdBy;

	@Column(name="created_on")
	private Time createdOn;

	@Column(name="updated_by")
	private Integer updatedBy;

	@Column(name="updated_on")
	private Time updatedOn;

	//bi-directional many-to-one association to AppRole
	@ManyToOne(cascade = { CascadeType.PERSIST })
	@JoinColumn(name="app_role_id")
	private AppRole appRole;

	
	//bi-directional many-to-one association to DepartmentMaster
	@ManyToOne
	@JoinColumn(name="app_context_id")
	private DepartmentMaster departmentMaster;
	
	//bi-directional many-to-one association to RoleHierarchy
		@ManyToOne(cascade = { CascadeType.PERSIST })
		@JoinColumn(name="parent_in_hierarchy_id")
		private RoleHierarchy roleHierarchy;

		//bi-directional many-to-one association to RoleHierarchy
		@OneToMany(mappedBy="roleHierarchy")
		private List<RoleHierarchy> roleHierarchies;
	

	public RoleHierarchy() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getActiveC() {
		return this.activeC;
	}

	public void setActiveC(String activeC) {
		this.activeC = activeC;
	}

	public String getContents() {
		return this.contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}


	public String getHead() {
		return this.head;
	}

	public void setHead(String head) {
		this.head = head;
	}
	public Integer getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	public Time getCreatedOn() {
		return this.createdOn;
	}

	public void setCreatedOn(Time createdOn) {
		this.createdOn = createdOn;
	}

	public Integer getUpdatedBy() {
		return this.updatedBy;
	}

	public void setUpdatedBy(Integer updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Time getUpdatedOn() {
		return this.updatedOn;
	}

	public void setUpdatedOn(Time updatedOn) {
		this.updatedOn = updatedOn;
	}

	public AppRole getAppRole() {
		return this.appRole;
	}

	public void setAppRole(AppRole appRole) {
		this.appRole = appRole;
	}


	public DepartmentMaster getDepartmentMaster() {
		return this.departmentMaster;
	}

	public void setDepartmentMaster(DepartmentMaster departmentMaster) {
		this.departmentMaster = departmentMaster;
	}
	
	public RoleHierarchy getRoleHierarchy() {
		return this.roleHierarchy;
	}

	public void setRoleHierarchy(RoleHierarchy roleHierarchy) {
		this.roleHierarchy = roleHierarchy;
	}

	public List<RoleHierarchy> getRoleHierarchies() {
		return this.roleHierarchies;
	}

	public void setRoleHierarchies(List<RoleHierarchy> roleHierarchies) {
		this.roleHierarchies = roleHierarchies;
	}

	public RoleHierarchy addRoleHierarchy(RoleHierarchy roleHierarchy) {
		getRoleHierarchies().add(roleHierarchy);
		roleHierarchy.setRoleHierarchy(this);

		return roleHierarchy;
	}

	public RoleHierarchy removeRoleHierarchy(RoleHierarchy roleHierarchy) {
		getRoleHierarchies().remove(roleHierarchy);
		roleHierarchy.setRoleHierarchy(null);

		return roleHierarchy;
	}

}