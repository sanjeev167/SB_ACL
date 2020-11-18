<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!-- Content Wrapper. Contains page content -->
  <div class="content-wrapper">

<section class="content" style="margin-left: 20%;margin-right: 20%">
<div class="row" >
	<div class="col-md-12" >
		<!-- Horizontal Form -->
		<div class="box box-info">
			<div class="box-header with-border">
				<h3 class="box-title">Create New ${source} Post</h3>
			</div>
			<!-- /.box-header -->
			<!-- form start -->
			<c:url var="saveUrl" value="/admin/add" />
			<form:form modelAttribute="postAttribute" class="form-horizontal" method="POST"
				action="${saveUrl}">
				<div class="box-body">
					
                   
					
                   
					<div class="form-group">
						<form:label path="message" class="col-sm-2 control-label">Message:</form:label>

						<div class="col-sm-10">
							<form:input path="message" class="form-control" size="50" />
						</div>
					</div>

				</div>
				<!-- /.box-body -->
				<div class="box-footer">
					<button type="button" onclick="javascript:history.go(-1)" class="btn btn-default">Back</button>
					<button type="submit" class="btn btn-info pull-right">Save</button>
				</div>
				<!-- /.box-footer -->
			</form:form>
		</div>
		<!-- /.box -->
	</div>
</div>
</section>
</div>





