$(document).ready(function() {

//	Open permission form for change owner
	$(".changeObjectOwnerClass").on('click',function(){ 		
		//Check the loggedIn user's permission for changing object ownership		
		checkCurrentUserPermissionForChangingOwnership();		
	});// End of Open permission form for change owner

//	Save changed owner.
	$(document).on('click', '.changeOwnerClass',function() {
		cleanPermissionMsg();
		var sidtype = $("input[name='sidtype']:checked").val();		
		var domainClassName=$("#domainId").text().trim(); 		
		var domainId=$("#selectedObjectId").text().trim();		
		
		var oldUserName=$("#oldObjectOwnerLabelId").text().split("-")[1].trim();
		
		var newUserName=$("#userComboId option:selected").text().trim();//We are taking text as we require it in ACL table update		
		var roleId=$("#roleComboId").val();
		var userId=$("#userComboId").val();	
		
		jsonObject={								
				"id":domainId,
				"domainClassName":domainClassName,
				"sidtype":sidtype,
				"roleId":roleId,
				"userId":userId,
				"newUserName":newUserName,
				"oldUserName":oldUserName
				};		
		var url="/acl_admin/saveChangeOwnerships";		
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
				 }else{
					 //permissionIdErr	oldObjectOwnerLabelIdErr userComboIdErr 
					 //userInRoleTdlIdErr roleComboIdErr sidTypeId					 
					 $("#roleComboIdErr").html(response.fieldErrMsgMap.roleId);					 
					 $("#userComboIdErr").html(response.fieldErrMsgMap.userId);
					 $("#oldObjectOwnerLabelIdErr").html(response.fieldErrMsgMap.oldUserName);				 
				 }				 	     
			}}); // Ajaax call end		
	});// End of saving changed owner
});//$(document).ready(function()



function adjustPermissionForm_forOwnerChange(){	
	$("#roleId").prop("disabled", true);
	$("#userId").prop("disabled", true);
	$("#permissionAllChkId").prop("disabled", true);
	$("#addEditHeaderId").html("Change-Object-Owner");
	$("#changeButtonId").html("<button class='changeOwnerClass  input-sm ui-button ui-widget ui-corner-all pull-right'>Change-Owner</button>"); 
	$("#permissionAllChkId").prop("checked",false); //Clean All chk box selection
	//Enable required contents.
	$("#userComboId").prop("disabled", false);	 
	$("#roleComboId").prop("disabled", false);

	//Clean pre-checked and pre-selected items.
	$("input[name=sidtype][value='R']").prop("checked",false);
	$("input[name=sidtype][value='U']").prop("checked",true);
	$("#oldObjectOwnerLabelId").html("");
	$("#roleComboId").val("");
	$("#userComboId").val("");
	//Change required label
	$("#userInRoleTdlId").html("<strong style='color:green'><i class='fa fa-user'></i> [ New ] </strong>"); 
	//Show and hide contents.
	document.getElementById("oldObjectOwnerLabelId").style.display = "block"; 
	$('#userInRoleTrlId').show();	
	// checkbox click disable	
	$(document).on('click', '.permissionChkBoxClass_forChangeOwner',function(event) {
		event.preventDefault();
		event.stopPropagation();
		return false;
	});	  
}//adjustPermissionForm_forOwnerChange 	

function loadCustomBasedPermissions_forSeeingChangeOwnerPermission(changedContextId,domainClass,domainId){
	method = 'GET';
	url = "/acl_admin" + "/loadCustomBasedPermissions_forSeeingChangeOwnerPermission?contextId="+changedContextId+"&domainClass="+domainClass+"&domainId="+domainId+"";	
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
					"permissionChkBoxClass_forChangeOwner"
			);				
			// checkbox click disable				
			$(document).on('click', '.permissionChkBoxClass_forChangeOwner',function(event) {
				event.preventDefault();
				event.stopPropagation();
				return false;
			});		
		},error : function(jqXHR, exception) {formatErrorMessage(jqXHR, exception);}
	});	//Ajax

}


function checkCurrentUserPermissionForChangingOwnership(){	
	var url="/acl_admin/checkCurrentUserPermissionForChangingOwnership";
	var method="GET";	
	$.ajax({
		type : method,	         
		url: url,
		data : {},
		beforeSend : function(xhr) {
			xhr.setRequestHeader("Accept", "application/json");
			xhr.setRequestHeader("Content-Type", "application/json");
		},		 
		success: function(response){// alert("Response is coming.");
			if(response.status)
				openPermissionFormForChangeOwnership();
			else
				userChangeOwnershipPermissionCheck(response.statusMsg);
			}
		
	}); // Ajaax call end	
}

function openPermissionFormForChangeOwnership(){
	cleanPermissionMsg();
	 $("#roleComboIdErr").html("");					 
	 $("#userComboIdErr").html("");
	 $("#oldObjectOwnerLabelIdErr").html("");	
	permissionFormOpeningMode=1;// This will set permission form in change owner mode 		
	adjustPermissionForm_forOwnerChange();  	 
	// Now fetch current object owner permission details and its name.
	var userOrRoleName=$("#loggedUserId").text();  	
	var isPrincipal=true;
	var domain=$("#domainId").text(); 		
	var domainId=$("#selectedObjectId").text();
	var url="/acl_admin/getPermissionListOfObjectOwner?id="+domainId
	+"&userOrRoleName="+userOrRoleName+"&isPrincipal="+isPrincipal+"&domain="+domain+"";

	var method="GET";	
	$.ajax({
		type : method,	         
		url: url,
		data : {},
		beforeSend : function(xhr) {
			xhr.setRequestHeader("Accept", "application/json");
			xhr.setRequestHeader("Content-Type", "application/json");
		},		 
		success: function(response){// alert("Response is coming.");
			$("#oldObjectOwnerLabelId").html("&nbsp;<i class='fa fa-user'></i> [ Old ] :-  "+response.objectOwner);     	   
			perparePermissionChkBoxesForAllType(response,
					"basePermissionId_permissionSection",
					"customPermissionId1_permissionSection",
					"customPermissionId2_permissionSection",
					"customContextComboId_permissionSection",
					"permissionChkBoxClass_forChangeOwner"
			);		     
		}}); // Ajaax call end	
}


function userChangeOwnershipPermissionCheck(msg) {
	$("#successMessageId").html(msg);
	$("#success-dialogueId").dialog({		
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
}
