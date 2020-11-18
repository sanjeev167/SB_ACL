/**
 * 
 */
package com.pvt.sec.acl.adm.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Sanjeev
 *
 */
public class Aclrights {
	
	List<Integer> preCheckedVals = new ArrayList<Integer>();//Will be user for object's main owner rights
	List<String> checkedVals = new ArrayList<String>();	//Will be user for receiving new rights rights
	private String role;//Will be used by main owner form	
	private String sidtype;//will be used by grid and add/edit form
	private int sidId;
	private String roleOrUserName;//will be used by grid and add/edit form	
	List<String> preCheckedValuesListForRow = new ArrayList<String>();
	
	
	
	List<List<String>> preCheckedValuesListForGrids = new ArrayList<List<String>>();
	/**
	 * @return the preCheckedVals
	 */
	public List<Integer> getPreCheckedVals() {
		return preCheckedVals;
	}
	/**
	 * @param preCheckedVals the preCheckedVals to set
	 */
	public void setPreCheckedVals(List<Integer> preCheckedVals) {
		this.preCheckedVals = preCheckedVals;
	}
	/**
	 * @return the checkedVals
	 */
	public List<String> getCheckedVals() {
		return checkedVals;
	}
	/**
	 * @param checkedVals the checkedVals to set
	 */
	public void setCheckedVals(List<String> checkedVals) {
		this.checkedVals = checkedVals;
	}
	/**
	 * @return the role
	 */
	public String getRole() {
		return role;
	}
	/**
	 * @param role the role to set
	 */
	public void setRole(String role) {
		this.role = role;
	}
	/**
	 * @return the sidtype
	 */
	public String getSidtype() {
		return sidtype;
	}
	/**
	 * @param sidtype the sidtype to set
	 */
	public void setSidtype(String sidtype) {
		this.sidtype = sidtype;
	}
	/**
	 * @return the roleOrUserName
	 */
	public String getRoleOrUserName() {
		return roleOrUserName;
	}
	/**
	 * @param roleOrUserName the roleOrUserName to set
	 */
	public void setRoleOrUserName(String roleOrUserName) {
		this.roleOrUserName = roleOrUserName;
	}
	/**
	 * @return the preCheckedValuesListForRow
	 */
	public List<String> getPreCheckedValuesListForRow() {
		return preCheckedValuesListForRow;
	}
	/**
	 * @param preCheckedValuesListForRow the preCheckedValuesListForRow to set
	 */
	public void setPreCheckedValuesListForRow(List<String> preCheckedValuesListForRow) {
		this.preCheckedValuesListForRow = preCheckedValuesListForRow;
	}
	/**
	 * @return the preCheckedValuesListForGrids
	 */
	public List<List<String>> getPreCheckedValuesListForGrids() {
		return preCheckedValuesListForGrids;
	}
	/**
	 * @param preCheckedValuesListForGrids the preCheckedValuesListForGrids to set
	 */
	public void setPreCheckedValuesListForGrids(List<List<String>> preCheckedValuesListForGrids) {
		this.preCheckedValuesListForGrids = preCheckedValuesListForGrids;
	}
	/**
	 * @return the sidId
	 */
	public int getSidId() {
		return sidId;
	}
	/**
	 * @param sidId the sidId to set
	 */
	public void setSidId(int sidId) {
		this.sidId = sidId;
	}
	
}
