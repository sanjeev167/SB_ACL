package com.pvt.sec.rbac.entity;

import java.io.Serializable;
import javax.persistence.*;

import com.pvt.sec.env.entity.PageMaster;


/**
 * The persistent class for the Page_access database table.
 * 
 */
@Entity
@Table(name="page_access")
@NamedQuery(name="PageAccess.findAll", query="SELECT p FROM PageAccess p")
public class PageAccess implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	//bi-directional many-to-one association to AccessRightsRbac
	@ManyToOne
	@JoinColumn(name="role_id")
	private AppRole appRole;

	//bi-directional many-to-one association to PageMaster
	@ManyToOne
	@JoinColumn(name="page_id")
	private PageMaster PageMaster;

	public PageAccess() {
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
		return this.PageMaster;
	}

	public void setPageMaster(PageMaster PageMaster) {
		this.PageMaster = PageMaster;
	}

}