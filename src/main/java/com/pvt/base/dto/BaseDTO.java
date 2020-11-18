/**
 * 
 */
package com.pvt.base.dto;

/**
 * @author Sanjeev
 *
 */
public class BaseDTO {

	private String actionMode="add";
	private String actionResponseMsg;
	private boolean isActionCompleted = false;

	public String getActionMode() {
		return actionMode;
	}

	public void setActionMode(String actionMode) {
		this.actionMode = actionMode;
	}

	public String getActionResponseMsg() {
		return actionResponseMsg;
	}

	public void setActionResponseMsg(String actionResponseMsg) {
		this.actionResponseMsg = actionResponseMsg;
	}

	public boolean isActionCompleted() {
		return isActionCompleted;
	}

	public void setActionCompleted(boolean isActionCompleted) {
		this.isActionCompleted = isActionCompleted;
	};

}// End of BaseDTO
