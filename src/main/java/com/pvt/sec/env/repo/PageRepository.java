/**
 * 
 */
package com.pvt.sec.env.repo;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.pvt.sec.env.entity.PageMaster;

/**
 * @author Sanjeev
 *
 */
@Repository
public interface PageRepository extends JpaRepository<PageMaster, Integer> {

	@Query(value = "SELECT * FROM PageMaster", nativeQuery = true)
	List<PageMaster> findAllByName(List<String> listOfStateNames);
	
	@Modifying	
	@Query("delete from PageMaster pm where pm.id in ?1")
	void deletePageWithIds(Integer[] recordIdArray);

	//Will be used for checking uniqueness of module with page name
	@Query("SELECT CASE WHEN COUNT(name) > 0 THEN 'true' ELSE 'false' END FROM PageMaster pm where pm.moduleMaster.id in ?1 and pm.name in ?2")		
    public Boolean existsByModuleIdAndPageName(Integer moduleId,String pageName );
	
	@Query("SELECT CASE WHEN COUNT(name) > 0 THEN 'true' ELSE 'false' END FROM PageMaster pm where pm.moduleMaster.id in ?1 and pm.name in ?2 and pm.id <> ?3")	
	boolean existsByModuleIdAndPageNameExceptThisId(int parseInt, String pageName, int id);
	
	
	//Will be used for checking uniqueness of base url with page name
	
	@Query("SELECT CASE WHEN COUNT(name) > 0 THEN 'true' ELSE 'false' END FROM PageMaster pm where pm.moduleMaster.id in ?1 and pm.baseurl in ?2")	
    public Boolean existsByPageNameAndBaseUrl(Integer moduleId,String baseUrl );
	
	@Query("SELECT CASE WHEN COUNT(name) > 0 THEN 'true' ELSE 'false' END FROM PageMaster pm where pm.moduleMaster.id in ?1 and pm.baseurl in ?2 and pm.id <> ?3")	
	boolean existsByPageNameAndBaseUrlExceptThisId(Integer moduleId,String baseUrl, int id);

	@Query(value = "SELECT pm FROM PageMaster pm where pm.baseurl='#' ")
	ArrayList<PageMaster> getDummyNodePageIfExists();
	
	

	
}
