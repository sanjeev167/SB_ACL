
$(document).ready(function() {
	zeroOwnerPermission();
	$("#domainComboId_ownerSection").change(function() {	
		cleanMessages();
		var domainId = $("#domainComboId_ownerSection").val();
		var contextId = $("#customContextComboId_ownerSection").val();	
		if (domainId != 0)			
			loadOwnerPermissionBasedOnDomainAndContextId(domainId, true,contextId);
		else
			zeroOwnerPermission();
		
		
	});// domainComboId_ownerSection

//	Save owner permission policy
	$(document).on('click', '.saveButtonClass_ownerSection',function() {
		cleanMessages();
		saveOwnerPermissionPolicy();
	});

//	Update owner permission policy
	$(document).on('click', '.updateButtonClass_ownerSection',function() {
		cleanMessages();
		updateOwnerPermissionPolicy();
	});

//	Load permission when custom context is changed in owner section.
	$("#customContextComboId_ownerSection").on('change',function(e) {
		cleanMessages();
		var domainId = $("#domainComboId_ownerSection").val();
		var contextId = $("#customContextComboId_ownerSection").val();	
		if (domainId != 0)
			loadOwnerPermissionBasedOnDomainAndContextId(domainId, true,contextId);
	});


	$("#ownerPermissionFormHelp").click(function(){
		$("#ownerPermissionModalHelpId").dialog({		
			dialogClass : "no-close",
			resizable : false,
			draggable: false,
			height : "auto",
			width : 400,
			modal : true,
			position: { my: "center", at: "center" },
			buttons : {
				"OK" : function() {					
					$( this ).dialog( "close" );
				}
			}		
		});
	});

	$("#ckbOwnerBaseCheckAll").click(function () {		
	    $(".basePermissionId10Class").prop('checked', $(this).prop('checked'));
	});
	
	
	
	$(document).on('change', '.basePermissionId10Class',function() {
		if($(".basePermissionId10Class").length == $(".basePermissionId10Class:checked").length) {            
			$("#ckbOwnerBaseCheckAll").prop("checked",true);
       }else {$("#ckbOwnerBaseCheckAll").prop("checked",false); }		
	});
	

});// $(document).ready(function()

function zeroOwnerPermission(){
	$.ajax({
		url : "/domain_acl/zeroPermission",
		data : {},
		method:"GET",
		beforeSend : function(xhr) {
			xhr.setRequestHeader("Accept", "application/json");
			xhr.setRequestHeader("Content-Type","application/json");
		},									
		success : function(result) {
			//alert("Response is coming.");			
			fillPermissionsInOwnerSection(result);				
		}
		,
		error : function(jqXHR, exception) {			
			formatErrorMessage(jqXHR, exception);
		}
	});// Ajax
	
}

function saveOwnerPermissionPolicy() {
	var domainId = $("#domainComboId_ownerSection").val();	
	var permissionSelected = getTotalPermissionSelectedForOwnerPermission();
	var json = {				
			"domainId" : domainId,
			"ownerPermissions":permissionSelected,			
	};
	$.ajax({
		type : 'POST',
		url : "/domain_acl/saveOwnerPermissionPolicy",
		data : JSON.stringify(json),
		beforeSend : function(xhr) {
			xhr.setRequestHeader("Accept", "application/json");
			xhr.setRequestHeader("Content-Type", "application/json");
		},
		success : function(response) {
			//alert("Response of saving permission is coming. ");
			if(response.status){			
				resetOwnerPermissionForm();
				successDialogue(response.statusMsg);				
				populatePermissionGrid();
			}else{
				//alert(response.fieldErrMsgMap.domainName);					
				$('#ownerDomainIdErr').html(response.fieldErrMsgMap.domainId);	
				$('#ownerPermissionErrId').html(response.fieldErrMsgMap.ownerPermissions);	
			   }
		}
		,
		error : function(jqXHR, exception) {			
			formatErrorMessage(jqXHR, exception);
		}
	}); // End of ajax call for saving record.
	
}// saveOwnerPermissionPolicy

