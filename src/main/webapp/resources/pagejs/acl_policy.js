$(document).ready(function() {	
	
	loadDomainCombo();
	populatePermissionGrid();
	loadPermissionCustomContextComboForBothRoleAndOwner();	

	// Disable checkbox of permission grid
	$(document).on('click', '.chkGridIndvRow',function(event) {
		event.preventDefault();
		event.stopPropagation();
		return false;
	});		


	/** Edit permission open * */
	$("#grid_permission_tableId").on('click','.permissionEditClass',function() {
		cleanMessages();
		//alert("Edit link in grid has been clicked.");
		var currentRow = $(this).closest("tr");
		var domainPolicyId = currentRow.find("td:eq(1)").text();
		var domainClassName = currentRow.find("td:eq(2)").text();
		var ownerOrRole = currentRow.find("td:eq(3)").text();
		adjustFormHeaderAndSubmitButton(ownerOrRole, domainPolicyId);
		// Load domain combo, role combo and permission checkboxes through Ajax
		var permissionHolderId;
		var domainHolderId;
		var isPrinciple; var customContextComboId;
		if (ownerOrRole == "Owner") { 				
			domainHolderId = "domainComboId_ownerSection";
			customContextComboId=$("#customContextComboId_ownerSection").val();
			isPrinciple = true;
			zeroRolePermission();
			loadPermissionWithSelectedGridRowForOwner(domainPolicyId,domainClassName);		
		} else {							
			domainHolderId = "domainComboId_roleSection";
			customContextComboId=$("#customContextComboId_roleSection").val();
			loadRoleComboWithSelectedRoleOfGrid(ownerOrRole);
			isPrinciple = false;
			zeroOwnerPermission()
			loadPermissionWithSelectedGridRowForRole(domainPolicyId,domainClassName);
		}
		loadDomainComboWithSelectedGridRow(domainClassName,domainHolderId);
		
	});



//	Delete ACl permission policy
	$(document).on('click', '.permissionDeleteClass',function() {	
		cleanMessages();
		var currentRow = $(this).closest("tr");
		var domainPolicyId = currentRow.find("td:eq(1)").text();
		confirmACLPermissionDelete(domainPolicyId);
	});

});// End of document.ready

function balanceHeight(){
	var height = Math.max($(".left-col").height(), $(".right-col").height());
	//alert("left = "+$(".left-col").height());
	//alert("right = "+$(".right-col").height());
	//alert("height = "+height);
    $(".left-col").height(height);
    $(".right-col").height(height);
	
}

function cleanMessages(){
	$('#ownerDomainIdErr').html("");	
	$('#ownerPermissionErrId').html("");
	$('#roleDomainIdErr').html("");	
	$('#rolePermissionErrId').html("");	
	$('#roleRoleErrId').html("");
}

function loadPermissionWithSelectedGridRowForOwner(domainPolicyId,domainClassName) {

	$.ajax({
		url : "/domain_acl/loadPermissionWithSelectedGridRow?domainPolicyId="
			+ domainPolicyId + "&domainClassName="+domainClassName+"",
			data : {},
			beforeSend : function(xhr) {
				xhr.setRequestHeader("Accept", "application/json");
				xhr.setRequestHeader("Content-Type","application/json");
			},									
			success : function(result) {
				// alert("Response is coming.");			
				fillPermissionsInOwnerSection(result);			
			}
			,
			error : function(jqXHR, exception) {			
				formatErrorMessage(jqXHR, exception);
			}
	});// Ajax
}

function loadPermissionWithSelectedGridRowForRole(domainPolicyId,domainClassName) {

	$.ajax({
		url : "/domain_acl/loadPermissionWithSelectedGridRow?domainPolicyId="
			+ domainPolicyId + "&domainClassName="+domainClassName+"",
			data : {},
			beforeSend : function(xhr) {
				xhr.setRequestHeader("Accept", "application/json");
				xhr.setRequestHeader("Content-Type","application/json");
			},									
			success : function(result) {
				//alert("Response is coming.");			
				fillPermissionsInRoleSection(result);			
			}
	
			,
			error : function(jqXHR, exception) {			
				formatErrorMessage(jqXHR, exception);
			}
	});// Ajax
}

