<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<section class="content">
	<!-- Admin Post -->
	<div class="callout callout-info">
		<c:url var="addAdminPostUrl" value="/admin/add" />

		<h4>
			Admin-Post [ Domain ] <span style="margin-left: 30%; color: yellow;">ACL-POLICY</span>
			<button type="button" class="btn btn-box-tool"
				id="viewAclPolicyModalHelpId1" style="color: white">
				<i class="fa fa-eye"></i>
			</button>
			<span class="pull-right"><a href="${addAdminPostUrl}"><i class="fa fa-plus">&nbsp;&nbsp;&nbsp;Post</i></a></span>
		</h4>
		<hr />
		<p>
		<table width="100%">
			<tbody>
				<tr>
					<th>#</th>
					<th>Date</th>
					<th>Message</th>
					<th>Edit</th>
					<th>Delete</th>
					<th>Permission [ Share/Update/Delete ]</th>

				</tr>

				<c:forEach items="${adminposts}" var="post">
					<c:url var="editUrl" value="/admin/edit?id=${post.id}" />
					<c:url var="deleteUrl" value="/admin/delete?id=${post.id}" />
					<c:url var="acl_admin"
						value="/acl_admin?id=${post.id}&domain=${adminDomain}" />
					<tr>
						<td><c:out value="${post.id}" /></td>
						<td><c:out value="${post.date}" /></td>
						<td><c:out value="${post.message}" /></td>
						<td><a href="${editUrl}"><i class="fa fa-edit"></i></a></td>
						<td><a href="${deleteUrl}"><i class="fa fa-trash"></i></a></td>
						<td><a href="${acl_admin}"><i class="fa fa-share-alt"></i></a></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		</p>
	</div>
	<!-- Personal Post -->
	<div class="callout callout-success">
		<c:url var="addPersonalPostUrl" value="/personal/add" />

		<h4>
			Personal-Post [ Domain ] <span style="margin-left: 28%; color: yellow;">ACL-POLICY</span>
			<button type="button" class="btn btn-box-tool"
				id="viewAclPolicyModalHelpId2" style="color: white">
				<i class="fa fa-eye"></i>
			</button>
			<span class="pull-right"><a href="${addPersonalPostUrl}"><i class="fa fa-plus">&nbsp;&nbsp;&nbsp;Post</i>
					</a></span>
		</h4>
		<hr />
		<p>
		<table width="100%">
			<tbody>
				<tr>
					<th>#</th>
					<th>Date</th>
					<th>Message</th>
					<th>Edit</th>
					<th>Delete</th>
					<th>Permission [ Share/Update/Delete ]</th>
				</tr>
				<c:forEach items="${personalposts}" var="post">
					<c:url var="editUrl" value="/personal/edit?id=${post.id}" />
					<c:url var="deleteUrl" value="/personal/delete?id=${post.id}" />
					<c:url var="acl_admin"
						value="/acl_admin?id=${post.id}&domain=${personalDomain}" />
					<tr>
						<td><c:out value="${post.id}" /></td>
						<td><c:out value="${post.date}" /></td>
						<td><c:out value="${post.message}" /></td>
						<td><a href="${editUrl}"><i class="fa fa-edit"></i></a></td>
						<td><a href="${deleteUrl}"><i class="fa fa-trash"></i></a></td>
						<td><a href="${acl_admin}"><i class="fa fa-share-alt"></i></a></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>

		</p>
	</div>

	<!-- Public Post -->
	<div class="callout callout-danger">
		<c:url var="addPublicPostUrl" value="/public/add" />

		<h4>
			Public-Post [ Domain ] <span style="margin-left: 30%; color: yellow;">ACL-POLICY 
			</span>
			<button type="button" class="btn btn-box-tool"
								id="viewAclPolicyModalHelpId3" style="color:white">
								<i class="fa fa-eye"></i>
				</button>
			 <span class="pull-right"><a href="${addPublicPostUrl}"><i class="fa fa-plus">&nbsp;&nbsp;&nbsp;Post</i>
					</a></span>
		</h4>
		<hr />
		<p>
		<table width="100%">
			<tbody>
				<tr>
					<th>#</th>
					<th>Date</th>
					<th>Message</th>
					<th>Edit</th>
					<th>Delete</th>
					<th>Permission [ Share/Update/Delete ]</th>
				</tr>

				<c:forEach items="${publicposts}" var="post">
					<c:url var="editUrl" value="/public/edit?id=${post.id}" />
					<c:url var="deleteUrl" value="/public/delete?id=${post.id}" />
					<c:url var="acl_admin"
						value="/acl_admin?id=${post.id}&domain=${publicDomain}" />
					<tr>
						<td><c:out value="${post.id}" /></td>
						<td><c:out value="${post.date}" /></td>
						<td><c:out value="${post.message}" /></td>
						<td><a href="${editUrl}"><i class="fa fa-edit"></i></a></td>
						<td><a href="${deleteUrl}"><i class="fa fa-trash"></i></a></td>
						<td><a href="${acl_admin}"><i class="fa fa-share-alt"></i></a></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		</p>
	</div>

</section>
<!-- /.content -->





