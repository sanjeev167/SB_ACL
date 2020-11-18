/**
 * 
 */
package com.pvt.sec.acl.adm.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.pvt.sec.acl.adm.entity.PermissionContext;
import com.pvt.sec.acl.adm.entity.PermissionType;

/**
 * @author Sanjeev
 *
 */
@Repository
public interface PermissionTypeRepo extends JpaRepository<PermissionType, Integer>{
	
	@Query("Select pt from PermissionType pt ORDER BY pt.mask ASC")
	public List<PermissionType> getPermissionType();
	
	@Query(value = "SELECT max(mask) FROM PermissionType")
	public Integer maxPermissionMask();

	public List<PermissionType> findByPermissionContext(PermissionContext permissionContext);

	public PermissionType findByName(String permissionLabel);

	public PermissionType findByMask(Integer valueOf);

	

}
