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

import com.pvt.sec.env.entity.MenuManager;


/**
 * @author Sanjeev
 *
 */
@Repository
public interface MenuManagerRepository extends JpaRepository<MenuManager, Integer>{
	
	
	// Query for a single data element.	
	@Query("SELECT coalesce(min(mm.id), 0) from MenuManager mm")
	Integer getMinId();

	
	@Query(value = "SELECT mm FROM MenuManager mm ORDER BY mm.id")
	List<MenuManager> findAllSortedTreeNodeList();
	
	@Query(value = "SELECT mm FROM MenuManager mm where mm.nodeType='N'")
	List<MenuManager> findAllTreeNodeList();
	
	@Query(value = "SELECT mm FROM MenuManager mm where mm.nodeName='None'")
	List<MenuManager> findAllTreeNodeListWithSpecificNodeName();
	
	@Modifying
	@Transactional
	@Query("delete from MenuManager mm where mm.id in ?1")
	void deleteMenuManagerWithIds(Integer[] recordIdArray);
	
	@Query("SELECT CASE WHEN COUNT(mm.nodeName) > 0 THEN 'true' ELSE 'false' END FROM MenuManager mm "
			+ " where mm.departmentMaster.id in ?1 and mm.nodeName in ?2")
    public Boolean existsByDepartmentMasterIdAndMenuManagerNodeName(Integer departmentMasterId,String nodeName);
		
	
	@Query("SELECT CASE WHEN COUNT(mm.nodeName) > 0 THEN 'true' ELSE 'false' END FROM MenuManager mm where mm.departmentMaster.id in ?1 and mm.nodeName in ?2 and mm.id <> ?3")	
	boolean existsByDepartmentMasterIdAndMenuManagerNodeNameExceptThisId(int departmentMasterId, String nodeName, int recordId);

}
