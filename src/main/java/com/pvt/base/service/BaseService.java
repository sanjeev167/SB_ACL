/**
 * 
 */
package com.pvt.base.service;

import javax.annotation.security.RolesAllowed;

import org.springframework.security.access.prepost.PreAuthorize;

import com.config.exception.common.CustomBusinessException;
import com.config.exception.common.CustomRuntimeException;
import com.pvt.base.dto.BaseDTO;

/**
 * @author Sanjeev
 *
 */
public interface BaseService {
	/**
	 * The @Secured annotation is used to specify a list of roles on a method.
	 * Hence, a user only can access that method if she has at least one of the
	 * specified roles.
	 * 
	 * The @RoleAllowed annotation is the JSR-250’s equivalent annotation of
	 * the @Secured annotation.
	 * 
	 * Both @PreAuthorize and @PostAuthorize annotations provide expression-based
	 * access control. Hence, predicates can be written using SpEL (Spring
	 * Expression Language).
	 * 
	 * The @PreAuthorize annotation checks the given expression before entering the
	 * method, whereas, the @PostAuthorize annotation verifies it after the
	 * execution of the method and could alter the result.
	 */

	// @Secured("ROLE_ADMIN")
	// @RolesAllowed({ "ROLE_ADMIN", "ROLE_USER" })
	// @RolesAllowed({ "ROLE_ADMIN"})
	// @PreAuthorize("hasRole('ROLE_ADMIN')")
	// @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")

	/**
	 * Following services are required by each controller for a page.
	 **/
	@PreAuthorize("hasRole('ROLE_USER')")
	public BaseDTO create(BaseDTO baseDTO) throws CustomBusinessException, CustomRuntimeException;

	@RolesAllowed({ "ROLE_USER" })
	public BaseDTO read(Integer recordId) throws CustomBusinessException, CustomRuntimeException;

	// @Secured("ROLE_ADMIN")
    @PreAuthorize("hasRole('ROLE_ADMIN')")	
	public BaseDTO update(Integer recordId, BaseDTO baseDTO) throws CustomBusinessException, CustomRuntimeException;

	// @Secured("ROLE_ADMIN")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	// @SecureMethodExecution
	public boolean delete(Integer recordId) throws CustomBusinessException, CustomRuntimeException;
}// End of BaseService
