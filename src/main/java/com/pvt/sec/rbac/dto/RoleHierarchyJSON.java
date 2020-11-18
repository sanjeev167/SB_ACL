/**
 * 
 */
package com.pvt.sec.rbac.dto;

import java.util.ArrayList;

/**
 * @author Sanjeev
 *
 */
public class RoleHierarchyJSON extends RoleHierarchyJSONLeaf{
	

	
	private ArrayList<Object> children=null;
	
	

	public RoleHierarchyJSON() {
		// TODO Auto-generated constructor stub
	}



	/**
	 * @return the children
	 */
	public ArrayList<Object> getChildren() {
		return children;
	}



	/**
	 * @param children the children to set
	 */
	public void setChildren(ArrayList<Object> children) {
		this.children = children;
	}


}
