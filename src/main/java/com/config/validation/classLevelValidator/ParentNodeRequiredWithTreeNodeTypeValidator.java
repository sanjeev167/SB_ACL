/**
 * 
 */
package com.config.validation.classLevelValidator;

import java.lang.reflect.InvocationTargetException;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.config.exception.common.ExceptionApplicationUtility;
import com.pvt.sec.env.repo.MenuManagerRepository;

/**
 * @author Sanjeev
 *
 */
public class ParentNodeRequiredWithTreeNodeTypeValidator
		implements ConstraintValidator<ParentNodeRequiredWithTreeNodeType, Object> {

	private String nodeType;
	private String parentNodeCount;
	private String menuManagerParentNodeName;
	private String menuManagerParentId;
	private String id;
	private String message;

	@Override
	public void initialize(ParentNodeRequiredWithTreeNodeType parentNodeRequiredWithTreeNodeType) {
		this.nodeType = parentNodeRequiredWithTreeNodeType.nodeType();
		this.parentNodeCount = parentNodeRequiredWithTreeNodeType.parentNodeCount();
		this.menuManagerParentNodeName = parentNodeRequiredWithTreeNodeType.menuManagerParentNodeName();
		this.menuManagerParentId = parentNodeRequiredWithTreeNodeType.menuManagerParentId();
		this.id=parentNodeRequiredWithTreeNodeType.id();
		this.message = parentNodeRequiredWithTreeNodeType.message();
	}

	int parentNodeCountValueForMsg;
	String menuManagerParentIdValueForMsg;	
	String dynamicErrorMsg;

	@Autowired
	MenuManagerRepository menuManagerRepository;
	@Override
	@Transactional("appTransactionManager")
	public boolean isValid(Object object, ConstraintValidatorContext context) {
		final Object nodeTypeValue;
		final Object parentNodeCountValue;
		final Object menuManagerParentIdValue;
		final Object menuManagerParentNodeNameValue;
		final Object idValue;
		boolean isValid = true;
		int existingNoneNodeChildCount=0;
		try {
			
			if(menuManagerRepository.findAllTreeNodeListWithSpecificNodeName().size()>0)
			existingNoneNodeChildCount=menuManagerRepository.findAllTreeNodeListWithSpecificNodeName().get(0).getChildNodeManagerList().size();
			
			nodeTypeValue = BeanUtils.getProperty(object, this.nodeType);
			parentNodeCountValue = BeanUtils.getProperty(object, this.parentNodeCount);
			menuManagerParentNodeNameValue = BeanUtils.getProperty(object, this.menuManagerParentNodeName);
			menuManagerParentIdValueForMsg = (String) menuManagerParentNodeNameValue;
			menuManagerParentIdValue = (String) BeanUtils.getProperty(object, this.menuManagerParentId);
			idValue=BeanUtils.getProperty(object, this.id);
			
			try {
				// Write validation logic here
				if (nodeTypeValue.equals("N")) {
					// If the node type is "N", check the parent node count for making it sure
					// that whether the root node has been created or not.

					if (!parentNodeCountValue.equals("") && Integer.parseInt((String) parentNodeCountValue) > 1
							&& menuManagerParentIdValue.equals("")) {
						parentNodeCountValueForMsg = Integer.parseInt((String) parentNodeCountValue);
						isValid = false;
					} else {
						//This will allow you to create a root node under a parent as "None" if it exists. 
						//or it will also allow you to create a root node along with this None node if None doesn't exist
						if (menuManagerParentNodeNameValue.equals("None") && existingNoneNodeChildCount <2) {
							//existingNoneNodeChildCount will be either 1 or 0 if there is a single None node in the system
							if(idValue!=null)
							isValid = true;
							else {
								isValid = false;//In case of add, prevent creating any node under None if root is 
							                    //already created	
								dynamicErrorMsg = "None has already been used for a Root-Node. Can't be used more than onece.";
							}
						} else {
							if (menuManagerParentNodeNameValue.equals("None")
									&& Integer.parseInt((String) parentNodeCountValue) > 2) {
								isValid = false;
								dynamicErrorMsg = "None has already been used for a Root-Node. Can't be used more than onece.";
							} else
								isValid = true;
						}
					}
				} else {
					// If the nodeType is leaf [ "L" ], the parent node must be selected.
					// so the validation is required. No need to check the parentNodeCountValue as
					// the leaf selection is allowed when the node count value is greater than 2.
					// It has been checked through javascript before page submission.
					if (!menuManagerParentIdValue.equals("") && !menuManagerParentNodeNameValue.equals("None")) {
						isValid = true;
					} else {
						isValid = false;
						dynamicErrorMsg = "None has already been used for a Root-Node. Can't be used more than onece.";
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
			if (parentNodeCountValueForMsg < 4 && menuManagerParentIdValueForMsg.equals("None"))
				context.buildConstraintViolationWithTemplate(dynamicErrorMsg).addPropertyNode(this.menuManagerParentId)
						.addConstraintViolation();
			else
				context.buildConstraintViolationWithTemplate(this.message).addPropertyNode(this.menuManagerParentId)
						.addConstraintViolation();
		}
		return isValid;
	}
}