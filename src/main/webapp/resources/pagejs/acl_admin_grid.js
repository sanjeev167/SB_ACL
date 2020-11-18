$(document).ready(function() {

	populatePermissionGrid();
	// Inactive checkbox of permission grid
	$(document).on('click', '.chkGridIndvRow',function(event) {
		event.preventDefault();
		event.stopPropagation();
		return false;
	});

	//Open for editing existing permission.
	$("#permissionTable").on('click','.gridRowUserRoleEditClass',function(){
		cleanPermissionMsg();
		permissionFormOpeningMode=2;
		var currentRow=$(this).closest("tr");         
		var userOrRoleName=currentRow.find("td:eq(1)").text();	         
		userOrRoleNameGlobal=userOrRoleName;
		var sidType=currentRow.find("td:eq(2)").text();
		var isPrincipal;
		var editRoleId="";		
		if(sidType=='Role'){
			isPrincipal=false;
			$("input[name=sidtype][value='R']").prop("checked",true); 			
			$("#userId").prop("disabled", true);			
		}
		else{
			isPrincipal=true;	 
			$("input[name=sidtype][value='U']").prop("checked",true); 
			$("#roleId").prop("disabled", true);				
		}
		var domain=$("#domainId").text();  
		var domainId=$("#selectedObjectId").text();			
		var url="/acl_admin/editUserOrRolePermission?id="+domainId
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
			success: function(response){
				adjustPermissionForm_forEditingPermission(response,userOrRoleName);        	   
				perparePermissionChkBoxesForAllType(response,
						"basePermissionId_permissionSection",
						"customPermissionId1_permissionSection",
						"customPermissionId2_permissionSection",
						"customContextComboId_permissionSection",
						"permissionChkBoxClass_forEditPermission"
				);	  

			}}); //Ajax call end 	
	});//End of edit 

//Update permission.
	$(document).on('click', '.editPermissionClass',function(event) {	
		//alert("updatePermissionClass is clicked.");  
		cleanPermissionMsg();
		var sidType = $("input[name='sidtype']:checked").val();
		var actionMode="edit";
		if(sidType=="U")
			shareOrUpdateUserPermission(actionMode);
		if(sidType=="R")
			shareOrUpdateRolePermission(actionMode);		
	});//End of Update permission.


//	Delete permission of a role or user.
	$("#permissionTable").on('click','.gridRowUserRoleDeleteClass',function(){	
		var currentRow=$(this).closest("tr");   		
		confirmDeleteUserOrRoleFromGrid(currentRow);		
	});// End of calling delete function


	$(document).on('click', '.toggledClass',function(event) {
		adjustPermissionForm_forNewPermissionAssignment(); 
		loadPermissionListDefinedOnDomain();
	});
	
});//End of $(document).ready(function()


