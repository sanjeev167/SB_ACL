
$(document).ready(function() {

var permissionRecordIdForEdit;

loadPermissionDefinitionContextComboInPermissionContainer();

loadPermissionCustomContextCombo();
	
//This will temporarily remove href attribute from the link
removeLinkOfEditAndDelete();

$("#permissionDefinitionRefreshId").click(function(){
	resetPermissionForm();
});
//Load permission when custom context is changed in permission container is changed.
$("#permissionCustomContextContextId").on('change', function(e) {
	resetPermissionAddForm();
	var contextId=$("#permissionCustomContextContextId").val();
	var basePermissionContainerId0="basePermissionId20";
	var customPermissionContainerId1="basePermissionId21";
	var customPermissionContainerId2="basePermissionId22";
	
	loadPermissionWhenCustomContextIsChangedInPermissionContainer(contextId,
			                                    basePermissionContainerId0,
			                                    customPermissionContainerId1,
			                                    customPermissionContainerId2);
});


//After clicking one permission, uncheck the rest radio butoon
$(document).on('click','.basePermissionRadioId0Class',function () {
	resetPermissionAddForm();	
	$("a#basePermissionEditId20Id").prop('href',"#");
	$("a#basePermissionEditId21Id").removeAttr('href'); 
	$("a#basePermissionEditId22Id").removeAttr('href'); 
	
	$("a#basePermissionDeleteId20Id").prop("href", "#");	
	$("a#basePermissionDeleteId21Id").removeAttr('href');		
	$("a#basePermissionDeleteId22Id").removeAttr('href');
	
 });//End of base permission checking radio


$(document).on('click','.basePermissionRadioId1Class',function () {
    resetPermissionAddForm();	
	$("a#basePermissionEditId20Id").removeAttr('href');
	$("a#basePermissionEditId21Id").prop("href", "#");		
	$("a#basePermissionEditId22Id").removeAttr('href');	
	
	$("a#basePermissionDeleteId20Id").removeAttr('href');	
	$("a#basePermissionDeleteId21Id").prop("href", "#");		
	$("a#basePermissionDeleteId22Id").removeAttr('href');	
});//End of custom-1 permission checking radio


$(document).on('click','.basePermissionRadioId2Class',function () {
        resetPermissionAddForm();
		$("a#basePermissionEditId20Id").removeAttr('href');
		$("a#basePermissionEditId21Id").removeAttr('href');			
		$("a#basePermissionEditId22Id").prop("href", "#");
		
		$("a#basePermissionDeleteId20Id").removeAttr('href');	
		$("a#basePermissionDeleteId21Id").removeAttr('href');		
		$("a#basePermissionDeleteId22Id").prop("href", "#");
		
});//End of custom-2 permission checking radio


$("#basePermissionEditId20Id").on('click',function() {
	cleanPermissionDefinitionMessages();
	   //alert("permissionDefinitionBase edit has been clicked.");  
	   adjustPermissionFormInEditMode();
    var radioValue = $("input[name='base_permission']:checked").val();
    var radioArr=radioValue.split("_");       
    var label=radioArr[0];
    var permissionContextId=radioArr[1];
    var recordId=radioArr[2];       
    $("#permissionContextId").val(permissionContextId);
    $("#permissionLabel").val(label);
    permissionRecordIdForEdit=recordId;//Will be used when edit submit is called
    
});


$("#basePermissionDeleteId20Id").on('click',function() {
	cleanPermissionDefinitionMessages();
	   var radioValue = $("input[name='base_permission']:checked").val();
	   var radioArr=radioValue.split("_");       
    var label=radioArr[0];
    var permissionContextId=radioArr[1];
    var recordId=radioArr[2];  
    confirm_delete_permission_definition(recordId);
});

$("#basePermissionEditId21Id").on('click',function() {	
	   
	   var radioValue = $("input[name='custom_permission']:checked").val();
	   if(radioValue){
		cleanPermissionDefinitionMessages();
		adjustPermissionFormInEditMode();	  
	    var radioArr=radioValue.split("_");       
	    var label=radioArr[0];
	    var permissionContextId=radioArr[1];
	    var recordId=radioArr[2];       
	    $("#permissionContextId").val(permissionContextId);
	    $("#permissionLabel").val(label);
	    permissionRecordIdForEdit=recordId;//Will be used when edit submit is called
     }
});

$("#basePermissionDeleteId21Id").on('click',function() {
	var radioValue = $("input[name='custom_permission']:checked").val();
	if(radioValue){
	cleanPermissionDefinitionMessages();
	adjustPermissionFormInEditMode();	   
	var radioArr=radioValue.split("_");       
    var label=radioArr[0];
    var permissionContextId=radioArr[1];
    var recordId=radioArr[2];     
    confirm_delete_permission_definition(recordId);  
	}
});


$("#basePermissionEditId22Id").on('click',function() {	
	var radioValue = $("input[name='custom_permission']:checked").val();
	if(radioValue){
	cleanPermissionDefinitionMessages();
	adjustPermissionFormInEditMode();	   
	var radioArr=radioValue.split("_");       
    var label=radioArr[0];
    var permissionContextId=radioArr[1];
    var recordId=radioArr[2];       
    $("#permissionContextId").val(permissionContextId);
    $("#permissionLabel").val(label);
    permissionRecordIdForEdit=recordId;//Will be used when edit submit is called
	}
});


$("#basePermissionDeleteId22Id").on('click',function() {
	var radioValue = $("input[name='custom_permission']:checked").val();
	if(radioValue){
	cleanPermissionDefinitionMessages();
	adjustPermissionFormInEditMode();	
	var radioArr=radioValue.split("_");       
    var label=radioArr[0];
    var permissionContextId=radioArr[1];
    var recordId=radioArr[2];  
    confirm_delete_permission_definition(recordId);
	}
});

//Permission definition toggle
$(document). on("click", "a.permissionToggleClass" , function() {
	resetPermissionForm();		
});

//Add new permission 
$(document).on('click', '.permissionAddButtonClass' , function(){	
	cleanPermissionDefinitionMessages();
	var permissionContextId=$("#permissionContextId").val();
    var permissionName=$("#permissionLabel").val();   
	savePermissionDefinition(permissionContextId,permissionName);
});

$(document).on('click', '.permissionEditButtonClass' , function(){	
	cleanPermissionDefinitionMessages();
	var permissionContextId=$("#permissionContextId").val();
    var permissionName=$("#permissionLabel").val();
    var recordId=permissionRecordIdForEdit;    
	updatePermissionDefinition(recordId,permissionContextId,permissionName);
	permissionName=$("#permissionLabel").val("");	
});

});//$(document).ready(function()


