

<!-- Content Header (Page header) -->

<section class="content-header">
	<h1>Assign Web-Access-Rights</h1>	
</section>

<!-- Main content -->
<section class="content container-fluid">

	<div class="row">
		<div class="col-md-5 ">
		    <div class="box  box-primary" style="min-height: 530px;">
			
				<div class="box-header with-border">
					<h3 class="box-title">Select a web-page</h3>
					<div class="box-tools pull-right">

						<button type="button">
							<i class="fa fa-search searchClass" data-toggle="modal">&nbsp;</i>
						</button>
						<button type="button" id="reloadGrid">
							<i class="fa fa-refresh">&nbsp;Grid</i>
						</button>
						<button type="button" id="refreshGrid">
							<i class="fa fa-refresh">&nbsp;Page</i>
						</button>

					</div>
					<div style="text-align: center; padding-top: 10px; display: none;"
						id="roleResourceLabelId">
						<label id="roleAssUnassLabelId"></label> <label
							style="color: blue;"><span id="roleLabelForGridId"></span></label>
					</div>
				</div>

				<div class="box-body" >
					<table width="100%" id="accessRightsRbacId" 
						class="table table-striped table-bordered table-hover table-condensed dt-responsive data-table">
						<thead>
							<tr>
								<th width="6%">#</th>
								<th width="6%">ID</th>
								<th>App. Context</th>
								<th>Module</th>
								<th>Page</th>
								<th width="6%">Permission</th>
							</tr>
						</thead>

					</table>

				</div>
				<!-- /.box-body -->
			</div>
			<!-- /.box -->
		</div>
		<!-- /.col -->
		    
		<div class="col-md-4">
			<div class="box box-primary item"  id="selectRolePageId"
				style="display: none;min-height: 530px;">
				<div class="box-header with-border">

					<div class="box-title">Assign selected page to roles</div>
				</div>

				<div class="box-body ">
					<h5>
						<label>App. Context :&nbsp;</label>[<label style="color: blue;"
							class='onRoleSel' id="departmentNameIdForRole"></label>]
					</h5>
					<h5>
						<label>Module :&nbsp;</label>[<label id="moduleNameIdOnResSel"
							style="color: blue;"></label>]
					</h5>


					<h5>
						<label>Selected Page:&nbsp;</label>[<label style="color: blue;"
							id="selResIdForRoleAssignment">Resource</label>]
					</h5>
					<h5>
						<label>TPA [ <span style="color: red;">Total page
								assigned</span> ]
						</label>
					</h5>

					<div id="NoRoleDefinedEithinDepartmentId" class="item">
						<table
							class="table table-striped table-bordered table-hover table-condensed dt-responsive data-table">
							<thead>
								<tr>
									<th width="80%" style="color: red;">No role has yet been
										defined within this context.</th>
								</tr>
							</thead>

						</table>
					</div>
					<div id="roleDefinedEithinDepartmentId" class="item">
						<table id="roleSelectionTableId"
							class="table table-striped table-bordered table-hover table-condensed dt-responsive data-table">
							<thead>
								<tr>
									<th>All&nbsp;<input type="checkbox" name="resource"
										id="chkAllRoleId" />&nbsp;Roles
									</th>
									<th>Rights</th>
								</tr>
								<tr>

								</tr>
							</thead>
							<tbody id="roleHolder"></tbody>

							<tr id="hideRoleUpdateButtonId" style="text-align: left">
								<td colspan="2"><input type="button" value="Assign"
									id="savePageAssignmentButtonId" /></td>
							</tr>
						</table>
					</div>
				</div>
				<!-- /.box-body -->
			</div>
			<!-- /.box -->
		</div>
		<!-- /.col -->

		<div class="col-md-3">
			<div class="box box-primary item" id="selectPageListContainerId"
				style="display: none;min-height: 530px;">
				<div class="box-header with-border">
					<div class="box-title">Remove Assigned-Page</div>
				</div>

				<div class="box-body ">
					<h5>
						<label>App. Context :&nbsp;</label>[<label style="color: blue;"
							id="departmentNameIdOnResSel"></label>]
					</h5>

					<h5>
						<label>Module :&nbsp;</label>[<label id="moduleNameIdOnResUpd"
							style="color: blue;"></label>]
					</h5>
					<h5>
						<label>Selected Role :&nbsp;</label> [<label id="roleSelectedId"
							style="color: orange;">None</label>]
					</h5>

					<h5>
						<label style="color: red;">Select page for permission removal..</label>
					</h5>
					<div id="NoOpDefinedOnPageId" style="min-height: 240px;">
						<table width="100%" id="operationTableId"
							class="table table-striped table-bordered table-hover table-condensed dt-responsive data-table">

							<tbody>
								<tr>
									<th style="color: red;">No page is assigned.</th>
								</tr>
							</tbody>
						</table>
					</div>
					<div id="opDefinedOnPageId" style="min-height: 240px;">
						<table width="100%" id="operationTableId"
							class="table table-striped table-bordered table-hover table-condensed dt-responsive data-table">

							<tbody>

								<tr>
									<th><input type="checkbox" class="opSelClass"
										name="method" id="chkAll" />&nbsp;<label style="color: red;">Remove
											All</label>&nbsp;&nbsp;&nbsp;<span id="updateSuccessMsgId"></span></th>
								</tr>
								<tr>
									<td>
										<table>
											<tbody id="opHolder"></tbody>
											<tr id="hideUpdateButtonId">
												<td><input type="button" value="Remove"
													id="updatePageButtonId" /></td>
											</tr>
										</table>
									</td>
								</tr>
							</tbody>
						</table>
					</div>



				</div>
				<!-- /.box-body -->
			</div>
			<!-- /.box -->
		</div>
		<!-- /.col -->
	</div>
