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

import com.pvt.sec.rbac.entity.WebAccessRights;


/**
 * @author Sanjeev
 *
 */
@Repository
public interface WebAccessRightsRepository extends JpaRepository<WebAccessRights, Integer> {
	
	@Query(value = "SELECT * FROM WebAccessRights", nativeQuery = true)
	List<WebAccessRights> findAllByName(List<String> listOfStateNames);
	
	@Modifying
	@Transactional("appTransactionManager")
	@Query("delete from WebAccessRights om where om.id in ?1")
	void deleteOperationWithIds(Integer[] recordIdArray);
	
	@Modifying
	@Transactional("appTransactionManager")
	@Query("delete from WebAccessRights arr where arr.appRole.id=?1 and arr.pageMaster.id in ?2")	
	void deleteRecordWithPageIdAndRoleId(Integer roleId, Integer[] pageIdArray);	

	//Using named parameter in Query method
	//@Query("Select arr from WebAccessRights arr where arr.appRole.id=:roleId and arr.pageMaster.id=:pageId")	
	//List<WebAccessRights> loadOperationOnPage(@Param("roleId") Integer roleId, @Param("pageId") Integer pageId);
			
	 //position based parameter binding:
	@Query("Select arr from WebAccessRights arr where arr.appRole.id=?1 and arr.pageMaster.id=?2")	
	List<WebAccessRights> loadOperationOnPage(Integer roleId, Integer pageId);
			
	@Query("Select arr from WebAccessRights arr where arr.appRole.id=?1")	
	List<WebAccessRights> loadRoleWithPageAccessCountAfterAssignmentAndRemoval(Integer roleId);
	
	
	@Query("Select arr from WebAccessRights arr where arr.appRole.id=?1 and arr.pageMaster.id=?2")
	List<WebAccessRights> loadUniqueRoleAndPageCombination(Integer roleId, Integer pageId);
		
}
