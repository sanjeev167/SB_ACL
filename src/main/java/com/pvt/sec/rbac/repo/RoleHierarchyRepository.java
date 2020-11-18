/**
 * 
 */
package com.pvt.sec.rbac.repo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.pvt.sec.env.entity.PageMaster;
import com.pvt.sec.rbac.entity.RoleHierarchy;


/**
 * @author Sanjeev
 *
 */
@Repository
public interface RoleHierarchyRepository extends JpaRepository<RoleHierarchy, Integer>{
	
	String treeFromTopToBottom = "WITH RECURSIVE subordinates AS ( "+
			"SELECT "+
			"id, "+
			"head, "+
			"contents, "+
            "app_role_id, "+
            "parent_in_hierarchy_id, "+
            "app_context_id, "+
            "created_by, "+
            "created_on, "+
            "updated_by, "+
            "updated_on, "+
            "active_c "+
		"FROM "+
			"role_hierarchy "+
		"WHERE "+
			"parent_in_hierarchy_id is Null "+
		"UNION "+
			"SELECT "+
			"rh.id, "+
			"rh.head, "+
			"rh.contents, "+
            "rh.app_role_id, "+
            "rh.parent_in_hierarchy_id, "+
            "rh.app_context_id, "+
            "rh.created_by, "+
            "rh.created_on, "+
            "rh.updated_by, "+
            "rh.updated_on, "+
            "rh.active_c "+
			"FROM "+
				"role_hierarchy rh "+
			"INNER JOIN subordinates sub ON sub.id = rh.parent_in_hierarchy_id "+
	") SELECT * FROM subordinates";

	@Query(value = treeFromTopToBottom, nativeQuery = true)
	ArrayList<RoleHierarchy> treeFromTopToBottom();

	String childrenInRoleInHierarchy = 
			"WITH RECURSIVE subordinates AS ( "+
					"SELECT "+
						"id, "+
						"head, "+
						"contents, "+
                        "app_role_id, "+
                        "parent_in_hierarchy_id, "+
                        "app_context_id, "+
                        "created_by, "+
                        "created_on, "+
                        "updated_by, "+
                        "updated_on, "+
                        "active_c "+
					"FROM "+
						"role_hierarchy "+
					"WHERE "+
						"parent_in_hierarchy_id = :parentId "+
					"UNION "+
						"SELECT "+
						"rh.id, "+
						"rh.head, "+
						"rh.contents, "+
                        "rh.app_role_id, "+
                        "rh.parent_in_hierarchy_id, "+
                        "rh.app_context_id, "+
                        "rh.created_by, "+
                        "rh.created_on, "+
                        "rh.updated_by, "+
                        "rh.updated_on, "+
                        "rh.active_c "+
						"FROM "+
							"role_hierarchy rh "+
						"INNER JOIN subordinates sub ON sub.id = rh.parent_in_hierarchy_id "+
				") SELECT * FROM subordinates";

	@Query(value = childrenInRoleInHierarchy, nativeQuery = true)
	ArrayList<RoleHierarchy> childrenInRoleInHierarchy(@Param("parentId") Integer parentId);
	
	
	
	
	
	// Query for a single record element.	
	@Query("SELECT coalesce(min(rh.id), 0) from RoleHierarchy rh")
	Integer getMinId();

	
	@Query(value = "SELECT rh FROM RoleHierarchy rh ORDER BY rh.id")
	List<RoleHierarchy> findAllSortedTreeNodeList();
	
	@Query(value = "SELECT rh FROM RoleHierarchy rh ")
	List<RoleHierarchy> findAllTreeNodeList();
	
	@Query(value = "SELECT rh FROM RoleHierarchy rh where rh.head='None'")
	List<RoleHierarchy> findAllTreeNodeListWithSpecificNodeName();
	
	@Modifying	
	@Query("delete from RoleHierarchy rh where rh.id in ?1")
	void deleteRoleHierarchyWithIds(Integer[] recordIdArray);
	
	@Query("SELECT CASE WHEN COUNT(rh.head) > 0 THEN 'true' ELSE 'false' END FROM RoleHierarchy rh "
			+ " where rh.departmentMaster.id in ?1 and rh.head in ?2")
    public Boolean existsByDepartmentMasterIdAndRoleHierarchyNodeName(Integer departmentMasterId,String head);
		
	
	@Query("SELECT CASE WHEN COUNT(rh.head) > 0 THEN 'true' ELSE 'false' END FROM RoleHierarchy rh where rh.departmentMaster.id in ?1 and rh.head in ?2 and rh.id <> ?3")	
	boolean existsByDepartmentMasterIdAndRoleHierarchyNodeNameExceptThisId(int departmentMasterId, String head, int recordId);

	RoleHierarchy getByHead(String roleAsHead);


}
