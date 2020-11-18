
//########## Grid related code ############	

$(document).on("change", ".dataTables_length", function() {
	clearCheckBoxSelection();
});

$(document).on("click", ".dataTables_paginate", function() {
	clearCheckBoxSelection();
});

$(document).on("click", ".dataTables_filter", function() {
	clearCheckBoxSelection();
});

//Clear check box selection if any other table head is clicked
$("thead th").click(function(e) {
	var thid = e.target.id;
	if (thid == 'chkAll')
		$("#chkAll").prop("checked").toggle;
	else
		$("#chkAll").prop("checked", false);
});

function clearCheckBoxSelection() {	
	$("#chkAll").prop("checked", false);
	$(".chkIndvRow").each(function() {
		$(this).prop("checked", false);
	});
}
//End: Code clearing selected check boxes

//Check/Uncheck All checkboxes
$("#chkAll").click(function() {	
	if ($("#chkAll").is(':checked')) {
		$(".chkIndvRow").each(function() {
			$(this).prop("checked", true);
		});
	} else {
		$(".chkIndvRow").each(function() {
			$(this).prop("checked", false);
		});
	}
	// Get selected Row Ids in Array
	// GetSelectedRowID();
});

$(document).on( "click", ".chkIndvRow",function() {
	if ($(this).is(':checked')) {
		// check/uncheck "Select All" checkbox on change of individual row checkbox change
		if ($('.chkIndvRow:checked').length == $('.chkIndvRow').length) {
			$("#chkAll").prop("checked",true);
		}
	} else {
		$("#chkAll").prop("checked", false);
	}										
});