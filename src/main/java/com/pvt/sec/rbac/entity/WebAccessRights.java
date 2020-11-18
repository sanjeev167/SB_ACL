package com.pvt.sec.rbac.entity;

import java.io.Serializable;
import javax.persistence.*;

import com.pvt.sec.env.entity.PageMaster;

/**
 * The persistent class for the access_rights_rbac database table.
 * 
 */
@Entity
@Table(name="web_access_rights")
@NamedQuery(name="WebAccessRights.findAll", query="SELECT a FROM WebAccessRights a")
public class WebAccessRights implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	//bi-directional many-to-one association to AppRole
	@ManyToOne
	@JoinColumn(name="role_id")
	private AppRole appRole;

	//bi-directional many-to-one association to PageMaster
	@ManyToOne
	@JoinColumn(name="page_id")
	private PageMaster pageMaster;		

	public WebAccessRights() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public AppRole getAppRole() {
		return this.appRole;
	}

	public void setAppRole(AppRole appRole) {
		this.appRole = appRole;
	}

	public PageMaster getPageMaster() {
		return this.pageMaster;
	}

	public void setPageMaster(PageMaster pageMaster) {
		this.pageMaster = pageMaster;
	}

}