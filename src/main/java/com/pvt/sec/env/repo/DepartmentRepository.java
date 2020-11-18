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

import com.pvt.sec.env.entity.DepartmentMaster;



/**
 * @author Sanjeev
 *
 */
@Repository
public interface DepartmentRepository extends JpaRepository<DepartmentMaster, Integer> {

	@Query(value = "SELECT * FROM CountryMaster", nativeQuery = true)
	List<DepartmentMaster> findAllByName(List<String> listOfCountryNames);
	
	@Query("SELECT CASE WHEN COUNT(name) > 0 THEN 'true' ELSE 'false' END FROM DepartmentMaster dm where dm.name in ?1")
    public Boolean existsByDepartmentName(String departmentName);
	
	@Query("SELECT CASE WHEN COUNT(name) > 0 THEN 'true' ELSE 'false' END FROM DepartmentMaster dm where dm.name in ?1 and dm.id <> ?2")
	boolean existsByDepartmentNameExceptThisId(String name, int id);
	
	@Modifying
	@Transactional
	@Query("delete from DepartmentMaster cm where cm.id in ?1")
	void deleteDepartmentWithIds(Integer[] recordIdArray);
}