</section>
<!-- /.content -->

<input type="hidden" id="pageRecordIdForRole" />
<input type="hidden" id="opRecordIdForRole" />
<input type="hidden" id="accessRightsRbacId" />

<!-- Modals for this form -->

<style type="text/css">
.field-error {
	color: red;
	font-size: small;
}

.example-modal .modal {
	position: relative;
	top: auto;
	bottom: auto;
	right: auto;
	left: auto;
	display: block;
	z-index: 1;
}

.example-modal .modal {
	background: transparent !important;
}
</style>


<style type="text/css">
.modal-dialog {
	width: 400px;
}

.modal-header {
	background-color: #337AB7;
	padding: 16px 16px;
	color: #FFF;
	border-bottom: 2px dashed #337AB7;
}
</style>

<!-- modal for add/update/view/search -->
<div class="modal fade" id="modal-common">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title" id="commonModalTitleId">Common Modal</h4>
			</div>
			<div class="modal-body">
				<!-- form start -->
				<form class="form-horizontal" id="commonFormId" action="">
					<div class="box-body">
						<h5 id="successMsgId"></h5>
						<h5 id="searchMsgId"></h5>
						<div class="form-group">
							<label for="inputDepartmentName" class="col-sm-4 control-label">
								App. Context</label>
							<div class="col-sm-7">
								<select class="form-control" name="departmentNameId"
									id="departmentNameId">
									<option value="">-- Select --</option>

								</select> <span id="departmentNameId_err" class="field-error"></span>
							</div>
						</div>
						<div class="form-group">
							<label for="inputPageName" class="col-sm-4 control-label">
								View Pages</label>
							<div class="col-sm-7">
								<input type="radio" id="assignedPageViewId"
									name="pageViewCondition" value="A" />&nbsp;&nbsp;Assigned
								&nbsp;&nbsp; <input type="radio" id="unassignedPageViewId"
									name="pageViewCondition" value="U" />&nbsp;&nbsp;Unassigned
								&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" id="bothPageViewId"
									checked="checked" name="pageViewCondition" value="B" />
								&nbsp;&nbsp;Both [Unassigned/Assigned] <span id="pageNameId_err"
									class="field-error"></span>
							</div>
						</div>
						<div class="form-group" id="roleSelectionId"
							style="display: none;">
							<label for="inputPageName" id="roleLabelId"
								class="col-sm-4 control-label"> Role</label>
							<div class="col-sm-7">
								<select class="form-control" name="roleNameId" id="roleNameId">
									<option value="">-- Select --</option>
								</select> <span id="roleNameId_err" class="field-error"></span>
							</div>
						</div>

						<div class="form-group" id="moduleSelectionId"
							style="display: block;">
							<label for="inputModuleName" class="col-sm-4 control-label">
								Module</label>
							<div class="col-sm-7">
								<select class="form-control" name="moduleNameId"
									id="moduleNameId">
									<option value="">-- Select --</option>

								</select> <span id="moduleNameId_err" class="field-error"></span>
							</div>
						</div>


						<div class="form-group" id="pageSelectionId"
							style="display: block;">
							<label for="inputPageName" class="col-sm-4 control-label">
								Page</label>
							<div class="col-sm-7">
								<select class="form-control" name="pageNameId" id="pageNameId">
									<option value="">-- Select --</option>
								</select> <span id="pageNameId_err" class="field-error"></span>
							</div>
						</div>


					</div>
					<!-- /.box-body -->

					<!-- /.box-footer -->
				</form>

			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-info" data-dismiss="modal">Close</button>
				<button type="button"
					class="btn btn-warning commonButtonActionClass"
					id="commonButtonActionId">Search</button>
			</div>
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
</div>
<!-- /.modal -->


<!-- DataTables -->
<script
	src="<%request.getContextPath();%>/resources/assets/bower_components/jquery/dist/jquery.min.js"
	type="text/javascript"></script>
<script
	src="https://cdn.datatables.net/1.10.21/js/jquery.dataTables.min.js"></script>
<script
	src="https://cdn.datatables.net/1.10.21/js/dataTables.jqueryui.min.js"></script>

<script
	src="${contextPath}/resources/pagejs/webAccessRights.js"></script>




