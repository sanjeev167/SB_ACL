/**
 * This js file will be used for handling sharing new permissions and updating an existing ones.
 * cleanPermissionMsg(); :-- THis is a common methods for cleaning messages and it is available
 * in acl_admin.js. 
 * ***/

$(document).ready(function() {

	loadPermissionListDefinedOnDomain();
	// Open:-- Share permission form.
	$(".openSharePermissionClass").on('click',function(){	
		cleanPermissionMsg();
		permissionFormOpeningMode=0;// This will set permission form mode in share mode
		// change-owner mode
		adjustPermissionForm_forNewPermissionAssignment();      
		loadPermissionListDefinedOnDomain();// Will load clean permission list			    	
	});// End of add open

	// Share permissions.
	$(document).on('click', '.sharePermissionClass',function() {	
		cleanPermissionMsg();	
		var sidType = $("input[name='sidtype']:checked").val();
		var actionMode="add";
		if(sidType=="U")
		   shareOrUpdateUserPermission(actionMode);
		if(sidType=="R" ||sidType!="U" && sidType!="R")
		   shareOrUpdateRolePermission(actionMode);
		
	});// End of sharing permission	
	
	// Load other User's and role's defined permissions based on context change.
	$("#customContextComboId_permissionSection").on('change',function(){
		cleanPermissionMsg();
		var domainClass=$("#domainId").text();
		var domainId=$("#selectedObjectId").text();
		var sidtype=$("input[name='sidtype']:checked"). val();
		var userOrRoleName=userOrRoleNameGlobal;// $("#oldObjectOwnerLabelId").html().split("-")[1];
		if(sidtype=="U")
			isPrincipal=true;	
		else
			isPrincipal=false;	
		var changedContextId=$("#customContextComboId_permissionSection").val();		
		
		if(permissionFormOpeningMode==0)
			loadCustomBasedPermissions_forSharingPermission(changedContextId,domainClass,domainId);		
		if(permissionFormOpeningMode==1)
			loadCustomBasedPermissions_forSeeingChangeOwnerPermission(changedContextId,domainClass,domainId);
		if(permissionFormOpeningMode==2)
			loadCustomBasedPermissions_forEditingPermission(isPrincipal,userOrRoleName,changedContextId,domainClass,domainId);
	});

$("#roleComboId").change(function(){		
		//alert("role has been changed.");		
		if(permissionFormOpeningMode==0){
			//Load permission for sharing with existing shared permission of the selected role
			var roleId=$("#roleComboId").val();			
			var contextId=$("#customContextComboId_permissionSection").val();	
			
			
			loadPermissionsForSharingBasedOnSelectedRole(roleId,contextId);			
		}
});

$("#userComboId").change(function(){		
			//alert("User has been changed.");		
			if(permissionFormOpeningMode==0){
				//Load permission for sharing with existing shared permission of the selected role
				var roleId=$("#roleComboId").val();
				var userId=$("#userComboId").val();
				var contextId=$("#customContextComboId_permissionSection").val();
				var slectedUserName=$("#userComboId option:selected").text();
				
				if(slectedUserName==objectOwnerName)
				   loadPermissionsForSharingBasedOnSelectedUser(roleId,userId,contextId);
				else
				   loadPermissionsForSharingBasedOnSelectedRole(roleId,contextId);	
			}		
	});
	
	//Permission section help
$("#permissionHelpId").click(function(){
		$("#permissionModalHelpId").dialog({		
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

	//Check All
	$("#permissionAllChkId").click(function () {		
	    $(".permissionChkBoxClass_forNewPermission").prop('checked', $(this).prop('checked'));
	});	
	
	$(document).on('change', '.permissionChkBoxClass_forNewPermission',function() {
		if($(".permissionChkBoxClass_forNewPermission").length == $(".permissionChkBoxClass_forNewPermission:checked").length) {            
			$("#permissionAllChkId").prop("checked",true);
       }else {$("#permissionAllChkId").prop("checked",false); }		
	});
	
	
	//Update Permission
	$("#permissionAllChkId").click(function () {		
	    $(".permissionEditClass_permissionSection").prop('checked', $(this).prop('checked'));
	});	
	
	$(document).on('change', '.permissionEditClass_permissionSection',function() {
		if($(".permissionEditClass_permissionSection").length == $(".permissionEditClass_permissionSection:checked").length) {            
			$("#permissionAllChkId").prop("checked",true);
       }else {$("#permissionAllChkId").prop("checked",false); }		
	});
	
	
	//Change Owner
	$("#permissionAllChkId").click(function () {		
	    $(".permissionChkBoxClass_forChangeOwner").prop('checked', $(this).prop('checked'));
	});	
	
	$(document).on('change', '.permissionChkBoxClass_forChangeOwner',function() {
		if($(".permissionChkBoxClass_forChangeOwner").length == $(".permissionChkBoxClass_forChangeOwner:checked").length) {            
			$("#permissionAllChkId").prop("checked",true);
       }else {$("#permissionAllChkId").prop("checked",false); }		
	});
	///////

});

function loadCustomBasedPermissions_forSharingPermission(changedContextId,domainClass,domainId){	
	method = 'GET';
	url = "/acl_admin" + "/loadCustomBasedPermissions_forSharingPermission?contextId="+changedContextId+"&domainClass="+domainClass+"&domainId="+domainId+"";	
	$.ajax({
		type : method,
		url : url,
		data : {},
		beforeSend : function(xhr) {
			xhr.setRequestHeader("Accept", "application/json");
			xhr.setRequestHeader("Content-Type", "application/json");
		},
		success : function(response) {
			//alert("Response is coming");				
			perparePermissionChkBoxesForAllType(response,
					"basePermissionId_permissionSection",
					"customPermissionId1_permissionSection",
					"customPermissionId2_permissionSection",
					"customContextComboId_permissionSection",
					"permissionChkBoxClass_forNewPermission"
			);							
		},error : function(jqXHR, exception) {formatErrorMessage(jqXHR, exception);}
	});	//Ajax
}

function adjustPermissionForm_forNewPermissionAssignment(){
	$("#permissionAllChkId").prop("disabled", false);
	$("#roleId").prop("disabled", false);
	$("#userId").prop("disabled", false);
	
	//Change header and submit button
	$("#addEditHeaderId").html("Share ");

	$("#changeButtonId").html("<button class='sharePermissionClass input-sm ui-button ui-widget ui-corner-all pull-right ' >Share-Permission</button>");
	$("#permissionAllChkId").prop("checked",false); //Clean All chk box selection
	//Disable required contents.
	$('#roleComboId').prop('disabled', true);
	$('#userComboId').prop('disabled', true);	  
	//Clean pre-checked and pre-selected items.
	$("input[name=sidtype][value='R']").prop("checked",false);
	$("input[name=sidtype][value='U']").prop("checked",false);
	$("#oldObjectOwnerLabelId").html("");
	$("#roleComboId").val("");
	$("#userComboId").val("");	  
	//Change required label
	$("#userInRoleTdlId").html("<strong style='color:green'><i class='fa fa-user'></i> [ New ]</strong>");
	//Show and hide contents.
	document.getElementById("oldObjectOwnerLabelId").style.display = "none"; 
	$('#userInRoleTrlId').show();
}//adjustPermissionForm_forNewPermissionAssignment

function loadPermissionListDefinedOnDomain(){	
	var domainClass=$("#domainId").text();		
	method = 'GET';
	url = "/acl_admin" + "/loadPermissionListDefinedOnDomain?domainClass="+domainClass+"";	
	$.ajax({
		type : method,
		url : url,
		data : {},
		beforeSend : function(xhr) {
			xhr.setRequestHeader("Accept", "application/json");
			xhr.setRequestHeader("Content-Type", "application/json");
		},
		success : function(response) {
			//alert("Response is coming");				
			perparePermissionChkBoxesForAllType(response,
					"basePermissionId_permissionSection",
					"customPermissionId1_permissionSection",
					"customPermissionId2_permissionSection",
					"customContextComboId_permissionSection",
					"permissionChkBoxClass_forNewPermission"
			);
		},
		error : function(jqXHR, exception) {			
			formatErrorMessage(jqXHR, exception);
		}
	});	//Ajax
}

/**
 * This method will be used in the following two cases
 * 
 * 1. For sharing a new permission-set with a new user.
 * 2. For updating an already shared permissions with an existing User.
 * 
 * Remark: While updating a user's permissions, you can update existing
 * permissions as well as user's name, too. This will not be a new record; but it will be an 
 * updated one. 
 * 
 * **/

function shareOrUpdateUserPermission(actionMode){
	var userSharedPermissions;
	var userId; var roleId;
	var  userNameSelected=$("#oldObjectOwnerLabelId").text().split("-")[1];
	var ifUserChanged;
	var sidType = $("input[name='sidtype']:checked").val();		
	var domainClassName=$("#domainId").text().trim(); 		
	var domainId=$("#selectedObjectId").text().trim();
	
	if(actionMode=="add"){
		userSharedPermissions = getTotalPermissionSelectedForDomain("permissionChkBoxClass_forNewPermission");	
		 userId=$("#userComboId").val();
		 roleId=$("#roleComboId").val();
		 ifUserChanged=$("#userComboId option:selected").text();//Always new user will be selected		 			
	}
	 if(actionMode=="edit"){
		 userSharedPermissions = getTotalPermissionSelectedForDomain("permissionChkBoxClass_forEditPermission");	
		 if($("#userComboId").val()==""){
		     userId="dummy";//being set for avoiding error message while there is no change in user/role
		     roleId="dummy";//being set for avoiding error message while there is no change in user/role
		     }
		 else{
			 userId=$("#userComboId").val();
			 ifUserChanged=$("#userComboId option:selected").text();			 
			 roleId=$("#roleComboId").val();
			 }
	 } 	 
		 
	 var comingContextId=$("#customContextComboId_permissionSection").val();
	jsonObject={								
			"id":domainId,
			"domainClassName":domainClassName,
			"sidType":sidType,
			"roleId":roleId,
			"userId":userId,
			"ifUserChanged":ifUserChanged,
			"userNameSelected":userNameSelected,
			"userSharedPermissions":userSharedPermissions,
			"actionMode":actionMode,
			"comingContextId":comingContextId};	
	var url="/acl_admin/shareOrUpdateUserPermission";		
	$.ajax({
		type : "POST",	         
		url: url,
		data : JSON.stringify(jsonObject),
		beforeSend : function(xhr) {
			xhr.setRequestHeader("Accept", "application/json");
			xhr.setRequestHeader("Content-Type", "application/json");
		},		 
		success: function(response){				 	
			 //alert("Response is coming.");
			 if(response.status){				 
				 actionResponseSuccess(response.statusMsg);				 
				 populatePermissionGrid();
			 }else{	
				 $("#sidTypeErrId").html(response.fieldErrMsgMap.sidType);
				 $("#roleComboIdErr").html(response.fieldErrMsgMap.roleId);					 
				 $("#userComboIdErr").html(response.fieldErrMsgMap.userId);
				 $("#permissionIdErr").html(response.fieldErrMsgMap.userSharedPermissions);				 
			 }				 	     
		   },
				
		}); // Ajaax call end	
}

/**
 * This method will be used in the following two cases
 * 
 * 1. For sharing a new permission-set with a new role.
 * 2. For updating an already shared permissions with an existing role.
 * 
 * Remark: While updating a role's permissions, you can update existing
 * permissions as well as role's name, too. This will not be a new record; but it will be an 
 * updated one. 
 * 
 * **/
function shareOrUpdateRolePermission(actionMode){	
	var roleNameSelected=$("#oldObjectOwnerLabelId").text().split("-")[1];	
	if(actionMode=="add")
		roleSharedPermissions = getTotalPermissionSelectedForDomain("permissionChkBoxClass_forNewPermission");	
	if(actionMode=="edit")
		roleSharedPermissions = getTotalPermissionSelectedForDomain("permissionChkBoxClass_forEditPermission");	
			
	var sidType = $("input[name='sidtype']:checked").val();		
	var domainClassName=$("#domainId").text().trim(); 		
	var domainId=$("#selectedObjectId").text().trim();	
	var roleId=$("#roleComboId").val();
	var ifRoleChanged=$("#roleComboId option:selected").text();	
	var comingContextId=$("#customContextComboId_permissionSection").val();
	jsonObject={								
			"id":domainId,
			"domainClassName":domainClassName,
			"sidType":sidType,
			"ifRoleChanged":ifRoleChanged,
			"roleNameSelected":roleNameSelected,
			"roleId":roleId,
			"roleSharedPermissions":roleSharedPermissions,
			"actionMode":actionMode,
			"comingContextId":comingContextId};	
	var url="/acl_admin/shareOrUpdateRolePermission";		
	$.ajax({
		type : "POST",	         
		url: url,
		data : JSON.stringify(jsonObject),
		beforeSend : function(xhr) {
			xhr.setRequestHeader("Accept", "application/json");
			xhr.setRequestHeader("Content-Type", "application/json");
		},		 
		success: function(response){				 	
			 //alert("Response is coming."+response.status);
			 if(response.status){				
				 actionResponseSuccess(response.statusMsg);
				 populatePermissionGrid();
			 }else{	
				 $("#sidTypeErrId").html(response.fieldErrMsgMap.sidType);
				 $("#roleComboIdErr").html(response.fieldErrMsgMap.roleId);	
				 $("#userComboIdErr").html(response.fieldErrMsgMap.userId);	
				 $("#permissionIdErr").html(response.fieldErrMsgMap.roleSharedPermissions);				 
			 }				 	     
		  },
		}); // Ajaax call end
}

function loadPermissionsForSharingBasedOnSelectedRole(roleId,contextId){	
	
	var domainClass=$("#domainId").text();	
	var url="/acl_admin/loadPermissionsForSharingBasedOnSelectedRole?roleId="+roleId
	                                                           +"&contextId="+contextId
	                                                           +"&domainClass="+domainClass;
	$.ajax({
		type : "GET",	         
		url: url,
		data : {},
		beforeSend : function(xhr) {
			xhr.setRequestHeader("Accept", "application/json");
			xhr.setRequestHeader("Content-Type", "application/json");
		},		 
		success: function(response){				 	
			 //alert("Response is coming.");
			 perparePermissionChkBoxesForAllType(response,
						"basePermissionId_permissionSection",
						"customPermissionId1_permissionSection",
						"customPermissionId2_permissionSection",
						"customContextComboId_permissionSection",
						"permissionChkBoxClass_forNewPermission"
				);
		     } ,				 	     
		}); // Ajaax call end	
}

function loadPermissionsForSharingBasedOnSelectedUser(roleId,userId,contextId){
	var domainClass=$("#domainId").text();	
	var url="/acl_admin/loadPermissionsForSharingBasedOnSelectedUser?roleId="+roleId
	 +"&userId="+userId
    +"&contextId="+contextId
    +"&domainClass="+domainClass;	
	$.ajax({
	type : "GET",	         
	url: url,
	data : {},
	beforeSend : function(xhr) {
	xhr.setRequestHeader("Accept", "application/json");
	xhr.setRequestHeader("Content-Type", "application/json");
	},		 
	success: function(response){				 	
	//alert("Response is coming.");
	perparePermissionChkBoxesForAllType(response,
			"basePermissionId_permissionSection",
			"customPermissionId1_permissionSection",
			"customPermissionId2_permissionSection",
			"customContextComboId_permissionSection",
			"permissionChkBoxClass_forNewPermission"
	       );
	   },
	}); // Ajaax call end
  }
