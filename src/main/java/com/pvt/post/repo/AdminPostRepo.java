/**
 * 
 */
package com.pvt.post.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pvt.post.entity.AdminPost;

/**
 * @author Sanjeev
 *
 */
@Repository
public interface AdminPostRepo extends JpaRepository<AdminPost, Long>{

}
