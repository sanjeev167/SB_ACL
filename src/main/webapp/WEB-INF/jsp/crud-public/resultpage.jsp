
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!-- Content Wrapper. Contains page content -->
<div class="content-wrapper">

	<section class="content" style="margin-left: 20%; margin-right: 20%">
		<div class="row">
			<div class="col-md-12">
				<!-- Horizontal Form -->
				<div class="box box-info">
					<div class="box-header with-border">
						<h3 class="box-title">Result Page</h3>
					</div>
					<!-- /.box-header -->
					<!-- form start -->
				
					<form:form modelAttribute="postAttribute" class="form-horizontal">
						<div class="box-body">

                           <div class="form-group">
						      <label class="col-sm-2 control-label">Current user:</label>

								<div class="col-sm-10">
								 <label class="col-sm-2 control-label">${username}</label>									
								</div>
						  </div>
						  
						  <div class="form-group">
						      <label class="col-sm-2 control-label">Current Role:</label>

								<div class="col-sm-10">
								 <label class="col-sm-2 control-label">${role}</label>									
								</div>
						  </div>
						  
						  <div class="form-group">
						      <label class="col-sm-2 control-label">Post:--</label>

								<div class="col-sm-10">
								 <label class="col-sm-2 control-label">${source} Post</label>									
								</div>
						  </div>
                          <div class="form-group">
                               <label class="col-sm-6 control-label" style="font-size:20px; color:green">  ${result}</label>	
                              
                          </div>



						</div>
						<!-- /.box-body -->
						<div class="box-footer"><button type="button" onclick="javascript:history.go(-1)" class="btn btn-default">Back</button>
					</div>
						<!-- /.box-footer -->
					</form:form>
				</div>
				<!-- /.box -->
			</div>
		</div>
	</section>
</div>
