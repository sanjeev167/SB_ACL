/*******************************************************************************
 * @Author: Sanjeev Kumar
 * @Date: 12/2/2019 *
 ******************************************************************************/

// ###########################################################//
// ########## Start of grid and CRUD related code ###########//
// #########################################################//

//These are the varibles which will be used globally in different cases
var preservedRoleId; 
var preservedRoleName; 
var preservedModuleId;
var preservedModuleName; 
var preservedPageId; 
var preservedPageName; 



$(document).ready(function() {	
	
	$('#hideUpdateButtonId').hide();	

// [0] Load grid and loadDepartmentCombo while opening the page.
       loadGrid("", "","","","B");
       loadDepartmentCombo();
	   
//This will set the width of the datatable search box. It must be place after grid loading
	   $('.dataTables_filter input[type="search"]').
	   attr('placeholder','Search text').
	   css({'width':'100px','display':'inline-block'});   
	 	   
// [1] Load all roles with its page access no. based on application context.
//This application context is picked using page_id
	  $(document).on("click", ".rbacRightsClass", function(e) {
		 event.preventDefault();
		 //Picking pageId on clicking one row in page grid. Store it at a global preservedPageId 
		 preservedPageId = $(this).attr("href").split('=')[1];		 
		 $('#pageRecordIdForRole').val(preservedPageId);//Preserved it for future usage
		// This will load all the roles in the role selection combo	
		 loadContextBasedRolesWithAlreadyAssignedPages(preservedPageId);
		 loadContextBasedRolesWithAlreadyAssignedPages(preservedPageId);				
		
		 $('#hideUpdateButtonId').hide();
		$('#selectRolePageId').show();//It will show the role selection container
		$('#selectPageListContainerId').hide();//It will hide page listing for a role for update.		
		$('#roleSelectedId').html("");//Will keep role name as blank. Will be filled only when a role is selected.
		$('#updateSuccessMsgId').html("");//Will keep update a message cleaned.
		});
	  
// [2] Load all the pages assigned to a selected role. It will be called once a role's right is clicked for update 
	 $(document).on("click",".roleSelClass",	function(e) {
		event.preventDefault();
		$('#selectPageListContainerId').show();			
		$('#departmentNameIdOnResSel').html($('#departmentNameIdForRole').text());			
		$('#moduleNameIdOnResUpd').html($('#moduleNameIdOnResSel').text());			
		$('#hideUpdateButtonId').show();
		$('#updateSuccessMsgId').html("");		
		preservedRoleId = $(this).attr("href").split('=')[1];		
		getAllThePagesAssignedToRole(preservedRoleId)		
		//enableOPerationDisabledCheckBox();
	});

  // [3] Save a record.
	$(document).on("click", "#savePageAssignmentButtonId", function() {		
	  savePageAssignment();	 
	// This will load both role and page
	  loadContextBasedRolesWithAlreadyAssignedPages(preservedPageId);	  	 
	  $('#hideUpdateButtonId').hide();
	  $('#roleSelectedId').html("");	  
	  $("#chkAllRoleId").prop("checked", false);//This will clean the all named check box	  
	 });
	
	// [4] Remove page assignment
	$(document).on("click", "#updatePageButtonId", function() {		
	  removePageAssignment();  
	  if(assignedPageCount==0)
	  $('#hideUpdateButtonId').hide();	
	 });

// [5] Open a search modal.
	$(document).on("click",	".searchClass",	function() {
	 
	  $("#searchMsgId").html('<span style="color:green"><h4>Search with any combination.</h4><\span>');
	  $("#searchMsgId").show();
	  $("#commonModalTitleId").html('Search records');
	  $('.commonButtonActionClass').show();
	  $('.commonButtonActionClass').attr('id', 'searchFormButtonId');
	  $("#searchFormButtonId").html('Search');
	  $('#modal-common').modal('toggle');
	});

// [6] Search record
	$(document).on("click", "#searchFormButtonId", function(e) {
		var departmentId = $('#departmentNameId').val();
		var moduleNameId = $('#moduleNameId').val();		
		var pageNameId = $('#pageNameId').val();		
		var roleNameId = $('#roleNameId').val();		
		var pageViewCondition = $("input[name='pageViewCondition']:checked").val();		
		searchAndReloadGrid(departmentId,moduleNameId,pageNameId,roleNameId,pageViewCondition);
	});

// [7] New one.
	$("#reloadGrid").click(function() {
		reloadGrid();
	});

// [8] Refresh grid page
	$("#refreshGridPage").click(function() {
	 refreshGridPage();
	});
	
// [9] When department combo change, load module combo
	$('#departmentNameId').on('change', function(e) {		
		$('#moduleNameId').empty().append('<option  value="">-Select-</option>');
		loadModuleCombo($('#departmentNameId').val());
		loadRoleCombo($('#departmentNameId').val());
	});
// [10] When module combo change, load page combo		
	$('#moduleNameId').on('change', function(e) {
		$('#pageNameId').empty().append('<option  value="">-Select-</option>');
		loadPageCombo($('#moduleNameId').val());
	});

});// End of document ready


