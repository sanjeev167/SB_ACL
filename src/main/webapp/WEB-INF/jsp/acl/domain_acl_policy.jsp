<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>Domain ACL Policy</title>
</head>
<body>

	<!-- Main content -->
	<section class="content container-fluid">
		<div class="row">
			<div class="col-md-6 left-col">
				<div class="box" style="width: 100%; ">
					<div class="box-header with-border">

						<h3 class="box-title" id="formHeaderId_ownerSection">Add:--
							[Owner-Level ] Permission-Policies.</h3>
						<div class="box-tools pull-right">

							<button type="button" class="btn btn-box-tool"
								id="ownerPermissionFormHelp">
								<i class="fa fa-question"></i>
							</button>
							<button type="button" class="btn btn-box-tool"
								id="refreshOwnerFormId">
								<i class="fa fa-refresh"></i>
							</button>
							<button type="button" class="btn btn-box-tool"
								data-widget="collapse">
								<i class="fa fa-minus"></i>
							</button>
						</div>
					</div>
					<!-- /.box-header -->
					<div class="box-body form-group">
						<table class="table table-bordered ">
							<tr>
								<td style="width: 10px"><label>Domain</label></td>
								<td colspan="2"><select id="domainComboId_ownerSection"
									class="form-control input-sm select1" style="width: 100%;">

								</select> <span id="ownerDomainIdErr" class="error"></span></td>
							</tr>
							<tr>
								<td colspan="2" width="50%"><label>Permission
										Defined</label>
								<span id="ownerPermissionErrId" class="error"></span>		
								</td>
								<td width="40%" id="formSaveButtonId_ownerSection"><button
										class=" saveButtonClass_ownerSection input-sm ui-button ui-widget ui-corner-all pull-right">Save-Permission</button></td>
							</tr>
							<tr>
								<th width="30%"><input type="checkbox" id="ckbOwnerBaseCheckAll">&nbsp;All&nbsp;&nbsp;Base-Permission</th>
								<th width="30%">Custom-Permission</th>
								<th width="30%"><select
									id="customContextComboId_ownerSection"
									class="form-control input-sm select1" style="width: 100%;">
										<option value="">Select context</option>
								</select></th>
							</tr>
							<tr>
								<td id="basePermissionId_ownerSection" width="30px;">Select
									a domain.</td>
								<td id="customPermissionId1_ownerSection" width="30px;"></td>
								<td id="customPermissionId2_ownerSection" width="30px;"></td>
							</tr>
						</table>
					</div>
					<!-- /.box-body -->

				</div>
				<!-- /.box -->
			</div>

			<div class="col-md-6 right-col">
				<div class="box" style="width: 100%; ">
					<div class="box-header with-border">
						<h3 class="box-title" id="addEditFormHeaderId_roleSection">
							Add:-- [Role-Level ] Permission-Policies.</h3>
						<div class="box-tools pull-right">

							<button type="button" class="btn btn-box-tool"
								id="rolePermissionFormHelp">
								<i class="fa fa-question"></i>
							</button>

							<button type="button" class="btn btn-box-tool"
								id="refreshRoleFormId">
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
								<td style="width: 10px"><label>Domain</label></td>
								<td colspan="2"><select id="domainComboId_roleSection"
									class="form-control input-sm select1" style="width: 100%;">

								</select>
								<span id="roleDomainIdErr" class="error"></span>
								</td>
							</tr>
							<tr>
								<td><label>Permission-Defined</label><br><span id="rolePermissionErrId" class="error"></span></td>
								<td><select id="roleComboId_roleSection"
									class="form-control input-sm select1" style="width: 100%;">
										<option value="">Select Role</option>
								</select>
								<span id="roleRoleErrId" class="error"></span>
								</td>
								<td id="savePolicyButtonId_roleSection"><button
										class="saveButtonClass_roleSection input-sm ui-button ui-widget ui-corner-all pull-right">
										Save-Permission</button></td>
							</tr>
							<tr>
								<th width="30%"><input type="checkbox" id="ckbRoleBaseCheckAll">&nbsp;All&nbsp;&nbsp;Base-Permission
								
								</th>
								<th width="30%">Custom-Permission</th>
								<th width="30%"><select
									id="customContextComboId_roleSection"
									class="form-control input-sm select1" style="width: 100%;">
										<option value="">Select context</option>
								</select></th>

							</tr>
							<tr>
								<td width="30%" id="basePermissionId_roleSection"></td>
								<td width="30%" id="customPermissionId1_roleSection"></td>
								<td width="30%" id="customPermissionId2_roleSection"></td>
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
					<div class="box-header">
						<h3 class="box-title">ACL-Permission-Policies[
							Owner/Role-Level ] Details.</h3>
					</div>
					<!-- /.box-header -->
					<div class="box-body">
						<table id="grid_permission_tableId"
							class="table display table-bordered table-striped grid_permission_table">
							<thead>
								<tr>
									<th>#</th>
									
									<th>ACL Policy ID</th>
									<th>Domain</th>
									<th>Owner/Role</th>
									<th>Permission Policy Assigned [ Owner/Role ]</th>
									<th>Edit</th>
									<th>Delete</th>
								</tr>
							</thead>
							<tbody id="grid_permission_tableBodyId">

							</tbody>
							<tfoot>
								<tr>
									<th>#</th>
									
									<th>ACL Policy ID</th>
									<th>Domain</th>
									<th>Owner/Role</th>
									<th>Permission Policy Assigned [ Owner/Role ]</th>
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
		
		
		
		
	<script type="text/javascript"
		src="<%request.getContextPath();%>/resources/pagejs/acl_policy.js"></script>
	<script type="text/javascript"
		src="<%request.getContextPath();%>/resources/pagejs/owner_acl.js"></script>
	<script type="text/javascript"
		src="<%request.getContextPath();%>/resources/pagejs/role_acl.js"></script>

	


	<script>
		$(function() {
			$('#grid_permission_tableId1').DataTable({
				'paging' : true,
				'lengthChange' : true,
				'searching' : true,
				'ordering' : true,
				'info' : true,
				'autoWidth' : true
			})
		})
	</script>




	<!-- Start: Dialogue boxes -->

	<div id="confirmACLPermissionDeleteId" title="Confirm ACL delete."
		style="display: none;">
		<p>Are you sure to delete this ACL ?</p>
		<div id=aclPermissionDeleteSuccessMsgId class="success"></div>
	</div>


	<div id="ownerPermissionModalHelpId" title="Owner-Permission-Help"
		style="display: none;">
		<p>This is a help for owner-permission.</p>
	</div>

	<div id="rolePermissionModalHelpId" title="Role-Permission-Help"
		style="display: none;">
		<p>This is a help for role-permission.</p>
	</div>

<div id="success-dialogueId" title="Action Response"
		style="display: none;">
		<p id="successMessageId"></p>
	</div>

	<!-- End: Dialogue boxes -->



</body>
</html>