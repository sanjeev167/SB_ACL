<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>

<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">


</head>
<body>
	<!-- Main content -->
	<section class="content container-fluid ">


		<div class="row">
			<div class="col-md-6 ">
				<div class="box" style="width: 100%">
					<div class="box-header with-border ">
						<h3 class="box-title">
							<span id="addEditDomainFormHeaderId">Add/Edit:--</span><span>
								[ Domain-Definition ]</span>
						</h3>
						<div class="box-tools pull-right ">




							<button type="button" 
								class="btn btn-box-tool" data-toggle="tooltip"
								title="Show Domain List" >
								<i class="fa fa-lock" id="domainListToggleId"></i>
							</button>

							<button type="button" class="btn btn-box-tool"
								id="domainDefinitionHelp">
								<i class="fa fa-question"></i>
							</button>
							
							<button type="button" class="btn btn-box-tool"
								data-widget="collapse">
								<i class="fa fa-minus"></i>
							</button>

						</div>
					</div>
					<!-- /.box-header -->


					<div class="box-body form-group">
						<table class="table table-bordered " id="domainRecordingTableId">
							<tr>
								<td style="width: 10px"><label>Domain</label></td>
								<td colspan="2"><input class="input-sm" size="35"
									type="text" id="domainNameId">&nbsp; <span
									id="domainAddEditButtonId">

										<button
											class="domainAddClass ui-button ui-widget ui-corner-all input-sm pull-right">Add</button>
								</span>



									<div id="domainNameId_err" class="error"></div>
									<div id="successMsgId" class="success"></div></td>
							</tr>

							<tr>
								<td style="width: 10px; color: red"><label>Domain
										for edit/delete</label></td>
								<td colspan="2"><select id="domainEditDeleteComboId"
									class="form-control input-sm select1" style="width: 100%;">
										<option>select domain for edit & delete</option>
								</select></td>
							</tr>

							<tr>
								<th colspan="3">Associate
									Domain-Permissions:--									
									<div class="box-tools pull-right ">
										<button type="button" class="btn btn-box-tool"
											id="domainPermissionRefreshId">
											<i class="fa fa-refresh"></i>
										</button>
						            </div>
						       </th>
							</tr>
							<tr>
								<td colspan="2" width="50%"><select
									class="form-control input-sm select1" style="width: 100%;"
									id="domainComboId"></select>
									<div id="domainIdErr" class="error"></div>
									<div id="domainPermissionIdErr" class="error"></div>
									<div id="domainPermissionIdSuccess" class="success"></div></td>
								<td width="40%" id="addEditButtonClassId">
									<button
										class="input-sm ui-button ui-widget ui-corner-all pull-right addEditDomainPermissionButtonClass">
										Update-Permission</button>
								</td>
							</tr>

							<tr>
								<th width="30%"><input type="checkbox" name="z" id="ckbDomainBaseCheckAll" value="ww">&nbsp;All
									&nbsp; Base-Permission</th>
								<th width="35%"><input type="checkbox" name="z"id="ckbDomainCustomCheckAll" value="ww">&nbsp;<strong>All</strong>
									&nbsp; Custom-Permission</th>
								<th width="30%"><select
									class="form-control input-sm select1" style="width: 95%;"
									id="domainPermissionCustomContextContextId">

								</select></th>
							</tr>

							<tr>
								<td width="30%">
									<div id="basePermissionId10"></div>
								</td>
								<td width="30%">
									<div id="basePermissionId11"></div>
								</td>
								<td>
									<div id="basePermissionId12"></div>
								</td>
							</tr>
						</table>
					</div>
					<!-- /.box-body -->

				</div>
				<!-- /.box -->


			</div>

			<div class="col-md-6">
				<div class="box" style="width: 100%">
					<div class="box-header with-border ">
						<h3 class="box-title">
							<span id="addEditPermissionFormHeaderId">Add/Edit:--</span><span>
								[ Custom-Permission-Definition ]</span>
						</h3>
						<div class="box-tools pull-right">

							<button type="button" class="btn btn-box-tool"
								id="permissionDefinitionHelp">
								<i class="fa fa-question"></i>
							</button>
							<button type="button" class="btn btn-box-tool"
								id="permissionDefinitionRefreshId">
								<i class="fa fa-refresh"></i>
							</button>


							<button type="button" class="btn btn-box-tool"
								data-widget="collapse">
								<i class="fa fa-minus"></i>
							</button>
						</div>
					</div>
					<!-- /.box-header -->
					<div class="box-body">
						<table class="table table-bordered form-group">
							<tr>
								<td style="width: 10px"><label>Permission-Context</label></td>
								<td colspan="5"><select
									class="form-control input-sm select1" style="width: 100%;"
									id="permissionContextId"></select>
									<div id="permissionContextIdErr" class="error"></div></td>
							</tr>
							<tr>
								<td style="width: 10px"><label>Permission-Name</label></td>
								<td colspan="5"><input class="input-sm" type="text"
									id="permissionLabel" size="35">&nbsp;<span
									id="permissionAddEditButtonId"><button
											class="permissionAddButtonClass input-sm ui-button ui-widget ui-corner-all pull-right">Add</button></span>
									<div id="permissionLabelErr" class="error"></div></td>
							</tr>


							<tr>
								<th colspan="6">Recorded-Permissions:--

									<div id="permissionDefinitionSavedSuccessId" class="success"></div>
								</th>
							</tr>

							<tr>
								<th width="30%">Base-Permission</th>

								<th width="30%" colspan="2">Custom-Permission &nbsp;</th>
								<th width="30%" colspan="3"><select
									class="form-control input-sm select1 permissionCustomContextContextIdClass"
									style="width: 100%;" id="permissionCustomContextContextId">

								</select></th>
							</tr>

							<tr>
								<td>
									<div id="basePermissionId20"></div>
								</td>
								<td>
									<div id="basePermissionId21"></div>
								</td>
								<th width="3%"><a href="#" id="basePermissionEditId21Id"><i
										class="fa fa-edit iconColor"></i></a> <br> <br> <span
									style='font-size: 20px;'>&#8624;</span> <br> <br> <a
									href="#" id="basePermissionDeleteId21Id"><i
										class="fa fa-trash iconColor"></i></a></th>

								<td>
									<div id="basePermissionId22"></div>
								</td>
								<th width="3%"><a href="#" id="basePermissionEditId22Id"><i
										class="fa fa-edit iconColor"></i></a> <br> <br> <span
									style='font-size: 20px;'>&#8624;</span> <br> <br> <a
									href="#" id="basePermissionDeleteId22Id"><i
										class="fa fa-trash iconColor"></i></a></th>
							</tr>
						</table>
					</div>
					<!-- /.box-body -->
				</div>
				<!-- /.box -->
			</div>
		</div>



		<!-- Data table -->
		<div class="row">
			<div class="col-xs-12">
				<div class="box">
					<div class="box-header with-border ">
						<h3 class="box-title">Domain-Associated-Permissions.</h3>
					</div>
					<!-- /.box-header -->
					<div class="box-body">
						<table id="grid_permission_tableId"
							class="table table-bordered table-striped grid_permission_table">
							<thead>
								<tr>
									<th>#</th>
									<th>ID</th>
									<th width="20%">Defined-Domain</th>
									<th width="40%">Associated-Permissions with a domain</th>
									<th>Edit</th>
									<th>Delete</th>
								</tr>
							</thead>
							<tbody id="grid_permission_tableBodyId">
							</tbody>
							<tfoot>
								<tr>
									<th>#</th>
									<th>ID</th>
									<th width="20%">Defined-Domain</th>
									<th width="40%">Associated-Permissions with a domain</th>
									<th>Edit</th>
									<th>Delete</th>
								</tr>
							</tfoot>

						</table>
					</div>
					<!-- /.box-body -->
				</div>
				<!-- /.box -->
			</div>
			<!-- /.col -->
		</div>
		<!-- /.row -->
		<!-- Data table -->
	</section>
	<!-- /.content -->

	
	<!-- page script -->
