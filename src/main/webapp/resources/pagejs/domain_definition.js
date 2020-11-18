$(document).ready(function() {
	
	loadDomainEditDeleteCombo();
	
	$('#domainRecordingTableId').find('tbody tr:eq(1)').hide();
	//hide row at specifield index (index starts at 0)
	  $('#domainListToggleId').click(function(){
		  cleanMessages();  
	   $('#domainRecordingTableId').find('tbody tr:eq(1)').toggle();	   
	   $('#domainListToggleId').toggleClass( "fa fa-unlock fa fa-lock " );
	   $("#domainAddEditButtonId").html("<button class='domainAddClass input-sm ui-button ui-widget ui-corner-all pull-right'>Add</button>");
	   $("#domainNameId").val(""); $("#domainEditDeleteComboId").val("0");
	  });
	  	  
	//### Help Modal popup
	$("#domainDefinitionHelp").click(function(){
		openDomainDefinitionHelp();		
	});
		
	var domainClassNameForEdit;
	$("#domainEditDeleteComboId").change(function(){
		cleanMessages();
		if($("#domainEditDeleteComboId").val()!="0"){		
		$("#domainAddEditButtonId").html("<button class='domainDeleteClass input-sm ui-button ui-widget ui-corner-all pull-right'><i class='fa fa-trash'></i>" +
		"</button> <button class='domainEditClass input-sm ui-button ui-widget ui-corner-all pull-right'><i class='fa fa-edit'></i></button>");
		$("#domainNameId").val($("#domainEditDeleteComboId option:selected").text());		
		 //This will be used while editing
		domainClassNameForEdit = $("#domainEditDeleteComboId option:selected").text();
		}else{
			$("#domainNameId").val("");
			 $("#domainAddEditButtonId").html("<button class='domainAddClass input-sm ui-button ui-widget ui-corner-all pull-right'>Add</button>");  
		}
	});
	
	
	
$(document).on('click','.domainDeleteClass',function () {	
		
		confirm_domain_delete($("#domainNameId").val());
});
	

//Add a new domain	
$(document). on('click', '.domainAddClass' , function() {		
	saveDomainDefinition();
});

//updateExistingDomainDefinition
$(document). on("click", ".domainEditClass" , function() {			
	updateExistingDomainDefinition(domainClassNameForEdit);
});
	
});//$(document).ready(function()

function cleanMessages(){	
	$('#successMsgId').html("");
	$('#domainNameId_err').html("");
	
}
function saveDomainDefinition(){
	var domainName=$("#domainNameId").val();			
	var json = {				
			"domainName" : domainName
	};
	$.ajax({
		type : 'POST',
		url : "/domain_permission" + "/saveDomainDefinition",
		data : JSON.stringify(json),
		beforeSend : function(xhr) {
			xhr.setRequestHeader("Accept", "application/json");
			xhr.setRequestHeader("Content-Type", "application/json");
		},
		success : function(response) {
			 //alert("Response is coming");				
			if(response.status){
				//Do something if required	
				$('#successMsgId').html(response.statusMsg);
				loadDomainEditDeleteCombo();populatePermissionGrid();
			}else{
				//alert(response.fieldErrMsgMap.domainName);					
				$('#domainNameId_err').html(response.fieldErrMsgMap.domainName);	
			}
		},
		error : function(jqXHR, exception) {			
			formatErrorMessage(jqXHR, exception);
		}
	});//Ajax
}

function updateExistingDomainDefinition(domainClassNameForEdit){	
	var newDomainName=$("#domainNameId").val();	
	var json = {
			"domainName" : newDomainName,
			"domainClassNameForEdit":domainClassNameForEdit
	};
	$.ajax({
		type : 'POST',
		url : "/domain_permission" + "/updateExistingDomainDefinition",
		data : JSON.stringify(json),
		beforeSend : function(xhr) {
			xhr.setRequestHeader("Accept", "application/json");
			xhr.setRequestHeader("Content-Type", "application/json");
		},
		success : function(response) {
			 //alert("Response is coming");				
			if(response.status){
				//Do something if required	
				$('#successMsgId').html(response.statusMsg);
				$("#domainNameId").val("");//Clean input box
				loadDomainEditDeleteCombo();populatePermissionGrid();
				
			}else{
				//alert(response.fieldErrMsgMap.domainName);					
				$('#domainNameId_err').html(response.fieldErrMsgMap.domainName);	
			}
		},
		error : function(jqXHR, exception) {			
			formatErrorMessage(jqXHR, exception);
		}
	});//Ajax
}

var hasARecordBeenDeleted=false;
function deleteSelectedDomain(){ 	
	var domainName=$("#domainNameId").val();	
	$.ajax({
		type : 'POST',
		url : "/domain_permission" + "/deleteSelectedDomain?domainName="+domainName+"",
		data : {},
		beforeSend : function(xhr) {
			xhr.setRequestHeader("Accept", "application/json");
			xhr.setRequestHeader("Content-Type", "application/json");
		},
		success : function(response) {
			// alert("Response is coming");				
			if(response.status){
				//Do something if required	
				$('#domainDeleteSuccessMsgId').html(response.statusMsg);				
				loadDomainEditDeleteCombo();
				populatePermissionGrid();
				hasARecordBeenDeleted=true;
			}else{
				//alert(response.fieldErrMsgMap.domainName);					
				$('#domainNameId_err').html(response.fieldErrMsgMap.domainName);	
			}
		},
		error : function(jqXHR, exception) {			
			formatErrorMessage(jqXHR, exception);
		}
	});//Ajax
}


function confirm_domain_delete() {
	
	$("#confirm_domain_delete").dialog({		
		dialogClass : "no-close",
		resizable : false,
		draggable: false,
		height : "auto",
		width : 400,
		modal : true,
		position: { my: "center", at: "center" },
		buttons : {
			"Yes" : function() {
				deleteSelectedDomain();
				if(hasARecordBeenDeleted)
				$( this ).dialog( "close" );
			},
			"Cancel" : function() {
				$("#selectedDomainDeletedId").html("");
				$(this).dialog("close");
			},
		}
	});
}//confirm_delete_permission_definition
