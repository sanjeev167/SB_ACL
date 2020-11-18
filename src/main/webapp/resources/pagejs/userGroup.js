/*******************************************************************************
 * @Author: Sanjeev Kumar
 * @Date: 12/2/2019 *
 ******************************************************************************/


//###########################################################//
//########## Start of grid and CRUD related code ###########//
//#########################################################//

$(document).ready(function() {

	// [0] Load grid while opening the page.
	loadGrid("", "","");
	preparePage();
	// [1] Open an add modal.
	$(document).on("click",".addClass",function() {
		clearFormData();
		cleanAllMsg();
		hideAllRequired();
		cleanAllHiddenInput();
		removeReadOnlyProp();
		
		$('#saveFormButtonId').attr("disabled", false);			
		$("#commonModalTitleId").html('Associate users with a group');
		$('.commonButtonActionClass').show();
		$('.commonButtonActionClass').attr('id',
		'saveFormButtonId');
		$("#saveFormButtonId").html('Save');
		$('#modal-common').modal('toggle');
	});

	// [2] Save a record.
	$(document).on("click", "#saveFormButtonId", function(e) {
		
		cleanAllMsg();
		var appUserGroupNameUniqueness="Yes";
		saveAndUpdateRecord(appUserGroupNameUniqueness);		
		refreshGridPage();// Refresh with recently added record
	});

	// [3] Open an edit modal.
	$(document).on("click",".eClass",function() {		
		cleanAllMsg();
		hideAllRequired();
		cleanAllHiddenInput();
		removeReadOnlyProp();
		
		$('#updateFormButtonId').attr("disabled", false);		
		getIdBasedRecord($(this).attr("href").split('=')[1]);

		$("#commonModalTitleId")
		.html('Update user-group association');
		$('.commonButtonActionClass').show();
		$('.commonButtonActionClass').attr('id',
		'updateFormButtonId');
		$("#updateFormButtonId").html('Update');
		$('#modal-common').modal('toggle');
	});

	// [4] Update a record.
	$(document).on("click", "#updateFormButtonId", function() {
		cleanAllMsg();
		var appUserGroupNameUniqueness="No";
			saveAndUpdateRecord(appUserGroupNameUniqueness);	
		refreshGridPage();// Reload with recently edited record
	});

	// [5] Open a delete confirm modal.
	$(document).on("click",	".dClass",function() {		
		cleanAllMsg();
		hideAllRequired();
		cleanAllHiddenInput();
		event.preventDefault(); // Will restrict the clicked url to be posted
		$('#deleteFormButtonId').attr("disabled", false);
		$('#recordIdForDelete').val($(this).attr("href").split('=')[1]);
		$('#modal-delete').modal('toggle');
	});

	// [6] Delete a record.
	$('#deleteFormButtonId').on('click', function(e) {
		deleteRecord($('#recordIdForDelete').val());
		$('#deleteFormButtonId').attr("disabled", true);
		refreshGridPage();
	});

	// [7] Open a view modal.
	$(document).on("click", ".vClass", function() {			
		cleanAllMsg();
		hideAllRequired();
		cleanAllHiddenInput();
		applyReadOnlyProp();		
		getIdBasedRecord($(this).attr("href").split('=')[1]);
		$("#commonModalTitleId")
		.html('View user-group association');
		$('#modal-common').modal('toggle');
	});

	// [8] Open a selected delete confirm modal.
	$("#deleteSelected").click(function() {		
		cleanAllMsg();
		hideAllRequired();
		cleanAllHiddenInput();
		if (GetSelectedRowID() == "") {
			$("#deleteSelFormButtonId").attr("disabled", true);
			$('deleteSelFormButtonId').prop('disabled', true);
			$('#showAllertMsg').html("Please select at least one record.");
			$('#showSelectedRow').html("");
		} else {
			$('#deleteSelFormButtonId').attr("disabled", false);
			$('#showAllertMsg').html(
					"<label>Do you really want to delete these "
					+ GetSelectedRowID()[2]
					+ " records? This process cannot be undone.</label>");
			$('#showSelectedRow').html(GetSelectedRowID()[1]);
		}
		$('#deleteSelFormButtonId').attr("disabled", false);
		$('#modal-sDelete').modal('toggle');
	});

	// [9] Delete selected records.
	$('#deleteSelFormButtonId').on('click', function(e) {
		if (GetSelectedRowID() != "") {
			deleteSelectedRecord(GetSelectedRowID()[0]);
			$('#deleteSelFormButtonId').attr("disabled", true);
		}
		refreshGridPage();
	});

	// [10] Open a search modal.
	$(document).on("click",".searchClass",function() {
		clearFormData();
		cleanAllMsg();
		hideAllRequired();
		
		cleanAllHiddenInput();
		removeReadOnlyProp();
		$("#searchMsgId").html(
		'<span style="color:green"><h4>Search with any combination.</h4><\span>');
		$("#searchMsgId").show();
		$("#commonModalTitleId").html('Search records');
		$('.commonButtonActionClass').show();
		$('.commonButtonActionClass').attr('id', 'searchFormButtonId');
		$("#searchFormButtonId").html('Search');
		$('#modal-common').modal('toggle');
	});

	// [11] Search record
	$(document).on("click", "#searchFormButtonId", function(e) {
		var departmentNameId = $('#departmentNameId').val();
		var groupNameId = $('#groupNameId').val();		
		var userNameId = $('#userNameId').val();		
		searchAndReloadGrid(departmentNameId,groupNameId,userNameId);
	});

	// [12] New one.
	$("#reloadGrid").click(function() {
		reloadGrid();
	});
	
	// [12] New one.
	$("#refreshGrid").click(function() {
		refreshGridPage();
	});
});// End of document ready


