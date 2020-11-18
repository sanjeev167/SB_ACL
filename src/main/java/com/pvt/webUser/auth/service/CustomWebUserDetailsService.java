/**
 * This class will be used for loading web user details while authentication. Usually, it is used 
 * in security by authentication manager when dao approach is used.
 */
package com.pvt.webUser.auth.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pvt.sec.rbac.entity.WebUser;
import com.pvt.sec.rbac.repo.WebUserRepository;



/**
 * @author Sanjeev
 *
 */
@Service
public class CustomWebUserDetailsService implements UserDetailsService{

	private static final Logger log=LoggerFactory.getLogger(CustomWebUserDetailsService.class);
    
	@Autowired
    private WebUserRepository webUserRepository;
    
    @Transactional
    @Override
    public UserDetails loadUserByUsername(String email) {		    	
    	UserBuilder builder = null;
    	try {	      
       WebUser webUser = webUserRepository.findByEmail(email).get();     
        CustomWebUserDetails customUserDetails=new CustomWebUserDetails();	      
        log.info("Sanjeev = "+new BCryptPasswordEncoder().encode(webUser.getPassword()));
        if (webUser != null) {	        	
          builder = org.springframework.security.core.userdetails.User.withUsername(email);
          builder.password(new BCryptPasswordEncoder().encode(webUser.getPassword()));          
          builder.authorities(customUserDetails.getAuthorities(webUser));	        
        }
    	}catch(Exception ex) {ex.printStackTrace();}
        return builder.build();
        
    }

}