function loadCustomBasedPermissions_forEditingPermission(isPrincipal,userOrRoleName,changedContextId,domainClass,domainId)
{	
	method = 'GET';
	url = "/acl_admin" + "/loadCustomBasedPermissions_forEditingPermission?contextId="+changedContextId
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
					"basePermissionId_permissionSection",
					"customPermissionId1_permissionSection",
					"customPermissionId2_permissionSection",
					"customContextComboId_permissionSection",
					"permissionChkBoxClass_forEditPermission"
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

//Adjust: adjustPermissionForm_forEditingPermission	    
function adjustPermissionForm_forEditingPermission(response,userOrRoleName){
	cleanPermissionMsg();
	$("#permissionAllChkId").prop("disabled", false);
	$("#addEditHeaderId").html("<a href='#' class='toggledClass'><i class=' fa fa-toggle-on'></i></a>&nbsp;Update");	    	    	
	$("#changeButtonId").html("<button  class='editPermissionClass  input-sm ui-button ui-widget ui-corner-all pull-right'>Update Permission</button>");	    	

	$("#permissionAllChkId").prop("checked",false); //Clean All chk box selection

	if(!response.editRoleId==""){	
		$("#userComboId").val("");
		$("#roleComboId").prop("disabled", false);
		$("#userComboId").prop("disabled", true);
		$("#roleComboId").val(response.editRoleId);
		$("#oldObjectOwnerLabelId").html("[ Sel ]:-  "+userOrRoleName);       	   
		document.getElementById("oldObjectOwnerLabelId").style.display="block";
		$('#objectOwnerTrlId').show();
		$('#userInRoleTrlId').show();
	}
	else{
		$("#roleComboId").val("");
		$("#roleComboId").prop("disabled", false);
		$("#userComboId").prop("disabled", false);
		document.getElementById("oldObjectOwnerLabelId").style.display="block";
		$("#oldObjectOwnerLabelId").html("<i class='fa fa-user'></i> [ Sel ]:-  "+userOrRoleName); 

		$('#objectOwnerTrlId').show();      		 
	}  	
}//adjustPermissionForm_forEditingPermission 


var hasUserOrRolePermissionDeleted=false;//It is used for closing delete confirm.
function confirmDeleteUserOrRoleFromGrid(currentRow){
	 
	$("#confirmACLObjectPermissionDeleteId").dialog({		
		dialogClass : "no-close",
		resizable : false,
		draggable: true,
		height : "auto",
		width : 400,
		modal : true,
		position: { my: "center", at: "center" },
		buttons : {
			"OK" : function() {		
				deleteUserOrRoleFromGrid(currentRow);
				if(hasUserOrRolePermissionDeleted)
				$( this ).dialog( "close" );
			   },
			   "Cancel" : function() {					
					$( this ).dialog( "close" );
			}
		}		
	});
}

function deleteUserOrRoleFromGrid(currentRow){		
	    var userOrRoleName=currentRow.find("td:eq(1)").text();		
		var domainClassName=$("#domainId").text().trim(); 		
		var domainId=$("#selectedObjectId").text().trim();
		var isPrincipal;
		var isUserOrRole=currentRow.find("td:eq(2)").text();;
		if(isUserOrRole=="Role")
			isPrincipal=false;
		else
			isPrincipal=true;		
		var url="/acl_admin/deleteUserOrRolepermission?id="+domainId		
	    +"&domainClassName="+domainClassName
	    +"&userOrRoleName="+userOrRoleName+"&isPrincipal="+isPrincipal;		
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
				$("#aclPermissionDeleteSuccessMsgId").html(response.statusMsg);
				hasUserOrRolePermissionDeleted=true;
				populatePermissionGrid();
			}
		}); // Ajaax call end	
}



//##### Grid data population #######//

function populatePermissionGrid() {
	var domainClass=$("#domainId").text();
	var id=$("#selectedObjectId").text();

	method = 'GET';
	var url = "/acl_admin" + "/populatePermissionGrid?id="+id+"&domainClass="+domainClass+"";	
	
	$('#permissionTable').DataTable(
			{	'rowReorder' : true,			
				"retrieve" : true,// used for refreshing
				"bAutoWidth" : true,
				//"scrollY" : '110vh',
				//"scrollCollapse" : true,
				"pagingType": "full_numbers",
				 "paging": true,
				"lengthMenu" : [ 5, 10, 15, 20 ],
				"processing" : true,
				"serverSide" : false,
				"ordering" : true,
				"searching" : true,
				//"aaSorting" : [ [ 2, "asc" ] ],
				"ajax" : {
					"async": false,
					"url" : url,					
					"type" : "GET",	
					"dataSrc": "",				
				},
				

				"columns" : [
					{
						"searchable" : false,
						"orderable" : false,
						"targets" : 0,
						"render" : function(data, type, full, meta) {
							return meta.row + 1;// Will send row index
						}
					},
					
					{ 	"data" : "roleOrUserName",
						"name" : "roleOrUserName",
						"title" : "Object Owner",
						"bVisible" : true, // used for hiding a column
					},
					
					{   "data" : "role",
						"name" : "role",
						"title" : "User/Role",
						"bVisible" : true, // used for hiding a column
						"render" : function(data,type,row){
							return prepareUserOrRoleLabelForGrid(data);
						}
					},
					
					{  "data" : "preCheckedValuesListForRow",
						"name" : "preCheckedValuesListForRow",
						"title" : "Permission",
						"render" : function(data, type, row) {											
							return preparePermission(data);	
						}
						
					},
								
					{	"data" : "roleOrUserName",
						"sortable" : false,
						"bVisible" : true, // used for hiding a column
						"render" : function(data, type, row) {
							
							return prepareEditLinkForGrid(data);
						}
					},
					{
						"data" : "roleOrUserName",
						"sortable" : false,
						"render" : function(data, type, row) {
							return prepareDeleteLinkForGrid(data);
						}
					} 
					
				]
			});

	
}// End of loading grid