<!-- Modals -->
<div id="aclPolicyModalHelpId1" title="ACL-Policy for Domain-Object [ Admin-Post ]"
	style="display: none;">
	
	<table  class="table table-bordered">
	    <tr>
	       <th>Role</th>
	       <th>Create</th>
	       <th>Read</th>
	       <th>Update</th>
	       <th>Delete</th>
	       <th>Administration</th>
	    </tr>
	    <tr>
	       <td>Owner</td>
	       <td>Yes</td>
	       <td>Yes</td>
	       <td>Yes</td>
	       <td>Yes</td>
	       <td>Yes [ User-Level ]</td>
	    </tr>
	    <tr>
	       <td>Role_Super-Admin</td>
	       <td>No</td>
	       <td>Yes</td>
	       <td>No</td>
	       <td>No</td>
	       <td>Yes [ User/Role-Level ]</td>
	    </tr>
	    <tr>
	       <td>Role_Admin</td>
	       <td>Yes</td>
	       <td>No</td>
	       <td>No</td>
	       <td>No</td>
	       <td>No</td>
	    </tr>
	    <tr>
	       <td>Role_User</td>
	       <td>No</td>
	       <td>No</td>
	       <td>No</td>
	       <td>No</td>
	       <td>No</td>
	    </tr>
	    <tr>
	       <td>Role_Visitor</td>
	       <td>No</td>
	       <td>No</td>
	       <td>No</td>
	       <td>No</td>
	       <td>No</td>
	    </tr>
	</table>
</div>

<div id="aclPolicyModalHelpId2" title="ACL-Policy for Domain-Object [ Personal-Post ]"
	style="display: none;">
	<table  class="table table-bordered">
	    <tr>
	       <th>Role</th>
	       <th>Create</th>
	       <th>Read</th>
	       <th>Update</th>
	       <th>Delete</th>
	       <th>Administration</th>
	    </tr>
	    <tr>
	       <td>Owner</td>
	       <td>Yes</td>
	       <td>Yes</td>
	       <td>Yes</td>
	       <td>Yes</td>
	       <td>Yes [ User-Level ]</td>
	    </tr>
	    <tr>
	       <td>Role_Super-Admin</td>
	       <td>No</td>
	       <td>Yes</td>
	       <td>No</td>
	       <td>No</td>
	       <td>Yes [ User/Role-Level ]</td>
	    </tr>
	    <tr>
	       <td>Role_Admin</td>
	       <td>Yes</td>
	       <td>No</td>
	       <td>No</td>
	       <td>No</td>
	       <td>No</td>
	    </tr>
	    <tr>
	       <td>Role_User</td>
	       <td>Yes</td>
	       <td>Yes</td>
	       <td>No</td>
	       <td>No</td>
	       <td>No</td>
	    </tr>
	    <tr>
	       <td>Role_Visitor</td>
	       <td>No</td>
	       <td>No</td>
	       <td>No</td>
	       <td>No</td>
	       <td>No</td>
	    </tr>
	</table>
</div>

<div id="aclPolicyModalHelpId3" title="ACL-Policy for Domain-Object [ Public-Post ]"
	style="display: none;">
	<table  class="table table-bordered">
	    <tr>
	       <th>Role</th>
	       <th>Create</th>
	       <th>Read</th>
	       <th>Update</th>
	       <th>Delete</th>
	       <th>Administration</th>
	    </tr>
	    <tr>
	       <td>Owner</td>
	       <td>Yes</td>
	       <td>Yes</td>
	       <td>Yes</td>
	       <td>Yes</td>
	       <td>Yes [ User-Level ]</td>
	    </tr>
	    <tr>
	       <td>Role_Super-Admin</td>
	       <td>No</td>
	       <td>Yes</td>
	       <td>No</td>
	       <td>No</td>
	       <td>Yes [ User/Role-Level ]</td>
	    </tr>
	    <tr>
	       <td>Role_Admin</td>
	       <td>Yes</td>
	       <td>Yes</td>
	       <td>No</td>
	       <td>No</td>
	       <td>No</td>
	    </tr>
	    <tr>
	       <td>Role_User</td>
	       <td>Yes</td>
	       <td>Yes</td>
	       <td>No</td>
	       <td>No</td>
	       <td>No</td>
	    </tr>
	    <tr>
	       <td>Role_Visitor</td>
	       <td>No</td>
	       <td>Yes</td>
	       <td>No</td>
	       <td>No</td>
	       <td>No</td>
	    </tr>
	</table>
</div>

<script
	src="<%request.getContextPath();%>/resources/assets/bower_components/jquery/dist/jquery.min.js"
	type="text/javascript"></script>

<script type="text/javascript">
	$(document).ready(function() {
		
		$("#viewAclPolicyModalHelpId1").click(function(){
			$("#aclPolicyModalHelpId1").dialog({		
				dialogClass : "no-close",
				resizable : false,
				draggable: false,
				height : "auto",
				width : 650,
				modal : true,
				position: { my: "center", at: "center" },
				buttons : {
					"OK" : function() {					
						$( this ).dialog( "close" );
					}
				}		
			});
		});
		
		
		

		$("#viewAclPolicyModalHelpId2").click(function() {
			$("#aclPolicyModalHelpId2").dialog({		
				dialogClass : "no-close",
				resizable : false,
				draggable: false,
				height : "auto",
				width : 650,
				modal : true,
				position: { my: "center", at: "center" },
				buttons : {
					"OK" : function() {					
						$( this ).dialog( "close" );
					}
				}		
			});
		});

		$("#viewAclPolicyModalHelpId3").click(function() {
			$("#aclPolicyModalHelpId3").dialog({		
				dialogClass : "no-close",
				resizable : false,
				draggable: false,
				height : "auto",
				width : 650,
				modal : true,
				position: { my: "center", at: "center" },
				buttons : {
					"OK" : function() {					
						$( this ).dialog( "close" );
					}
				}		
			});
		});

	
	});//Document ready
	
</script>

