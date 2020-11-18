
$(document).ready(function() {
	cleanDomainPermissionMessage();
	$("#permissionDefinitionHelp").click(function(){
		openPermissionDefinitionHelp();		
	});
	
	populatePermissionGrid();
	loadDomainCombo();	
	loadPermissionWithNoPreSelection();
	
	var permissionRecordIdForEdit;//Will be initialized when permission record is clicked for edit   
	//Disable checkbox event
	$(document).on('click', '.chkGridIndvRow',function(event) {
	  event.preventDefault();
	  event.stopPropagation();
	  return false;
	});
	
	/** Edit permission open **/	
	$("#grid_permission_tableId").on('click','.permissionEditClass',function() {
	   //alert("Edit link in grid has been clicked.");
		cleanDomainPermissionMessage();
		var currentRow = $(this).closest("tr");
	    var domainId = currentRow.find("td:eq(1)").text();	
	    var domainClassNameForEdit=currentRow.find("td:eq(2)").text();	   
	    $("#domainComboId").val(domainId);				
		loadPermissionDetailsOnDomainChangeInDomainContainer(domainId);		
		});
	
	
	/** Delete permission **/
	$("#grid_permission_tableId").on('click','.permissionDeleteClass',function() {
		cleanDomainPermissionMessage();
		var currentRow = $(this).closest("tr");
	    var domainId = currentRow.find("td:eq(1)").text();
	    confirm_domain_permission_delete(domainId);					
		});
	
	$("#domainComboId").on('change', function(e) {
		cleanDomainPermissionMessage();
		var domainId=$("#domainComboId").val();			
		loadPermissionDetailsOnDomainChangeInDomainContainer(domainId);	
		
		
	});
	
	//Load permission when custom context is changed in domain container is changed. 
	$("#domainPermissionCustomContextContextId").on('change', function(e) {
		cleanDomainPermissionMessage();
		var domainId=$("#domainComboId").val();		
		var withDomainFlag=false;
		if(domainId!=0)
			withDomainFlag=true;
		var contextId=$("#domainPermissionCustomContextContextId").val();
		var basePermissionContainerId0="basePermissionId10";
		var customPermissionContainerId1="basePermissionId11";
		var customPermissionContainerId2="basePermissionId12";		
		loadPermissionWhenCustomContextIsChangedInDomainContainer(domainId,withDomainFlag,contextId,
				                                    basePermissionContainerId0,
				                                    customPermissionContainerId1,
				                                    customPermissionContainerId2);		
		
	});
	
	
	
	//Add/Edit domain permission	
	$(document). on("click", ".addEditDomainPermissionButtonClass" , function() {
		
		cleanDomainPermissionMessage();
		var domainId=$("#domainComboId").val();
		var totalPermission = getTotalPermissionSelectedForDomain();		
		addEditDomainPermission(domainId,totalPermission);
		
		
	});
	
	//Refresh Form
	
	$("#domainPermissionRefreshId").click(function(){
		resetDomainForm();
	});
	
	
	//toggle All check [Base Permission section ]	
	$("#ckbDomainBaseCheckAll").click(function () {		
	    $(".basePermissionId0Class").prop('checked', $(this).prop('checked'));
	});
	
	$(document).on('change', '.basePermissionId0Class',function() {
		if($(".basePermissionId0Class").length == $(".basePermissionId0Class:checked").length) {            
			$("#ckbDomainBaseCheckAll").prop("checked",true);
       }else {$("#ckbDomainBaseCheckAll").prop("checked",false); }		
	});

	
	//toggle All check [Custom Permission section ]	
	$("#ckbDomainCustomCheckAll").click(function () {		
	    $(".customPermissionId1Class").prop('checked', $(this).prop('checked'));
	});
	
	$(document).on('change', '.customPermissionId1Class',function() {
		if($(".customPermissionId1Class").length == $(".customPermissionId1Class:checked").length) {            
			$("#ckbDomainCustomCheckAll").prop("checked",true);
       }else {$("#ckbDomainCustomCheckAll").prop("checked",false); }		
	});
	
	
	
	
});//End of document.ready