////////////////////////////////////////////////////////////////
//Start: Following method will be called when search page is used

$('#assignedPageViewId').on('click',function(e){
	//Set initial value	
	$("#roleNameId").val('');
	$("#moduleNameId").val('');
	$("#pageNameId").val('');
	
	$("#roleSelectionId").show();
	$("#moduleSelectionId").show();
	$("#pageSelectionId").hide();
	$("#roleLabelId").html("Assigned Role");	
});

$('#bothPageViewId').on('click',function(e){
	//Set initial value	
	$("#roleNameId").val('');
	$("#moduleNameId").val('');
	$("#pageNameId").val('');
	
	$("#roleSelectionId").hide();
	$("#moduleSelectionId").show();
	$("#pageSelectionId").show();	
});

$('#unassignedPageViewId').on('click',function(e){	
	//Set initial value	
	$("#roleNameId").val('');
	$("#moduleNameId").val('');
	$("#pageNameId").val('');
	
	$("#roleSelectionId").show();
	$("#moduleSelectionId").show();
	$("#pageSelectionId").hide();
	$("#roleLabelId").html("Unassigned Role");	
});
//End: Following method will be called when search page is used
////////////////////////////////////////////////////////////////


// Reload Grid.
function reloadGrid() {
	event.preventDefault();
	clearCheckBoxSelection();
	window.location.replace("/pvt/sec/webAccessRiights");

}
function refreshGridPage() {
	event.preventDefault();
	clearCheckBoxSelection();
	t.draw();// Loading existing opened page only
	
}

