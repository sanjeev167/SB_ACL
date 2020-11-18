/**
 * 
 */
package com.pvt.sec.acl.monitor.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.pvt.sec.acl.monitor.entity.AclClass;
/**
 * @author Sanjeev
 *
 */
@Repository
public interface AclClassRepository extends JpaRepository<AclClass, Long> {

	@Query("SELECT CASE WHEN COUNT(ac.class_) > 0 THEN 'true' ELSE 'false' END FROM AclClass ac where ac.class_ in ?1")	   
	boolean existsByClassName(String classWithPkg);
	
	@Modifying
	@Transactional
	@Query("delete from AclClass ac where ac.id in ?1")
	void deleteDomainWithIds(Long[] recordIdArray);
}