function cleanDomainPermissionMessage(){
	//Clean erro message
	$('#domainPermissionIdErr').html("");	
	$('#domainIdErr').html("");
	$('#domainPermissionIdSuccess').html("");
	$('#deleteSuccessId').html("");
	$('#successMsgId').html("");
	
}

function  addEditDomainPermission(domainId,totalPermission){
	//alert("totalPermission="+totalPermission);
	
	var contextId=$("#domainPermissionCustomContextContextId").val();
	var json = {				
			"domainId" : domainId,
			"totalPermission":totalPermission,
			"contextId":contextId
	};
	
	//Fire an ajax call	
	$.ajax({
		type : 'POST',
		url : "/domain_permission" + "/addEditDomainPermission",
		data : JSON.stringify(json),
		beforeSend : function(xhr) {
			xhr.setRequestHeader("Accept", "application/json");
			xhr.setRequestHeader("Content-Type", "application/json");
		},
		
		success : function(response) {
			 //alert("Response is coming");				
			if(response.status){
				//Do something if required	
				$('#domainPermissionIdSuccess').html(response.statusMsg);
				populatePermissionGrid();
			}else{
				//alert(response.fieldErrMsgMap.domainName);					
				$('#domainPermissionIdErr').html(response.fieldErrMsgMap.totalPermission);	
				$('#domainIdErr').html(response.fieldErrMsgMap.domainId);	
			}
		},
		error : function(jqXHR, exception) {			
			formatErrorMessage(jqXHR, exception);
		}
	});//Ajax
	
	
//populatePermissionGrid();
}

function deleteDomainPermissionAssignment(domainId){
	
	//Fire an ajax call	
	$.ajax({
		type : 'GET',
		url : "/domain_permission" + "/deleteDomainPermissionAssignment?domainId="+domainId+"",
		data : {},
		beforeSend : function(xhr) {
			xhr.setRequestHeader("Accept", "application/json");
			xhr.setRequestHeader("Content-Type", "application/json");
		},
		success : function(response) {
			 //alert("Response is coming");				
			if(response.status){
				//Do something if required	
				$('#deleteSuccessId').html(response.statusMsg);
				populatePermissionGrid();
			}else{
				//alert(response.fieldErrMsgMap.domainName);				
			}
		},
		error : function(jqXHR, exception) {			
			formatErrorMessage(jqXHR, exception);
		}
	});//Ajax
	
}