$('#departmentNameId').on('change', function(e) {
	//Clean before loading
	$('#groupNameId').empty().append('<option  value="">-Select-</option>');	
	loadGroupCombo($('#departmentNameId',"").val());	
	//Clean before loading
	$('#categoryNameId').empty().append('<option  value="">-Select-</option>');
	loadCategoryCombo($('#departmentNameId').val(),"");
	
	$('#userNameId').empty().append('<option  value="">-Select-</option>');
	cleanAllMsg();
});

$('#categoryNameId').on('change', function(e) {
	//Clean before loading
	$('#userNameId').empty().append('<option  value="">-Select-</option>');	
	loadUserCombo($('#categoryNameId',"").val());		
	cleanAllMsg();
});

//Reload Grid.
function reloadGrid() {
	//event.preventDefault();
	clearCheckBoxSelection();
	//var table = $('#countryId').DataTable();
	// table.clear().draw();
	// table.ajax.reload();//Loading existing opened page only
	// table.ajax.url( "paginated?name="+""+"&sortname="+""+"&phonecode="+""+"").load();	
	// Reset column order    
	// table.colReorder.reset();
	// Redraw table (and reset main search filter and column specific filter if any)	
	// t.search( '' ).columns().search( '' ).draw();//Will work without global search4

	window.location.replace("/sec/groupToUser/");
}
function refreshGridPage() {
	//event.preventDefault();
	clearCheckBoxSelection();	
	t.draw();//Loading existing opened page only
	//t.ajax.url( "paginated?name="+""+"&sortname="+""+"&phonecode="+""+"").load();
	// t.search( '' ).columns().search( '' ).draw();//Will work without global search4
	// Example call to load a new file
	//  t.fnReloadAjax( 'media/examples_support/json_source2.txt' );

	// Example call to reload from original file
	//t.fnReloadAjax();
}


function searchAndReloadGrid(departmentNameId,groupNameId,userNameId) {
	clearCheckBoxSelection();
	var table = $('#appUserGroupId').DataTable();
	table.ajax.url(
			"paginated?departmentNameId=" + departmentNameId + "&groupNameId=" +groupNameId+"&userNameId="+userNameId).load();
	$("#successMsgId").html("<span style='font:strong'>Search completed. Check the grid.</span>");
}