function cleanPermissionDefinitionMessages(){
	//Clean message
	$('#permissionContextIdErr').html("");
	$('#permissionLabelErr').html("");
	$('#permissionDefinitionSavedSuccessId').html("");	
	$('#deletePermissionSuccessId').html("");
}

function savePermissionDefinition(permissionContextId,permissionName){	
	
	var permissionContextId=$("#permissionContextId").val();
	var permissionLabel=$("#permissionLabel").val();			
	var json = {				
			"permissionContextId" : permissionContextId,
			"permissionLabel":permissionLabel
	};
	
	//Fire an ajax call.
	$.ajax({
		type : 'POST',
		url : "/domain_permission" + "/savePermissionDefinition",
		data : JSON.stringify(json),
		beforeSend : function(xhr) {
			xhr.setRequestHeader("Accept", "application/json");
			xhr.setRequestHeader("Content-Type", "application/json");
		},
		success : function(response) {
			 //alert("Response is coming");				
			if(response.status){
				//Do something if required	
				$('#permissionDefinitionSavedSuccessId').html(response.statusMsg);
				reloadPermissionDefinition();
			}else{
				//alert(response.fieldErrMsgMap.domainName);					
				$('#permissionContextIdErr').html(response.fieldErrMsgMap.permissionContextId);
				$('#permissionLabelErr').html(response.fieldErrMsgMap.permissionLabel);
			}
		},
		error : function(jqXHR, exception) {			
			formatErrorMessage(jqXHR, exception);
		}
	});//Ajax
		
}//savePermissionDefinition

