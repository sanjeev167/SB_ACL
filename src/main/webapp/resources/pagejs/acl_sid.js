/*******************************************************************************
 * @Author: Sanjeev Kumar
 * @Date: 12/2/2019 *
 ******************************************************************************/


//###########################################################//
//########## Start of grid and CRUD related code ###########//
//#########################################################//

$(document).ready(function() {

	// [0] Load grid while opening the page.
	loadGridForAclSid("");


	// [1] Open a search modal.
	$(document).on("click",".searchClass",function() {		
		preparePage();

		$("#searchMsgId").html(
		'<span style="color:green"><h4>Search with any combination.</h4><\span>');
		$("#searchMsgId").show();
		$("#commonModalTitleId").html('Search records');
		$('.commonButtonActionClass').show();
		$('.commonButtonActionClass').attr('id', 'searchFormButtonId');
		$("#searchFormButtonId").html('Search');
		$('#modal-common').modal('toggle');
	});

	// [2] Search record
	$(document).on("click", "#searchFormButtonId", function(e) {
		var sid = $('#sidId').val();
		
		searchAndReloadGridForAclSid(sid);
	});

	// [12] New one.
	$("#reloadGridForAclSid").click(function() {
		reloadGridForAclSid();
	});
	
	// [12] New one.
	$("#refreshGrid").click(function() {
		refreshGridPage();
	});
	
	

});// End of document ready


//Reload Grid.
function reloadGridForAclSid() {
	//event.preventDefault();
	clearCheckBoxSelection();

	window.location.replace("/acl/monitor/sid/");


}
function refreshGridPage() {
	//event.preventDefault();
	clearCheckBoxSelection();	
	t.draw();//Loading existing opened page only
	
}


function searchAndReloadGridForAclSid(sid) {
	clearCheckBoxSelection();
	var table = $('#sidTableId').DataTable();
	table.ajax.url(
			"paginated?sid=" +  "").load();
	$("#successMsgId").html("<span style='font:strong'>Search completed. Check the grid.</span>");
}


//## Code for loading grid ##
var t;


function loadGridForAclSid(sid) {

$('.dataTables_filter input[type="search"]').
  attr('placeholder','Search in this blog ....').
  css({'width':'70px','display':'inline-block'});

	
	t = $('#sidTableId').DataTable(
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
				"aaSorting" : [ [ 3, "asc" ] ],
				//sDom: '<"search-box"r>lftip',//Will keep search within datable div
					"language": { "search": "<i class='fa fa-search'></i>&nbsp;" },
				"ajax" : {
					"url" : "/acl/monitor/sid/paginated?sid=" + sid + "",
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
						"data" : "id",
						"name" : "ID",
						"title" : "ID",
						"searchable" : false,
						"bVisible" : true, // used for hiding a column
					},
					{
						"data" : "principal",
						"name" : "principal",
						"title" : "Principal"
					},
					{
						"data" : "sid",
						"name" : "sid",
						"title" : "Sid"
					},
					

					{
						"data" : null,
						"sortable" : false,
						"bVisible" : false,
						"render" : function(data, type, row) {
							return '<a class="vClass" href=?record_id='
							+ row.id + '>'
							+ 'Acl Obj' + '</a>';
						}
					},
					
			   ]
			});

	
	

}// End of loading grid

//###########################################################//
//########## End of grid and CRUD related code #############//
//#########################################################//


//#######################################################################//
//########## Start: Methods for supporting above operations ############//
//#####################################################################//

var baseUrl = '';


//If the form requires anything pre-loaded. it can be done here.
function preparePage() {

}


function saveAndUpdateRecord() { 
	/* stop form from submitting normally */
	event.preventDefault();
	/* get the form data of the from */
	// var formSerializeData = $('#commonFormId').serialize();

	var id = $('#countryRecordId').val();
	var name = $('#countryNameId').val();
	var sortname = $('#countrySortNameId').val();
	var phonecode = $('#countryPhoneCodeId').val();
	var json = {
			"id" : id,
			"name" : name,
			"sortname" : sortname,
			"phonecode" : phonecode
	};

	$
	.ajax({
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
		error : function() {
			;
		}
	});
	
}


//#######################################################################//
//########## End: Methods for supporting above operations ############//
//#####################################################################//


