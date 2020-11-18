/**
 * 
 */
package com.pvt.sec.acl.monitor.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.pvt.sec.acl.monitor.entity.AclSid;

/**
 * @author Sanjeev
 *
 */
@Repository
public interface AclSidRepository extends JpaRepository<AclSid, Long> {	
	@Query("SELECT CASE WHEN COUNT(sid) > 0 THEN 'true' ELSE 'false' END FROM AclSid where sid in ?1")	   
	boolean existsByClassName(String sid);
}