function searchAndReloadGrid(departmentNameId, moduleNameId,pageNameId,roleNameId,pageViewCondition) {		
	
	if((roleNameId==="") && ((pageViewCondition==="A") ||(pageViewCondition==="U"))){
		//alert("No role selection");
		if(pageViewCondition==="A")
		$("#roleNameId_err").html("Assigned to which Role ?");
		if(pageViewCondition==="U")
			$("#roleNameId_err").html("Unassigned to which Role ?");
		$("#roleResourceLabelId").hide();
		$("#roleAssUnassLabelId").hide();		
		return false;
	}
	var appContextName=($("#departmentNameId option:selected").text());
	var moduleName=($("#moduleNameId option:selected").text());
	var roleName=$("#roleNameId option:selected").text();
	if(departmentNameId==="") appContextName="All";
	if(moduleNameId==="") moduleName="All";
	if(roleNameId==="") roleName="All Roles";
	
	if(((pageViewCondition==="A") ||(pageViewCondition==="U"))){
		//alert("Role selected");
		//Clean error message		
		$("#roleNameId_err").html("");
		//Show role above grid for knowing which resources have been either assigned or unassigned
		if(pageViewCondition==="A")
			$("#roleAssUnassLabelId").html("<span style='color:red;'>Assigned Rrsources of</span>  <span style='color:blue;'>["
					+appContextName+"]<\span><span style='color:red;'> context </span>and <span style='color:blue;'>["
					+moduleName+"]<\span><span style='color:red;'> module</span> <br> <span style='color:red;'>for role : </span>");
			if(pageViewCondition==="U")
				$("#roleAssUnassLabelId").html("<span style='color:red;'>Unassigned Rrsources of</span>  <span style='color:blue;'>["
						+appContextName+"]<\span><span style='color:red;'> context </span>and <span style='color:blue;'>["
						+moduleName+"]<\span><span style='color:red;'> module</span> <br> <span style='color:red;'>for role : </span>");
			
			
		$("#roleLabelForGridId").html("[ "+roleName+" ]");
		$("#roleResourceLabelId").show();
		//Load grid
		var table = $('#accessRightsRbacId').DataTable();
		table.ajax.url(
				"paginated?departmentNameId=" + departmentNameId + "&moduleNameId="
						+ moduleNameId+"&pageNameId="+pageNameId+"&roleNameId="+roleNameId+"&pageViewCondition="+pageViewCondition).load();
		$("#successMsgId").html("<span style='font:strong'>Search completed. Check the grid.</span>");
		
		
	}
	if((pageViewCondition==="B")){		
		$("#roleAssUnassLabelId").html("");
		$("#roleAssUnassLabelId").html("<span style='color:red;'>Mixed Rrsources of</span>  <span style='color:blue;'>["
				+appContextName+"]<\span><span style='color:red;'> context </span>and <span style='color:blue;'>["
				+moduleName+"]<\span><span style='color:red;'> module</span> <br> <span style='color:red;'>for role : </span>");
	
		
		$("#roleLabelForGridId").html("[ "+roleName+" ]");
		$("#roleResourceLabelId").show();
		//Load grid
		var table = $('#accessRightsRbacId').DataTable();
		table.ajax.url(
				"paginated?departmentNameId=" + departmentNameId + "&moduleNameId="
						+ moduleNameId+"&pageNameId="+pageNameId+"&roleNameId="+roleNameId+"&pageViewCondition="+pageViewCondition).load();
		$("#successMsgId").html("<span style='font:strong'>Search completed. Check the grid.</span>");
		
	   }
	}

// ## Code for loading grid ##
var t;
function loadGrid(departmentNameId, moduleNameId,pageNameId,roleNameId,pageViewCondition) {
	t = $('#accessRightsRbacId')
			.DataTable(
					{
						"retrieve" : true,// used for refreshing
						"bAutoWidth" : true,
						// "scrollY" : '110vh',
						// "scrollCollapse" : true,
						"lengthMenu" : [ 5, 10, 15, 20 ],
						"processing" : true,
						"serverSide" : true,
						"ordering" : true,
						"searching" : true,
						"aaSorting" : [ [ 2, "asc" ], [ 3, "asc" ],
								[ 4, "asc" ] ],
						"ajax" : {

							"url" : "paginated?departmentNameId="
									+ departmentNameId + "&moduleNameId="
									+ moduleNameId + "&pageNameId="+pageNameId
									+ "&roleNameId="+roleNameId
									+ "&pageViewCondition="+pageViewCondition
									+"",
							"type" : "POST",							
						},

						"columns" : [
								{
									"searchable" : false,
									"orderable" : false,
									"targets" : 0,
									"render" : function(data, type, full, meta) {
										return meta.row + 1;// Will send row
										// index
									}
								},

								{
									"data" : "id",
									"name" : "ID",
									"title" : "ID",
									"searchable" : false,
									"bVisible" : false, // used for hiding a
								// column
								},
								{
									"data" : "departmentName",
									"name" : "departmentName",
									"title" : "App. Context",
									"bVisible" : false,
								},
								{
									"data" : "moduleName",
									"name" : "moduleName",
									"title" : "Module"
								},
								{
									"data" : "pageName",
									"name" : "pageName",
									"title" : "Page"
								},
								{
									"data" : null,
									"sortable" : false,
									"render" : function(data, type, row) {
										return '<a class="rbacRightsClass" href=?record_id='
										+ row.id + '>'
										+ '<i  class="fa fa-edit"></i>'
										+ '</a>';
									}
								}
							 ]
					});

}// End of loading grid

