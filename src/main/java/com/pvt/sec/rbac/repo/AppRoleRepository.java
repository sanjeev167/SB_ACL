/**
 * 
 */
package com.pvt.sec.rbac.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.pvt.sec.rbac.entity.AppRole;

/**
 * @author Sanjeev
 *
 */
@Repository
public interface AppRoleRepository extends JpaRepository<AppRole, Integer> {

	

	@Modifying
	@Transactional
	@Query("delete from AppRole ar where ar.id in ?1")
	void deleteAppRoleWithIds(Integer[] recordIdArray);
	
	
	    //Will be used for checking uniqueness of module with page name
		@Query("SELECT CASE WHEN COUNT(roleName) > 0 THEN 'true' ELSE 'false' END FROM AppRole ar where ar.departmentMaster.id in ?1 and ar.roleName in ?2")		
	    public Boolean existsByDepartmentIdAndAppRoleName(Integer parentId,String roleName );
		
		@Query("SELECT CASE WHEN COUNT(roleName) > 0 THEN 'true' ELSE 'false' END FROM AppRole ar where ar.departmentMaster.id in ?1 and ar.roleName in ?2 and ar.id <> ?3")	
		boolean existsByDepartmentIdAndRoleExceptThisId(int parseInt, String roleName, int id);
		
		@Query(value = "SELECT ar FROM AppRole ar where ar.roleName='None'")
		List<AppRole> findAllRoleListWithSpecificNodeName();


		String findByRoleName(String roleName);
		
	
	
	
	
}