function updateOwnerPermissionPolicy() {
	// alert("update Owner Permission Policy is called");
	var policyId = $(".selectedOwnerPolicyIdClass").text();
	var domainId = $("#domainComboId_ownerSection").val();	
	var permissionSelected = getTotalPermissionSelectedForOwnerPermission();
	var json = {				
			"domainId" : domainId,
			"ownerPermissions":permissionSelected,
			"policyId":policyId
	};
	$.ajax({
		type : 'POST',
		url : "/domain_acl/updateOwnerPermissionPolicy",
		data : JSON.stringify(json),
		beforeSend : function(xhr) {
			xhr.setRequestHeader("Accept", "application/json");
			xhr.setRequestHeader("Content-Type", "application/json");
		},
		success : function(response) {
			// alert("Response of saving permission is coming.");
		if(response.status){			
			resetOwnerPermissionForm();
			successDialogue(response.statusMsg);			
			populatePermissionGrid();
		}else{
			//alert(response.fieldErrMsgMap.domainName);					
			$('#ownerDomainIdErr').html(response.fieldErrMsgMap.domainId);	
			$('#ownerPermissionErrId').html(response.fieldErrMsgMap.ownerPermissions);	
		   }
						
		}
		,
		error : function(jqXHR, exception) {			
			formatErrorMessage(jqXHR, exception);
		}
	}); // End of ajax call for saving record.
}// End of updateOwnerPermissionPolicy

function loadDomainComboWithSelectedGridRow(domainClassName, domainHolderId) {
	method = 'GET';
	url = "/domain_acl/loadDomainComboWithSelectedGridRow?domainClassName="
		+ domainClassName + "";
	$.ajax({
		type : method,
		url : url,
		data : {},
		beforeSend : function(xhr) {
			xhr.setRequestHeader("Accept", "application/json");
			xhr.setRequestHeader("Content-Type", "application/json");
		},
		success : function(response) {
			// alert("Response is coming.");
			if (response.status) {
				$("#" + domainHolderId + "").empty().append(
				'<option  value="">-Select Domain-</option>');
				var ctrl = "";
				for (var i = 0; i < response.comboList.length; i++) {
					// POPULATE SELECT ELEMENT WITH JSON.
					ctrl = '<option class="roleComboClass"'
						+ response.comboList[i]['allReadyAssigned']
					+ '  value="' + response.comboList[i]['id'] + '">'
					+ response.comboList[i]['name'] + '</option>';
					$("#" + domainHolderId + "").append(ctrl);
				}
				// Do something if required
			} else {
				alert("Wrong Status");
			}
		},
		error : function(jqXHR, exception) {
			alert("Ajax error");
		}
	}); // Ajax
}

function resetOwnerPermissionForm() {
	$("#ownerPermissionSavedOrUpdatedResponseId").html("No action taken.");
	// Reset Owner form
	$("#formHeaderId_ownerSection").html("Add:-- [Owner-Level ] Permission-Policies.");
	$("#formSaveButtonId_ownerSection")
	.html(
	"<button class='saveButtonClass_ownerSection input-sm ui-button ui-widget ui-corner-all pull-right'>Save-Permission</button>");
	// Initialize Owner form
	$("#domainComboId_ownerSection").val("0");
	$("#basePermissionId_ownerSection").html("Select a domain.");
	$("#customPermissionId1_ownerSection").html("Select a domain.");
	$("#customPermissionId2_ownerSection").html("Select a domain.");
}