function updatePermissionDefinition(recordId){	  	   
	    var permissionContextId=$("#permissionContextId").val();
		var permissionLabel=$("#permissionLabel").val();				
		var json = {
		"recordId": recordId,
		"permissionContextId" : permissionContextId,
		"permissionLabel":permissionLabel
		};
		
		//Fire an ajax call.
		$.ajax({
			type : 'POST',
			url : "/domain_permission" + "/updatePermissionDefinition",
			data : JSON.stringify(json),
			beforeSend : function(xhr) {
				xhr.setRequestHeader("Accept", "application/json");
				xhr.setRequestHeader("Content-Type", "application/json");
			},
			success : function(response) {
				 alert("Response is coming");				
				if(response.status){
					//Do something if required	
					$('#permissionDefinitionSavedSuccessId').html(response.statusMsg);
					reloadPermissionDefinition();
				}else{										
					$('#permissionContextIdErr').html(response.fieldErrMsgMap.permissionContextId);
					$('#permissionLabelErr').html(response.fieldErrMsgMap.permissionLabel);
				}
			},
			error : function(jqXHR, exception) {
				alert("Sanjeev: Ajax error");	
				formatErrorMessage(jqXHR, exception);
			}
		});//Ajax	
}//updatePermissionDefinition

function reloadPermissionDefinition(){	
	var contextId=$("#permissionContextId").val();
	var basePermissionContainerId0="basePermissionId20";
	var customPermissionContainerId1="basePermissionId21";
	var customPermissionContainerId2="basePermissionId22";	
	loadPermissionWhenCustomContextIsChangedInPermissionContainer(contextId,
			                                    basePermissionContainerId0,
			                                    customPermissionContainerId1,
			                                    customPermissionContainerId2);
}

var hasThisPermissionDeleted=false;
function deletePermissionDefinition(recordId){	
	$.ajax({
		type : 'POST',
		url : "/domain_permission" + "/deletePermissionDefinition?recordId="+recordId+"",
		data : {},
		beforeSend : function(xhr) {
			xhr.setRequestHeader("Accept", "application/json");
			xhr.setRequestHeader("Content-Type", "application/json");
		},
		success : function(response) {
			// alert("Response is coming");				
			if(response.status){
				//Do something if required	
				$('#deletePermissionSuccessId').html(response.statusMsg);		
				populatePermissionGrid();
				hasThisPermissionDeleted=true;
			}else{
				//alert(response.fieldErrMsgMap.domainName);					
				$('#domainNameId_err').html(response.fieldErrMsgMap.domainName);	
			}
		},
		error : function(jqXHR, exception) {			
			formatErrorMessage(jqXHR, exception);
		}
	});//Ajax
	
}//deletePermissionDefinition

function resetPermissionAddForm(){
	cleanPermissionDefinitionMessages();
	$("#permissionContextId").val(0);
	$("#permissionLabel").val("");
	$("#addEditPermissionFormHeaderId").html("Add/Edit:--");
	$("#permissionAddEditButtonId").html("<button  class='input-sm ui-button ui-widget ui-corner-all pull-right permissionAddButtonClass' >Add</button>");
	
}

function loadPermissionDefinitionContextComboInPermissionContainer() {
	
	method = 'GET';
	url = "/domain_permission" + "/loadPermissionContextList";
	$.ajax({
				type : method,
				url : url,
				data : {},
				beforeSend : function(xhr) {
					xhr.setRequestHeader("Accept", "application/json");
					xhr.setRequestHeader("Content-Type","application/json");
				},
				success : function(response) {
					//alert("Response for permission context list is coming.");
					if (response.status) {							
						var ele = document.getElementById('permissionContextId');
						for (var i = 0; i < response.comboList.length; i++) {
							// POPULATE SELECT ELEMENT WITH JSON.
							ele.innerHTML = ele.innerHTML
									+ '<option class="permissionContextComboClass" value="' + response.comboList[i]['id'] + '">'
									+ response.comboList[i]['name']
									+ '</option>';
						}
						//Do something if required			
					} else {
						alert("Wrong Status");
					}
				},
				error : function(jqXHR, exception) {
					alert("Ajax error");
				}
			});
}