<link rel="stylesheet"
	href="https://code.jquery.com/ui/1.12.1/themes/trontastic/jquery-ui.css">
	<!-- Data table js requirement -->
	<!-- jQuery 3 -->
	<script
		src="<%request.getContextPath();%>/resources/assets/bower_components/jquery/dist/jquery.min.js"
		type="text/javascript"></script>
	<!-- Bootstrap 3.3.7 -->
	<script
		src="<%request.getContextPath();%>/resources/assets/bower_components/bootstrap/dist/js/bootstrap.min.js"
		type="text/javascript"></script>
		
		
		


	<script
		src="<%request.getContextPath();%>/resources/pagejs/domain_definition.js"></script>
	<script
		src="<%request.getContextPath();%>/resources/pagejs/domain_permission.js"></script>


	<script
		src="<%request.getContextPath();%>/resources/pagejs/permission_definition.js"></script>
	<script>
		$('#grid_permission_tableIdOld').DataTable({
			'paging' : true,
			'lengthChange' : true,
			'searching' : true,
			'ordering' : true,
			'info' : true,
			'autoWidth' : true
		});
	</script>



	<!-- Start: Dialogue boxes -->
    <div id="confirm_domain_delete" title="Are you sure to delete this domain?"
		style="display: none;">
		
		<h3>Deleting a domain will delete its all associated permissions and ACLs defined on it.</h3>
		<div id=domainDeleteSuccessMsgId class="success"></div>
	</div>
	
	
	<div id="confirm_domain_permission_delete" title="Domain-Definition-Delete ?"
		style="display: none;">
		<p>Are you sure to delete this domain permission?</p>
		<div id="deleteSuccessId" class="success"></div>
	</div>

	<div id="dialog-confirm-permission"
		title="Permission-Definition-Delete ?" style="display: none;">
		<p>Are you sure to delete this permission-definition ?</p>
		<div id="deletePermissionSuccessId" class="success"></div>
	</div>

	<div id="dialog-success" title="Action Success Response"
		style="display: none;">
		<p id="actionSuccessId" class="success"></p>

	</div>

	<div id="domainDefinitionHelpId" title="Domain-Definition-Help"
		style="display: none;">
		<p>This page has two seperate forms.
		<ul>
			<li>Domain-Definition-Form:-- Using this form, you can Add/Edit
				domain name.</li>
			<li>Domain-Associated-Permission-Form</li>
		</ul>
		</p>

	</div>

	<div id="permissionDefinitionHelpId" title="Permission-Definition-Help"
		style="display: none;">
		<p>This is a help for domain definition page.</p>
	</div>

	<!-- End: Dialogue boxes -->

</body>
</html>