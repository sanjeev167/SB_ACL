/**
 * 
 */
package com.pvt.post.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pvt.post.entity.PersonalPost;
import com.pvt.post.entity.PublicPost;

/**
 * @author Sanjeev
 *
 */

@Repository
public interface PublicPostRepo extends JpaRepository<PublicPost, Long>{

}
