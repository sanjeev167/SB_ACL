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

import com.pvt.sec.rbac.entity.UserReg;

/**
 * @author Sanjeev
 *
 */
@Repository
public interface AppAdminUserRepository extends JpaRepository<UserReg, Integer> {

	//@Query(value = "SELECT * FROM UserRegMaster", nativeQuery = true)
	//List<StateMaster> findAllByName(List<String> listOfStateNames);	

	@Modifying
	@Transactional
	@Query("delete from UserReg ur where ur.id in ?1")
	void deleteAppAdminUserWithIds(Integer[] recordIdArray);
	
	@Query("SELECT CASE WHEN COUNT(ur.email) > 0 THEN 'true' ELSE 'false' END FROM UserReg ur "
			+ " where ur.email in ?1")
    public Boolean existsByUserLoginId(String email);
	
	@Query("SELECT CASE WHEN COUNT(ur.email) > 0 THEN 'true' ELSE 'false' END FROM UserReg ur "
			+ " where ur.email in ?1 and ur.id <> ?2")
    public Boolean existsByUserLoginIdExceptThisId(String email,Integer id);

	 @Query("SELECT user FROM UserReg user where user.email = ?1 ")
	 public Optional<UserReg> findByEmail(String email);
}
