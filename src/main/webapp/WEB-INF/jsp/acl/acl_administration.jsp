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
<body class="hold-transition skin-red sidebar-collapse   sidebar-mini">
	<!-- page content -->
	<section class="content container-fluid">

		<div class="row">
			<div class="col-md-6">
				<div class="box" style="width: 100%">
					<div class="box-header with-border">

						<h3 class="box-title">Logged-In User's Permission-Details.</h3>
						<div class="box-tools pull-right">
							<button type="button" class="btn btn-box-tool"
								id="loggedInUserHelpId">
								<i class="fa fa-question"></i>
							</button>
							<button type="button" class="btn btn-box-tool"
								id="loggedInUserRefreshId">
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
						<form:form action="" modelAttribute="aclrights">

							<table class="table table-bordered owner_table">

								<tr>
									<td>Domain</td>
									<td colspan="2"><strong style="color: blue;"> [ <span
											id="domainId">${domain}</span> ]
									</strong></td>
								</tr>
								<tr>
									<td>Object-Id</td>
									<td colspan="2"><strong style="color: blue;"> [ <span
											id="selectedObjectId">${selectedObjectId}</span> ]
									</strong></td>
								</tr>
								<tr>
									<th colspan="3">Permission related actions.
									     
										<div class="box-tools pull-right">
											<c:if test="${hasAdministrationRights}">
												<button type="button" data-toggle="tooltip"
													title="Share Permission"
													class="openSharePermissionClass  btn btn-box-tool">
													<i class="fa fa-share" style='color: blue;'></i>
												</button>
												<button type="button" data-toggle="tooltip"
													title="Change Owner"
													class="changeObjectOwnerClass  btn btn-box-tool">
													<i class="fa fa-user" style='color: blue;'></i>
												</button>
												<button type="button" data-toggle="tooltip"
													title="Delete complete ACL"
													class="deleteObjectAclAndObjectClass  btn btn-box-tool">
													<i class="fa fa-trash" style='color: red;'></i>
												</button>
											</c:if>
										</div>
                                        <p style="color:green;"> Administrator
                                        <c:if test="${isObjectOwner}"> [
									      <strong style="color:orange;">Owner</strong>
									      ] 
									      </c:if>:--  									      
									     <c:out value="${username}"></c:out>
									     <strong style='color:red;'>  
									      <c:out value="${rolenames}"></c:out>
									      </strong>
									    </p>
									</th>
								</tr>
								<tr>
									<th width="30%">Base-Permission</th>
									<th width="30%">Custom-Permission</th>
									<th width="30%"><select
										id="customContextComboId_adminSection"
										class="form-control input-sm select1" style="width: 100%;">
											<option>Select context</option>
									</select></th>
								</tr>

								<tr>
									<td width="30%" id="basePermissionId_adminSection"></td>
									<td width="30%" id="customPermissionId1_adminSection"></td>
									<td width="30%" id="customPermissionId2_adminSection"></td>
								</tr>
							</table>
						</form:form>
					</div>

					<!-- /.box-body -->
				</div>
				<!-- /.box -->
			</div>


			<div class="col-md-6">
				<div class="box" style="width: 100%">
					<div class="box-header with-border ">
						<h3 class="box-title">
							<span id="addEditHeaderId">Share/Edit/Change-Owner</span> [
							Object-Permission ]

						</h3>
						<div class="box-tools pull-right">
							<button type="button" class="btn btn-box-tool"
								id="permissionHelpId">
								<i class="fa fa-question"></i>
							</button>
							<button type="button" class="btn btn-box-tool"
								id="permissionRefreshId">
								<i class="fa fa-refresh"></i>
							</button>
							<button type="button" class="btn btn-box-tool"
								data-widget="collapse">
								<i class="fa fa-minus"></i>
							</button>
						</div>
					</div>
					<!-- /.box-header -->
					<div class="box-body ">
						<table class="table table-bordered">

							<tr>
								<td width="30%">Share User/Role</td>

								<td width="10%"><c:if test="${isObjectOwner}">
										<input type="radio" name="sidtype" value="R" id="roleId"> Role&nbsp;&nbsp;&nbsp;
									</c:if> <input type="radio" class="minimal" name="sidtype" value="U"
									id="userId"> User <span id="sidTypeErrId" class="error"></span>
								</td>

								<td width="50%"><select
									class="form-control input-sm select1" id="roleComboId"></select>
									<span id="roleComboIdErr" class="error"></span></td>
							</tr>


							<tr id="userInRoleTrlId">
								<td style="color: green; font-weight: bold;"><span
									id="userInRoleTdlId"><i class='fa fa-user'></i>[ New ]</span> <br>
									<span id="newObjectOwnerLabelIdErr" class="error"></span></td>
								<td><select id="userComboId"
									class="form-control input-sm select1">
										<option value="">-Select-</option>
								</select> <span id="userComboIdErr" class="error"></span></td>
								<td><strong style="color: blue; display: none"
									id="oldObjectOwnerLabelId"> </strong> <span
									id="oldObjectOwnerLabelIdErr" class="error"></span></td>
							</tr>

							<tr>

								<th colspan="2">Owner/Role Permission-Details:-- <span
									id="permissionIdErr" class="error"></span>
								</th>
								<td style="text-align: center;"><c:if
										test="${!hasAdministrationRights}">
										<span style="color: green;">No Administrative-Rights</span>
									</c:if> <span style="text-align: right;"> <c:if
											test="${hasAdministrationRights}">
											<span id="changeButtonId"><b>
													<button
														class="sharePermissionClass input-sm ui-button ui-widget ui-corner-all pull-right ">Share-Permission</button>
											</b></span>
										</c:if>
								</span></td>
							</tr>
							<tr>
								<th width="30%"><input type="checkbox"
									id="permissionAllChkId">&nbsp;All&nbsp;Base-Perm</th>
								<th>Custom-Permission</th>
								<th><select class="form-control input-sm select1"
									id="customContextComboId_permissionSection">
										<option value="">-select context-</option>
								</select></th>
							</tr>
							<tr>
								<td id="basePermissionId_permissionSection" width="30%"></td>
								<td width="30%" id="customPermissionId1_permissionSection">No
									permission found.</td>
								<td width="30%" id="customPermissionId2_permissionSection">No
									permission found.</td>
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
						<h3 class="box-title">Other Users'/Roles' Object-Permission</h3>
					</div>
					<!-- /.box-header -->

					<div class="box-body">

						<table id="permissionTable"
							class="table table-bordered table-striped grid_permission_table">
							<thead>
								<tr>
									<th width="10%">ID</th>
									<th width="15%">Object Owner</th>
									<th width="12%">User/Role</th>
									<th>Permission</th>
									<!--<c:if test="${hasAdministrationRights}">-->
										<th width="10%">Edit</th>
										<th width="10%">Delete</th>
									<!--</c:if>-->
								</tr>
							</thead>
							<tbody id="grid_permission_tableBodyId">

							</tbody>
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
	<!-- /page content -->

	<!-- jQuery 3 -->
	<script
		src="<%request.getContextPath();%>/resources/assets/bower_components/jquery/dist/jquery.min.js"
		type="text/javascript"></script>
	<script
		src="https://cdn.datatables.net/1.10.21/js/jquery.dataTables.min.js"></script>
	<script
		src="https://cdn.datatables.net/1.10.21/js/dataTables.jqueryui.min.js"></script>



	<script type="text/javascript"
		src="<%request.getContextPath();%>/resources/pagejs/acl_admin.js"></script>
	<script
		src="<%request.getContextPath();%>/resources/pagejs/acl_admin_grid.js"
		type="text/javascript"></script>

	<script
		src="<%request.getContextPath();%>/resources/pagejs/acl_admin_permission_share.js"
		type="text/javascript"></script>

	<script
		src="<%request.getContextPath();%>/resources/pagejs/acl_admin_owner_change.js"
		type="text/javascript"></script>

	<script>
		$(function() {
			$('#permissionTableOld').DataTable({
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

	<div id="confirmACLObjectPermissionDeleteId"
		title="Confirm Object-ACL delete." style="display: none;">
		<p>Are you sure to delete this Object-ACL ?</p>
		<div id="aclPermissionDeleteSuccessMsgId" class="success"></div>
	</div>

	<div id="loggedInUserModalHelpId" title="Logged-In-User-Help"
		style="display: none;">
		<p>A Logged-In user.</p>

		<ul>
			<li>as an <span style="color: orange; font-weight: bold;">Object-Owner</span>
				<ul>
					<li>can share/update/delete ACL of an object.</li>
					<li style="color: green; font-weight: bold;">can't share
						ADMINISTRATION rights.</li>
				</ul>
			</li>

			<li>with role <span style="color: orange; font-weight: bold;">ROLE_SUPER_ADMIN</span>
				<ul>
					<li>can share/update/delete ACL of an object.</li>
					<li style="color: green; font-weight: bold;">Can change Object
						Ownership.</li>
					<li style="color: green; font-weight: bold;">Can delete
						complete ACL.</li>
					<li style="color: green; font-weight: bold;">Can assign
						ADMINISTRATION rights.</li>
				</ul> <br> <span style="font-weight: bold;"> Remark: Deleting
					complete ACL will prevent everyone to access it except
					ROLE_SUPER_ADMIN user.</span>
			</li>
		</ul>
	</div>

	<div id="permissionModalHelpId" title="Share/Edit/Change-Owner-Help"
		style="display: none;">
		<p>This single form is being used for three purposes.</p>
		<ul>
			<li>For sharing-permission [ ACL ] with a user or role.</li>
			<li>For updating existing-permission [ ACL ].</li>
			<li>For changing Object-Ownership.</li>
		</ul>
	</div>

	<div id="success-dialogueId" title="Action Response"
		style="display: none;">
		<p id="successMessageId"></p>
	</div>


	<div id="completeACLDeleteConfirm-dialogueId"
		title="Complete ACL Delete Confirm?" style="display: none;">
		<p>
		<ul>
			<li style="color: red; font-weight: bold; font-size: 15px;">
				This delete-action will delete followings.
				<ul>
					<li style="color: green; font-weight: bold; font-size: 15px;">Complete
						Owner's permissions.</li>
					<li style="color: green; font-weight: bold; font-size: 15px;">Complete
						other users' permissions.</li>
					<li style="color: green; font-weight: bold; font-size: 15px;">Complete
						roles' permissions.</li>

				</ul>
			</li>
			<li style="color: red; font-weight: bold; font-size: 15px;">
				Only ROLE_SUPER_ADMIN's permissions can't be deleted.</li>
		</ul>
		</p>
	</div>

	<!-- End: Dialogue boxes -->





</body>
</html>