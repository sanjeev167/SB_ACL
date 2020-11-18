/*******************************************************************************
 * @Author: Sanjeev Kumar
 * @Date: 12/2/2019 *
 ******************************************************************************/


//###########################################################//
//########## Start of grid and CRUD related code ###########//
//#########################################################//

$(document).ready(function() {
    
	var baseUrl = '/acl/monitor/aclClass';

	// [0] Load grid while opening the page.
	loadGridForAclClass("");


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


function searchAndReloadGridAclClass(classWithPkg) {
	clearCheckBoxSelection();
	var table = $('#aclClassTableId').DataTable();
	table.ajax.url(
			baseUrl+"/paginated?classWithPkg=" +classWithPkg+  "").load();
	$("#successMsgId").html("<span style='font:strong'>Search completed. Check the grid.</span>");
}


//## Code for loading grid ##
var t;


function loadGridForAclClass(classWithPkg) {	
	t = $('#aclClassTableId').DataTable(
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
				"aaSorting" : [ [ 2, "asc" ] ],
				"ajax" : {
					"url" : "/acl/monitor/aclClass/paginated?classWithPkg=" + classWithPkg + "",
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
						"data" : "classWithPkg",
						"name" : "classWithPkg",
						"title" : "Class [ Domain ]"
					},
					

					{
						"data" : null,
						"searchable" : false,
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


//If the form requires anything pre-loaded. it can be done here.
function preparePage() {

}


//#######################################################################//
//########## End: Methods for supporting above operations ############//
//#####################################################################//


