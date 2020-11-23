/*******************************************************************************
 * @Author: Sanjeev Kumar
 * @Date: 12/2/2019 *
 ******************************************************************************/


//###########################################################//
//########## Start of grid and CRUD related code ###########//
//#########################################################//

$(document).ready(function() {

	var baseUrl = '/acl/monitor/entry';

	// [0] Load grid while opening the page.
	loadGridForAclEntry("");


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
		
		searchAndReloadGridAclClass(sid);
	});

	// [3] New one.
	$("#reloadGridAclClass").click(function() {
		reloadGridAclClass();
	});
	
	// [4] New one.
	$("#refreshGrid").click(function() {
		refreshGridPage();
	});
	
	

});// End of document ready


//Reload Grid.
function reloadGridAclClass() {
	//event.preventDefault();
	clearCheckBoxSelection();

	window.location.replace(baseUrl);


}
function refreshGridPage() {
	//event.preventDefault();
	clearCheckBoxSelection();	
	t.draw();//Loading existing opened page only
	
}


function searchAndReloadGridAclClass(objectIdentity) {
	clearCheckBoxSelection();
	var table = $('#aclEntryTableId').DataTable();
	table.ajax.url(
			baseUrl+"/paginated?objectIdentity=" +objectIdentity+  "").load();
	$("#successMsgId").html("<span style='font:strong'>Search completed. Check the grid.</span>");
}


//## Code for loading grid ##
var t;


function loadGridForAclEntry(objectIdentity) {	
	t = $('#aclEntryTableId').DataTable(
			{
				"retrieve" : true,// used for refreshing
				"bAutoWidth" : true,
				//"scrollY" : '110vh',
				//"scrollCollapse" : true,
				"lengthMenu" : [ 5, 10, 15, 20 ],
				"processing" : true,
				"serverSide" : true,
				//"ordering" : true,
					"language": { "search": "<i class='fa fa-search'></i>&nbsp;" },
				"searching" : true,
				"aaSorting" : [ [ 2, "asc" ] ],
				"ajax" : {
					"url" : "/acl/monitor/entry/paginated?objectIdentity=" + objectIdentity + "",
					"type" : "POST",
					
				},

				"columns" : [
					{						
						"searchable" : false,
						"sortable" : false,
						"targets" : 0,
						"render" : function(data, type, full, meta) {
							return meta.row + 1;// Will send row index
						}
					},
					

					{
						"data" : "id",
						"name" : "ID",
						"title" : "ID"						
					},
					
					{
						"data" : "acl_object_identity",
						"name" : "acl_object_identity",
						"title" : "Obj_Id"
					},
					{
						"data" : "ace_order",
						"name" : "ace_order",
						"title" : "Ace(O)"
					},
					{
						"data" : "sid",
						"name" : "sid",
						"title" : "Sid"
					},
					{
						"data" : "mask",
						"name" : "mask",
						"title" : "Mask"
					},
					{
						"data" : "granting",
						"name" : "granting",
						"title" : "Grant"
					},
					{
						"data" : "audit_success",
						"name" : "audit_success",
						"title" : "Aud(S)"						
					},
					{
						"data" : "audit_failure",
						"name" : "audit_failure",
						"title" : "Aud(F)"						
					}
					
			   ]
			});

	
	

}// End of loading grid

//###########################################################//
//########## End of grid and CRUD related code #############//
//#########################################################//


//#######################################################################//
//########## Start: Methods for supporting above operations ############//
//#####################################################################//


//If the form requires anything pre-loaded. it can be done here.
function preparePage() {

}


//#######################################################################//
//########## End: Methods for supporting above operations ############//
//#####################################################################//


