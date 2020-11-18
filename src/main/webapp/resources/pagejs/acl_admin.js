$(document).ready(function() {

	var permissionFormOpeningMode=0;// For New-Permission [ permissionFormOpeningMode=0; ]
	var userOrRoleNameGlobal;	
    var objectOwnerName;//Will be initialized either while loading grid or owner login 
                    //Will be used while populating permissions on user selection

	// Following will be loaded on page onload
	loadPermissionCustomContextCombo();// Keep it at top otherwise contextId will not be available for
	// permission loading.
	loadRoleCombo();		
		
	loadMyPermissionList();


	// Dependent combo will be loaded on a selection of another combo.
	$('#roleComboId').on('change', function() {		
		//$('#userComboId').empty().append('<option  value="">-Select-</option>');	
		loadUserCombo($('#roleComboId').val());
	});	

	// Load loggedInUser's defined permissions based on context change.
	$("#customContextComboId_adminSection").on('change',function(){		
		var domainClass=$("#domainId").text();
		var domainId=$("#selectedObjectId").text();
		var changedContextId=$("#customContextComboId_adminSection").val();
		var userOrRoleName=$("#loggedInUserId").text();
		loadCustomBasedPermissions_forLoggedInUser(true,userOrRoleName,changedContextId,domainClass,domainId);
	});		

//	Keep user and role combo disabled on page select
	$('#roleComboId').prop('disabled', true);
	$('#userComboId').prop('disabled', true);
	
// Toggle user and role combo on sidtype selection
	$("input[type='radio']"). click(function(){
		var radioValue = $("input[name='sidtype']:checked"). val();
		if(radioValue=='R'){
			$('#userComboId').prop('disabled', true);
			$('#roleComboId').prop('disabled', false);
		}
		if(radioValue=='U'){
			$('#userComboId').prop('disabled', false);
			$('#roleComboId').prop('disabled', false);
		}
	});

//	Delete object and its all acl entry
	$(".deleteObjectAclAndObjectClass").on('click',function(){ 
		checkWheatherThisUserHasRightsOfDeletingCompleteACL();			    	
	});// End of Delete of complete acl of an object
	
	
	
	$("#loggedInUserHelpId").click(function(){
		$("#loggedInUserModalHelpId").dialog({		
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

	
});// End of document read .

function loadMyPermissionList(){
	var domainName=$("#domainId").text();
	var domainId=$("#selectedObjectId").text();
	method = 'GET';
	url = "/acl_admin" + "/loadMyPermissionList?domainName="+domainName+"&domainId="+domainId+"";	
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
					"basePermissionId_adminSection",
					"customPermissionId1_adminSection",
					"customPermissionId2_adminSection",
					"customContextComboId_adminSection",
					"permissionChkBoxClass_forLoggedInUser"
			);
			$(document).on('click', '.permissionChkBoxClass_forLoggedInUser',function(event) {
				event.preventDefault();
				event.stopPropagation();
				return false;
			});		
		},error : function(jqXHR, exception) {formatErrorMessage(jqXHR, exception);}
	});	//Ajax	
}//loadMyPermissionList

function loadCustomBasedPermissions_forLoggedInUser(isPrincipal,userOrRoleName,changedContextId,domainClass,domainId){

	method = 'GET';
	url = "/acl_admin" + "/loadCustomBasedPermissions_forLoggedInUser?contextId="+changedContextId
	+"&domainClass="+domainClass
	+"&domainId="+domainId
	+"&isPrincipal="+isPrincipal
	+"&userOrRoleName="+userOrRoleName+"";	
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
					"basePermissionId_adminSection",
					"customPermissionId1_adminSection",
					"customPermissionId2_adminSection",
					"customContextComboId_adminSection",
					"permissionChkBoxClass_forLoggedInUser"
			);				
			// checkbox click disable				
			$(document).on('click', '.permissionChkBoxClass_forLoggedInUser',function(event) {
				event.preventDefault();
				event.stopPropagation();
				return false;
			});		
		},error : function(jqXHR, exception) {formatErrorMessage(jqXHR, exception);}
	});	//Ajax
}

