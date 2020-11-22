<!-- Content Header (Page header) -->
<section class="content-header">
	<h1>Manage Role Hierarchy</h1>	
</section>

<!-- Main content -->
<section class="content container-fluid" >
	<div class="row">
		<div class="col-md-5" id="roleHirarchyGridId">
			<div class="box box-primary" style="min-height: 530px;">
				
				<div class="box-header with-border">
					<table width="100%">
						<tr>
							<td width="25%"><label>Application Context</label></td>
							<td width="25%" id="appContextIdg">[ <strong
								style="color: black;"><span
									id="showingAppContextLebelId">Showing All Context</span></strong> ]
							</td>
						</tr>
					</table>


				</div>
				<div class="box-body ">
					<table width="100%" id="roleHierarchyId"
						class="  table table-striped table-bordered table-hover table-condensed dt-responsive data-table">
						<thead>
							<tr>
								<th width="2%">#</th>
								<th><input id="chkAll" type="checkbox">&nbsp;All</th>
								<th>ID</th>
								<th>Parent</th>
								<th>Child</th>
								<th>Contents</th>
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

		<div class="col-md-7" id="roleHirarchyWidthChangeId">
			<div class="box box-primary " style="min-height: 530px;">
				<div class="box-header with-border">
					<div class="box-title">
						Role Hierarchy View in [ <input type="radio"
							name="viewCompactOrLarge" id="roleHirarchyCompactId"
							checked="checked" value="C" /> Compact <input type="radio"
							id="roleHirarchyLargerId" name="viewCompactOrLarge" value="L" />
						 Expanded ] Mode
					</div>
				</div>
				
				<div class="box-body">
					<div id="roleHierarchyTree"></div>
					<!-- /.box-body -->
				</div>
				<!-- /.box -->
			</div>
			<!-- /.col -->
		</div>
	</div>
</section>
<!-- /.content -->





<!-- Modals for this form -->
<style type="text/css">
.field-error {
	color: red;
	font-size: small;
}

</style>

<!-- modal for add/update/view/search -->
<div class="modal fade" id="modal-common">
	<div class="modal-dialog modal-md">
		<div class="modal-content" style="border-radius: 10px;">
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
								<select class="form-control" name="contextId" id="contextId">
									<option value="">-- Select --</option>
								</select> <span id="contextId_err" class="field-error"></span>
							</div>
						</div>
						<label style="color: black;" id="roleHierarchyDetailsLabelId">Input <span
							style="color: red;">Role-Hierarchy-Tree</span> details
						</label>
						<div class="form-group" id="parentIdLabelId"
							style="display: none;">
							<label class="col-sm-4 control-label">Parent-Node</label>
							<div class="col-sm-7">
								<select class="form-control" name="parentId" id="parentId">
									<option value="">-- Select --</option>
								</select> <span id="parentId_err" class="field-error">Root child
									node doesn't require parent node.</span>
							</div>
						</div>
						<div class="form-group" id="childNameLabledId"
							style="display: none;">
							<label for="contents" class="col-sm-4 control-label">Child-Node</label>
							<div class="col-sm-7">
								<input type="text" class="form-control" name="childName"
									id="childNameId" placeholder="Tree Child Node"> <span
									id="childName_err" class="field-error"></span>
							</div>
						</div>
						<div class="form-group" id="contentsLabledId"
							style="display: none;">
							<label for="contents" class="col-sm-4 control-label">
								Child Contents</label>
							<div class="col-sm-7">
								<input type="text" class="form-control" name="contents"
									id="contentsId" placeholder="Child details if any."> <span
									id="contents_err" class="field-error"></span>
							</div>
						</div>
						<label style="color: black;" id="roleBindingInHierarchyTreeLabelId">Bind a role with <span
							style="color: red;">Child-Node</span> in <span
							style="color: red;">Role-Hierarchy-Tree</span></label>
						<div class="form-group" id="roleNameIdLabledId"
							style="display: none;">
							<label for="roleNameId" class="col-sm-4 control-label">
								Associated Role</label>
							<div class="col-sm-7">
								<select class="form-control" name="roleNameId" id="roleNameId">
									<option value="">-- Select --</option>
								</select> <span id="roleNameId_err" class="field-error"></span>
							</div>
						</div>
						<input hidden="true" id="roleHierarchyRecordId" />
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
		<div class="modal-content" style="border-radius: 10px;">
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
		<div class="modal-content" style="border-radius: 10px;">
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
		<div class="modal-content" >
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
<!-- End of modals for form -->


<!-- Data table css styling using data table -->
<!-- This css is specific to role hierarchy page only. Let it be there only -->
<link rel="stylesheet"
	href="https://code.jquery.com/ui/1.12.1/themes/trontastic/jquery-ui.css">


<link href="${contextPath}/resources/roleHierachy/CSS/jHTree.css" rel="stylesheet" type="text/css" />

<!-- jQuery 3 -->
	<script
		src="<%request.getContextPath();%>/resources/assets/bower_components/jquery/dist/jquery.min.js"></script>
	<!-- Bootstrap 3.3.7 -->
	<script
		src="<%request.getContextPath();%>/resources/assets/bower_components/bootstrap/dist/js/bootstrap.min.js"
		type="text/javascript"></script>
    
<script	src="${contextPath}/resources/pagejs/roleHierarchy.js"></script>

<!-- jH tree related js . Keep it here only just below jquery qnd boot strap js not anywhere else. it will maintain js calling order  --> 	
<script	src="${contextPath}/resources/roleHierachy/js/jquery-ui-1.10.4.custom.min.js" type="text/javascript"></script>
<script	src="${contextPath}/resources/roleHierachy/js/jQuery.jHTree.js"	type="text/javascript"></script>
	