function loadPermissionCustomContextComboForBothRoleAndOwner() {	
	method = 'GET';
	url = "/domain_permission" + "/loadPermissionCustomContextCombo";
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
				// Before populating permission-context, clean it.
				$("#customContextComboId_ownerSection").html(ele1);
				$("#customContextComboId_roleSection").html(ele2);
				for (var i = 0; i < response.comboList.length; i++) {
					// POPULATE SELECT ELEMENT WITH JSON.
					ele1 =  '<option class="permissionOwnerCustomContextClass"'+response.comboList[i]['allReadyAssigned']+' value="' + response.comboList[i]['id'] + '">'
					+ response.comboList[i]['name']
					+ '</option>';

					ele2 =  '<option class="permissionRoleCustomContextClass"'+response.comboList[i]['allReadyAssigned']+' value="' + response.comboList[i]['id'] + '">'
					+ response.comboList[i]['name']
					+ '</option>';	

					$("#customContextComboId_ownerSection").append(ele1);
					$("#customContextComboId_roleSection").append(ele2);
				}						
			} else {
				alert("Wrong Status");
			}
		},
		error : function(jqXHR, exception) {
			alert("Sanjeev Ajax error");
		}
	});
}

var isAclPolicyDeleted=false;
function deletePermissionPolicy(domainPolicyId) {	
	$.ajax({
		url : "/domain_acl/deletePermissionPolicy?domainPolicyId="
			+ domainPolicyId + "",
			data : {},
			beforeSend : function(xhr) {
				xhr.setRequestHeader("Accept", "application/json");
				xhr.setRequestHeader("Content-Type", "application/json");
			},
			success : function(response) {
				// alert("Response of deleting permission is coming. ");
				$("#aclPermissionDeleteSuccessMsgId").html(response.statusMsg);
				isAclPolicyDeleted=true;
				populatePermissionGrid();
			}
			,
			error : function(jqXHR, exception) {			
				formatErrorMessage(jqXHR, exception);
			}
	
	}); // End of ajax call for saving record.

}// End of deletePermissionPolicy

function adjustFormHeaderAndSubmitButton(ownerOrRole, domainPolicyId) {	
	$("#selectedPolicyDeletedId").html("");
	if (ownerOrRole == "Owner") {
		$("#formHeaderId_ownerSection").html(
				"<a href=''><i class='fa fa-toggle-on'></i></a>&nbsp;&nbsp;Edit Owner-Policy [ Policy Id:-- <span class='selectedOwnerPolicyIdClass'>"
				+ domainPolicyId + "</span> ]:-- ");

		$("#formSaveButtonId_ownerSection").html(
		"<button class='updateButtonClass_ownerSection input-sm ui-button ui-widget ui-corner-all pull-right'>Update</button>");
		// Reset role form
		$("#addEditFormHeaderId_roleSection").html("Add:-- [ Role-Level ] Permission-Policies.");
		$("#savePolicyButtonId_roleSection").html(
		"<button class='saveButtonClass_roleSection input-sm ui-button ui-widget ui-corner-all pull-right' >Save-Permission</button>");
		// Initialize role form		
		$("#domainComboId_roleSection").val("0");
		$("#roleComboId_roleSection").empty().append('<option  value="">-Select Role-</option>');
	} else {
		$("#addEditFormHeaderId_roleSection").html(
				"<a href=''><i class='fa fa-toggle-on'></i></a>&nbsp;&nbsp;Edit Role-Policy [ Policy Id:-- <span class='selectedRolePolicyIdClass'>"
				+ domainPolicyId+ "</span> ] :-- ");
		$("#savePolicyButtonId_roleSection").html(
		"<button class='updateButtonClass_roleSection input-sm ui-button ui-widget ui-corner-all pull-right'>Update</button>");
		// Reset Owner form
		$("#formHeaderId_ownerSection").html("Add:-- [ Owner-Level ] Permission-Policies. ");
		$("#formSaveButtonId_ownerSection").html(
		"<button class='saveButtonClass_ownerSection input-sm ui-button ui-widget ui-corner-all pull-right' style='font-weight:bold'>Save-Permission</button>");
		$("#domainComboId_ownerSection").val("0");		
	}
}

function openDialogue(domainPolicyId) {
	$("#dialog-confirm").dialog({
		dialogClass : "no-close",
		resizable : false,
		height : "auto",
		width : 400,
		modal : true,
		buttons : {
			"Yes" : function() {
				deletePermissionPolicy(domainPolicyId);
				// $( this ).dialog( "close" );
			},
			"Cancel" : function() {
				$("#selectedPolicyDeletedId").html("");
				$(this).dialog("close");
			},
		}
	});
}// openDialogue

function confirmACLPermissionDelete(domainPolicyId) {
	//Clean any staled message
	$("#aclPermissionDeleteSuccessMsgId").html("");
	$("#confirmACLPermissionDeleteId").dialog({	
		dialogClass : "no-close",
		resizable : false,
		draggable: false,
		height : "auto",
		width : 400,
		modal : true,
		position: { my: "center", at: "center" },
		buttons : {
			"Yes" : function() {
				deletePermissionPolicy(domainPolicyId);
				if(isAclPolicyDeleted)		    
					$( this ).dialog( "close" ); 
			},
			"Cancel" : function() {			
				$(this).dialog("close");
			},
		}
	});
}//confirmACLPermissionDelete