// ###########################################################//
// ########## End of grid and CRUD related code #############//
// #########################################################//



// #######################################################################//
// ########## Start: Methods for supporting above operations ############//
// #####################################################################//

//If the form requires anything pre-loaded. it can be done here.
function preparePage() {loadDepartmentCombo();}

var baseUrl = '/pvt/sec/webAccessRiights';

//Check/Uncheck all checkboxes on role selection check all option  
$("#chkAllRoleId").click(function() {	
	if ($("#chkAllRoleId").is(':checked')) {
		$(".chkIndvRoleRow").each(function() {
			$(this).prop("checked", true);
		});
	} else {
		$(".chkIndvRoleRow").each(function() {
			$(this).prop("checked", false);
		});
	}
	// Get selected Row Ids in Array
	// GetSelectedRowID();
});

//This will be called when one of role selection checkbox is clicked
$(document).on( "click", ".chkIndvRoleRow",function() {
	if ($(this).is(':checked')) {
		// check/uncheck "Select All" checkbox on change of individual row checkbox change
		if ($('.chkIndvRoleRow:checked').length == $('.chkIndvRoleRow').length) {
			$("#chkAllRoleId").prop("checked",true);
		}
	} else { $("#chkAllRoleId").prop("checked", false);}		 								
});


//Fetch records based on id
function loadRoleWithPageAccessCountAfterAssignmentAndRemoval() {
	/* stop form from submitting normally */
	event.preventDefault();
	var appContextId=$('#departmentNameIdForRole').val();
	
	method = 'GET';
	url = baseUrl + "/loadRoleWithPageAccessCountAfterAssignmentAndRemoval?appContextId="+appContextId;
	
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
			$('#successMsgId').html(
					"<span style='color:green;font:bold;'>"
							+ response.statusMsg + "</span>");
			if (response.status) {
				// alert(response.formObject.countryNameId);
				loadRolesInRoleSelectionForm(response.formObject);				
			} else {
				showBusinessEerror(response.fieldErrMsgMap);
			}
			// Now load the page with response
			if (response.status == "ErrorFree")
				loadRecord(response);
			else
				showBusinessEerror(response);
		},
		error : function(jqXHR, exception) {			
			formatErrorMessage(jqXHR, exception);
		}
	});
	return false; // prevent the browser following the href
}// End of loadContextBasedRolesWithAlreadyAssignedPages

