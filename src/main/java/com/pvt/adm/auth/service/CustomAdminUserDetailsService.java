/**
 * 
 */
package com.pvt.adm.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pvt.sec.rbac.entity.UserReg;
import com.pvt.sec.rbac.repo.AppAdminUserRepository;


/**
 * @author Sanjeev
 *
 */
@Service
public class CustomAdminUserDetailsService implements UserDetailsService {

	@Autowired
	private AppAdminUserRepository appAdminUserRepository;	

	@Transactional(value = "appTransactionManager")
	@Override
	public UserDetails loadUserByUsername(String email) {
		
		UserReg userReg = appAdminUserRepository.findByEmail(email).get();
		
		CustomAdminUserDetails customAdminUserDetails = new CustomAdminUserDetails();
		UserBuilder builder = null;
		if (userReg != null) {
			builder = org.springframework.security.core.userdetails.User.withUsername(email);
			builder.password(new BCryptPasswordEncoder().encode(userReg.getPassword()));
			builder.authorities(customAdminUserDetails.getAuthorities(userReg));			
		} else {
			throw new UsernameNotFoundException("User not found.");
		}
		return builder.build();
	}

}
