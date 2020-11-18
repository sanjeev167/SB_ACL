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

import com.pvt.sec.rbac.entity.AppGroupRole;

/**
 * @author Sanjeev
 *
 */
@Repository
public interface AppGroupRoleRepository extends JpaRepository<AppGroupRole, Integer> {

	@Query(value = "SELECT * FROM AppGroupRole", nativeQuery = true)
	List<AppGroupRole> findAllByName(List<String> listOfAppGroupRoleNames);

	@Modifying
	@Transactional
	@Query("delete from AppGroupRole cm where cm.id in ?1")
	void deleteAppGroupRoleWithIds(Integer[] recordIdArray);

	// Will be used for checking uniqueness of group with role association
	@Query("SELECT CASE WHEN COUNT(agr.id) > 0 THEN 'true' ELSE 'false' END FROM AppGroupRole agr where agr.appGroup.id in ?1 and agr.appRole.id in ?2")
	public Boolean existsByGroupIdAndRoleName(Integer parentId, Integer roleId);

	@Query("SELECT CASE WHEN COUNT(agr.id) > 0 THEN 'true' ELSE 'false' END FROM AppGroupRole agr where agr.appGroup.id in ?1 and agr.appRole.id in ?2 and agr.id <> ?3")
	boolean existsByGroupIdAndRoleNameExceptThisId(int parseInt, Integer roleId, int id);

}