function loadRoleCombo(){	
	/* stop form from submitting normally */	
	method = 'GET';
	url = "/acl_admin" + "/roleList";	
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
			if(response.status){
				var ele = document.getElementById('roleComboId');	       
				for (var i = 0; i < response.comboList.length; i++) {		        	
					// POPULATE SELECT ELEMENT WITH JSON.
					ele.innerHTML = ele.innerHTML +
					'<option class="roleComboClass" value="' + response.comboList[i]['id'] + '">' + response.comboList[i]['name'] + '</option>';
				}						
				//Do something if required			
			}else{
				showBusinessEerror(response.fieldErrMsgMap);
			}
		},
		error : function(jqXHR, exception) {			
			formatErrorMessage(jqXHR, exception);
		}
	});
}//loadRoleCombo

function loadUserCombo(id){			
	/* stop form from submitting normally */	
	method = 'GET';
	url = "/acl_admin" + "/userList?roleId=" + id;		
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
			if(response.status){				
				//alert("Response is coming");
				var ele = document.getElementById('userComboId');	
				ele.innerHTML = "";
				for (var i = 0; i < response.comboList.length; i++) {			           
					ele.innerHTML = ele.innerHTML +
					'<option value="' + response.comboList[i]['id'] + '">' + response.comboList[i]['name'] + '</option>';
				}					
				//Do something if required			
			}else{					
				showBusinessEerror(response.fieldErrMsgMap);
			}
		},
		error : function(jqXHR, exception) {			
			formatErrorMessage(jqXHR, exception);
		}
	});		
}//loadUserCombo

function loadPermissionCustomContextCombo() {	
	method = 'GET';
	url = "/acl_admin" + "/loadPermissionCustomContextCombo";
	$.ajax({
		type : method,
		url : url,
		data : {},
		beforeSend : function(xhr) {
			xhr.setRequestHeader("Accept", "application/json");
			xhr.setRequestHeader("Content-Type","application/json");
		},
		success : function(response) {
			//alert("Response for permission context list is coming.");
			if (response.status) {							
				var ele1 = "";
				var ele2 =""; 
				//Before populating permission-context, clean it.
				$("#customContextComboId_adminSection").html(ele1);
				$("#customContextComboId_permissionSection").html(ele2);
				for (var i = 0; i < response.comboList.length; i++) {
					// POPULATE SELECT ELEMENT WITH JSON.
					ele1 =  '<option class="permissionOwnerCustomContextClass"'+response.comboList[i]['allReadyAssigned']+' value="' + response.comboList[i]['id'] + '">'
					+ response.comboList[i]['name']
					+ '</option>';

					ele2 =  '<option class="permissionRoleCustomContextClass"'+response.comboList[i]['allReadyAssigned']+' value="' + response.comboList[i]['id'] + '">'
					+ response.comboList[i]['name']
					+ '</option>';							
					$("#customContextComboId_adminSection").append(ele1);
					$("#customContextComboId_permissionSection").append(ele2);
				}						
			} else {
				alert("Wrong Status");
			}
		},
		error : function(jqXHR, exception) {
			alert("Sanjeev Ajax error");
		}
	});
}//loadPermissionCustomContextCombo

