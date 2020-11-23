/*******************************************************************************
 * @Author: Sanjeev Kumar
 * @Date: 12/2/2019 *
 ******************************************************************************/


//###########################################################//
//########## Start of grid and CRUD related code ###########//
//#########################################################//

$(document).ready(function() {

	var baseUrl = '/acl/monitor/objIdentity';

	// [0] Load grid while opening the page.
	loadGridAclObjectIdentity("","");


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
		
		searchAndReloadGridAclObjectIdentity(sid,classWithPkg);
	});

	// [3] New one.
	$("#reloadGridAclObjectIdentity").click(function() {
		reloadGridAclObjectIdentity();
	});
	
	// [4] New one.
	$("#refreshGrid").click(function() {
		refreshGridPage();
	});
	
	

});// End of document ready


//Reload Grid.
function reloadGridAclObjectIdentity() {
	//event.preventDefault();
	clearCheckBoxSelection();

	window.location.replace(baseUrl);


}
function refreshGridPage() {
	//event.preventDefault();
	clearCheckBoxSelection();	
	t.draw();//Loading existing opened page only
	
}


function searchAndReloadGridAclObjectIdentity(sid,classWithPkg) {
	clearCheckBoxSelection();
	var table = $('#aclObjectIdentityTableId').DataTable();
	table.ajax.url(
			baseUrl+"/paginated?sid="+sid+"&classWithPkg=" +classWithPkg+  "").load();
	$("#successMsgId").html("<span style='font:strong'>Search completed. Check the grid.</span>");
}


//## Code for loading grid ##
var t;


function loadGridAclObjectIdentity(sid,classWithPkg) {	


  
  
	t = $('#aclObjectIdentityTableId').DataTable(
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
				//sDom: '<"search-box"r>lftip',//Will keep search within datable div
				
				"language": { "search": "<i class='fa fa-search'></i>&nbsp;" },
				"aaSorting" : [ [ 2, "asc" ] ],
				"ajax" : {
					"url" : "/acl/monitor/objIdentity/paginated?sid="+sid +
							"&classWithPkg=" + classWithPkg + "",
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
						"bVisible" : true, // used for hiding a column
					},
					
					{
						"data" : "object_id_class",
						"name" : "object_id_class",
						"title" : "ClassId"
					},
					{
						
						"data" : "object_id_identity",
						"name" : "object_id_identity",
						"title" : "Object Identity"
					},
					
					/*{
						"searchable" : false,
						"sortable" : false,
						"data" : "parent_object",
						"name" : "parent_object",
						"title" : "Pr_Obj","bVisible" : true, // used for hiding a column
					},*/
					{
						"data" : "owner_sid",
						"name" : "owner_sid",
						"title" : "Sid"
					},
					
					{
						"data" : "entries_inheriting",
						"name" : "entries_inheriting",
						"title" : "en_inh"
					},
					
					
					
					{
						"data" : null,
						"searchable" : false,
						"sortable" : false,
						"bVisible" : false,
						"render" : function(data, type, row) {
							return '<a class="vClass" href=?record_id='
							+ row.id + '>'
							+ 'Entry' + '</a>';
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


