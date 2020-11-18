$(document).ready(function() {	

	zeroRolePermission();

	$("#domainComboId_roleSection").change(function() {
		cleanMessages();
		var domainId=$("#domainComboId_roleSection").val();
		var contextId=$("#customContextComboId_roleSection").val();
		loadRoleCombo();
		if(domainId!=0)
			loadDomainAndContextBasedAvailablePermission_roleSection(domainId,contextId);
		else
			zeroRolePermission();
	});


	$("#roleComboId_roleSection").change(function() {
		cleanMessages();
		var roleId = $("#roleComboId_roleSection").val();	
		var domainId = $("#domainComboId_roleSection").val();
		var contextId=$("#customContextComboId_roleSection").val();	

		if(roleId==0 && domainId!=0)
			loadDomainAndContextBasedAvailablePermission_roleSection(domainId,contextId);
		if(roleId!=0 && domainId!=0)
			loadPermissionWith_Domain_Role_PermissionContext_roleSection(domainId,roleId,contextId);		  
	});  


	$("#customContextComboId_roleSection").on('change', function(e) {
		cleanMessages();
		var domainId = $("#domainComboId_roleSection").val();
		var roleId=$("#roleComboId_roleSection").val();				
		var contextId=$("#customContextComboId_roleSection").val();			
		if(domainId == 0 && roleId==0)
			loadContextBasedNoPrePermission_roleSection(domainId,contextId);			
		if(domainId != 0 && roleId==0)	
			loadDomainAndContextBasedAvailablePermission_roleSection(domainId,contextId);
		if(domainId != 0 && roleId!=0)	
			loadPermissionWith_Domain_Role_PermissionContext_roleSection(domainId,roleId,contextId);	 
	});	

//	Save role permission policy
	$(document).on('click', '.saveButtonClass_roleSection ',function() {
		cleanMessages();
		saveRolePermissionPolicy();
	});

//	Update role permission policy
	$(document).on('click','.updateButtonClass_roleSection', function() {
		cleanMessages();
		updateRolePermissionPolicy();
	});	   


	$("#rolePermissionFormHelp").click(function(){
		$("#rolePermissionModalHelpId").dialog({		
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
	
	$("#ckbRoleBaseCheckAll").click(function () {		
	    $(".basePermissionId20Class").prop('checked', $(this).prop('checked'));
	});
	
	
	$(document).on('change', '.basePermissionId20Class',function() {
		if($(".basePermissionId20Class").length == $(".basePermissionId20Class:checked").length) {            
			$("#ckbRoleBaseCheckAll").prop("checked",true);
       }else {$("#ckbRoleBaseCheckAll").prop("checked",false); }		
	});
	

});//$(document).ready(function()

function zeroRolePermission(){
	$.ajax({
		url : "/domain_acl/zeroPermission",
		method:"GET",
		data : {},
		beforeSend : function(xhr) {
			xhr.setRequestHeader("Accept", "application/json");
			xhr.setRequestHeader("Content-Type","application/json");
		},									
		success : function(result) {
			// alert("Response is coming.");			
			fillPermissionsInRoleSection(result);			
		}
	});// Ajax
}

function loadDomainAndContextBasedAvailablePermission_roleSection(domainId,contextId){	
	$.ajax({
		url : "/domain_acl/loadDomainAndContextBasedAvailablePermission_roleSection?domainId=" 
			+ domainId+ "&contextId="+contextId+"",
			success : function(result) {
				//alert("Response is coming");
				var jsonObj = JSON.parse(result);				
				fillPermissionsInRoleSection(jsonObj)
			}
	});// Ajax	
}

function loadContextBasedNoPrePermission_roleSection(domainId,contextId){	
	$.ajax({
		url : "/domain_acl/loadContextBasedNoPrePermission_roleSection?domainId="+domainId+"contextId=" 
			+ contextId
			+ "",
			success : function(result) {
				var jsonObj = JSON.parse(result);				
				fillPermissionsInRoleSection(jsonObj);}
	});// Ajax

}

function loadPermissionWith_Domain_Role_PermissionContext_roleSection(domainId,roleId,contextId){	
	if (roleId != 0 && domainId != 0) {
		$.ajax({
			url : "/domain_acl/loadPermissionWith_Domain_Role_PermissionContext_roleSection?domainId="
				+ domainId
				+ "&roleId="
				+ roleId
				+ "&contextId="
				+ contextId
				+ "",
				success : function(result) {
					var jsonObj = JSON.parse(result);				
					fillPermissionsInRoleSection(jsonObj);		}
		});// Ajax
	} else {			
		$("#basePermissionId_roleSection").html("No permission is defined.");
		$("#customPermissionId1_roleSection").html("No permission is defined.");
		$("#customPermissionId2_roleSection").html("No permission is defined.");
	}
}

//Save role permission policy
function saveRolePermissionPolicy() {
	var policyId = $(".selectedOwnerPolicyIdClass").text();
	var domainId = $("#domainComboId_roleSection").val();
	var roleId = $("#roleComboId_roleSection").val();	
	var permissionSelected = getTotalPermissionSelectedForRolePermission();	
	var json = {				
			"domainId" : domainId,
			"rolePermissions":permissionSelected,
			"roleId":roleId,
			"policyId":policyId
	};
	
	$.ajax({
		type : 'POST',
		url : "/domain_acl/saveRolePermissionPolicy",
		data : JSON.stringify(json),
		beforeSend : function(xhr) {
			xhr.setRequestHeader("Accept", "application/json");
			xhr.setRequestHeader("Content-Type", "application/json");
		},
		success : function(response) {
			//alert("Response of saving permission is coming.");
			if(response.status){			
				resetRolePermissionForm();
				successDialogue(response.statusMsg);				
				populatePermissionGrid();
			}else{
				//alert(response.fieldErrMsgMap.domainName);					
				$('#roleDomainIdErr').html(response.fieldErrMsgMap.domainId);	
				$('#rolePermissionErrId').html(response.fieldErrMsgMap.rolePermissions);	
				$('#roleRoleErrId').html(response.fieldErrMsgMap.roleId);	
			   }
		}
	}); // End of ajax call for saving record.
}

//Update role permission policy
function updateRolePermissionPolicy() {
	// alert("update Role Permission Policy is called");
	var policyId = $(".selectedRolePolicyIdClass").text();
	var domainId = $("#domainComboId_roleSection").val();
	var roleId = $("#roleComboId_roleSection").val();
	var permissionSelected = getTotalPermissionSelectedForRolePermission();	
	var json = {				
			"domainId" : domainId,
			"rolePermissions":permissionSelected,
			"roleId":roleId,
			"policyId":policyId
	};
	
	$.ajax({
		type : 'POST',
		url : "/domain_acl/updateRolePermissionPolicy",
		data : JSON.stringify(json),
		beforeSend : function(xhr) {
			xhr.setRequestHeader("Accept", "application/json");
			xhr.setRequestHeader("Content-Type", "application/json");
		},
		success : function(response) {
			 //alert("Response of saving permission is coming.");			
			if(response.status){			
				resetRolePermissionForm();
				successDialogue(response.statusMsg);
				populatePermissionGrid();
			}else{
				//alert(response.fieldErrMsgMap.domainName);					
				$('#roleDomainIdErr').html(response.fieldErrMsgMap.domainId);	
				$('#rolePermissionErrId').html(response.fieldErrMsgMap.rolePermissions);	
				$('#roleRoleErrId').html(response.fieldErrMsgMap.roleId);	
			   }			
		},
		error : function(jqXHR, exception) {
			alert("Sanjeev:-- Ajax error");
		}
	}); // End of ajax call for saving record.
}

function loadRoleComboWithSelectedRoleOfGrid(ownerOrRole) {		
	$.ajax({
		url : "/domain_acl/loadRoleComboWithSelectedRoleOfGrid?ownerOrRole="
			+ ownerOrRole + "",
			success : function(response) {
				var jsonObj = JSON.parse(response);
				// Will make state combo clean
				var ctrl = "";
				$("#roleComboId_roleSection").empty().append(
				'<option  value="">-Select Role-</option>');
				for (var i = 0; i < jsonObj.comboList.length; i++) {
					ctrl = '<option class="roleComboClass"'+jsonObj.comboList[i]['allReadyAssigned']+'  value="' + jsonObj.comboList[i]['id'] + '">'
					+ jsonObj.comboList[i]['name']
					+ '</option>';
					$("#roleComboId_roleSection").append(ctrl);
				}
			}
	});// Ajax
}

function resetRolePermissionForm() {

	$("#rolePermissionSavedOrUpdatedResponseId").html("No action taken.");
	// Reset role form
	$("#addEditFormHeaderId_roleSection").html("Add:-- [Role-Level ] Permission-Policies.");
	$("#savePolicyButtonId_roleSection")
	.html(
	"<button class='saveButtonClass_roleSection input-sm ui-button ui-widget ui-corner-all pull-right'>Save-Permission</button>");

	// Initialize role form
	$("#basePermissionId_roleSection").html("Select a role.");
	$("#customPermissionId1_roleSection").html("Select a role.");
	$("#customPermissionId2_roleSection").html("Select a role.");
	// Initialize Role form
	$("#domainComboId_roleSection").val("0");
	$("#roleComboId_roleSection").empty().append(
	'<option  value="">-Select Role-</option>');
}

function loadRoleCombo() {
	method = 'GET';
	url = "/acl_admin" + "/roleList";
	$.ajax({
		type : method,
		url : url,
		data : {},
		beforeSend : function(xhr) {
			xhr.setRequestHeader("Accept", "application/json");
			xhr
			.setRequestHeader("Content-Type",
			"application/json");
		},
		success : function(response) {

			if (response.status) {
				$("#roleComboId_roleSection").empty();// Empty the
				// combo
				var ele = document.getElementById('roleComboId_roleSection');
				for (var i = 0; i < response.comboList.length; i++) {
					// POPULATE SELECT ELEMENT WITH JSON.
					ele.innerHTML = ele.innerHTML
					+ '<option class="roleComboClass" value="' + response.comboList[i]['id'] + '">'
					+ response.comboList[i]['name']
					+ '</option>';
				}
				// Do something if required
			} else {
				alert("Wrong Status");
			}
		},
		error : function(jqXHR, exception) {
			alert("Sanjeev:-- Ajax error");
		}
	});
}

function fillPermissionsInRoleSection(result){	
	var ctrl = "";var ctrl_1 = "";var ctrl_2 = "";
	$("#basePermissionId_roleSection").html(ctrl);
	$("#customPermissionId1_roleSection").html(ctrl_1);
	$("#customPermissionId2_roleSection").html(ctrl_2);
	var mask;
	var customTextIdSelected= $("#customContextComboId_roleSection").val();				
	var customTextIdComing;var baseCount_1=0; var customCount_1=0;var customCount_2=0;	
	for (i = 0; i < result.comboList.length; i++) {
		mask=(result.comboList[i]['maskPower'])*1;
		customTextIdComing=(result.comboList[i]['customTextId'])*1;		
		// Base-Permission
		if(-1 < mask && mask < 5)	{baseCount_1=baseCount_1+1;	
		ctrl = "<input class='basePermissionId20Class' " + result.comboList[i]['allReadyAssigned']
		+ " type='checkbox' value='" + result.comboList[i]['id']
		+ "' />&nbsp;"
		+ result.comboList[i]['name']
		+ "<br>";
		$("#basePermissionId_roleSection").append(ctrl);
		}
		// Custom-Permission-1
		if(customTextIdComing==customTextIdSelected && (customCount_1<5))	{

			ctrl_1 = "<input class='customPermissionId21Class' " + result.comboList[i]['allReadyAssigned']
			+ " type='checkbox' value='" + result.comboList[i]['id']
			+ "' />&nbsp;"
			+ result.comboList[i]['name']
			+ "<br>";
			$("#customPermissionId1_roleSection").append(ctrl_1);
			customCount_1=customCount_1+1;
		}

		// Custom-Permission-2
		if( (customTextIdComing==customTextIdSelected) &&  (customCount_1==5 && customCount_2<10 ))	{
			if(customCount_2!=0){
				ctrl_2 = "<input class='customPermissionId22Class' " + result.comboList[i]['allReadyAssigned']
				+ " type='checkbox' value='" + result.comboList[i]['id']
				+ "' />&nbsp;"
				+ result.comboList[i]['name']
				+ "<br>";
				$("#customPermissionId2_roleSection").append(ctrl_2);
				//
				customCount_2=customCount_2+1;
			}else{customCount_2=1; }
		}
	}// For loop
	if (baseCount_1 == 0) {$("#basePermissionId_roleSection").append("No permission found.");}
	if (customCount_1 == 0) {$("#customPermissionId1_roleSection").append("No permission found.");}
	if (customCount_2 == 0) {$("#customPermissionId2_roleSection").append("No permission found.");}

	calculateAndCheckAll_RoleSection();
}

function getTotalPermissionSelectedForRolePermission(){
	var basePermissionChkPermissionArray = [];
	// Look for all checkboxes that have a specific class and was checked
	$(".basePermissionId20Class:checked").each(function() {
		basePermissionChkPermissionArray.push($(this).val());
	});
	// Join the array separated by the comma		        
	var basePermissionSelected = basePermissionChkPermissionArray.join(',');
	//alert("basePermissionSelected = "+basePermissionSelected);

	var customPermissionChkPermissionArray1 = [];
	// Look for all checkboxes that have a specific class and was checked
	$(".customPermissionId21Class:checked").each(function() {
		customPermissionChkPermissionArray1.push($(this).val());
	});
	// Join the array separated by the comma		        
	var customPermissionSelected1 = customPermissionChkPermissionArray1.join(',');
	//alert("customPermissionSelected1 = "+customPermissionSelected1);

	var customPermissionChkPermissionArray2 = [];
	// Look for all checkboxes that have a specific class and was checked
	$(".customPermissionId22Class:checked").each(function() {
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
	return totalPermission; 

}

function calculateAndCheckAll_RoleSection(){	
	//Clean Staled selection permissionOwnerCustomContextClass
	$("#ckbRoleBaseCheckAll").prop("checked",false);
	
	
	//Now check All if condition satisfied.
	if($(".basePermissionId20Class").length == $(".basePermissionId20Class:checked").length)            
		$("#ckbRoleBaseCheckAll").prop("checked",true);
	
	if($(".basePermissionId20Class").length ==0 && $(".basePermissionId20Class:checked").length==0)            
		$("#ckbRoleBaseCheckAll").prop("checked",false);
	
	//if($(".customPermissionId1Class").length == $(".customPermissionId1Class:checked").length)            
	//	$("#ckbDomainCustomCheckAll").prop("checked",true);
	
	
}