function perparePermissionChkBoxesForAllType(response,basePermissionId,customPermissionId1,customPermissionId2,customContextComboId,permissionChkBoxClass){		
	var ctrl = "";var ctrl_1 = "";var ctrl_2 = "";
	var isChecked = "";	

	$("#"+basePermissionId+"").html(ctrl);
	$("#"+customPermissionId1+"").html(ctrl);
	$("#"+customPermissionId2+"").html(ctrl);
	var mask;
	var customTextIdSelected= $("#"+customContextComboId+"").val();				
	var customTextIdComing; var baseCount_1=0; var customCount_1=0;var customCount_2=0;	
	
	for (i = 0; i < response.comboList.length; i++) {			
		mask=(response.comboList[i]['maskPower'])*1;
		customTextIdComing=(response.comboList[i]['customTextId'])*1;
		//Base Permission
		if(-1 < mask && mask < 5)	{	baseCount_1=baseCount_1+1;			
			ctrl = "<input class='"+permissionChkBoxClass+"' " + response.comboList[i]['allReadyAssigned']
			+ " type='checkbox' value='" + response.comboList[i]['id']
			+ "' />&nbsp;"
			+ response.comboList[i]['name']+"<br>";
			$("#"+basePermissionId+"").append(ctrl);
		}			
		//Custom-Permission-1
		if(customTextIdComing==customTextIdSelected && (customCount_1<5))	{
			ctrl_1 = "<input class='"+permissionChkBoxClass+"' " + response.comboList[i]['allReadyAssigned']
			+ " type='checkbox' value='" + response.comboList[i]['id']
			+ "' />&nbsp;"
			+ response.comboList[i]['name']+"<br>";

			$("#"+customPermissionId1+"").append(ctrl_1);
			customCount_1=customCount_1+1;
		}
		//Custom-Permission-2
		if( (customTextIdComing==customTextIdSelected) &&  (customCount_1==5 && customCount_2<10 ))	{
			if(customCount_2!=0){			
				ctrl_2 = "<input class='"+permissionChkBoxClass+"' " + response.comboList[i]['allReadyAssigned']
				+ " type='checkbox' value='" + response.comboList[i]['id']
				+ "' />&nbsp;"
				+ response.comboList[i]['name']+"<br>";
				$("#"+customPermissionId2+"").append(ctrl_2);		        
				customCount_2=customCount_2+1;
			}else{customCount_2=1; }
		}			
	}//for loop		
	
	if(baseCount_1==0){$("#"+basePermissionId+"").html("<span style='color:red;'>No permission found.</span>")}
	if(customCount_1==0){$("#"+customPermissionId1+"").html("<span style='color:red;'>No permission found.</span>")}
	if(customCount_2==0){$("#"+customPermissionId2+"").html("<span style='color:red;'>No permission found.</span>")}
	
}//perparePermissionChkBoxesForAllType

function getTotalPermissionSelectedForDomain(permissionChkBoxClass){	
	var permissionChkArray = [];	
	$("."+permissionChkBoxClass+":checked").each(function() {		
		permissionChkArray.push($(this).val());		
	});		        
	var permissionSelected = permissionChkArray.join(',');
	//alert("permissionSelected = "+permissionSelected);	
	//Now remove starting and trailing "," before returning.		
	totalPermission = permissionSelected.replace(/^,|,$/g,'');
	return totalPermission; 
}


function actionResponseSuccess(msg) {
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

var confirmCompleteAclDeleteFlag=false;

function deleteAllAclsExceptOwnerAndSuperAdminAcls(){
	var domainClass=$("#domainId").text();
	var domainId=$("#selectedObjectId").text();
	var url="/acl_admin/deleteAllAclsExceptOwnerAndSuperAdminAcls?id="+domainId+"&domainClass="+domainClass;
	
	var method="GET";	
	$.ajax({
		type : method,	         
		url: url,
		data : {},
		beforeSend : function(xhr) {
			xhr.setRequestHeader("Accept", "application/json");
			xhr.setRequestHeader("Content-Type", "application/json");
		},		 
		success: function(response){
		    //alert("Response is coming.");
			confirmCompleteAclDeleteFlag=true;
			actionResponseSuccess(response.statusMsg);			
		}		
	}); // Ajaax call end	
}


function confirmCompleteAclDelete(){	
	$("#completeACLDeleteConfirm-dialogueId").dialog({		
		dialogClass : "no-close",
		resizable : false,
		draggable: false,
		height : "auto",
		width : 400,
		modal : true,
		position: { my: "center", at: "center" },
		buttons : {
			"Yes" : function() {				
				deleteAllAclsExceptOwnerAndSuperAdminAcls();
				if(confirmCompleteAclDeleteFlag)
				  $( this ).dialog( "close" );
			},
			"Cancel" : function() {					
				$( this ).dialog( "close" );
			},
		}		
	});
}

function cleanPermissionMsg(){
	$("#roleComboIdErr").html("");					 
    $("#userComboIdErr").html("");
    $("#oldObjectOwnerLabelIdErr").html("");     
    $("#sidTypeErrId").html("");
	$("#roleComboIdErr").html(""); 	
	$("#permissionIdErr").html("");	
}


function checkWheatherThisUserHasRightsOfDeletingCompleteACL(){
	
	var url="/acl_admin/checkWheatherThisUserHasRightsOfDeletingCompleteACL";
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
				confirmCompleteAclDelete();
			else
				userCompleteAClDeletePermissionCheck(response.statusMsg);
			}
		
	}); // Ajaax call end		
}



function userCompleteAClDeletePermissionCheck(msg) {
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