//Get Selected Row IDs
function GetSelectedRowID() {
	var table = $('#appUserGroupId').DataTable();
	var checkedRowIdAndName = [];
	var checkedRowIds = [];
	var checkedRowNames = [];
	var recordCount = 0;
	$(".chkIndvRow").each(function() {
		if ($(this).is(':checked')) {
			// $(this).val() will return row index
			var selectedRows = table.rows($(this).val()).data();
			checkedRowNames.push(selectedRows[0].departmentName +" => "+
					             selectedRows[0].groupName +" => "+" => "+
					             selectedRows[0].categoryName +" => "+" => "+
					             selectedRows[0].email + "</br>");
			checkedRowIds.push(selectedRows[0].id);
			recordCount++;
		}
	});
	// Here You will get all selected persons ID
	// alert("Selected Rows:- " + checkedRowIds);
	if (recordCount > 0) {
		checkedRowIdAndName.push(checkedRowIds);
		checkedRowIdAndName.push(checkedRowNames);
		checkedRowIdAndName.push(recordCount);
	}
	return checkedRowIdAndName;
}

//## Code for loading grid ##
var t;
function loadGrid(departmentNameId,groupNameId,userNameId) {
	t = $('#appUserGroupId').DataTable(
			{
				"responsive": true,
				"retrieve" : true,// used for refreshing
				"bAutoWidth" : true,
				//"scrollY" : '110vh',
				//"scrollCollapse" : true,
				"lengthMenu" : [ 5, 10, 15, 20 ],
				"processing" : true,
				"serverSide" : true,
				"ordering" : true,
				"searching" : true,
				"aaSorting" : [ [ 3, "asc" ], [ 4, "asc" ], [ 4, "asc" ] ],
				"ajax" : {					
					"url" : "paginated?departmentNameId=" + departmentNameId+ "&groupNameId=" + groupNameId + "&userNameId=" + userNameId + "",
					"type" : "POST",
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
					{
						"data" : null,
						"sortable" : false,
						"render" : function(data, type, full, meta) {
							return '<input class="chkIndvRow" value='
							+ meta.row + ' type="checkbox" >';// Will
							// index
						}
					},

					{
						"data" : "id",
						"name" : "ID",
						"title" : "ID",
						"searchable" : false,
						"bVisible" : false, // used for hiding a column
					},
					{
						"data" : "departmentName",
						"name" : "departmentName",
						"title" : "App. Context"
					},
					{
						"data" : "groupName",
						"name" : "groupName",
						"title" : "Group"
					},
					{
						"data" : "categoryName",
						"name" : "categoryName",
						"title" : "Category"
					},
					
					{
						"data" : "userName",
						"name" : "userName",
						"title" : "User"
					},
					

					{
						"data" : null,
						"sortable" : false,
						"render" : function(data, type, row) {
							return '<a class="vClass" href=?record_id='
							+ row.id + '>'
							+ '<i class="fa fa-eye"></i>' + '</a>';
						}
					},
					{
						"data" : null,
						"sortable" : false,
						"render" : function(data, type, row) {
							return '<a class="eClass" href=?record_id='
							+ row.id + '>'
							+ '<i  class="fa fa-edit"></i>'
							+ '</a>';
						}
					},
					{
						"data" : null,
						"sortable" : false,
						"render" : function(data, type, row) {
							return '<a class="dClass" href=?record_id='
							+ row.id + '>'
							+ '<i class="fa fa-trash"></i>'
							+ '</a>';
						}
					} ]
			});

	
}// End of loading grid

//###########################################################//
//########## End of grid and CRUD related code #############//
//#########################################################//



//#######################################################################//
//########## Start: Methods for supporting above operations ############//
//#####################################################################//

var baseUrl = '/sec/groupToUser';

function clearFormData() {	
	$('#groupNameId').empty().append('<option selected="selected" value="">-Select-</option>');
	$('#userNameId').empty().append('<option selected="selected" value="">-Select-</option>');
	//alert("Clearing form data");
	$("#addEditFormId").trigger("reset");
	$('#departmentNameId').val("");
	$('#groupNameId').val("");
	$('#userNameId').val("");
	$('#categoryNameId').val("");
	
	//Will make state combo clean
	
}
//This will be called for cleaning the error message already shown on the page.
function cleanAllMsg() {
	$("#successMsgId").html("");
	$('#deleteSuccessMsgId').html("");
	$('#deleteSelectedSuccessMsgId').html("");
	$('#departmentNameId_err').html("");
	$('#groupNameId_err').html("");
	$('#userNameId_err').html("");
	$('#categoryNameId_err').html("");
}

