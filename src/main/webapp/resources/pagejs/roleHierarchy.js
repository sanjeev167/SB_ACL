/*******************************************************************************
 * @Author: Sanjeev Kumar
 * @Date: 12/2/2019 *
 ******************************************************************************/


//###########################################################//
//########## Start of grid and CRUD related code ###########//
//#########################################################//

$(document).ready(function() {
	
	 // Select and loop the container element of the elements you want to equalise
    $('.container2Div').each(function(){      
      // Cache the highest
      var highestBox = 0;      
      // Select and loop the elements you want to equalise
      $('.item', this).each(function(){        
        // If this box is higher than the cached highest then store it
        if($(this).height() > highestBox) {
          highestBox = $(this).height(); 
        }      
      });              
      // Set the height of all those children to whichever was highest 
      //alert(highestBox);
      $('.item',this).height(highestBox);                 
    }); 
	
	
  
    
    
    
	// [0] Load grid while opening the page.
	loadGrid("");
	preparePage();
	  
	
		   
	// [1] Open an add modal.
	$(document).on("click",".addClass",function() {
		clearFormData();
		cleanAllMsg();		
		showAllRequiredLabels();		
		cleanAllHiddenInput();
		removeReadOnlyProp();	
		
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
		saveAndUpdateRecord();		
		refreshGridPage("");// Refresh with recently added record		
	});

	// [3] Open an edit modal.
	$(document).on("click",".eClass",function() {		
		clearFormData();
		cleanAllMsg();		
		showAllRequiredLabels();		
		cleanAllHiddenInput();
		removeReadOnlyProp();	
		
		$('#updateFormButtonId').attr("disabled", false);		
		getIdBasedRecord($(this).attr("href").split('=')[1]);

		$("#commonModalTitleId")
		.html('Update a record');
		$('.commonButtonActionClass').show();
		$('.commonButtonActionClass').attr('id',
		'updateFormButtonId');
		$("#updateFormButtonId").html('Update');
		$('#modal-common').modal('toggle');
	});

	// [4] Update a record.
	$(document).on("click", "#updateFormButtonId", function() {
		cleanAllMsg();		
		saveAndUpdateRecord();			
		refreshGridPage("");// Reload with recently edited record
	});

	// [5] Open a delete confirm modal.
	$(document).on("click",	".dClass",function() {		
		cleanAllMsg();
		hideAllRequiredLabels();
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
		refreshGridPage("");
		
	});

	// [7] Open a view modal.
	$(document).on("click", ".vClass", function() {			
		clearFormData();
		cleanAllMsg();		
		showAllRequiredLabels();		
		cleanAllHiddenInput();
		applyReadOnlyProp();	
		
		getIdBasedRecord($(this).attr("href").split('=')[1]);
		$("#commonModalTitleId").html('View a record');
		$('#modal-common').modal('toggle');
	});

	// [8] Open a selected delete confirm modal.
	$("#deleteSelected").click(function() {		
		cleanAllMsg();
		hideAllRequiredLabels();
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
		refreshGridPage("");
	});

	// [10] Open a search modal.
	$(document).on("click",".searchClass",function() {
		clearFormData();
		cleanAllMsg();		
		hideAllRequiredLabels();		
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
		var contextId = $('#contextId').val();		
		searchAndReloadGrid(contextId);
		refreshGridPage(contextId);
	});

	// [12] New one.
	$("#reloadGrid").click(function() {
		reloadGrid();
	});
	
	// [12] New one.
	$("#refreshGrid").click(function() {
		
		refreshGridPage("");
			
	});	

});// End of document ready


//Removing focus from the selected radio button
$('input[type="radio"]').keydown(function(e){
		    var arrowKeys = [37, 38, 39, 40];
		    if (arrowKeys.indexOf(e.which) !== -1)
		    { $(this).blur(); return false;}
});

//Change tree compact view to expanded view
$('#roleHirarchyCompactId').on('click',function(e){		
	  $("#roleHirarchyGridId").show();	 
	  $("#roleHirarchyWidthChangeId").removeClass("col-md-12");
	  $("#roleHirarchyWidthChangeId").addClass("col-md-7");
});

