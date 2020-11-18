/**
 * 
 */
package com.pvt.sec.acl.monitor.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pvt.sec.acl.monitor.entity.AclEntry;

/**
 * @author Sanjeev
 *
 */
@Repository
public interface AclEntryRepository extends JpaRepository<AclEntry, Long>{
	

}