function hideAllRequired() {	
	$("#searchMsgId").hide();
	$('.commonButtonActionClass').hide();
	
}

function cleanAllHiddenInput() {
	$('#appUserGroupRecordId').val("");
	$('#recordIdForDelete').val("");
}

//Apply read only on all the fields of the form so that thevalue of the field
//can not be changed.
function applyReadOnlyProp() {	
	$('#departmentNameId').attr('disabled', true);
	$('#groupNameId').attr('disabled', true);
	$('#userNameId').attr("disabled", true);	
	$('#categoryNameId').attr("disabled", true);
	
}

//Remove read only from all the fields of the form so that they could be
//changed.
function removeReadOnlyProp() {	
	$('#departmentNameId').attr('disabled', false);
	$('#groupNameId').attr('disabled', false);
	$('#userNameId').attr("disabled", false);
	$('#categoryNameId').attr("disabled", false);
}

//If the form requires anything pre-loaded. it can be done here.
function preparePage() {
   loadDepartmentCombo();  
}

//This will be called with an ajax response object and it will be used for
//loading the page with the response.
function loadRecordInForm(response) {
	$('#appUserGroupRecordId').val(response.id);	
	$('#departmentNameId').val(response.departmentNameId);	
	//Clean before loading group combo
	$('#groupNameId').empty().append('<option  value="">-Select-</option>');
	var selectedId=response.groupNameId;
	loadGroupCombo(response.departmentNameId,selectedId);	
	//Clean before loading category combo
	$('#categoryNameId').empty().append('<option  value="">-Select-</option>');
	var selectedId=response.categoryNameId;
	loadCategoryCombo(response.departmentNameId,selectedId);	
	
	//Clean before loading role combo
	$('#userNameId').empty().append('<option  value="">-Select-</option>');	
	selectedId=response.userNameId;
	loadUserCombo(response.categoryNameId,selectedId);	
}