//This will prepare the role selection container and will be used by loadRoleWithPageAccessCountAfterAssignmentAndRemoval
function loadRolesInRoleSelectionForm(response) {	
	$('#departmentNameIdForRole').html(response.departmentName);
	$('#moduleNameIdOnResSel').html(response.moduleName);
	$('#selResIdForRoleAssignment').html(response.pageName);
	var roleListInDepartment = response.roleListInDepartment;	
	var ctrl = "";
	$("#roleHolder").html(ctrl);
	for (i = 0; i < roleListInDepartment.length; i++) {
		var index = i + 1;		
		if (roleListInDepartment[i]['accessCount'] != 0)
			ctrl = "<tr><td>[" + index + "]&nbsp;<input class='chkIndvRoleRow' type='checkbox' name='"+roleListInDepartment[i]['id']+"' />&nbsp;<label style='color: blue;'>"
					+ roleListInDepartment[i]['name'] + "  <span style='color: green;'>[TPA:-"
					+ roleListInDepartment[i]['accessCount']
					+ "<span>]</label></td><td><a class='roleSelClass' href='?id="
					+ roleListInDepartment[i]['id']
					+ "'><i class='fa fa-edit'></a></td></tr>";
		else
			ctrl = "<tr><td>[" + index + "]&nbsp;<input class='chkIndvRoleRow' type='checkbox' name='"+roleListInDepartment[i]['id']+"' />&nbsp;<label style='color: blue;'>"
					+ roleListInDepartment[i]['name'] + "  <span style='color: red;'>[TPA:-"
					+ roleListInDepartment[i]['accessCount']
					+ "<span>]</label></td><td><a class='roleSelClass' href='?id="
					+ roleListInDepartment[i]['id']
					+ "'><i class='fa fa-edit'></a></td></tr>";
		
		$("#roleHolder").append(ctrl);
	}
	
	if(roleListInDepartment.length<1){
		$('#NoRoleDefinedEithinDepartmentId').show();
		$('#roleDefinedEithinDepartmentId').hide();
	}else{$('#roleDefinedEithinDepartmentId').show();$('#NoRoleDefinedEithinDepartmentId').hide();}
	
	
}


//Will be called on a page selection
function loadContextBasedRolesWithAlreadyAssignedPages(pageId) {
	/* stop form from submitting normally */
	
	event.preventDefault();
	method = 'GET';
	url = baseUrl + "/loadContextBasedRolesWithAlreadyAssignedPages" + "?pageId=" + pageId;
	
	$.ajax({
		type : method,
		url : url,
		data : {},
		beforeSend : function(xhr) {
			xhr.setRequestHeader("Accept", "application/json");
			xhr.setRequestHeader("Content-Type", "application/json");
		},
		success : function(response) {
			// alert("Response is coming for fetching pages for a role");
			$('#successMsgId').html(
					"<span style='color:green;font:bold;'>"
							+ response.statusMsg + "</span>");
			if (response.status) {
				// alert(response.formObject.countryNameId);
				loadRolesInRoleSelectionForm(response.formObject);
				//loadRecordInOperationSelectionForm(response.formObject);
			} else {
				showBusinessEerror(response.fieldErrMsgMap);
			}
			// Now load the page with response
			if (response.status == "ErrorFree")
				loadRecord(response);
			else
				showBusinessEerror(response);
		},
		error : function(jqXHR, exception) {	
			//alert("Error found ");
			formatErrorMessage(jqXHR, exception);
		}
	});
	return false; // prevent the browser following the href
}// End of loadContextBasedRolesWithAlreadyAssignedPages


function getAllThePagesAssignedToRole(roleId) {
	/* stop form from submitting normally */
	event.preventDefault();	
	var method = 'GET';
	var url = baseUrl + "/getAllThePagesAssignedToRole" + "?roleId=" + roleId;	
	$.ajax({
		async:false,
		type : method,
		url : url,
		data : {},
		beforeSend : function(xhr) {
			xhr.setRequestHeader("Accept", "application/json");
			xhr.setRequestHeader("Content-Type", "application/json");
		},
		success : function(response) {

			if (response.status) {
				//alert("Response is coming");					
				loadPagesWithinPageContainer(response.formObject);
				$('#roleSelectedId').html(response.formObject.roleName);
			} else {
				showBusinessEerror(response.fieldErrMsgMap);
			}
			// Now load the page with response
			if (response.status == "ErrorFree")
				loadRecord(response);
			else
				showBusinessEerror(response);
		},
		error : function(jqXHR, exception) {			
			formatErrorMessage(jqXHR, exception);
		}
	});

	
}

