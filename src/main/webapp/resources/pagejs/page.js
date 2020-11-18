/*******************************************************************************
 * @Author: Sanjeev Kumar
 * @Date: 12/2/2019 *
 ******************************************************************************/


//###########################################################//
//########## Start of grid and CRUD related code ###########//
//#########################################################//

$(document).ready(function() {

	// [0] Load grid while opening the page.
	loadGrid("", "");
	preparePage();
	// [1] Open an add modal.
	$(document).on("click",".addClass",function() {
		clearFormData();
		cleanAllMsg();
		hideAllRequired();
		cleanAllHiddenInput();
		removeReadOnlyProp();
		$('#pageLabledId').show();
		$('#baseurlLabledId').show();
		$('#saveFormButtonId').attr("disabled", false);			
		$("#commonModalTitleId").html('Add a record');
		$('.commonButtonActionClass').show();
		$('.commonButtonActionClass').attr('id',
		'saveFormButtonId');
		$("#saveFormButtonId").html('Save');
		$('#modal-common').modal('toggle');
	});

	// [2] Save a record.
	$(document).on("click", "#saveFormButtonId", function(e) {
		
		cleanAllMsg();
		var pageNameUniqueness="Yes";
		saveAndUpdateRecord(pageNameUniqueness);			
		refreshGridPage();// Refresh with recently added record
	});

	// [3] Open an edit modal.
	$(document).on("click",".eClass",function() {
		
		cleanAllMsg();
		hideAllRequired();
		cleanAllHiddenInput();
		removeReadOnlyProp();
		$('#pageLabledId').show();
		$('#baseurlLabledId').show();
		
		$('#updateFormButtonId').attr("disabled", false);		
		getIdBasedRecord($(this).attr("href").split('=')[1]);

		$("#commonModalTitleId").html('Update a record');
		$('.commonButtonActionClass').show();
		$('.commonButtonActionClass').attr('id',
		'updateFormButtonId');
		$("#updateFormButtonId").html('Update');
		$('#modal-common').modal('toggle');
	});

	// [4] Update a record.
	$(document).on("click", "#updateFormButtonId", function() {
		cleanAllMsg();
		var pageNameUniqueness="No";
		saveAndUpdateRecord(pageNameUniqueness);	
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
		$('#pageLabledId').show();
		$('#baseurlLabledId').show();
		getIdBasedRecord($(this).attr("href").split('=')[1]);
		$("#commonModalTitleId").html('View a record');
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
			$('#showAllertMsg')
			.html("Please select at least one record.");
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
		var departmentId = $('#departmentNameId').val();
		var moduleNameId = $('#moduleNameId').val();		
		
		
		searchAndReloadGrid(departmentId,moduleNameId);
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

$('#moduleNameId').on('change', function(e) {
	cleanAllMsg();	
});
$('#departmentNameId').on('change', function(e) {
	cleanAllMsg();
	//alert("Going to load State combo");
	//This will clean the state combo of state and show only select in the combo box
	$('#moduleNameId').empty().append('<option  value="">-Select-</option>');	
	loadModuleCombo($('#departmentNameId').val(),"");
});

//Reload Grid.
function reloadGrid() {
	event.preventDefault();
	clearCheckBoxSelection();

	//var table = $('#countryId').DataTable();

	// table.clear().draw();
	// table.ajax.reload();//Loading existing opened page only
	// table.ajax.url( "paginated?name="+""+"&sortname="+""+"&phonecode="+""+"").load();	
	// Reset column order    
	// table.colReorder.reset();

	// Redraw table (and reset main search filter and column specific filter if any)	
	// t.search( '' ).columns().search( '' ).draw();//Will work without global search4

	window.location.replace("/sec/env/page/");


}
function refreshGridPage() {
	event.preventDefault();
	clearCheckBoxSelection();	
	t.draw();//Loading existing opened page only
	//t.ajax.url( "paginated?name="+""+"&sortname="+""+"&phonecode="+""+"").load();
	// t.search( '' ).columns().search( '' ).draw();//Will work without global search4
	// Example call to load a new file
	//  t.fnReloadAjax( 'media/examples_support/json_source2.txt' );

1	// Example call to reload from original file
	//t.fnReloadAjax();
}


function searchAndReloadGrid(departmentNameId,moduleNameId) {
	clearCheckBoxSelection();
	var table = $('#pageId').DataTable();
	table.ajax.url(
			"paginated?departmentNameId=" + departmentNameId + "&moduleNameId=" +moduleNameId).load();
	$("#successMsgId").html("<span style='font:strong'>Search completed. Check the grid.</span>");
}

//Get Selected Row IDs
function GetSelectedRowID() {
	var table = $('#pageId').DataTable();
	var checkedRowIdAndName = [];
	var checkedRowIds = [];
	var checkedRowNames = [];
	var recordCount = 0;
	$(".chkIndvRow").each(function() {
		if ($(this).is(':checked')) {
			// $(this).val() will return row index
			var selectedRows = table.rows($(this).val()).data();
			checkedRowNames.push(selectedRows[0].departmentName +" => "
					             +selectedRows[0].moduleName +" => "
					             +selectedRows[0].pageName +" => "
					             +selectedRows[0].baseurl + "</br>");
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
function loadGrid(departmentNameId,moduleNameId) {
	t = $('#pageId').DataTable(
			{
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
					
					"url" : "paginated?departmentNameId=" + departmentNameId + "&moduleNameId=" + moduleNameId + "",

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
						"data" : "baseurl",
						"name" : "baseurl",
						"title" : "Base Url"
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

var baseUrl = '/sec/env/page';

function clearFormData() {
	//alert("Clearing form data");
	$("#addEditFormId").trigger("reset");
	$('#moduleNameId').val("");
	$('#departmentNameId').val("");
	$('#pageNameId').val("");
	$('#baseurlId').val("");
	//Will make module combo clean
	$('#moduleNameId').empty()
    .append('<option selected="selected" value="">-Select-</option>');
}
//This will be called for cleaning the error message already shown on the page.
function cleanAllMsg() {
	$("#successMsgId").html("");
	$('#deleteSuccessMsgId').html("");
	$('#deleteSelectedSuccessMsgId').html("");
	$('#moduleNameId_err').html("");
	$('#departmentNameId_err').html("");
	$('#pageName_err').html("");
	$('#baseurl_err').html("");
	
	
}

function hideAllRequired() {	
	$("#searchMsgId").hide();
	$('.commonButtonActionClass').hide();
	$('#pageLabledId').hide();
	$('#baseurlLabledId').hide();
}

function cleanAllHiddenInput() {
	$('#pageRecordId').val("");
	$('#recordIdForDelete').val("");
}

//Apply read only on all the fields of the form so that thevalue of the field
//can not be changed.
function applyReadOnlyProp() {	
	$('#departmentNameId').attr('disabled', true);
	$('#moduleNameId').attr('disabled', true);
	$('#pageNameId').prop("readonly", true);
	$('#baseurlId').prop("readonly", true);
	
	
}

//Remove read only from all the fields of the form so that they could be
//changed.
function removeReadOnlyProp() {	
	$('#departmentNameId').attr('disabled', false);
	$('#moduleNameId').attr('disabled', false);
	$('#pageNameId').prop("readonly", false);
	$('#baseurlId').prop("readonly", false);
	
}

//If the form requires anything pre-loaded. it can be done here.
function preparePage() {
	$('#departmentNameId').empty().append('<option  value="">-Select-</option>');	
   loadDepartmentCombo("");  
}

//This will be called with an ajax response object and it will be used for
//loading the page with the response.
function loadRecordInForm(response) {
	$('#pageRecordId').val(response.id);	
	$('#departmentNameId').val(response.departmentNameId);
	$('#departmentNameId').empty();
	$('#departmentNameId').empty().append('<option  value="">-Select-</option>');	
	loadDepartmentCombo(response.departmentNameId); 
	//This will clean the state combo of state and show without select in the combo box
	$('#moduleNameId').empty().append('<option  value="">-Select-</option>');	
	loadModuleCombo(response.departmentNameId,response.moduleNameId);
	$('#moduleNameId').val(response.moduleNameId);
	$('#pageNameId').val(response.pageName);
	$('#baseurlId').val(response.baseurl);
	
	
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
	$('#moduleNameId_err').html(fieldErrMsgMap.moduleNameId);
	$('#pageName_err').html(fieldErrMsgMap.pageName);
	$('#baseurl_err').html(fieldErrMsgMap.baseurl);
	
}

function saveAndUpdateRecord(pageNameUniqueness) { 
	
	/* stop form from submitting normally */
	event.preventDefault();
	/* get the form data of the from */
	// var formSerializeData = $('#commonFormId').serialize();
	var id = $('#pageRecordId').val();	
	var departmentNameId = $('#departmentNameId').val();	
	var moduleNameId = $('#moduleNameId').val();	
	var pageName = $('#pageNameId').val();	
	var baseurl = $('#baseurlId').val();	
	//alert("stateName = "+stateName);
	//alert("countryId = "+countryId);
	var json = {
			"id" : id,
			"departmentNameId" : departmentNameId,
			"moduleNameId" : moduleNameId,
			"pageName" : pageName,
			"baseurl"  :baseurl,"pageNameUniqueness":pageNameUniqueness
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
	
}

function deleteRecord(id) {	
	/* stop form from submitting normally */
	event.preventDefault();	
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
	event.preventDefault();
	
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
function loadDepartmentCombo(idForSelected){	
	/* stop form from submitting normally */	
	method = 'GET';
	
	url = "/sec/env/page/department" + "/list";	
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
		        	if(idForSelected == response.comboList[i]['id'])
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

function loadModuleCombo(id,idForSelectModule){	
	
	/* stop form from submitting normally */	
	method = 'GET';
	url = "/sec/env/page/module" + "/list?id=" + id;		
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
						
				var ele = document.getElementById('moduleNameId');	       
		        for (var i = 0; i < response.comboList.length; i++) {		        	
		            // POPULATE SELECT ELEMENT WITH JSON.
		        	if(idForSelectModule == response.comboList[i]['id'])
		            ele.innerHTML = ele.innerHTML +
		                '<option selected value="' + response.comboList[i]['id'] + '">' + response.comboList[i]['name'] + '</option>';
		        	else
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


//#######################################################################//
//########## End: Methods for supporting above operations ############//
//#####################################################################//