function loadDomainCombo() {
	// alert("Domain is going to be loaded in both owner and role section.");
	method = 'GET';
	url = "/domain_acl" + "/domainList";
	$
	.ajax({
		type : method,
		url : url,
		data : {},
		beforeSend : function(xhr) {
			xhr.setRequestHeader("Accept", "application/json");
			xhr.setRequestHeader("Content-Type", "application/json");
		},
		success : function(response) {
			if (response.status) {
				var ele = document
				.getElementById('domainComboId_roleSection');
				for (var i = 0; i < response.comboList.length; i++) {
					// POPULATE SELECT ELEMENT WITH JSON.
					ele.innerHTML = ele.innerHTML
					+ '<option class="roleComboClass" value="'
					+ response.comboList[i]['id'] + '">'
					+ response.comboList[i]['name']
					+ '</option>';
				}
				var ele = document
				.getElementById('domainComboId_ownerSection');
				for (var i = 0; i < response.comboList.length; i++) {
					// POPULATE SELECT ELEMENT WITH JSON.
					ele.innerHTML = ele.innerHTML
					+ '<option class="ownerComboClass" value="'
					+ response.comboList[i]['id'] + '">'
					+ response.comboList[i]['name']
					+ '</option>';
				}
				// Do something if required
			} else {
				alert("Wrong Status");
			}
		},
		error : function(jqXHR, exception) {
			alert("Ajax error");
		}
	});// Ajax
}

function loadOwnerPermissionBasedOnDomainAndContextId(domainId, withDomainFlag,contextId) {
	$.ajax({
		url : "/domain_acl/loadOwnerPermissionBasedOnDomainAndContextId?domainId="
			+ domainId
			+ "&withDomainFlag="
			+ withDomainFlag
			+ "&contextId=" + contextId + "",
			data : {},
			beforeSend : function(xhr) {
				xhr.setRequestHeader("Accept", "application/json");
				xhr.setRequestHeader("Content-Type", "application/json");
			},
			
			success : function(result) {				
				//alert("Response is coming.");
				fillPermissionsInOwnerSection(result);					
			},
			error : function(jqXHR, exception) {alert("Sanjeev=> Ajax error.");}
			
	});// Ajax
	
}// End of loadOwnerPermissionBasedOnDomainAndContextId

function loadContextBasedNoPrePermission_ownerSection() {
	var contextId = $("#customContextComboId_ownerSection").val();
	$.ajax({
		url : "/domain_acl/loadContextBasedNoPrePermission_ownerSection?contextId="+ contextId + "",
		data : {},
		beforeSend : function(xhr) {
			xhr.setRequestHeader("Accept", "application/json");
			xhr.setRequestHeader("Content-Type", "application/json");
		},		
		success : function(result) {			
			fillPermissionsInOwnerSection(result);				
		},
		error : function(jqXHR, exception) {alert("Sanjeev=> Ajax error.");}
		
	});// Ajax
	
}// loadContextBasedNoPrePermission_ownerSection

/*******************************************************************************
 * A common method of populating owner permission list
 ******************************************************************************/
function fillPermissionsInOwnerSection(result) {
	var ctrl_0 = "";	var ctrl_1 = "";var ctrl_2 = "";
	$("#basePermissionId_ownerSection").html(ctrl_0);
	$("#customPermissionId1_ownerSection").html(ctrl_1);
	$("#customPermissionId2_ownerSection").html(ctrl_2);
	var mask;
	var customTextIdSelected = $("#customContextComboId_ownerSection").val();
	var customTextIdComing;var baseCount_1 = 0;var customCount_1 = 0;var customCount_2 = 0;
	for (i = 0; i < result.comboList.length; i++) {
		mask = (result.comboList[i]['maskPower']) * 1;
		customTextIdComing = (result.comboList[i]['customTextId']) * 1;
		if (-1 < mask && mask < 5) {baseCount_1=baseCount_1+1;
		ctrl_0 = "<input class='basePermissionId10Class' "
			+ result.comboList[i]['allReadyAssigned']
		+ " type='checkbox' name='base_permission' value='"
		+ result.comboList[i]['id'] + "' />&nbsp;"
		+ result.comboList[i]['name'] + "<br>";
		$("#basePermissionId_ownerSection").append(ctrl_0);
		}

		if (customTextIdComing == customTextIdSelected && (customCount_1 < 5)) {
			ctrl_1 = "<input class='customPermissionId11Class' "
				+ result.comboList[i]['allReadyAssigned']
			+ " type='checkbox' name='cust_permission_col_1' value='"
			+ result.comboList[i]['id'] + "' />&nbsp;"
			+ result.comboList[i]['name'] + "<br>";
			$("#customPermissionId1_ownerSection").append(ctrl_1);
			customCount_1 = customCount_1 + 1;
		}

		if ((customTextIdComing == customTextIdSelected)&& (customCount_1 == 5 && customCount_2 < 10)) {
			if (customCount_2 != 0) {
				ctrl_2 = "<input class='customPermissionId12Class' "
					+ result.comboList[i]['allReadyAssigned']
				+ " type='checkbox'name='cust_permission_col_2' value='"
				+ result.comboList[i]['id'] + "' />&nbsp;"
				+ result.comboList[i]['name'] + "<br>";
				$("#customPermissionId2_ownerSection").append(ctrl_2);
				customCount_2 = customCount_2 + 1;
			} else {customCount_2 = 1;}
		}
	}// For loop

	if (baseCount_1 == 0) {$("#basePermissionId_ownerSection").append("No permission found.");}
	if (customCount_1 == 0) {$("#customPermissionId1_ownerSection").append("No permission found.");}
	if (customCount_2 == 0) {$("#customPermissionId2_ownerSection").append("No permission found.");}
	//Don't place it anywhere else
	calculateAndCheckAll_OwnerSection();
	
}//fillPermissionsInOwnerSection/

