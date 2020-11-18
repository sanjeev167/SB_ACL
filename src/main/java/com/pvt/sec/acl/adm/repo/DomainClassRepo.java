/**
 * 
 */
package com.pvt.sec.acl.adm.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.pvt.sec.acl.adm.entity.DomainClass;

/**
 * @author Sanjeev
 *
 */
@Repository
public interface DomainClassRepo extends JpaRepository<DomainClass, Long>{

	DomainClass findByClassName(String domainClass);

	@Query("Select d from DomainClass d where d.className=?1")
	DomainClass findDomainClassWithDomainName(String domainName);

	void deleteByClassName(String domainName);

}
