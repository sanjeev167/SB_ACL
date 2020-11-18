/**
 * 
 */
package  com.share;

/**
 * @author Sanjeev
 *
 */
public class NameValue {
  private String id;
  private String name;
  private String allReadyAssigned="";
  int maskPower;
  int customTextId;
  
   
  public NameValue(String id, String name) {
	  
	  this.id=id;
	  this.name=name;
  }
  
	public NameValue(String id, String name, String allReadyAssigned) {
		// TODO Auto-generated constructor stub
		this.id = id;
		this.name = name;
		this.allReadyAssigned = allReadyAssigned;
	}

	public NameValue(String id, String name,int maskPower,int customTextId, String allReadyAssigned) {
		// TODO Auto-generated constructor stub
		this.id = id;
		this.name = name;
		this.maskPower=maskPower;
		this.allReadyAssigned = allReadyAssigned;
		this.customTextId=customTextId;
	}

	
	
	
	
/**
	 * @return the customTextId
	 */
	public int getCustomTextId() {
		return customTextId;
	}

	/**
	 * @param customTextId the customTextId to set
	 */
	public void setCustomTextId(int customTextId) {
		this.customTextId = customTextId;
	}

/**
	 * @return the maskPower
	 */
	public int getMaskPower() {
		return maskPower;
	}

	/**
	 * @param maskPower the maskPower to set
	 */
	public void setMaskPower(int maskPower) {
		this.maskPower = maskPower;
	}

public NameValue() {
		// TODO Auto-generated constructor stub
	}

public NameValue(Integer id, String name) {
	// TODO Auto-generated constructor stub
	this.id=id+"";this.name=name;
}

/**
 * @return the id
 */
public String getId() {
	return id;
}
/**
 * @param id the id to set
 */
public void setId(String id) {
	this.id = id;
}
/**
 * @return the name
 */
public String getName() {
	return name;
}
/**
 * @param name the name to set
 */
public void setName(String name) {
	this.name = name;
}
/**
 * @return the allReadyAssigned
 */
public String getAllReadyAssigned() {
	return allReadyAssigned;
}
/**
 * @param allReadyAssigned the allReadyAssigned to set
 */
public void setAllReadyAssigned(String allReadyAssigned) {
	this.allReadyAssigned = allReadyAssigned;
}
  
  
  
  
  
}