//Fetch a e=record based on id
function getIdBasedRecord(id) {
	/* stop form from submitting normally */
	event.preventDefault();	
	method = 'GET';
	url = baseUrl + "/getRecord" + "?id=" + id;
	// alert("posting url = "+url);
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
			$('#successMsgId').html("<span style='color:green;font:bold;'>"+response.statusMsg+"</span>");
			if(response.status){
				//alert(response.formObject.countryNameId);
				
				loadRecordInForm(response.formObject);
			}else{
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
}// End of getIdBasedRecord

function showBusinessEerror(fieldErrMsgMap) {	
	$('#departmentNameId_err').html(fieldErrMsgMap.departmentNameId);	
	$('#groupNameId_err').html(fieldErrMsgMap.groupNameId);
	$('#userNameId_err').html(fieldErrMsgMap.userNameId);
	$('#categoryNameId_err').html(fieldErrMsgMap.categoryNameId);
	
}

function saveAndUpdateRecord(appUserGroupNameUniqueness) { 
	
	/* stop form from submitting normally */
	event.preventDefault();
	/* get the form data of the from */	
	var id = $('#appUserGroupRecordId').val();	
	var departmentNameId = $('#departmentNameId').val();	
	var groupNameId = $('#groupNameId').val();
	var categoryNameId=$('#categoryNameId').val();
	var userNameId = $('#userNameId').val();
	//alert("categoryNameId = "+categoryNameId);
	//alert("countryId = "+countryId);
	var json = {
			"id" : id,
			"departmentNameId" : departmentNameId,
			"groupNameId" : groupNameId,
			"categoryNameId": categoryNameId,
			"userNameId" : userNameId,
			"appUserGroupNameUniqueness":appUserGroupNameUniqueness
	};	
	$.ajax({
		type : 'POST',
		url : baseUrl + "/saveAndUpdateRecord",
		data : JSON.stringify(json),

		beforeSend : function(xhr) {
			xhr.setRequestHeader("Accept", "application/json");
			xhr.setRequestHeader("Content-Type", "application/json");
		},
		success : function(response) {
			// alert("Response is coming");
			$('#successMsgId').html("<span style='color:green;font:bold;'>"+response.statusMsg+"</span>");
			if(response.status){
				//Do something if required	
				$('#saveFormButtonId').attr("disabled", true);
			}else{
				//alert("Form has an error");
				showBusinessEerror(response.fieldErrMsgMap);
			}
		},
		error : function(jqXHR, exception) {			
			formatErrorMessage(jqXHR, exception);
		}
	});
	return false;
}

function deleteRecord(id) {	
	/* stop form from submitting normally */
	//event.preventDefault();	
	method = 'GET';
	url = baseUrl + "/deleteRecord" + "?id=" + id;
	// alert("posting url = "+url);
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
			$('#deleteSuccessMsgId').html("<span style='color:green;font:bold;'>"+response.statusMsg+"</span>");
			if(response.status){
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

function deleteSelectedRecord(recordIdArray) {
	//alert("Going to delete a record "+recordIdArray);
	//alert("recordIdArray[0] = "+GetSelectedRowID()[0][0]);
	//alert("recordIdArray[1] = "+GetSelectedRowID()[0][1]);
	/* stop form from submitting normally */
	//event.preventDefault();
	
	method = 'POST';
	url = baseUrl + "/deleteSelected";
	// alert("posting url = "+url);
	var idsArray=[];
	for(i=0;i<recordIdArray.length;i++)
		idsArray[i]=recordIdArray[i];	
	$.ajax({
		type : method,
		url : url,
		data : JSON.stringify(idsArray),//JSON.stringify(sendRecordIdArray),
		beforeSend : function(xhr) {
			xhr.setRequestHeader("Accept", "application/json");
			xhr.setRequestHeader("Content-Type", "application/json");
		},
		success : function(response) {
			$('#deleteSelectedSuccessMsgId').html("<span style='color:green;font:bold;'>"+response.statusMsg+"</span>");
			if(response.status){
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

function loadDepartmentCombo(){	
	/* stop form from submitting normally */	
	method = 'GET';
	url = "/sec/groupToUser/department" + "/list";	
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
				var ele = document.getElementById('departmentNameId');	       
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

function loadGroupCombo(id,selectedId){	
	
	/* stop form from submitting normally */	
	method = 'GET';
	url = "/sec/groupToUser/group" + "/list?id=" + id;	
	//alert("url = "+url);
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
						
				var ele = document.getElementById('groupNameId');	       
		        for (var i = 0; i < response.comboList.length; i++) {	
		        	
		            // POPULATE SELECT ELEMENT WITH JSON.
		        	if(selectedId==response.comboList[i]['id'])
		            ele.innerHTML = ele.innerHTML +
		                '<option selected value="' + response.comboList[i]['id'] + '">' + response.comboList[i]['name'] + '</option>';
		        	else
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

function loadCategoryCombo(id,selectedId){
	
	/* stop form from submitting normally */	
	method = 'GET';
	url = "/sec/groupToUser/userCategory" + "/list?id=" + id;	
	//alert("url = "+url);
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
						
				var ele = document.getElementById('categoryNameId');	       
		        for (var i = 0; i < response.comboList.length; i++) {	
		        	
		            // POPULATE SELECT ELEMENT WITH JSON.
		        	if(selectedId==response.comboList[i]['id'])
		            ele.innerHTML = ele.innerHTML +
		                '<option selected value="' + response.comboList[i]['id'] + '">' + response.comboList[i]['name'] + '</option>';
		        	else
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

function loadUserCombo(id,selectedId){		
	/* stop form from submitting normally */	
	method = 'GET';
	url = "/sec/groupToUser/adminUser" + "/list?id=" + id;
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
				var ele = document.getElementById('userNameId');	       
		        for (var i = 0; i < response.comboList.length; i++) {		        	
		            // POPULATE SELECT ELEMENT WITH JSON.
		        	if(selectedId == response.comboList[i]['id'])
		            ele.innerHTML = ele.innerHTML +
		                '<option selected value="' + response.comboList[i]['id'] + '">' + response.comboList[i]['name'] + '</option>';
		        	else
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

//#######################################################################//
//########## End: Methods for supporting above operations ############//
//#####################################################################//


