

<!-- Content Header (Page header) -->

<section class="content-header">
	<h1>Manage Application Menu</h1>
	<ol class="breadcrumb">
		<li><a href="#"><i class="fa fa-dashboard"></i> Dashboard</a></li>
		<li class="active">Application Menu</li>
	</ol>
</section>
<div class="col-md-12" style="display: none;" id="hOrientationId">
	<div class="box box-primary ">
		<div class="box-body " style="position: relative;">
			<header class="main-header">
				<!-- Logo -->
				<a href="#" class="logo"> <!-- mini logo for sidebar mini 50x50 pixels -->
					<div class="box-title treeOrientationHtoV">
						Orientation: <input type="radio" name="orientationH" value="V" />
						V <input type="radio" name="orientationH" checked="checked"
							value="H" /> H
					</div>
				</a>
				<!-- Header Navbar: style can be found in header.less -->
				<nav class="navbar navbar-static-top"
					style="vertical-align: middle; color: white;">
					<div style="margin-left: 30%;">
						<h4>Yet to be implemented.</h4>
					</div>
				</nav>
			</header>

			<!-- /.box-body -->
		</div>
		<!-- /.box -->
	</div>
	<!-- /.col -->
</div>

<!-- Main content -->
<section class="content container-fluid">

	<div class="col-md-9" id="changeGridWidthId">
		<div class="box box-primary " style="min-height: 530px;">
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
			<div class="box-header with-border">
				<table width="100%">
					<tr>
						<td width="25%"><label>Application Context</label></td>
						<td width="25%" id="appContextIdg">[ <strong
							style="color: blue;"><span id="showingAppContextLebelId">Showing
									All Context</span></strong> ]
						</td>
						<td width="25%"><label>Tree Menu Type</label></td>
						<td width="25%" id="treeMenuTypeIdg">[ <strong
							style="color: blue;"><span id="showingTreeTypeLebelId">Showing
									All tree type</span></strong> ]
						</td>
					</tr>
				</table>


			</div>
			<div class="box-body " >
				<table width="100%" id="manageMenuId"
					class="table table-striped table-bordered table-hover table-condensed dt-responsive data-table">
					<thead>
						<tr>
							<th width="2%">#</th>
							<th width="10%"><input id="chkAll" type="checkbox">&nbsp;All</th>

							<th width="6%">ID</th>
							<th>Context</th>
							<th>Menu</th>
							<th width="2%">Type</th>
							<th>Node[P]</th>
							<th>Child[N/L</th>
							<th>Page</th>
							<th width="2%">Img</th>
							<th width="2%">V</th>
							<th width="2%">E</th>
							<th width="2%">D</th>

						</tr>
					</thead>

				</table>
			</div>
			<!-- /.box-body -->
		</div>
		<!-- /.box -->
	</div>
	<!-- /.col -->

	<div class="col-md-3" style="display: block;" id="vOrientationId">
		<div class="box box-primary " style="min-height: 530px;">
			<div class="box-header with-border">
				<div class="box-title treeOrientationVtoH">
					Tree Structure [ Orientation: <input type="radio"
						name="orientationV" checked="checked" value="V" /> V <input
						type="radio" name="orientationV" value="H" /> H ]
				</div>
			</div>
			
			<div class="box-body" >
				<aside class="main-sidebar"
					style="position: relative; width: inherit;">
					<section class="sidebar">
						
						<div id="specificTreeMenuId"></div>
					</section>
				</aside>
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
							<label for="departmentMasterName" class="col-sm-4 control-label">
								App. Context</label>
							<div class="col-sm-7">
								<select class="form-control" name="departmentMasterId"
									id="departmentMasterId">
									<option value="">-- Select --</option>
								</select> <span id="departmentMasterId_err" class="field-error"></span>
							</div>
						</div>

						<div class="form-group">
							<label for="treeMenuTypeName" class="col-sm-4 control-label">
								Tree Menu</label>
							<div class="col-sm-7">
								<select class="form-control" name="treeMenuTypeId"
									id="treeMenuTypeId">
									<option value="">-- Select --</option>
								</select> <span id="treeMenuTypeId_err" class="field-error"></span>
							</div>
						</div>


						<div class="form-group" id="nodeTypeLabelId"
							style="display: none;">
							<label class="col-sm-4 control-label" style="color: red">
								Node Type</label>
							<div class="col-sm-7">
								<input type="radio" id="nodeTypeId" class="nodeType"
									name="nodeType" checked value="N">&nbsp;&nbsp;<strong>Node</strong>
								&nbsp;&nbsp; <input type="radio" id="leafTypeId" name="nodeType"
									value="L">&nbsp;&nbsp;<strong style="color: green">Leaf</strong>
							</div>
						</div>



						<div class="form-group" id="menuManagerParentLabelId"
							style="display: none;">
							<label class="col-sm-4 control-label"> Parent Node</label>
							<div class="col-sm-7">
								<select class="form-control" name="menuManagerParentId"
									id="menuManagerParentId">
									<option value="">-- Select --</option>
								</select> <span id="menuManagerParentId_err" class="field-error"></span>
							</div>
						</div>
						<div class="form-group" id="nodeNameLabledId"
							style="display: none;">
							<label for="nodeName" class="col-sm-4 control-label">
								Node or Leaf</label>
							<div class="col-sm-7">
								<input type="text" class="form-control" name="nodeName"
									id="nodeNameId" placeholder="Node or Leaf Name"> <span
									id="nodeName_err" class="field-error"></span>
							</div>
						</div>
						<div class="form-group" id="imgUrlLabledId" style="display: none;">
							<label for="imgUrl" class="col-sm-4 control-label"> Image</label>
							<div class="col-sm-7">
								<%@ include file="fontAwsome.jsp"%>
								<span id="imgUrl_err" class="field-error"></span>
							</div>
						</div>
						<div class="form-group" id="moduleLabelId" style="display: none;">
							<label class="col-sm-4 control-label"> Module</label>
							<div class="col-sm-7">
								<select class="form-control" name="moduleId" id="moduleId">
									<option value="">-- Select --</option>

								</select> <span id="moduleId_err" class="field-error"></span>
							</div>
						</div>
						<div class="form-group" id="leafUrlLabelId" style="display: none;">
							<label class="col-sm-4 control-label"> Leaf [ Page ]</label>
							<div class="col-sm-7">
								<select class="form-control" name="pageMasterId"
									id="pageMasterId">
									<option value="">-- Select --</option>
								</select> <span id="pageMasterId_err" class="field-error"></span>
							</div>
						</div>

						<input hidden="true" id="manageMenuRecordId" />
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
<div class="modal fade" id="modal-alert">
	<div class="modal-dialog modal-sm modal-danger">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title">Alert</h4>
			</div>
			<div class="modal-body">
				<div class="modal-body">
					<h4>
						Root node has not yet been created. Please do it first before
						creating any node or a leaf. Create it within parent node as
						"None" if it is there or create it without selecting it if parent
						node as "None" is not there.
						<p></p>
						<p>Parent node as "None" is required for creating a root node.
						</p>
					</h4>
				</div>
			</div>
			<div class="modal-footer">
				<button type="button" data-dismiss="modal" id="openCommonModalId"
					class="btn btn-danger">OK</button>
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

<script src="${contextPath}/resources/pagejs/manageMenuTree.js"
	type="text/javascript"></script>

<!-- End of modals for form -->