function getTotalPermissionSelectedForOwnerPermission(){
	//alert("Owner permission no");
	var basePermissionChkPermissionArray = [];
	// Look for all checkboxes that have a specific class and was checked
	$(".basePermissionId10Class:checked").each(function() {
		basePermissionChkPermissionArray.push($(this).val());
	});
	// Join the array separated by the comma		        
	var basePermissionSelected = basePermissionChkPermissionArray.join(',');
	//alert("basePermissionSelected = "+basePermissionSelected);

	var customPermissionChkPermissionArray1 = [];
	// Look for all checkboxes that have a specific class and was checked
	$(".customPermissionId11Class:checked").each(function() {
		customPermissionChkPermissionArray1.push($(this).val());
	});
	// Join the array separated by the comma		        
	var customPermissionSelected1 = customPermissionChkPermissionArray1.join(',');
	//alert("customPermissionSelected1 = "+customPermissionSelected1);

	var customPermissionChkPermissionArray2 = [];
	// Look for all checkboxes that have a specific class and was checked
	$(".customPermissionId12Class:checked").each(function() {
		customPermissionChkPermissionArray2.push($(this).val());
	});
	// Join the array separated by the comma		        
	var customPermissionSelected2 = customPermissionChkPermissionArray2.join(',');
	//alert("customPermissionSelected2 = "+customPermissionSelected2);

	var totalPermission="";
	if(basePermissionSelected!="")
		totalPermission=basePermissionSelected;
	if(customPermissionSelected1!="")
		totalPermission=totalPermission+","+customPermissionSelected1;
	if(customPermissionSelected2!="")
		totalPermission=totalPermission+","+customPermissionSelected2;

	//Now remove starting and trailing "," before returning.		
	totalPermission = totalPermission.replace(/^,|,$/g,'');
	//alert("Owner totalPermission = "+totalPermission);
	return totalPermission; 

}


//Calculate all checked option in a particular section and check all if all selected 

function calculateAndCheckAll_OwnerSection(){	
	//Clean Staled selection permissionOwnerCustomContextClass
	$("#ckbOwnerBaseCheckAll").prop("checked",false);
	
	
	//Now check All if condition satisfied.
	if($(".basePermissionId10Class").length == $(".basePermissionId10Class:checked").length)            
		$("#ckbOwnerBaseCheckAll").prop("checked",true);
	
	if($(".basePermissionId10Class").length ==0 && $(".basePermissionId10Class:checked").length==0)            
		$("#ckbOwnerBaseCheckAll").prop("checked",false);
	
	//if($(".customPermissionId1Class").length == $(".customPermissionId1Class:checked").length)            
	//	$("#ckbDomainCustomCheckAll").prop("checked",true);
	
	
}

