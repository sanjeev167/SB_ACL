/**
 * 
 */
package com.pvt.sec.env.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.pvt.sec.env.entity.ModuleMaster;



/**
 * @author Sanjeev
 *
 */
@Repository
public interface ModuleRepository extends JpaRepository<ModuleMaster, Integer> {

	@Query(value = "SELECT * FROM StateMaster", nativeQuery = true)
	List<ModuleMaster> findAllByName(List<String> listOfStateNames);
	
	@Query("SELECT CASE WHEN COUNT(mm.name) > 0 THEN 'true' ELSE 'false' END FROM ModuleMaster mm, DepartmentMaster dm "
			+ " where dm.id in ?1 and mm.name in ?2")
    public Boolean existsByDepartmentIdAndModuleName(Integer parentId,String moduleName);
	
	@Query("SELECT CASE WHEN COUNT(name) > 0 THEN 'true' ELSE 'false' END FROM ModuleMaster mm where mm.departmentMaster.id in ?1 and mm.name in ?2 and mm.id <> ?3")	
	boolean existsByDepartmentIdAndModuleNameExceptThisId(int parseInt, String stateName, int id);


	@Modifying
	@Transactional
	@Query("delete from ModuleMaster sm where sm.id in ?1")
	void deleteModuleWithIds(Integer[] recordIdArray);



}
