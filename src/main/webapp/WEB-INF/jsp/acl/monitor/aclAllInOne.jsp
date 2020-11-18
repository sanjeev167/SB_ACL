

<section class="content">
<!-- ##### First Row end ##### -->
	<div class="row">
      <!-- First column -->
		<div class="col-md-5">
			<div class="box box-primary">
				<div class="box-header with-border">
					<h3 class="box-title">ACL Sids</h3>
					<div class="box-tools pull-right">
						
						<button type="button" id="reloadGridSid">
							<i class="fa fa-refresh">&nbsp;Grid</i>
						</button>
						
						<button type="button" class="btn btn-box-tool"
							data-widget="collapse">
							<i class="fa fa-minus"></i>
						</button>
					</div>
				</div>
				<div class="box-body">
					<%@ include file="acl-sid.jsp"%>
				</div>
				<!-- /.box-body -->
			</div><!-- /.box -->		
		</div><!-- /col end -->
     <!-- Second column -->
		<div class="col-md-7">
			<div class="box box-primary">
				<div class="box-header with-border">
					<h3 class="box-title">ACl Domain Classes</h3>
					<div class="box-tools pull-right">
						
						<button type="button" id="reloadGridAclClass">
							<i class="fa fa-refresh">&nbsp;Grid</i>
						</button>
						
						<button type="button" class="btn btn-box-tool"
							data-widget="collapse">
							<i class="fa fa-minus"></i>
						</button>
					</div>
				</div>
				<div class="box-body">
					<%@ include file="acl-class.jsp"%>
				</div><!-- /.box-body -->
			</div><!-- /.box -->
		</div><!-- /.col -->
		
 </div>
<!-- ##### Row end ##### -->


<!-- ##### Second Row end #### -->
	<div class="row">

		<div class="col-md-5">
			<div class="box box-primary">
				<div class="box-header with-border">
					<h3 class="box-title">ACL Domain Objects Identity</h3>

					<div class="box-tools pull-right">
						
						<button type="button" id="reloadGridObjIdent">
							<i class="fa fa-refresh">&nbsp;Grid</i>
						</button>
						
						<button type="button" class="btn btn-box-tool"
							data-widget="collapse">
							<i class="fa fa-minus"></i>
						</button>


					</div>

				</div>
				<div class="box-body">
					<%@ include file="acl-obj-identity.jsp"%>

				</div>
				<!-- /.box-body -->
			</div>
			<!-- /.box -->

		</div>

		<div class="col-md-7">
			<div class="box box-primary">
				<div class="box-header with-border">
					<h3 class="box-title">Domain Objects ACL Entry</h3>
					<div class="box-tools pull-right">
						
						<button type="button" id="reloadGridAclEntry">
							<i class="fa fa-refresh">&nbsp;Grid</i>
						</button>
						
						<button type="button" class="btn btn-box-tool"
							data-widget="collapse">
							<i class="fa fa-minus"></i>
						</button>
					</div>
				</div>

				<div class="box-body">
					<%@ include file="acl-entry.jsp"%>
				</div>
				<!-- /.box-body -->
			</div>
			<!-- /.box -->
		</div>
		<!-- /.col -->
	
	</div>

<!-- ##### Row end ##### -->




</section>

<style>
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

<div class="modal fade" id="modal-default">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title">Default Modal</h4>
			</div>
			<div class="modal-body">
				<p>One fine body&hellip;</p>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default pull-left"
					data-dismiss="modal">Close</button>
				<button type="button" class="btn btn-primary">Save changes</button>
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

<script src="${contextPath}/resources/pagejs/acl_sid.js"></script>
<script src="${contextPath}/resources/pagejs/acl_class.js"></script>
<script src="${contextPath}/resources/pagejs/acl_objectIdentity.js"></script>
<script src="${contextPath}/resources/pagejs/acl_entry.js"></script>

