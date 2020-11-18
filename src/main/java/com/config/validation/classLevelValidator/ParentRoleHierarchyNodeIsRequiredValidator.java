/**
 * 
 */
package com.config.validation.classLevelValidator;

import java.lang.reflect.InvocationTargetException;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.config.exception.common.ExceptionApplicationUtility;
import com.pvt.sec.rbac.entity.RoleHierarchy;
import com.pvt.sec.rbac.repo.RoleHierarchyRepository;

/**
 * @author Sanjeev
 *
 */
public class ParentRoleHierarchyNodeIsRequiredValidator
		implements ConstraintValidator<ParentRoleHierarchyNodeIsRequired, Object> {
	private static final Logger log=LoggerFactory.getLogger(ParentRoleHierarchyNodeIsRequiredValidator.class);
	private String parentNodeCount;
	private String parentName;
	private String parentId;
	private String id;
	private String message;

	@Override
	public void initialize(ParentRoleHierarchyNodeIsRequired parentRoleHierarchyNodeIsRequired) {

		this.parentNodeCount = parentRoleHierarchyNodeIsRequired.parentNodeCount();
		this.parentName = parentRoleHierarchyNodeIsRequired.parentName();
		this.parentId = parentRoleHierarchyNodeIsRequired.parentId();
		this.id = parentRoleHierarchyNodeIsRequired.id();
		this.message = parentRoleHierarchyNodeIsRequired.message();
	}

	int parentNodeCountValueForMsg;
	String parentIdValueForMsg;
	String dynamicErrorMsg="";

	@Autowired
	RoleHierarchyRepository roleHierarchyRepository;

	@Override
	@Transactional("appTransactionManager")
	public boolean isValid(Object object, ConstraintValidatorContext context) {

		final String parentNodeCountValue;
		final String parentIdValue;
		final String parentNameValue;
		final String idValue;
		boolean isValid = true;
		int existingNoneNodeChildCount = 0;
		int topNodeId = 0;
		try {
			if (roleHierarchyRepository.findAllTreeNodeListWithSpecificNodeName().size() > 0) {
				RoleHierarchy roleHierarchy = roleHierarchyRepository.findAllTreeNodeListWithSpecificNodeName().get(0);

				existingNoneNodeChildCount = roleHierarchy.getRoleHierarchies().size();
				if (roleHierarchy.getRoleHierarchies() != null && roleHierarchy.getRoleHierarchies().size() > 0)
					topNodeId = roleHierarchy.getRoleHierarchies().get(0).getId();
			}
			parentNodeCountValue = (String) BeanUtils.getProperty(object, this.parentNodeCount);
			parentNameValue = (String) BeanUtils.getProperty(object, this.parentName);
			parentIdValueForMsg = (String) parentNameValue;
			parentIdValue = (String) BeanUtils.getProperty(object, this.parentId);
			idValue = (String) BeanUtils.getProperty(object, this.id);

			try {

				if (parentIdValue.equals("")) { // Working
					log.info("Parent not selected");
					if(Integer.parseInt(parentNodeCountValue)==1)
					    isValid = true;//When None is not created in the database.
					else
						isValid = false;//When None has already been created in the database; but parent not selected.
				}
                
				
				else if (!parentIdValue.trim().equals("") && parentNameValue != null && parentNameValue.equals("None")
						&& existingNoneNodeChildCount <= 1) {
					log.info("None has been selected");
					// Two possibilities
					// 1. existingNoneNodeChildCount is 0. No tree node has yet been created
					// 2. existingNoneNodeChildCount is 1 . One top root of tree has already been
					// created
					// 3. When it is 0. This is always an add case of top tree node
					// 4. When it is 1. Now possibilities.
					// 4.1 It may be an add case or edit case.
					// 4.2 When it is add case, idValue will come as null
					// 4.3 When it is edit case, idValue will not be null

					// 4.3.1 In case of edit, once again two cases are possible
					// 4.3.1.1 Child node with its parent node as None is coming . It should be
					// allowed
					// 4.3.1.2 Child nod with its non None node as parent wants to change it as
					// none. Should be blocked

					if (existingNoneNodeChildCount == 0) {// Working
						log.info("Add case: Tree Root node has not been created. Let it be created.");
						isValid = true;
					}
					if (existingNoneNodeChildCount == 1) {
						if (idValue == null) {// Working
							log.info("Add case: Tree Root node has already been created.Restrict it.");

							isValid = false;
							dynamicErrorMsg = "None has already been used for a Root-Node. Can't be used more than once.";

						}
						if (idValue != null) {
							if (topNodeId == Integer.parseInt(idValue)) {// Working
								isValid = true;// Will allow top root node for editing rest field other than None
								log.info("Edit case but; Tree Root node is coming. Let it edit.");
							} else {// Working
								log.info("Edit case but Tree Root node is not coming.restrict it.");
								isValid = false;// Will restrict all other
								dynamicErrorMsg = "None has already been used for a Root-Node. Can't be used more than once.";

							}
						}
					}

				}

			} catch (UnsupportedOperationException e) {
				// TODO Auto-generated catch block
				ExceptionApplicationUtility.wrapRunTimeException(e);
			}
		} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
			// TODO Auto-generated catch block
			ExceptionApplicationUtility.wrapRunTimeException(e);
		}
		// Set the error reporting path
		if (!isValid) {
			context.disableDefaultConstraintViolation();
			// Show the error message on menuManagerParentId field only as it is required
			// there only.
			if (!dynamicErrorMsg.equals(""))
				context.buildConstraintViolationWithTemplate(dynamicErrorMsg).addPropertyNode(this.parentId)
						.addConstraintViolation();
			else
				context.buildConstraintViolationWithTemplate(this.message).addPropertyNode(this.parentId)
						.addConstraintViolation();

			// Clean it
			dynamicErrorMsg = "";
		}
		return isValid;
	}
}