var assignedPageCount=0;
function loadPagesWithinPageContainer(response) {	
	//clearCheckBoxSelection();		
	var pageListOfThisRole = response.allAssignedPagesToThisRole;		
	var ctrl = "";
	var isChecked = "";
	$("#opHolder").html(ctrl);
	assignedPageCount=pageListOfThisRole.length;
	for (i = 0; i < pageListOfThisRole.length; i++) {		
		ctrl = "<tr><td><input class='opSelClass chkIndvRow' "// + pageListOfThisRole[i]['allReadyAssigned']
				+ " type='checkbox' name='" + pageListOfThisRole[i]['id']
				+ "' />&nbsp;<label style='color: blue;'>"
				+ pageListOfThisRole[i]['name'] + "</label></td></tr>";
		$("#opHolder").append(ctrl);
	}
	if(pageListOfThisRole.length<1){
		$('#NoOpDefinedOnPageId').show();
		$('#opDefinedOnPageId').hide();
	}else{$('#opDefinedOnPageId').show();$('#NoOpDefinedOnPageId').hide();}
	
	//enable all the operation check boxes
	$('.opSelClass').each(function() { 
	    if($(this).prop('checked')||!$(this).prop('checked')) {
	        $(this).prop('disabled', false);
	    }
	});
}

//This will be used for taking out the selected rows from the role selection 
function GetSelectedRowIDForRoleSelection() {	
	var table = $('#roleSelectionTableId');
	var checkedRowIds = [];
	$(".chkIndvRoleRow").each(function() {
		if ($(this).is(':checked')) {
			// $(this).val() will return row index
			var roleId = ($(this).attr("name"));
			checkedRowIds.push(roleId);
		}
	});
	return checkedRowIds;
}

//Save page assignment to different roles.
function savePageAssignment() {
	var roleIdArray = GetSelectedRowIDForRoleSelection();
	if(roleIdArray.length==0){
		alert("Select at least one record.");
		return;
      }
	/* stop form from submitting normally */
	event.preventDefault();
	var pageId = $('#pageRecordIdForRole').val();		
	$.ajax({
		async:false,//Client call will wait for the completion of ajax call
		type : 'POST',
		url : baseUrl + "/savePageAssignment?pageId=" + pageId + "&roleIdArray=" + roleIdArray + "",
		beforeSend : function(xhr) {
			xhr.setRequestHeader("Accept", "application/json");
			xhr.setRequestHeader("Content-Type", "application/json"); 
		},
		success : function(response) {
			//alert("Response is coming");
			$('#updateSuccessMsgId').html("<span style='color:green;font:bold;'>"+ response.statusMsg + "</span>");
			if (response.status) {
				// Do something if required	
				loadRoleWithPageAccessCountAfterAssignmentAndRemoval();
			} else {
				// alert("Form has an error");
				showBusinessEerror(response.fieldErrMsgMap);
			}
		},
		error : function(jqXHR, exception) {			
			formatErrorMessage(jqXHR, exception);
		}
	});

}
//Get Selected Row IDs
function GetSelectedRowID() {
	
	var table = $('#operationTableId');
	var checkedRowIds = [];
	$(".chkIndvRow").each(function() {
		if ($(this).is(':checked')) {
			// $(this).val() will return row index
			var roleId = ($(this).attr("name"));
			checkedRowIds.push(roleId);
		}
	});
	return checkedRowIds;
}

//Remove already assigned page to a role
function removePageAssignment() {
	var pageIdArray = GetSelectedRowID();	
	if(pageIdArray.length==0){
		alert("Please select at least one page to be removed.");
		return;
	}		
	/* stop form from submitting normally */
	event.preventDefault();	
	$.ajax({
		async:false,//Client call will wait for the completion of ajax call
		type : 'POST',
		url : baseUrl + "/removePageAssignment?roleId=" + preservedRoleId + "&pageIdArray=" + pageIdArray + "",
		beforeSend : function(xhr) {
			xhr.setRequestHeader("Accept", "application/json");
			xhr.setRequestHeader("Content-Type", "application/json");
		},
		success : function(response) {
			// alert("Response is coming");
			$('#updateSuccessMsgId').html("<span style='color:green;font:bold;'>"+ response.statusMsg + "</span>");
			if (response.status) {
				// Do something if required					
				getAllThePagesAssignedToRole(preservedRoleId);
				loadContextBasedRolesWithAlreadyAssignedPages(preservedPageId);// This will load all the roles in the role selection combo
				
				$("#chkAll").prop("checked", false);
			} else {
				// alert("Form has an error");
				showBusinessEerror(response.fieldErrMsgMap);
			}
		},
		error : function(jqXHR, exception) {			
			formatErrorMessage(jqXHR, exception);
		}
	});

}