function loadPermissionsWithChangedCustomContextInPermissionDefinitionContainer(){	
	
	var contextId=$("#permissionCustomContextContextId").val();
	var basePermissionContainerId0="basePermissionId20";
	var customPermissionContainerId1="basePermissionId21";
	var customPermissionContainerId2="basePermissionId22";
	loadPermissionWhenCustomContextIsChangedInPermissionContainer(contextId,
			                                    basePermissionContainerId0,
			                                    customPermissionContainerId1,
			                                    customPermissionContainerId2);
}

function openPermissionDefinitionHelp(){	
	$("#permissionDefinitionHelpId").dialog({		
		dialogClass : "no-close",
		resizable : false,
		draggable: false,
		height : "auto",
		width : 400,
		modal : true,
		position: { my: "center", at: "center" },
		buttons : {
			"OK" : function() {					
				$( this ).dialog( "close" );
			}
		}		
	});
}

function confirm_delete_permission_definition(recordId) {
	cleanPermissionDefinitionMessages();
	$("#dialog-confirm-permission").dialog({
		dialogClass : "no-close",
		resizable : false,
		draggable: false,
		height : "auto",
		width : 400,
		modal : true,
		position: { my: "center", at: "Center" },
		buttons : {
			"Yes" : function() {
				deletePermissionDefinition(recordId);
				resetPermissionForm();
				if(hasThisPermissionDeleted){
				$( this ).dialog( "close" );hasThisPermissionDeleted=false;}
			},
			"Cancel" : function() {
				resetPermissionForm();		
				$("#selectedPolicyDeletedId").html("");
				$(this).dialog("close");
			},
		}
	});
}//confirm_delete_permission_definition

/**
 * "<input class='basePermissionRadioId0Class' " + jsonObj.comboList[i]['allReadyAssigned']
+ " type='radio' name='base_permission' value='" + jsonObj.comboList[i]['id']
+ "' />&nbsp;"
 * **/
function loadPermissionWhenCustomContextIsChangedInPermissionContainer(contextId,basePermissionContainerId0,customPermissionContainerId1,customPermissionContainerId2){
	
	$.ajax({
		url : "/domain_permission/loadBaseAndCustomPermissionTogather",
		success : function(result) {
			//alert("Response is coming.");
			var jsonObj = JSON.parse(result);
			//Will make state combo clean			
			var ctrl_0 = "";var ctrl_1 = "";var ctrl_2 = "";								
			$("#"+basePermissionContainerId0+"").html(ctrl_0);
			$("#"+customPermissionContainerId1+"").html(ctrl_1);
			$("#"+customPermissionContainerId2+"").html(ctrl_2);				
			var mask;
			var customTextIdSelected=contextId;				
			var customTextIdComing; 
			var customCount_1=0;
			var customCount_2=0;			
			for (i = 0; i < jsonObj.comboList.length; i++) {					
				 mask=(jsonObj.comboList[i]['maskPower'])*1;
				 customTextIdComing=(jsonObj.comboList[i]['customTextId'])*1;				 
			if(-1 < mask && mask < 5)	{
	       ctrl_0 = "&nbsp;<span style='font-weight:bold'>"
			+ jsonObj.comboList[i]['name'] +" -2<sup>"+jsonObj.comboList[i]['maskPower'] + "</sup><br>";									
				$("#"+basePermissionContainerId0+"").append(ctrl_0)+"</span>";					
			}			
			if(customTextIdComing==customTextIdSelected && (customCount_1<5))	{				
				ctrl_1 = "<input class='basePermissionRadioId1Class' " + jsonObj.comboList[i]['allReadyAssigned']
				+ " type='radio' name='custom_permission' value='" + jsonObj.comboList[i]['id']
				+ "' />&nbsp;"
				+ jsonObj.comboList[i]['name'] +" -2<sup>"+jsonObj.comboList[i]['maskPower'] + "</sup><br>";									
					$("#"+customPermissionContainerId1+"").append(ctrl_1);		
				customCount_1=customCount_1+1;						
			}			
			if( (customTextIdComing==customTextIdSelected) &&  (customCount_1==5 && customCount_2<10 ))	{
				if(customCount_2!=0){
				
				ctrl_2 = "<input class='basePermissionRadioId2Class' " + jsonObj.comboList[i]['allReadyAssigned']
				+ " type='radio'name='custom_permission' value='" + jsonObj.comboList[i]['id']
				+ "' />&nbsp;"
				+ jsonObj.comboList[i]['name'] +" -2<sup>"+jsonObj.comboList[i]['maskPower'] + "</sup><br>";									
					$("#"+customPermissionContainerId2+"").append(ctrl_2);							
				
				customCount_2=customCount_2+1;
				}else{customCount_2=1;}
			}				
			}//For 			
			if(customCount_1==0){				
				$("#"+customPermissionContainerId1+"").append("No permission found.");
			}
			if(customCount_2==0){				
				$("#"+customPermissionContainerId2+"").append("No permission found.");
			}			
		}
	,
	error : function(jqXHR, exception) {			
		formatErrorMessage(jqXHR, exception);
	}
	
	});//Ajax
	
}//End of loadPermissionWhenCustomContextIsChangedInPermissionContainer

