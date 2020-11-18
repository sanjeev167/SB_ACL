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
				<h3 class="box-title">Edit ${source} Post</h3>
			</div>
			<!-- /.box-header -->
			<!-- form start -->
			<c:url var="saveUrl" value="/admin/edit?id=${postAttribute.id}" />
			<form:form modelAttribute="postAttribute" class="form-horizontal" method="POST"
				action="${saveUrl}">
				<div class="box-body">
					<div class="form-group">
						<form:label path="id" class="col-sm-2 control-label">Id:</form:label>

						<div class="col-sm-10">
							<form:input path="id" type="text" class="form-control"
								 disabled="true" />
						</div>
					</div>
                   
					<div class="form-group">
						<form:label path="date" class="col-sm-2 control-label">Date</form:label>

						<div class="col-sm-10">
							<form:input class="form-control" path="date" disabled="true" />
						</div>
					</div>
                   
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
					<button type="submit" class="btn btn-info pull-right">Update</button>
				</div>
				<!-- /.box-footer -->
			</form:form>
		</div>
		<!-- /.box -->
	</div>
</div>
</section>
</div>