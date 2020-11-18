/*******************************************************************************
 * @Author: Sanjeev Kumar
 * @Date: 12/2/2019 *
 ******************************************************************************/


//###########################################################//
//########## Start of grid and CRUD related code ###########//
//#########################################################//

$(document).ready(function() {
	// Select and loop the container element of the elements you want to equalize
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
      $('.item',this).height(highestBox);                 
    }); 
	   
    
	// [0] Load grid while opening the page.
	loadGrid("", "");
	preparePage();
	
	// [1] Open an add modal.
	$(document).on("click",".addClass",function() {
		clearFormData();
		cleanAllMsg();
		loadTreeParentNodeCombo("");
		showAllRequiredLabels();
		$('#moduleLabelId').hide();
		$('#leafUrlLabelId').hide();
		cleanAllHiddenInput();
		removeReadOnlyProp();	
		//
		 var $radios = $('input:radio[name=nodeType]');
		   if($radios.is(':checked') === true) {
		       $radios.filter('[value='+"N"+']').prop('checked', true);
		   }
		//
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
		refreshGridPage("","");// Refresh with recently added record		
	});

	// [3] Open an edit modal.
	$(document).on("click",".eClass",function() {
		
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
		refreshGridPage("","");// Reload with recently edited record
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
		refreshGridPage("","");
		
	});

	// [7] Open a view modal.
	$(document).on("click", ".vClass", function() {			
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
		refreshGridPage("","");
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
		var departmentMasterId = $('#departmentMasterId').val();
		var treeMenuTypeId = $('#treeMenuTypeId').val();
		searchAndReloadGrid(departmentMasterId,treeMenuTypeId);
		refreshGridPage(departmentMasterId,treeMenuTypeId);
	});

	// [12] New one.
	$("#reloadGrid").click(function() {
		reloadGrid();
	});
	
	// [12] New one.
	$("#refreshGrid").click(function() {
		refreshGridPage("","");
			
	});	

});// End of document ready


//Change tree orientation from horizontal to vertical and vice-versa

$('.treeOrientationVtoH').on('click',function(e){	
	$("#vOrientationId").hide();
	$("#hOrientationId").show();
	var $radios = $('input:radio[name=orientationH]');
    if($radios.is(':checked') === true) {
        $radios.filter('[value='+"H"+']').prop('checked', true);
    }
    $("#changeGridWidthId").removeClass("col-md-8");
    $("#changeGridWidthId").addClass("col-md-12");
});

$('.treeOrientationHtoV').on('click',function(e){	
	$("#vOrientationId").show();
	$("#hOrientationId").hide();
	var $radios = $('input:radio[name=orientationV]');
    if($radios.is(':checked') === true) {
        $radios.filter('[value='+"V"+']').prop('checked', true);
    }
    $("#changeGridWidthId").removeClass("col-md-12");
    $("#changeGridWidthId").addClass("col-md-8");
});

$('#departmentMasterId').on('change', function(e) {
	//cleanAllMsg();
	//alert("Going to load State combo");
	//This will clean the state combo of state and show only select in the combo box
	$('#moduleId').empty().append('<option  value="">-Select-</option>');	
	$('#pageMasterId').empty().append('<option  value="">-Select-</option>');	
	loadModuleCombo($('#departmentMasterId').val());
});

$('#moduleId').on('change', function(e) {
	//cleanAllMsg();	
	$('#pageMasterId').empty().append('<option  value="">-Select-</option>');	
	loadPageCombo($('#moduleId').val());
});


$('#nodeTypeId').on('change', function(e) {
	//alert("Node checked");
	$('#moduleLabelId').hide();
	$('#leafUrlLabelId').hide();
});

$('#leafTypeId').on('change', function(e) {
	//alert("Leaf checked");
	var parentNodeCount= document.getElementById("menuManagerParentId").options.length;	
	if(parentNodeCount > 2){		
		$('#moduleLabelId').show();
		$('#leafUrlLabelId').show();
	}else{		
		$("#nodeTypeId").prop("checked", true);
		//$('#modal-common').modal('toggle');	
		$('#modal-alert').modal('toggle');		
		return false;
	}	
});



