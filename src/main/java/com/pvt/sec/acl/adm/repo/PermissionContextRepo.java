/**
 * 
 */
package com.pvt.sec.acl.adm.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pvt.sec.acl.adm.entity.PermissionContext;

/**
 * @author Sanjeev
 *
 */
@Repository
public interface PermissionContextRepo extends JpaRepository<PermissionContext, Integer>{

	List<PermissionContext> findByIsBaseContext(boolean b);

}
