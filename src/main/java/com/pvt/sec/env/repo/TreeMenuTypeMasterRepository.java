/**
 * 
 */
package com.pvt.sec.env.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pvt.sec.env.entity.TreeMenuTypeMaster;


/**
 * @author Sanjeev
 *
 */
@Repository
public interface TreeMenuTypeMasterRepository extends JpaRepository<TreeMenuTypeMaster, Integer>{

}