//Reload Grid.
function reloadGrid() {
	event.preventDefault();
	clearCheckBoxSelection();
	window.location.replace("/sec/env/manage_menu/");
	}

function refreshGridPage(departmentMasterId,treeMenuTypeId) {
	//alert("Refreshing page");
	clearCheckBoxSelection();	
	//t.draw();//Loading existing opened page only	
	getSpecificTreeTypeStructure(1);
	loadTreeParentNodeCombo("");	
	t.ajax.url(
			"paginated?departmentMasterId=" + departmentMasterId + "&treeMenuTypeId=" +treeMenuTypeId).load();
	getSpecificTreeTypeStructure(1);//Let it be called twice for loading tree 100%
}


function searchAndReloadGrid(departmentMasterId,treeMenuTypeId) {	
	clearCheckBoxSelection();
	var table = $('#manageMenuId').DataTable();	
	
	table.ajax.url(
			"paginated?departmentMasterId=" + departmentMasterId + "&treeMenuTypeId=" +treeMenuTypeId).load();
	cleanAllMsg();
	$("#successMsgId").html("<strong style='font:strong;color:green'><h4>Search completed. Check results in the grid.</h4></strong>");
		
	$("#showingAppContextLebelId").html($('#departmentMasterId option:selected').text());
	$("#showingTreeTypeLebelId").html($('#treeMenuTypeId option:selected').text());
	
	
	
}

