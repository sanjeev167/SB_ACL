/**
 * 
 */
package com.pvt.sec.acl.monitor.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pvt.sec.acl.monitor.entity.AclObjectIdentity;

/**
 * @author Sanjeev
 *
 */
@Repository
public interface AclObjIdendentityRepository extends JpaRepository<AclObjectIdentity, Long>{
	

}
