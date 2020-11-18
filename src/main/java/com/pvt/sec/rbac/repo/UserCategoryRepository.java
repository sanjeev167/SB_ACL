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

import com.pvt.sec.rbac.entity.UserCategory;

/**
 * @author Sanjeev
 *
 */
@Repository
public interface UserCategoryRepository extends JpaRepository<UserCategory, Integer> {

	@Query(value = "SELECT * FROM UserCategory", nativeQuery = true)
	List<UserCategory> findAllByName(List<String> listOfAppRolesNames);


	@Modifying
	@Transactional
	@Query("delete from UserCategory sm where sm.id in ?1")
	void deleteUserCategoryWithIds(Integer[] recordIdArray);
	
	
	// Will be used for checking uniqueness of department with user category association
		@Query("SELECT CASE WHEN COUNT(uc.name) > 0 THEN 'true' ELSE 'false' END FROM UserCategory uc where uc.departmentMaster.id in ?1 and uc.name in ?2")
		public Boolean existsByDepartmentIdAndUserCategory(Integer parentId, String categoryName);

		@Query("SELECT CASE WHEN COUNT(uc.name) > 0 THEN 'true' ELSE 'false' END FROM UserCategory uc where uc.departmentMaster.id in ?1 and uc.name in ?2 and uc.id <> ?3")
		boolean existsByDepartmentIdAndUserCategoryExceptThisId(int parseInt, String categoryName, int id);
	
}