function prepareUserOrRoleLabelForGrid(role){	
	var userOrRole;
	if(role == "Owner"){
		userOrRole="<span style='color:red; font-weight:bold;'>"+role+"</span>";		
	}
	else
		userOrRole=role;	
	return userOrRole;
}

function prepareEditLinkForGrid(roleOrUserName){
	var noLink="<span style='color:red; font-weight:bold;'>Not Allowed</span>";
    var editLink="Not Allowed";    
	if(roleOrUserName == "ROLE_SUPER_ADMIN"){
		editLink=noLink;							
	}else{
		
		editLink="<a href='#' class='gridRowUserRoleEditClass'><i class='fa fa-edit'></a>" ;	    
	    }	
	return editLink;
}

function prepareDeleteLinkForGrid(roleOrUserName){
	var noLink="<span style='color:red; font-weight:bold;'>Not Allowed</span>";    
    var deleteLink="Not Allowed";    
	if(roleOrUserName == "ROLE_SUPER_ADMIN"){		
		deleteLink=noLink;					
	}else{		
	    deleteLink="<a href='#' class='gridRowUserRoleDeleteClass'><i class='fa fa-trash'></a>";
	    }	
	return deleteLink;
}

function preparePermission(preCheckedValuesListForRow){	
	// alert(preCheckedValuesListForRow);

	var permissionArr=(preCheckedValuesListForRow+"").split(",");	
	var chk="<input type='checkbox' class='chkGridIndvRow' checked/>&nbsp;";
	var permissionString;
	for(i=0;i<permissionArr.length;i++){
		if(i==0)
			permissionString=chk+permissionArr[i]+"&nbsp;";
		else
			permissionString=permissionString+chk+permissionArr[i]+"&nbsp;";
	}
	return permissionString;
}





//############### Old Code that has already been replaced with new one. 
//Will be deleted once the test is complete.
function populatePermissionGridOld(){
	var domainClass=$("#domainId").text();
	var id=$("#selectedObjectId").text();

	method = 'GET';
	url = "/acl_admin" + "/populatePermissionGrid?id="+id+"&domainClass="+domainClass+"";	
	$.ajax({
		type : method,
		url : url,
		data : {},
		beforeSend : function(xhr) {
			xhr.setRequestHeader("Accept", "application/json");
			xhr.setRequestHeader("Content-Type", "application/json");
		},
		success : function(response) {			
			// alert("Response is coming");
			$("#grid_permission_tableBodyId").html("");
			var ele = document
			.getElementById('grid_permission_tableBodyId');	
			var noLink="<span style='color:red; font-weight:bold;'>Not Allowed</span>";
            var editLink="Not Allowed";
            var deleteLink="Not Allowed";
			var aclrights;
			for (var i = 0; i < response.permissionListForGrid.length; i++) {				
				aclrights = response.permissionListForGrid[i];				
				var permission = preparePermission(aclrights.preCheckedValuesListForRow);
				var userOrRole;
				if(aclrights.role == "Owner"){
					userOrRole="<span style='color:red; font-weight:bold;'>"+ aclrights.role+"</span>";
					objectOwnerName=aclrights.roleOrUserName;
				}
				else
					userOrRole=aclrights.role;
				if(aclrights.roleOrUserName == "ROLE_SUPER_ADMIN"){
					editLink=noLink;deleteLink=noLink;					
				}else{
					editLink="<a href='#'><i class='fa fa-edit'></a>" ;
				    deleteLink="<a href='#'><i class='fa fa-trash'></a>";
				    }
				
				ele.innerHTML = ele.innerHTML 
				+ "<tr>" 
				+ "<td>"+ (i + 1) + "</td>" 
				+ "<td>" + aclrights.roleOrUserName + "</td>" 				
				+ "<td>"+userOrRole + "</td>" 
				+ "<td>" + permission + "</td>"				
				+ "<td class='gridRowUserRoleEditClass'>" + editLink+ "</td>"				
				+ "<td class='gridRowUserRoleDeleteClass'>" +deleteLink + "</td>" 				
				+ "</tr>";
			}//for loop			
		},
		error : function(jqXHR, exception) {	
			alert("Sanjeev : Ajax error. ");
			formatErrorMessage(jqXHR, exception);
		}
	});		
}