//Get Selected Row IDs
function GetSelectedRowID() {
	var table = $('#manageMenuId').DataTable();
	var checkedRowIdAndName = [];
	var checkedRowIds = [];
	var checkedRowNames = [];
	var recordCount = 0;
	$(".chkIndvRow").each(function() {
		
		if ($(this).is(':checked')) {
			// $(this).val() will return row index
			var selectedRows = table.rows($(this).val()).data();
			checkedRowNames.push(selectedRows[0].departmentMasterName +" => "
					             +selectedRows[0].treeMenuTypeName +" => "
					             +selectedRows[0].nodeType +" => "					           
					             +selectedRows[0].menuManagerParentNodeName +" => "	
					             +selectedRows[0].nodeName 
					             +"</br>");
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
function loadGrid(departmentMasterId,treeMenuTypeId) {

	t = $('#manageMenuId').DataTable(
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
				"aaSorting" : [[ 2, "asc" ], [ 3, "asc" ], [ 4, "asc" ], [ 5, "asc" ] ],//Will sort in ascending order of this column
				"ajax" : {
					async: false,
					"url" : "paginated?departmentMasterId=" + departmentMasterId + "&treeMenuTypeId=" + treeMenuTypeId 
					,

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
						"searchable" : false,
						"data" : "id",
						"name" : "ID",
						"title" : "ID",						
						"bVisible" : true, // used for hiding a column
					},
					
					{   
						
						"data" : "departmentMasterName",
						"name" : "departmentMasterName",
						"title" : "Context",
						"bVisible" : false, // used for hiding a column
					},
					{
						
						"data" : "treeMenuTypeName",
						"name" : "treeMenuTypeName",
						"title" : "Type",
						"bVisible" : false, // used for hiding a column
					},
					
					{
						
						"data" : "menuManagerParentNodeName",
						"name" : "menuManagerParentNodeName",
						"title" : "Node[P]"
					},
					
					{
						
						"data" : "nodeName",
						"name" : "nodeName",
						"title" : "Child[N/L]"
					},
{
						
						"data" : "nodeType",
						"name" : "nodeType",
						"title" : "Type"
					},
					{
						"data" : "pageBaseUrl",
						"name" : "pageBaseUrl",
						"title" : "Page",
						"sortable" : false,
					},
					{
						"data" : "imgUrl",
						"name" : "imgUrl",						
						"title" : "Img",
						"sortable" : false
						
					},				
					

					{
						"data" : null,
						"sortable" : false,
						"bVisible" : true, // used for hiding a column
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

var baseUrl = '/sec/env/manage_menu';

function clearFormData() {
	//alert("Clearing form data");
	$("#addEditFormId").trigger("reset");		
	//Select -select-
	$('#departmentMasterId').val('');
	$('#treeMenuTypeId').val('');
	$('#menuManagerParentId').val('');	
	$('#imgUrlId').val('');	
	
	//Will make module and page combo clean
	$('#nodeNameId').val("");
	$('#moduleNameId').empty()
    .append('<option selected="selected" value="">-Select-</option>');
	$('#pageNameId').empty()
    .append('<option selected="selected" value="">-Select-</option>');
}
//This will be called for cleaning the error message already shown on the page.
function cleanAllMsg() {
	
	$("#successMsgId").html("");
	$("#searchMsgId").html("");
	$('#deleteSuccessMsgId').html("");
	$('#deleteSelectedSuccessMsgId').html("");
	
	$('#departmentMasterId_err').html("");
	$('#treeMenuTypeId_err').html("");
	$('#nodeTypeId_err').html("");
	$('#leafTypeId_err').html("");
	$('#menuManagerParentId_err').html("");	
	$('#nodeName_err').html("");
	$('#imgUrl_err').html("");
	$('#moduleId_err').html("");
	$('#pageMasterId_err').html("");		
}

function showAllRequiredLabels() {	
	$("#searchMsgId").show();
	$('.commonButtonActionClass').show();
	
	$('#nodeTypeLabelId').show();
	$('#menuManagerParentLabelId').show();
	$('#nodeNameLabledId').show();	
	$('#nodeNameLabledId').show();	
	$('#imgUrlLabledId').show();	
	$('#moduleLabelId').show();	
	$('#leafUrlLabelId').show();
}

function hideAllRequiredLabels() {	
	$("#searchMsgId").hide();
	$('.commonButtonActionClass').hide();
	
	$('#nodeTypeLabelId').hide();
	$('#menuManagerParentLabelId').hide();
	$('#nodeNameLabledId').hide();	
	$('#nodeNameLabledId').hide();	
	$('#imgUrlLabledId').hide();	
	$('#moduleLabelId').hide();	
	$('#leafUrlLabelId').hide();
}

function cleanAllHiddenInput() {
	$('#manageMenuRecordId').val("");
	$('#recordIdForDelete').val("");
}

//Apply read only on all the fields of the form so that thevalue of the field
//can not be changed.
function applyReadOnlyProp() {	

	$('#departmentMasterId').attr('disabled', true);
	$('#treeMenuTypeId').attr('disabled', true);
	$('#nodeTypeId').attr("disabled", true);
	$('#nodeTypeId').attr("disabled", true);
	$('#leafTypeId').attr("disabled", true);
	$('#menuManagerParentId').attr('disabled', true);
	$('#nodeNameId').attr('disabled', true);
	$('#imgUrlId').attr('disabled', true);
	$('#moduleId').attr('disabled', true);
	$('#pageMasterId').attr('disabled', true);
	
	
}

//Remove read only from all the fields of the form so that they could be
//changed.
function removeReadOnlyProp() {	
	$('#departmentMasterId').attr('disabled', false);
	$('#treeMenuTypeId').attr('disabled', false);
	$('#nodeTypeId').attr("disabled", false);
	$('#nodeTypeId').attr("disabled", false);
	$('#leafTypeId').attr("disabled", false);
	$('#menuManagerParentId').attr("disabled", false);
	$('#nodeNameId').attr("disabled", false);
	$('#imgUrlId').attr("disabled", false);
	$('#moduleId').attr("disabled", false);
	$('#pageMasterId').attr("disabled", false);
	
}

//If the form requires anything pre-loaded. it can be done here.
function preparePage() {
   loadDepartmentCombo("");  
   loadTreeMenuTypeCombo();
   loadTreeParentNodeCombo();  
   getSpecificTreeTypeStructure(1);
  
}

//This will be called with an ajax response object and it will be used for
//loading the page with the response.
function loadRecordInForm(response) {	
	$('#manageMenuRecordId').val(response.id);		
	$('#departmentMasterId').val(response.departmentMasterId);		
	$('#treeMenuTypeId').val(response.treeMenuTypeId);	
	//Load radio button
	var $radios = $('input:radio[name=nodeType]');
    if($radios.is(':checked') === true) {
        $radios.filter('[value='+response.nodeType+']').prop('checked', true);
    }	
	
	$('#menuManagerParentId').val(response.menuManagerParentId);	
	//Load node or leaf	
	$('#nodeNameId').val(response.nodeName);	
	//Load image
	$('#imgUrlId').val(response.imgUrl);	
	
	//This will clean the state combo of state and show without select in the combo box	
	if(response.nodeType==="L")
	$('#moduleId').empty()
    .append('<option selected="selected" value="">-Select-</option>');
	loadModuleCombo(response.departmentMasterId,response.moduleId);	
	
	$('#pageMasterId').empty()
    .append('<option selected="selected" value="">-Select-</option>');	
	if(response.nodeType==="L")
	loadPageCombo(response.moduleId,response.pageMasterId);	
	
	if(response.nodeType==="N"){ //Hide the rest when the node is called
		$('#moduleLabelId').hide();	
		$('#leafUrlLabelId').hide();
	}
		
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
	$('#departmentMasterId_err').html(fieldErrMsgMap.departmentMasterId);
	$('#treeMenuTypeId_err').html(fieldErrMsgMap.treeMenuTypeId);	
	$('#menuManagerParentId_err').html(fieldErrMsgMap.menuManagerParentId);	
	$('#nodeName_err').html(fieldErrMsgMap.nodeName);
	$('#imgUrl_err').html(fieldErrMsgMap.imgUrl);
	$('#moduleId_err').html(fieldErrMsgMap.moduleId);
	$('#pageMasterId_err').html(fieldErrMsgMap.pageMasterId);	
}

function saveAndUpdateRecord() { 	
	/* stop form from submitting normally */
	event.preventDefault();
	/* get the form data of the from */	
	var id = $('#manageMenuRecordId').val();	
	var departmentMasterId = $('#departmentMasterId').val();
	var treeMenuTypeId = $('#treeMenuTypeId').val();
	var nodeType = $("input[name='nodeType']:checked"). val();	
	var menuManagerParentId = $('#menuManagerParentId').val();
	var menuManagerParentNodeName=$('#menuManagerParentId option:selected').text();	
	var nodeName = $('#nodeNameId').val();
	var imgUrl = $('#imgUrlId').val();	
	var moduleId = $('#moduleId').val();	
	var pageMasterId = $('#pageMasterId').val();	
	var parentNodeCount= document.getElementById("menuManagerParentId").options.length;		
	var json = {
			"id" : id,
			"departmentMasterId" : departmentMasterId,
			"treeMenuTypeId" : treeMenuTypeId,			
			"nodeType" : nodeType,		
			"menuManagerParentId":menuManagerParentId,			
			"nodeName":nodeName,
			"imgUrl":imgUrl,
			"moduleId":moduleId,
			"pageMasterId":pageMasterId,
			"parentNodeCount":parentNodeCount,
			"menuManagerParentNodeName":menuManagerParentNodeName
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
		error : function() {
			;
		}
	});
}

function loadDepartmentCombo(idForSelected){	
	/* stop form from submitting normally */	
	method = 'GET';
	url = "/sec/env/manage_menu/department" + "/list";	
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
				var ele = document.getElementById('departmentMasterId');	       
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

function loadModuleCombo(id,idForSelected){	
	
	/* stop form from submitting normally */	
	method = 'GET';
	url = "/sec/env/manage_menu/module" + "/list?id=" + id;		
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
						
				var ele = document.getElementById('moduleId');	       
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

function loadPageCombo(id,idForSelected){	
	
	/* stop form from submitting normally */	
	method = 'GET';
	url = "/sec/env/manage_menu/page" + "/listBaseUrl?id=" + id;		
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
						
				var ele = document.getElementById('pageMasterId');	       
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

function loadTreeMenuTypeCombo(selectedId){	
	
	/* stop form from submitting normally */	
	method = 'GET';
	url = "/sec/env/manage_menu/" + "listTreeMenuType";	
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
				var ele = document.getElementById('treeMenuTypeId');
				for (var i = 0; i < response.comboList.length; i++) {
					if(response.comboList[i]['id']===selectedId)
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

function loadTreeParentNodeCombo(selectedId){
	
	/* stop form from submitting normally */	
	method = 'GET';
	url = "/sec/env/manage_menu/" + "listTreeParentNode";	
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
				$('#menuManagerParentId').empty().append('<option  value="">-Select-</option>');
				var ele = document.getElementById('menuManagerParentId');
				for (var i = 0; i < response.comboList.length; i++) {	
					if(response.comboList[i]['id']===selectedId)
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


//Fetch a e=record based on id
function getSpecificTreeTypeStructure(id) {
	
	/* stop form from submitting normally */
	
	method = 'GET';
	url = baseUrl + "/getSpecificTreeTypeStructure" + "?id=" + id;
	// alert("posting url = "+url);
	$.ajax({
		type : method,
		async:false,		
		url : url,		
		beforeSend : function(xhr) {
			xhr.setRequestHeader("Accept", "application/json");
			xhr.setRequestHeader("Content-Type", "application/json");
		},
		success : function(response) {
			// alert("Response is coming");
			$('#successMsgId').html("<span style='color:green;font:bold;'>"+response.statusMsg+"</span>");
			if(response.status){
				loadTreeStructure(response.formObject,response.recordId);							
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

function loadTreeStructure(treeMenuJsonData,minParentId){	
	var data =treeMenuJsonData;
	
	var dataDummy = [
        //Home
         {"id": "0", "name": "Home", "parent_id": "-1","leafUrl":"#","nodeType":"N" ,"nodeImgUrl":"fa fa-home"},
       
        //Dashboard
        {"id": "1", "name": "Dashboard", "parent_id": "0","leafUrl":"#","nodeType":"N" ,"nodeImgUrl":"fa fa-dashboard"},
        {"id": "2", "name": "Dashboard v1", "parent_id": "1","leafUrl":"<%=request.getContextPath() %>/adm/pvt/db","nodeType":"L" ,"nodeImgUrl":"fa fa-files-o"},
        
        {"id": "3", "name": "Profile", "parent_id": "0","leafUrl":"<%=request.getContextPath() %>/adm/pvt/profile","nodeType":"L" ,"nodeImgUrl":"fa fa-user"},
        
        //API Manager
        {"id": "4", "name": "API Manager", "parent_id": "0","leafUrl":"#","nodeType":"N" ,"nodeImgUrl":"fa fa-user-secret"},
        {"id": "5", "name": "Manage Client", "parent_id": "4","leafUrl":"<%=request.getContextPath() %>/adm/pvt/api_mgr/manage_client/","nodeType":"L" ,"nodeImgUrl":"fa fa-files-o"},
        {"id": "6", "name": "Manage API", "parent_id": "4","leafUrl":"<%=request.getContextPath() %>/adm/pvt/api_mgr/manage_api/","nodeType":"L" ,"nodeImgUrl":"fa fa-files-o"},
        {"id": "7", "name": "Assign API Permission", "parent_id": "4","leafUrl":"<%=request.getContextPath() %>/adm/pvt/api_mgr/api_permission/","nodeType":"L" ,"nodeImgUrl":"fa fa-files-o"},
        {"id": "8", "name": "Test API", "parent_id": "4","leafUrl":"<%=request.getContextPath() %>/adm/pvt/api_mgr/test_api/","nodeType":"L" ,"nodeImgUrl":"fa fa-files-o"},
        
        //Menu Manager
        {"id": "9", "name": "Menu Manager", "parent_id": "0","leafUrl":"#","nodeType":"N" ,"nodeImgUrl":"fa fa-tree"},
        {"id": "10", "name": "Manage Menu", "parent_id": "9","leafUrl":"<%=request.getContextPath() %>/sec/env/manage_menu/","nodeType":"L" ,"nodeImgUrl":"fa fa-files-o"},
        
        //Masters
        {"id": "11", "name": "Master", "parent_id": "0","leafUrl":"#","nodeType":"N" ,"nodeImgUrl":"fa fa-table"},
        {"id": "12", "name": "Country Master", "parent_id": "11","leafUrl":"<%=request.getContextPath() %>/adm/pvt/masters/country/","nodeType":"L" ,"nodeImgUrl":"fa fa-files-o"},
        {"id": "13", "name": "State Master", "parent_id": "11","leafUrl":"<%=request.getContextPath() %>/adm/pvt/masters/state/","nodeType":"L" ,"nodeImgUrl":"fa fa-files-o"},
        {"id": "14", "name": "City Master", "parent_id": "11","leafUrl":"<%=request.getContextPath() %>/adm/pvt/masters/city/","nodeType":"L" ,"nodeImgUrl":"fa fa-files-o"},
        {"id": "15", "name": "Name Value Master", "parent_id": "11","leafUrl":"<%=request.getContextPath() %>/adm/pvt/masters/city/","nodeType":"L" ,"nodeImgUrl":"fa fa-files-o"},
        
        //Security
        {"id": "16", "name": "Security", "parent_id": "0","leafUrl":"#","nodeType":"N" ,"nodeImgUrl":"fa fa-wrench"},
        
        //Environment
        {"id": "18", "name": "Environment", "parent_id": "16","leafUrl":"#","nodeType":"N" ,"nodeImgUrl":"fa fa-gear"},
        {"id": "19", "name": "Create App Context", "parent_id": "18","leafUrl":"<%=request.getContextPath() %>/adm/pvt/sec/env/department/","nodeType":"L" ,"nodeImgUrl":"fa fa-files-o"},
        {"id": "20", "name": "Create Module", "parent_id": "18","leafUrl":"<%=request.getContextPath() %>/adm/pvt/sec/env/module/","nodeType":"L" ,"nodeImgUrl":"fa fa-files-o"},
        {"id": "21", "name": "Record Page", "parent_id": "18","leafUrl":"<%=request.getContextPath() %>/adm/pvt/sec/env/page/","nodeType":"L" ,"nodeImgUrl":"fa fa-files-o"},
        {"id": "22", "name": "Record Page Operation", "parent_id": "18","leafUrl":"<%=request.getContextPath() %>/adm/pvt/sec/env/op/","nodeType":"L" ,"nodeImgUrl":"fa fa-files-o"},
       
        //RBAC
        {"id": "23", "name": "RBAC", "parent_id": "16","leafUrl":"#","nodeType":"N" ,"nodeImgUrl":"fa fa-gear"},
        {"id": "24", "name": "Manage Role", "parent_id": "23","leafUrl":"<%=request.getContextPath() %>/adm/pvt/sec/rbac/role/","nodeType":"L" ,"nodeImgUrl":"fa fa-files-o"},
        {"id": "25", "name": "Manage Group", "parent_id": "23","leafUrl":"<%=request.getContextPath() %>/adm/pvt/sec/rbac/group/","nodeType":"L" ,"nodeImgUrl":"fa fa-files-o"},
        {"id": "26", "name": "Assign role to group", "parent_id": "23","leafUrl":"<%=request.getContextPath() %>/adm/pvt/sec/rbac/roleToGroup/","nodeType":"L" ,"nodeImgUrl":"fa fa-files-o"},
        {"id": "27", "name": "User Category", "parent_id": "23","leafUrl":"<%=request.getContextPath() %>/adm/pvt/sec/rbac/userCategory/","nodeType":"L" ,"nodeImgUrl":"fa fa-files-o"},
        {"id": "28", "name": "Internal User", "parent_id": "23","leafUrl":"<%=request.getContextPath() %>/adm/pvt/sec/rbac/appAdminUser/","nodeType":"L" ,"nodeImgUrl":"fa fa-files-o"},
        {"id": "29", "name": "Web Users", "parent_id": "23","leafUrl":"<%=request.getContextPath() %>/adm/pvt/sec/rbac/webUser/","nodeType":"L" ,"nodeImgUrl":"fa fa-files-o"},
        {"id": "30", "name": "Assign group to users", "parent_id": "23","leafUrl":"<%=request.getContextPath() %>/adm/pvt/sec/rbac/groupToUser/","nodeType":"L" ,"nodeImgUrl":"fa fa-files-o"},
        {"id": "31", "name": "Assign RBAC", "parent_id": "23","leafUrl":"<%=request.getContextPath() %>/adm/pvt/sec/rbac/monitor/","nodeType":"L" ,"nodeImgUrl":"fa fa-files-o"},	       
        
        
        //ACl
        {"id": "32", "name": "ACL", "parent_id": "16","leafUrl":"#","nodeType":"N" ,"nodeImgUrl":"fa fa-gear"},
        {"id": "33", "name": "Domain Master", "parent_id": "32","leafUrl":"<%=request.getContextPath() %>/adm/pvt/sec/acl/dmnMaster/","nodeType":"L" ,"nodeImgUrl":"fa fa-files-o"},
        {"id": "34", "name": "Domain Action Master", "parent_id": "32","leafUrl":"<%=request.getContextPath() %>/adm/pvt/sec/acl/dmnActionMaster/","nodeType":"L" ,"nodeImgUrl":"fa fa-files-o"},
        {"id": "35", "name": "Assign Permission", "parent_id": "32","leafUrl":"<%=request.getContextPath() %>/adm/pvt/sec/acl/aclPermissions/","nodeType":"L" ,"nodeImgUrl":"fa fa-files-o"},
        {"id": "36", "name": "View ACL Tables", "parent_id": "32","leafUrl":"<%=request.getContextPath() %>/adm/pvt/sec/acl/monitor/aclInOne/","nodeType":"L" ,"nodeImgUrl":"fa fa-files-o"},
       
        //ABAC
        {"id": "37", "name": "ABAC", "parent_id": "16","leafUrl":"#","nodeType":"N" ,"nodeImgUrl":"fa fa-gear"},
        {"id": "38", "name": "Dummy", "parent_id": "37","leafUrl":"#","nodeType":"L" ,"nodeImgUrl":"fa fa-files-o"},
	        
        ];

var endMenu =getMenu((minParentId*1+0)+"");
function getMenu( parentID ){
       return data.filter(function(node){ return ( node.parent_id === parentID ) ; }).map(function(node){
           var exists = data.some(function(childNode){  return childNode.parent_id === node.id; });	                
          // style="display:block; will be used for showing menu opened
           var subMenu = (exists) ? '<ul class="treeview-menu"">'+ getMenu(node.id) + '</ul>' : "";
           var menu;                
           if(exists)	     
        	   //menu-open class can be used for showing menu opened
        	   menu= '<li class="treeview " >'
                          +'<a href="'+node.leafUrl+'"> <i class="'+node.nodeImgUrl+'"></i>'
                          + '<span>' +node.name  +'</span>'
                          + '<span class="pull-right-container">' 
                          +'<i class="fa fa-angle-left pull-right"></i>'
                          +'</span>'
                          +'</a>'
                          + subMenu
                          + '</li>';
          else		        	
        	  menu= '<li >'
              +'<a href="'+node.leafUrl+'"> <i class="'+node.nodeImgUrl+'"></i>'
              + '<span>' +node.name  +'</span>'                      
              +'</a>'
           + '</li>' ;
         
           return menu;
                    	   
       });
   }	
//Comma after each li has been inserted. It needs to be removed. g is used for global removal
var finalMenu=endMenu.join('').replace(/,/g, "");
	$('#specificTreeMenuId').html('<ul class="sidebar-menu" data-widget="tree" >'+finalMenu+ '</ul>');	
}

//#######################################################################//
//########## End: Methods for supporting above operations ############//
//#####################################################################//


