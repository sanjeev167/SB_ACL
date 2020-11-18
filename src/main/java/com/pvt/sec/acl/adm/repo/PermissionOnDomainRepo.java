/**
 * 
 */
package com.pvt.sec.acl.adm.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.pvt.sec.acl.adm.entity.DomainClass;
import com.pvt.sec.acl.adm.entity.PermissionOnDomain;

/**
 * @author Sanjeev
 *
 */

@Repository
public interface PermissionOnDomainRepo extends JpaRepository<PermissionOnDomain, Integer> {

	PermissionOnDomain findByDomainClass(DomainClass domainClassLocal);

	@Query("Select pd from PermissionOnDomain pd where pd.domainClass=?1")
	PermissionOnDomain getDomainPermission(DomainClass domainClass);

	void deleteByDomainClass(DomainClass domainClass);

	

	

	

	

}