function successDialogue(responseMsg) {
    
	$("#successMessageId").html(responseMsg); // First write response in dialogue box
	$("#success-dialogueId").dialog({
		dialogClass : "no-close",
		resizable : false,
		height : "auto",
		width : 400,
		modal : true,
		position: { my: "center", at: "center" },
		buttons : {
			"Ok" : function() {
				$(this).dialog("close");
			}
		}
	});
}// successDialogue



//##### Grid data population #######//
function populatePermissionGrid() {
	$('#grid_permission_tableId').DataTable(
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
					"url" : "/domain_acl/permissionPolicyDefinedForAllDomain",					
					"type" : "GET",	"dataSrc": "",				
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
					
					
					{	"searchable" : false,
						"data" : "domainPolicyId",
						"name" : "domainPolicyId",
						"title" : "Policy-ID",						
						"bVisible" : true, // used for hiding a column
					},
					
					{ 	"data" : "domainName",
						"name" : "domainName",
						"title" : "Domain",
						"bVisible" : true, // used for hiding a column
					},
					
					{   "data" : "ownerOrRole",
						"name" : "ownerOrRole",
						"title" : "Owner/Role",
						"bVisible" : true, // used for hiding a column
					},
					
					{  "data" : "permissionList",
						"name" : "permissionList",
						"title" : "Permission Policy Assigned [ Owner/Role ]",
						"render" : function(data, type, row) {											
							return preparePermissionForGrid(data);	
						}
						
					},
										
					{	"data" : null,
						"sortable" : false,
						"bVisible" : true, // used for hiding a column
						"render" : function(data, type, row) {
							return '<a class="permissionEditClass" href="#">'
							+ '<i class="fa fa-edit"></i>' + '</a>';
						}
					},
					{
						"data" : null,
						"sortable" : false,
						"render" : function(data, type, row) {
							return '<a class="permissionDeleteClass" href="#">'
							+ '<i  class="fa fa-trash"></i>'
							+ '</a>';
						}
					} 
				]
			});

	
}// End of loading grid

function preparePermissionForGrid(data){
	var ctrl = "";
	for (j = 0; j <data.length; j++) {
		ctrl = ctrl
		+ "<input class='chkGridIndvRow' " + data[j]['allReadyAssigned']
		+ " type='checkbox' name='" + data[j]['id']
		+ "' />&nbsp;"
		+ data[j]['name']
		+ "&nbsp;&nbsp;";
	}// For	
	return ctrl;
}

//######### old code that has already been replaced. But don't delete
function populatePermissionGridOld() {
	// alert("Permission grid is going to be populated.");
	method = 'GET';
	url = "/domain_acl" + "/permissionPolicyDefinedForAllDomain";
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
			// alert("Permission Grid data is coming");
			if (response.status) {
				$("#grid_permission_tableBodyId").html("");
				var ele = document
				.getElementById('grid_permission_tableBodyId');
				ele.innerHTML="";

				var permissionGridDto;
				for (var i = 0; i < response.permissionGridDtoList.length; i++) {
					permissionGridDto = response.permissionGridDtoList[i];
					var permission = preparePermission(permissionGridDto);
					ele.innerHTML = ele.innerHTML + "<tr>" + "<td>"
					+ (i + 1) + "</td>" + "<td>"
					+ permissionGridDto.domainPolicyId
					+ "</td>" + "<td>"
					+ permissionGridDto.domainName
					+ "</td>" + "<td>"
					+ permissionGridDto.ownerOrRole
					+ "</td>" + "<td>" + permission
					+ "</td>"
					+ "<td class='permissionEditClass'>"
					+ "<a href='#'><i class='fa fa-edit'></a>" + "</td>"
					+ "<td class='permissionDeleteClass'>" + "<a href='#'><i class='fa fa-trash'></a>"
					+ "</td>" + "</tr>";
				}
				// Do something if required
			} else {
				alert("Wrong Status");
			}
		},
		error : function(jqXHR, exception) {
			alert("Ajax error");
		}
	});
}

function preparePermission(permissionGridDto) {

	var ctrl = "";
	for (j = 0; j < permissionGridDto.permissionList.length; j++) {
		ctrl = ctrl
		+ "<input class='chkGridIndvRow' " + permissionGridDto.permissionList[j]['allReadyAssigned']
		+ " type='checkbox' name='" + permissionGridDto.permissionList[j]['id']
		+ "' />&nbsp;"
		+ permissionGridDto.permissionList[j]['name']
		+ "&nbsp;&nbsp;";
	}// For
	return ctrl;
}// //////End: Prepare permssion checkboxes////////
