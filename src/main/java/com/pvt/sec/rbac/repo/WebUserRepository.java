/**
 * 
 */
package com.pvt.sec.rbac.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.pvt.sec.rbac.entity.WebUser;



/**
 * @author Sanjeev
 *
 */
@Repository
public interface WebUserRepository extends JpaRepository<WebUser, Long> {	
	 
	 @Query("SELECT user FROM WebUser user where user.email = ?1 ")
	 public Optional<WebUser> findByEmail(String email);		
	
	@Modifying
	@Transactional
	@Query("delete from WebUser ur where ur.id in ?1")
	void deleteAppAdminUserWithIds(Long[] recordIdArray);
	
	@Query("SELECT CASE WHEN COUNT(ur.email) > 0 THEN 'true' ELSE 'false' END FROM WebUser ur "
			+ " where ur.email in ?1")
    public Boolean existsByUserLoginId(String email);
	
	@Query("SELECT CASE WHEN COUNT(ur.email) > 0 THEN 'true' ELSE 'false' END FROM WebUser ur "
			+ " where ur.email in ?1 and ur.id <> ?2")
    public Boolean existsByUserLoginIdExceptThisId(String email,Long id);
}
