/**
 * 
 */
package com.pvt.sec.acl.adm.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.pvt.sec.acl.adm.entity.AclPolicyDefinedOnDomain;
import com.pvt.sec.acl.adm.entity.DomainClass;

import com.pvt.sec.rbac.entity.AppRole;

/**
 * @author Sanjeev
 *
 */

@Repository
public interface AclPolicyDefinedOnDomainRepo extends JpaRepository<AclPolicyDefinedOnDomain, Integer>{

	
	@Query("Select apd from AclPolicyDefinedOnDomain apd where apd.domainClass=?1 and apd.isPrincipal=?2")
	public AclPolicyDefinedOnDomain getAclPolicyDefinedOnDomainForOwner(DomainClass domainClass,Boolean isPrincipal);

	@Query("Select apd from AclPolicyDefinedOnDomain apd where apd.domainClass=?1 and apd.appRole=?2")
	public AclPolicyDefinedOnDomain getAclPolicyDefinedOnDomainForRole(DomainClass domainClassLocal, AppRole role);
	
	@Query("Select apd from AclPolicyDefinedOnDomain apd ORDER BY apd.id ASC")
	public List<AclPolicyDefinedOnDomain> getAllAclPolicy();
	
	@Query("Select apd from AclPolicyDefinedOnDomain apd where apd.domainClass=?1")
	List<AclPolicyDefinedOnDomain> getAllAclPolicyDefinedOnDomain(DomainClass domainClassLocal);
	
	
	
}
