

<!-- Content Header (Page header) -->

<section class="content-header">
	<h1>Manage Page-In-Module</h1>
	<ol class="breadcrumb">
		<li><a href="#"><i class="fa fa-dashboard"></i> Dashboard</a></li>
		<li class="active">Page-In-Module</li>
	</ol>
</section>

<!-- Main content -->
<section class="content container-fluid">
	<div class="row">
		<div class="col-md-12">
			<div class="box box-primary ">
				<div class="box-header with-border">

					<div class="box-title">
						<button type="button">
							<i class="fa fa-plus addClass" data-toggle="modal">&nbsp;</i>
						</button>
						<button type="button" id="deleteSelected">
							<i class="fa fa-trash" data-toggle="modal">&nbsp;</i>
						</button>
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
				</div>

				<div class="box-body ">
					<table width="100%" id="pageId"
						class="table table-striped table-bordered table-hover table-condensed dt-responsive data-table">
						<thead>
							<tr>
								<th width="6%">#</th>
								<th width="7%"><input id="chkAll" type="checkbox">&nbsp;All</th>

								<th width="6%">ID</th>
								<th>App. Context</th>
								<th>Module</th>
								<th>Page</th>
								<th>Base Url</th>
								<th width="6%">View</th>
								<th width="6%">Edit</th>
								<th width="6%">Delete</th>

							</tr>
						</thead>

					</table>
				</div>
				<!-- /.box-body -->
			</div>
			<!-- /.box -->
		</div>
		<!-- /.col -->
	</div>
</section>
<!-- /.content -->


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
								<select class="form-control" name="departmentNameId" id="departmentNameId" >
									<option value="">-- Select --</option>	
																	
								</select>
								 <span id="departmentNameId_err" class="field-error"></span>
							</div>
						</div>
						<div class="form-group">
							<label for="inputModuleName" class="col-sm-4 control-label">
								Module</label>
							<div class="col-sm-7">
								<select class="form-control" name="moduleNameId" id="moduleNameId" >
									<option value="">-- Select --</option>	
																	
								</select>
								 <span id="moduleNameId_err" class="field-error"></span>
							</div>
						</div>
						<div class="form-group" id="pageLabledId">
							<label for="inputPageName" class="col-sm-4 control-label">
								Page</label>
							<div class="col-sm-7">
								<input type="text" class="form-control" name="pageName"
									id="pageNameId" placeholder="Page Name">
								<span id="pageName_err" class="field-error"></span>
							</div>
						</div>
						
						<div class="form-group" id="baseurlLabledId">
							<label for="inputBaseurlName" class="col-sm-4 control-label">
								Base Url</label>
							<div class="col-sm-7">
								<input type="text" class="form-control" name="baseurl"
									id="baseurlId" placeholder="Base Url">
								<span id="baseurl_err" class="field-error"></span>
							</div>
						</div>
						
						<input hidden="true" name="id" id="pageRecordId" />
					</div>
					<!-- /.box-body -->

					<!-- /.box-footer -->
				</form>

			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-info" data-dismiss="modal">Close</button>
				<button type="button"
					class="btn btn-warning commonButtonActionClass"
					id="commonButtonActionId">Save</button>
			</div>
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
</div>
<!-- /.modal -->



<!-- modal for delete -->
<div class="modal fade" id="modal-delete">
	<div class="modal-dialog modal-sm modal-confirm">
		<div class="modal-content">
			<div class="modal-header">
				<h4 class="modal-title">Are you sure?</h4>

			</div>
			<div class="modal-body">
				<h5 id="deleteSuccessMsgId"></h5>
				<label>Do you really want to delete this record? This
					process cannot be undone.</label>
			</div>
			<input hidden="true" name="recordId" id="recordIdForDelete" />
			<div class="modal-footer">
				<button type="button" class="btn btn-info" data-dismiss="modal">Cancel</button>
				<button type="button" class="btn btn-danger" id="deleteFormButtonId">Delete</button>
			</div>
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
</div>
<!-- /.modal -->


<!-- modal for selected Delete -->
<div class="modal fade" id="modal-sDelete">
	<!-- modal for selected delete -->
	<div class="modal-dialog modal-sm modal-confirm">
		<div class="modal-content">
			<div class="modal-header">
				<h4 class="modal-title">Are you sure?</h4>

			</div>
			<div class="modal-body">
			    <h5 id="deleteSelectedSuccessMsgId"></h5>
				<label id="showAllertMsg"></label>
				<div id="showSelectedRow" style="margin-top: 10px;"></div>

			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-info" data-dismiss="modal">Cancel</button>
				<button type="button" class="btn btn-danger"
					id="deleteSelFormButtonId">Delete</button>
			</div>

		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
</div>
<!-- /.modal -->


<!-- modal for success -->
<div class="modal fade" id="modal-success">
	<div class="modal-dialog modal-sm modal-success">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title">Success Message</h4>
			</div>
			<div class="modal-body">
				<div class="modal-body">Record has been saved successfully.</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default pull-left"
					data-dismiss="modal">Cancel</button>
				<button type="button" class="btn btn-primary">Yes</button>
			</div>
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
</div>
<!-- /.modal -->

<!-- End of modals for form -->

<!-- DataTables -->
<script
	src="<%request.getContextPath();%>/resources/assets/bower_components/jquery/dist/jquery.min.js"
	type="text/javascript"></script>
<script
	src="https://cdn.datatables.net/1.10.21/js/jquery.dataTables.min.js"></script>
<script
	src="https://cdn.datatables.net/1.10.21/js/dataTables.jqueryui.min.js"></script>

<script src="${contextPath}/resources/pagejs/page.js"></script>