$('#roleHirarchyLargerId').on('click',function(e){		
	$("#roleHirarchyGridId").hide();	
    $("#roleHirarchyWidthChangeId").removeClass("col-md-7");
    $("#roleHirarchyWidthChangeId").addClass("col-md-12");
});



//Reload Grid.
function reloadGrid() {
	event.preventDefault();
	clearCheckBoxSelection();
	window.location.replace("/sec/roleHierarchy/");
	}

function refreshGridPage(contextId) {
	//alert("Refreshing page");
	
	clearCheckBoxSelection();	
	//t.draw();//Loads existing opened page only
	t.ajax.url("paginated?contextId=" + contextId).load();
	t.ajax.url("paginated?contextId=" + contextId).load();
	getRoleHierarchyLevelWiseStructure(); 
	getTreeParentNodeList();
}

function searchAndReloadGrid(contextId) {	
	clearCheckBoxSelection();
	var table = $('#roleHierarchyId').DataTable();	
	
	table.ajax.url(
			"paginated?contextId=" + contextId).load();
	cleanAllMsg();
	$("#successMsgId").html("<strong style='font:strong;color:green'><h4>Search completed. Check results in the grid.</h4></strong>");
		
	$("#showingAppContextLebelId").html($('#contextId option:selected').text());
		
}

//Get Selected Row IDs
function GetSelectedRowID() {
	var table = $('#roleHierarchyId').DataTable();
	var checkedRowIdAndName = [];
	var checkedRowIds = [];
	var checkedRowNames = [];
	var recordCount = 0;
	$(".chkIndvRow").each(function() {		
		if ($(this).is(':checked')) {
			// $(this).val() will return row index
			var selectedRows = table.rows($(this).val()).data();
			checkedRowNames.push(selectedRows[0].contextName +" => "					            				           
					             +selectedRows[0].parentName +" => "	
					             +selectedRows[0].childName +" => "	
					             +selectedRows[0].contents	
					             +"</br>");
			checkedRowIds.push(selectedRows[0].id);
			recordCount++;
		}
	});
	// Here You will get all selected persons ID	 
	if (recordCount > 0) {
		checkedRowIdAndName.push(checkedRowIds);
		checkedRowIdAndName.push(checkedRowNames);
		checkedRowIdAndName.push(recordCount);
	}
	return checkedRowIdAndName;
}

