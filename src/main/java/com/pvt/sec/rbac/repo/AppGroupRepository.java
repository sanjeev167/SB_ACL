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

import com.pvt.sec.rbac.entity.AppGroup;

/**
 * @author Sanjeev
 *
 */
@Repository
public interface AppGroupRepository extends JpaRepository<AppGroup, Integer> {

	@Query(value = "SELECT * FROM AppGroup", nativeQuery = true)
	List<AppGroup> findAllByName(List<String> listOfAppRolesNames);


	@Modifying
	@Transactional
	@Query("delete from AppGroup sm where sm.id in ?1")
	void deleteAppGroupWithIds(Integer[] recordIdArray);

	
	

	 //Will be used for checking uniqueness of module with page name
	@Query("SELECT CASE WHEN COUNT(groupName) > 0 THEN 'true' ELSE 'false' END FROM AppGroup ag where ag.departmentMaster.id in ?1 and ag.groupName in ?2")		
    public Boolean existsByDepartmentIdAndAppGroupName(Integer parentId,String groupName );
	
	@Query("SELECT CASE WHEN COUNT(groupName) > 0 THEN 'true' ELSE 'false' END FROM AppGroup ag where ag.departmentMaster.id in ?1 and ag.groupName in ?2 and ag.id <> ?3")	
	boolean existsByDepartmentIdAndGroupExceptThisId(int parseInt, String groupName, int id);
	
	
	
	
}