function loadDepartmentCombo() {
	/* stop form from submitting normally */
	method = 'GET';
	var url = "/sec/env/department" + "/list";
	$.ajax({
		type : method,
		url : url,
		data : {},
		beforeSend : function(xhr) {
			xhr.setRequestHeader("Accept", "application/json");
			xhr.setRequestHeader("Content-Type", "application/json");
		},
		success : function(response) {
			if (response.status) {
				var ele = document.getElementById('departmentNameId');
				for (var i = 0; i < response.comboList.length; i++) {
					// POPULATE SELECT ELEMENT WITH JSON.
					ele.innerHTML = ele.innerHTML + '<option value="'
							+ response.comboList[i]['id'] + '">'
							+ response.comboList[i]['name'] + '</option>';
				}

				// Do something if required
			} else {
				showBusinessEerror(response.fieldErrMsgMap);
			}
		},
		error : function(jqXHR, exception) {			
			formatErrorMessage(jqXHR, exception);
		}
	});
}

function loadModuleCombo(id) {
	/* stop form from submitting normally */
	method = 'GET';
	var url = "/sec/env/module" + "/list?id=" + id;
	$.ajax({
		type : method,
		url : url,
		data : {},
		beforeSend : function(xhr) {
			xhr.setRequestHeader("Accept", "application/json");
			xhr.setRequestHeader("Content-Type", "application/json");
		},
		success : function(response) {
			if (response.status) {
				var ele = document.getElementById('moduleNameId');
				for (var i = 0; i < response.comboList.length; i++) {
					// POPULATE SELECT ELEMENT WITH JSON.
					ele.innerHTML = ele.innerHTML + '<option value="'
							+ response.comboList[i]['id'] + '">'
							+ response.comboList[i]['name'] + '</option>';
				}

				// Do something if required
			} else {

				showBusinessEerror(response.fieldErrMsgMap);
			}
		},
		error : function(jqXHR, exception) {			
			formatErrorMessage(jqXHR, exception);
		}
	});

}

function loadPageCombo(id){	
	
	/* stop form from submitting normally */	
	method = 'GET';
	url = "/sec/env/page" + "/list?id=" + id;		
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
						
				var ele = document.getElementById('pageNameId');	       
		        for (var i = 0; i < response.comboList.length; i++) {		        	
		            // POPULATE SELECT ELEMENT WITH JSON.
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
	
}

function loadRoleCombo(id){		
	/* stop form from submitting normally */	
	method = 'GET';
	url = "/sec/rbac/role" + "/list?id=" + id;
	$.ajax({
		type : method,
		url : url,
		data : {},
		beforeSend : function(xhr) {
			xhr.setRequestHeader("Accept", "application/json");
			xhr.setRequestHeader("Content-Type", "application/json");
		},
		success : function(response) {			
			if(response.status){					
				var ele = document.getElementById('roleNameId');	
			
		        for (var i = 0; i < response.comboList.length; i++) {		        	
		            // POPULATE SELECT ELEMENT WITH JSON.		        	
		            ele.innerHTML = ele.innerHTML +
		                '<option  value="' + response.comboList[i]['id'] + '">' + response.comboList[i]['name'] + '</option>';
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
}

// #######################################################################//
// ########## End: Methods for supporting above operations ############//
// #####################################################################//