//## Code for loading grid ##
var t;
function loadGrid(contextId) {
	var toolbarContent='&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'
		  +'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'
		  +'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'
		  +'<span style="text-align=cener;"><a href="#"><i class="fa fa-plus addClass" data-toggle="modal"></i></a>'
		  +'&nbsp;&nbsp;&nbsp;&nbsp;<a href="#" id="deleteSelected" ><i data-toggle="modal" class="fa fa-trash" ></i></a>'	
		  +'&nbsp;&nbsp;&nbsp;&nbsp;<a href="#"><i class="fa fa-search searchClass" data-toggle="modal"></i></a>'	
		  +'&nbsp;&nbsp;&nbsp;&nbsp;<a href="#"id="reloadGrid" ><i class="fa fa-refresh" data-toggle="modal"></i></a>'	
		  +'&nbsp;&nbsp;&nbsp;&nbsp;<a href="#" id="refreshGrid"><i class="fa fa-refresh" data-toggle="modal"></i></a></span>'	
;

	t = $('#roleHierarchyId').DataTable(
			{					
				
				"retrieve" : true,// used for refreshing				
				//"bAutoWidth" : true,
				//"scrollY" : '41vh',
				//"scrollX" : '110vh',
				//"scrollCollapse" : true,
				"lengthMenu" : [ 5, 10, 15],
				"processing" : true,
				"serverSide" : true,
				"ordering" : true,
				"language": { "search": "<i class='fa fa-search'></i>&nbsp;" },
				"searching" : true,
				"aaSorting" : [[ 2, "asc" ] ],
				"ajax" : {
					async: false,
					"url" : "paginated?contextId=" + contextId,
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
						"searchable" : false,
						"orderable" : false,
						"data" : null,						
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
						"bVisible" : false, // used for hiding a column
					},
							
					
					{
						"data" : "parentName",
						"name" : "parentName",
						"title" : "Parent",
					},
					
					{	
						"data" : "childName",
						"name" : "childName",
						"title" : "Child"
					},								
					
					{   
						"data" : "contents",
						"name" : "contents",
						"title" : "Contents",
						"bVisible" : false // used for hiding a column
					},
					
					{
						"searchable" : false,
						"orderable" : false,
						"data" : null,						
						"render" : function(data, type, row) {
							return '<a class="eClass" href=?record_id='
							+ row.id + '>'
							+ '<i  class="fa fa-edit"></i>'
							+ '</a>';
						}
					},
					{
						"searchable" : false,
						"orderable" : false,
						"data" : null,						
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

var baseUrl = '/sec/roleHierarchy';

function clearFormData() {
	//alert("Clearing form data");
	$("#addEditFormId").trigger("reset");	
	
	//Select -select-
	$('#contextId').val('');	
	$('#parentId').val('');	
	
	$('#childNameId').val("");
	$('#roleNameId').val('');
	$('#contentsId').val('');
  }
//This will be called for cleaning the error message already shown on the page.
function cleanAllMsg() {
	
	$("#successMsgId").html("");
	$("#searchMsgId").html("");

	$('#deleteSuccessMsgId').html("");
	$('#deleteSelectedSuccessMsgId').html("");	
	
	$('#contextId_err').html("");	
	$('#parentId_err').html("");	
	$('#childName_err').html("");
	$('#roleNameId_err').html("");
	
	$('#contents_err').html("");
	
}

function showAllRequiredLabels() {	
	$("#searchMsgId").show();
	$('.commonButtonActionClass').show();
	$('#roleHierarchyDetailsLabelId').show();
	$('#roleBindingInHierarchyTreeLabelId').show();
	$('#parentIdLabelId').show();
	$('#childNameLabledId').show();	
	$('#roleNameIdLabledId').show();	
	
	$('#contentsLabledId').show();
}

function hideAllRequiredLabels() {	
	$("#searchMsgId").hide();
	$('.commonButtonActionClass').hide();
	$('#roleHierarchyDetailsLabelId').hide();
	$('#roleBindingInHierarchyTreeLabelId').hide();
	$('#parentIdLabelId').hide();
	$('#childNameLabledId').hide();
	$('#roleNameIdLabledId').hide();
	
	$('#contentsLabledId').hide();
	
}

function cleanAllHiddenInput() {
	$('#roleHierarchyRecordId').val("");
	$('#recordIdForDelete').val("");

}

//Apply read only on all the fields of the form so that thevalue of the field
//can not be changed.
function applyReadOnlyProp() {	

	$('#contextId').attr('disabled', true);	
	$('#parentId').attr('disabled', true);
	$('#childName').attr('disabled', true);	
	$('#roleNameId').attr('disabled', true);	
	
	$('#contentsId').attr("disabled", true);
}

//Remove read only from all the fields of the form so that they could be
//changed.
function removeReadOnlyProp() {	

	$('#contextId').attr('disabled', false);	
	$('#parentId').attr("disabled", false);
	$('#childName').attr("disabled", false);
	$('#roleNameId').attr("disabled", false);
	
	$('#contentsId').attr("disabled", false);
}

//If the form requires anything pre-loaded. it can be done here.
function preparePage() {	
   loadDepartmentCombo("");   
   getTreeParentNodeList();
   getRoleList(); 
   getRoleHierarchyLevelWiseStructure();  
}

//This will be called with an ajax response object and it will be used for
//loading the page with the response.
function loadRecordInForm(response) {	
	$('#roleHierarchyRecordId').val(response.id);		
	$('#contextId').val(response.contextId);	
	$('#parentId').val(response.parentId);	
	$('#childNameId').val(response.childName);
	$('#roleNameId').val(response.roleNameId);
	
	$('#contentsId').val(response.contents);	
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

	$('#contextId_err').html(fieldErrMsgMap.contextId);		
	$('#parentId_err').html(fieldErrMsgMap.parentId);	
	$('#childName_err').html(fieldErrMsgMap.childName);
	$('#roleNameId_err').html(fieldErrMsgMap.roleNameId);
	
	$('#contents_err').html(fieldErrMsgMap.contents);
}

function saveAndUpdateRecord() { 	

	/* stop form from submitting normally */
	event.preventDefault();
	/* get the form data of the from */	
	var id = $('#roleHierarchyRecordId').val();	
	var contextId = $('#contextId').val();		
	var parentId = $('#parentId').val();
	
	var parentName=$('#parentId option:selected').text();	
	
	var childName = $('#childNameId').val();
	var roleNameId = $('#roleNameId').val();
	
	var contents=$('#contentsId').val();
	var headNodeCount= document.getElementById("parentId").options.length;		
	var json = {
			"id" : id,
			"contextId" : contextId,				
			"parentId":parentId,
			"parentName":parentName,
			"childName":childName,
			"roleNameId":roleNameId,
			"contents" :contents,
			"headNodeCount":headNodeCount,			
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
			//alert("Response is coming");
			$('#successMsgId').html("<span style='color:green;font:bold;'>"+response.statusMsg+"</span>");
			if(response.status){
				//Do something if required	
				return true;
				//$('#saveFormButtonId').attr("disabled", true);
			}else{
				//alert("Form has a validation error");
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
				return true;
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
				return true;
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
	url = "/sec/roleHierarchy/department" + "/list";	
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
				var ele = document.getElementById('contextId');	       
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

function getTreeParentNodeList(){
	
	/* stop form from submitting normally */	
	method = 'GET';
	url = "/sec/roleHierarchy/" + "getTreeParentNodeList";	
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
				//alert("Response is coming");
				$('#parentId').empty().append('<option  value="">-Select-</option>');				
				var ele = document.getElementById('parentId');				
				for (var i = 0; i < response.comboList.length; i++) {						
					ele.innerHTML = ele.innerHTML +
		                '<option  value="' + response.comboList[i]['id'] + '">' + response.comboList[i]['name'] + '</option>';
					
				}		
				//Do something if required			
			}else{
				showBusinessEerror(response.fieldErrMsgMap);
			}
		},
		error : function(jqXHR, exception) {alert("Server showing error")	;		
			formatErrorMessage(jqXHR, exception);
		}
	});
	
}

function getRoleList(){
	
	/* stop form from submitting normally */	
	method = 'GET';
	url = "/sec/roleHierarchy/" + "getRoleList";	
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
				//alert("Response is coming");
				$('#roleNameId').empty().append('<option  value="">-Select-</option>');				
				var ele_role = document.getElementById('roleNameId');
				
				for (var i = 0; i < response.comboList.length; i++) {
					ele_role.innerHTML = ele_role.innerHTML +
		                '<option  value="' + response.comboList[i]['id'] + '">' + response.comboList[i]['name'] + '</option>';
					}	
				
				//Do something if required			
			}else{
				showBusinessEerror(response.fieldErrMsgMap);
			}
		},
		error : function(jqXHR, exception) {alert("Server showing error")	;		
			formatErrorMessage(jqXHR, exception);
		}
	});
	
}

//Fetch a e=record based on id
function getRoleHierarchyLevelWiseStructure(
) {	
	/* stop form from submitting normally */	
	method = 'GET';
	url = baseUrl + "/getRoleHierarchyLevelWiseStructure" ;//+ "?id=" + id;
	// alert("posting url = "+url);
	$.ajax({
		type : method,
		//async:false,		
		url : url,		
		beforeSend : function(xhr) {
			xhr.setRequestHeader("Accept", "application/json");
			xhr.setRequestHeader("Content-Type", "application/json");
		},
		success : function(response) {
			// alert("Response is coming");
			$('#successMsgId').html("<span style='color:green;font:bold;'>"+response.statusMsg+"</span>");
			if(response.status){
				loadTreeStructure(response.formObject);						
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

function loadTreeStructure(treeMenuJsonData){
	var datasourceJson=treeMenuJsonData;
	//alert("CC"+treeMenuJsonData);
	$("#roleHierarchyTree").html("");//Clean the previous tree structure
	var jsonStructureObject =treeMenuJsonData;
	$("#roleHierarchyTree").jHTree({
	callType : 'obj',
	structureObj : datasourceJson
      });	
	
}

//#######################################################################//
//########## End: Methods for supporting above operations ############//
//#####################################################################//