function adjustPermissionFormInEditMode(domainClassName,domainId) {	
	//Change the header of Add/Edit role permission as Edit
	$("#addEditPermissionFormHeaderId").html("<span  ><a id='permissionToggleId' class='permissionToggleClass' href='#'><strong ><i class='fa fa-toggle-on'></i></strong></a></span>&nbsp;&nbsp;Edit");
	$("#permissionAddEditButtonId").html("<button  class='input-sm ui-button ui-widget ui-corner-all pull-right permissionEditButtonClass'>Update</button>");
}

function removeLinkOfEditAndDelete(){
	$("a#basePermissionEditId20Id").removeAttr('href');	
	$("a#basePermissionEditId21Id").removeAttr('href');		
	$("a#basePermissionEditId22Id").removeAttr('href');	
	$("a#basePermissionDeleteId20Id").removeAttr('href');	
	$("a#basePermissionDeleteId21Id").removeAttr('href');		
	$("a#basePermissionDeleteId22Id").removeAttr('href');	
	
}

function resetPermissionForm(){
	cleanPermissionDefinitionMessages();
	$("#addEditPermissionFormHeaderId").html("Add/Edit:--");
	$("#permissionAddEditButtonId").html("<button class='input-sm ui-button ui-widget ui-corner-all pull-right permissionAddButtonClass'>Add</button>");
	$("#permissionContextId").val("0");
		
	
	$("#permissionLabel").val("");
	loadPermissionCustomContextCombo();
	$('input[name="custom_permission"]').prop('checked', false);
	removeLinkOfEditAndDelete();
	
	
}

function loadPermissionCustomContextCombo() {
	
	method = 'GET';
	url = "/domain_permission" + "/loadPermissionCustomContextCombo";
	$.ajax({
				type : method,
				url : url,
				data : {},
				beforeSend : function(xhr) {
					xhr.setRequestHeader("Accept", "application/json");
					xhr.setRequestHeader("Content-Type","application/json");
				},
				success : function(response) {
					//alert("Response for permission context list is coming.");
					if (response.status) {							
						
						var ele2 =""; 
						//Before populating permission-context, clean it.
						
						$("#permissionCustomContextContextId").html(ele2);
						for (var i = 0; i < response.comboList.length; i++) {
							// POPULATE SELECT ELEMENT WITH JSON.
							
									
							ele2 =  '<option class="permissionCustomContextClass"'+response.comboList[i]['allReadyAssigned']+' value="' + response.comboList[i]['id'] + '">'
							+ response.comboList[i]['name']
							+ '</option>';	
							
							
							$("#permissionCustomContextContextId").append(ele2);
						}						
						//This will load permissions available in the permission definition container
						loadPermissionsWithChangedCustomContextInPermissionDefinitionContainer();							
					} else {
						alert("Wrong Status");
					}
				},
				error : function(jqXHR, exception) {
					alert("Ajax error");
				}
			});
}