function loadDomainEditDeleteCombo(){
	method = 'GET';
	url = "/domain_acl" + "/domainList";
	$.ajax({
				type : method,
				url : url,
				data : {},
				beforeSend : function(xhr) {
					xhr.setRequestHeader("Accept", "application/json");
					xhr
							.setRequestHeader("Content-Type",
									"application/json");
				},
				success : function(response) {
					if (response.status) {
						
						var ele = document
								.getElementById('domainEditDeleteComboId');
						ele.innerHTML = "";
						for (var i = 0; i < response.comboList.length; i++) {
							// POPULATE SELECT ELEMENT WITH JSON.
							ele.innerHTML = ele.innerHTML
									+ '<option class="domainEditDeleteComboClass" value="' + response.comboList[i]['id'] + '">'
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

function loadDomainCombo() {
	
	
	method = 'GET';
	url = "/domain_acl" + "/domainList";
	$.ajax({
				type : method,
				url : url,
				data : {},
				beforeSend : function(xhr) {
					xhr.setRequestHeader("Accept", "application/json");
					xhr
							.setRequestHeader("Content-Type",
									"application/json");
				},
				success : function(response) {
					if (response.status) {
						
						var ele = document
								.getElementById('domainComboId');
						ele.innerHTML = "";
						for (var i = 0; i < response.comboList.length; i++) {
							// POPULATE SELECT ELEMENT WITH JSON.
							ele.innerHTML = ele.innerHTML
									+ '<option class="domainComboClass" value="' + response.comboList[i]['id'] + '">'
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

function loadPermissionsWithChangedCustomContextInDomainDefinitionContainer(){
	
	var contextId=$("#domainPermissionCustomContextContextId").val();
	var basePermissionContainerId0="basePermissionId10";
	var customPermissionContainerId1="basePermissionId11";
	var customPermissionContainerId2="basePermissionId12";
	var domainId=0;//Dummy. It will not be used while loading from here.
	var withDomainFlag=false;
	loadPermissionWhenCustomContextIsChangedInDomainContainer(domainId,withDomainFlag,contextId,
			                                    basePermissionContainerId0,
			                                    customPermissionContainerId1,
			                                    customPermissionContainerId2);
}

function loadPermissionDetailsOnDomainChangeInDomainContainer(domainId){
	//alert("Domain has been changed. "+domainId)
	
	var basePermissionContainerId0="basePermissionId10";
	var customPermissionContainerId1="basePermissionId11";
	var customPermissionContainerId2="basePermissionId12";
	$.ajax({
		url : "/domain_permission/permissionLoadingInDomainContainerOnDomainChange?domainId="+domainId+"",
		success : function(result) {
			//alert("Response is coming.");
			var jsonObj = JSON.parse(result);
			//Will make state combo clean			
			var ctrl_0 = "";var ctrl_1 = "";var ctrl_2 = "";								
			$("#"+basePermissionContainerId0+"").html(ctrl_0);
			$("#"+customPermissionContainerId1+"").html(ctrl_1);
			$("#"+customPermissionContainerId2+"").html(ctrl_2);				
			var mask;
			var customTextIdSelected=$("#domainPermissionCustomContextContextId").val();				
			var customTextIdComing; 
			var customCount_1=0;
			var customCount_2=0;			
			for (i = 0; i < jsonObj.comboList.length; i++) {					
				 mask=(jsonObj.comboList[i]['maskPower'])*1;
				 customTextIdComing=(jsonObj.comboList[i]['customTextId'])*1;
				 
			if(-1 < mask && mask < 5)	{			
			ctrl_0 = "<input class='basePermissionId0Class' " + jsonObj.comboList[i]['allReadyAssigned']
			+ " type='checkbox' name='domain_base_permission' value='" + jsonObj.comboList[i]['id']
			+ "' />&nbsp;<span style='font-weight:bold'>"
			+ jsonObj.comboList[i]['name'] +"<br>";									
				$("#"+basePermissionContainerId0+"").append(ctrl_0)+"</span>";					
			}			
			if(customTextIdComing==customTextIdSelected && (customCount_1<5))	{				
				ctrl_1 = "<input class='customPermissionId1Class' " + jsonObj.comboList[i]['allReadyAssigned']
				+ " type='checkbox' name='cust_permission_col_1' value='" + jsonObj.comboList[i]['id']
				+ "' />&nbsp;"
				+ jsonObj.comboList[i]['name'] + "<br>";									
					$("#"+customPermissionContainerId1+"").append(ctrl_1);		
				customCount_1=customCount_1+1;						
			}			
			if( (customTextIdComing==customTextIdSelected) &&  (customCount_1==5 && customCount_2<10 ))	{
				if(customCount_2!=0){
				
				ctrl_2 = "<input class='customPermissionId2Class' " + jsonObj.comboList[i]['allReadyAssigned']
				+ " type='checkbox'name='cust_permission_col_2' value='" + jsonObj.comboList[i]['id']
				+ "' />&nbsp;"
				+ jsonObj.comboList[i]['name'] + "<br>";									
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
			//Don't place anywhere else.
			calculateAndCheckAll();
		},
		error : function(jqXHR, exception) {			
			formatErrorMessage(jqXHR, exception);
		}
	});//Ajax
}

function loadPermissionWhenCustomContextIsChangedInDomainContainer(domainId,withDomainFlag,contextId,basePermissionContainerId0,customPermissionContainerId1,customPermissionContainerId2){
	
	$.ajax({
		url : "/domain_permission/loadBaseAndCustomPermissionTogatherWithPreSelection?domainId="+domainId+"&withDomainFlag="+withDomainFlag+"&contextId="+contextId+"",
		success : function(result) {
			//alert("Response is coming.");
			var jsonObj = JSON.parse(result);
			//Will make state combo clean			
			var ctrl_0 = "";var ctrl_1 = "";var ctrl_2 = "";								
			$("#"+basePermissionContainerId0+"").html("");
			$("#"+customPermissionContainerId1+"").html("");
			$("#"+customPermissionContainerId2+"").html("");				
			var mask;
			var customTextIdSelected=contextId;				
			var customTextIdComing; 
			var customCount_1=0;
			var customCount_2=0;			
			for (i = 0; i < jsonObj.comboList.length; i++) {					
				 mask=(jsonObj.comboList[i]['maskPower'])*1;
				 customTextIdComing=(jsonObj.comboList[i]['customTextId'])*1;
				 
			if(-1 < mask && mask < 5)	{			
				
			ctrl_0 = "<input class='basePermissionId0Class' " + jsonObj.comboList[i]['allReadyAssigned']
			+ " type='checkbox' name='domain_base_permission' value='" + jsonObj.comboList[i]['id']
			+ "' />&nbsp;<span style='font-weight:bold'>"
			+ jsonObj.comboList[i]['name'] +"</span><br>";									
				$("#"+basePermissionContainerId0+"").append(ctrl_0);					
			}			
			if(customTextIdComing==customTextIdSelected && (customCount_1<5))	{
				
				ctrl_1 = "<input class='customPermissionId1Class' " + jsonObj.comboList[i]['allReadyAssigned']
				+ " type='checkbox' name='cust_permission_col_1' value='" + jsonObj.comboList[i]['id']
				+ "' />&nbsp;"
				+ jsonObj.comboList[i]['name'] + "<br>";									
					$("#"+customPermissionContainerId1+"").append(ctrl_1);		
				customCount_1=customCount_1+1;						
			}			
			if( (customTextIdComing==customTextIdSelected) &&  (customCount_1==5 && customCount_2<10 ))	{
				if(customCount_2!=0){
				
				ctrl_2 = "<input class='customPermissionId2Class' " + jsonObj.comboList[i]['allReadyAssigned']
				+ " type='checkbox'name='cust_permission_col_2' value='" + jsonObj.comboList[i]['id']
				+ "' />&nbsp;"
				+ jsonObj.comboList[i]['name'] + "<br>";									
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
			
			//Don't place it anywhere else.
			calculateAndCheckAll();
		}
	,
	error : function(jqXHR, exception) {			
		formatErrorMessage(jqXHR, exception);
	}
	});//Ajax
	
}//End of loadPermissionWhenCustomContextIsChangedInDomainContainer	

function resetDomainForm(){	
	    cleanDomainPermissionMessage();
		//$("#addEditDomainFormHeaderId").html("&nbsp;&nbsp;Add/Edit");
		///$("#domainAddEditButtonId").html("<button class='domainAddClass input-sm ui-button ui-widget ui-corner-all pull-right' >Add</button>");
		$("#domainComboId").val("");		
		loadDomainCombo();			
		populatePermissionGrid();
		loadPermissionWithNoPreSelection();
		
	}

function getTotalPermissionSelectedForDomain(){
		var basePermissionChkPermissionArray = [];
		// Look for all checkboxes that have a specific class and was checked
		$(".basePermissionId0Class:checked").each(function() {
			basePermissionChkPermissionArray.push($(this).val().split("_")[2]);
		});
		// Join the array separated by the comma		        
		var basePermissionSelected = basePermissionChkPermissionArray.join(',');
		//alert("basePermissionSelected = "+basePermissionSelected);
		
		var customPermissionChkPermissionArray1 = [];
		// Look for all checkboxes that have a specific class and was checked
		$(".customPermissionId1Class:checked").each(function() {
			customPermissionChkPermissionArray1.push($(this).val().split("_")[2]);
		});
		// Join the array separated by the comma		        
		var customPermissionSelected1 = customPermissionChkPermissionArray1.join(',');
		//alert("customPermissionSelected1 = "+customPermissionSelected1);
		
		var customPermissionChkPermissionArray2 = [];
		// Look for all checkboxes that have a specific class and was checked
		$(".customPermissionId2Class:checked").each(function() {
			customPermissionChkPermissionArray2.push($(this).val().split("_")[2]);
		});
		// Join the array separated by the comma		        
		var customPermissionSelected2 = customPermissionChkPermissionArray2.join(',');
		//alert("customPermissionSelected2 = "+customPermissionSelected2);
		
		var totalPermission="";
		if(basePermissionSelected!="")
			totalPermission=basePermissionSelected;
		if(customPermissionSelected1!="")
			totalPermission=totalPermission+","+customPermissionSelected1;
		if(customPermissionSelected2!="")
			totalPermission=totalPermission+","+customPermissionSelected2;
		
		//Now remove starting and trailing "," before returning.		
		totalPermission = totalPermission.replace(/^,|,$/g,'');
		return totalPermission; 
	}

function loadPermissionWithNoPreSelection() {
	
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
						var ele1 = "";
						
						//Before populating permission-context, clean it.
						$("#domainPermissionCustomContextContextId").html(ele1);
						
						for (var i = 0; i < response.comboList.length; i++) {
							// POPULATE SELECT ELEMENT WITH JSON.
							ele1 =  '<option class="permissionDomainCustomContextClass"'+response.comboList[i]['allReadyAssigned']+' value="' + response.comboList[i]['id'] + '">'
									+ response.comboList[i]['name']
									+ '</option>';									
							$("#domainPermissionCustomContextContextId").append(ele1);							
						}
						//This will load permissions available in the domain definition container 
						loadPermissionsWithChangedCustomContextInDomainDefinitionContainer();													
					} else {
						alert("Wrong Status");
					}
				},
				error : function(jqXHR, exception) {
					alert("Ajax error");
				}
			});
}

function openDomainDefinitionHelp(){	
	$("#domainDefinitionHelpId").dialog({		
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

//Calculate all checked option in a particular section and check all if all selected 
function calculateAndCheckAll(){	
	//Clean Staled selection
	$("#ckbDomainBaseCheckAll").prop("checked",false);
	$("#ckbDomainCustomCheckAll").prop("checked",false);
	//Now check All if condition satisfied.
	if($(".basePermissionId0Class").length == $(".basePermissionId0Class:checked").length)            
		$("#ckbDomainBaseCheckAll").prop("checked",true);
	
	if($(".customPermissionId1Class").length == $(".customPermissionId1Class:checked").length)            
		$("#ckbDomainCustomCheckAll").prop("checked",true);
	
	
}

function confirm_domain_permission_delete(domainId) {
	$("#confirm_domain_permission_delete").dialog({
		
		dialogClass : "no-close",
		resizable : false,
		draggable: false,
		height : "auto",
		width : 400,
		modal : true,
		position: { my: "center", at: "center" },
		buttons : {
			"Yes" : function() {
				deleteDomainPermissionAssignment(domainId);
				//$( this ).dialog( "close" );
			},
			"Cancel" : function() {
				$("#selectedPolicyDeletedId").html("");
				$(this).dialog("close");
			},
		}
	});
}//confirm_delete_permission_definition

function successDialogue(responseMsg) {
		
		$("#responseId").html(responseMsg); //First write response in dialogue box
		$("#success-confirm").dialog({
			dialogClass : "no-close",
			resizable : false,
			draggable: false,
			height : "auto",
			width : 400,
			modal : true,
			position: { my: "center", at: "top" },
			buttons : {
				"Ok" : function() { 
					$(this).dialog("close");
				}
			}
		});
	}//successDialogue



//##### Grid data population #######//
function populatePermissionGrid() {
	
	
	var url = "/domain_permission" + "/permissionDefinedForAllDomain";
	
	
	$('#grid_permission_tableId').DataTable(
			{	'rowReorder' : true,			
				"retrieve" : true,// used for refreshing
				"bAutoWidth" : true,
				//"scrollY" : '110vh',
				//"scrollCollapse" : true,
				"pagingType": "full_numbers",
				 "paging": true,
				"lengthMenu" : [ 5, 10, 15, 20 ],
				"processing" : true,
				"serverSide" : false,
				"ordering" : true,
				"searching" : true,
				//"aaSorting" : [ [ 2, "asc" ] ],
				"ajax" : {
					"async": false,
					"url" : url,					
					"type" : "GET",	
					"dataSrc": "",				
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
					
					 {	"searchable" : false,
						"data" : "domainId",
						"name" : "domainId",
						"title" : "ID",						
						"bVisible" : true, // used for hiding a column
					},
					
					{ 	"data" : "domainName",
						"name" : "domainName",
						"title" : "Defined-Domain",
						"bVisible" : true, // used for hiding a column
					},					
					
					{  "data" : "permissionList",
						"name" : "permissionList",
						"title" : "Associated-Permissions with a domain",
						"render" : function(data, type, row) {											
							return preparePermissionForGridRow(data);	
						}
						
					},
										
					{	"data" : null,
						"sortable" : false,
						"bVisible" : true, // used for hiding a column
						"render" : function(data, type, row) {
							return '<a class="permissionEditClass" href="#">'
							+ '<i class="fa fa-edit"></i>' + '</a>';
						}
					},    
					{
						"data" : null,
						"sortable" : false,
						"render" : function(data, type, row) {
							return '<a class="permissionDeleteClass" href="#">'
							+ '<i  class="fa fa-trash"></i>'
							+ '</a>';
						}
					} 
				]
			});

	
}// End of loading grid

function preparePermissionForGridRow(permissionList) {	
	var ctrl = "";
	for (j = 0; j < permissionList.length; j++) {
		ctrl = ctrl
				+ "<input class='chkGridIndvRow' " + permissionList[j]['allReadyAssigned']
			+ " type='checkbox' name='" + permissionList[j]['id']
			+ "' />&nbsp;"
				+ permissionList[j]['name']
				+ "&nbsp;&nbsp;";
	}//For  
	return ctrl;
}

//###### Old code that has already been replaced; but don't delete it. #######//
function populatePermissionGridOld() {
		method = 'GET';
		url = "/domain_permission" + "/permissionDefinedForAllDomain";
		$.ajax({
					type : method,
					url : url,
					data : {},
					beforeSend : function(xhr) {
						xhr.setRequestHeader("Accept", "application/json");
						xhr
								.setRequestHeader("Content-Type",
										"application/json");
					},
					success : function(response) {
						//alert("Domain-Permission defined Grid data is coming");
						if (response.status) {
							$("#grid_permission_tableBodyId").html("");
							var ele = document
									.getElementById('grid_permission_tableBodyId');
							
							ele.innerHTML ="";//Clean staled record
							//response.permissionGridDtoList.length;
							var permissionGridDto;
							for (var i = 0; i < response.permissionGridDtoList.length; i++) {
								permissionGridDto = response.permissionGridDtoList[i];
								var permission = preparePermissionForGridRow(permissionGridDto);
								ele.innerHTML = ele.innerHTML + "<tr>" + "<td>"
										+ (i + 1) + "</td>" 
										+ "<td>"+ permissionGridDto.domainId+ "</td>" 
										+ "<td>"+ permissionGridDto.domainName+ "</td>" 
										
										+ "<td>" + permission
										+ "</td>"
										+ "<td class='permissionEditClass'>"
										+ "<a href='#'><i class='fa fa-edit'></a>" + "</td>"
										+ "<td class='permissionDeleteClass'>" + "<a href='#'><i class='fa fa-trash'></a>" 
										+ "</td>" + "</tr>";
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
		






