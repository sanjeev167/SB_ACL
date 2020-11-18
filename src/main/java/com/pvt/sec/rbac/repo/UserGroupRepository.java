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
import com.pvt.sec.rbac.entity.UserGroup;

/**
 * @author Sanjeev
 *
 */
@Repository
public interface UserGroupRepository extends JpaRepository<UserGroup, Integer> {

	@Query(value = "SELECT * FROM UserGroup", nativeQuery = true)
	List<AppGroupRole> findAllByName(List<String> listOfAppGroupRoleNames);


	@Modifying
	@Transactional
	@Query("delete from UserGroup ug where ug.id in ?1")
	void deleteWithIds(Integer[] recordIdArray);

	

	 //Will be used for checking uniqueness of user with group association
	@Query("SELECT CASE WHEN COUNT(ug.id) > 0 THEN 'true' ELSE 'false' END FROM UserGroup ug where ug.appGroup.id in ?1 and ug.userReg.id in ?2")		
    public Boolean existsByGroupIdAndUserId(Integer groupId,Integer userId );
	
	@Query("SELECT CASE WHEN COUNT(ug.id) > 0 THEN 'true' ELSE 'false' END FROM UserGroup ug where ug.appGroup.id in ?1 and ug.userReg.id in ?2 and ug.id <> ?3")	
	boolean existsByGroupIdAndUserIdExceptThisId(Integer groupId,Integer userId, int id);
	
	


} 