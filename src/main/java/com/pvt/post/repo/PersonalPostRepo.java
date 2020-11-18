/**
 * 
 */
package com.pvt.post.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pvt.post.entity.PersonalPost;

/**
 * @author Sanjeev
 *
 */
@Repository
public interface PersonalPostRepo extends JpaRepository<PersonalPost, Long>{

}
