<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<!-- Content Header (Page header) -->
	<section class="content-header">

		<h1>Road-Map.</h1>

	</section>




	<!-- Main content -->
	<section class="content">

		<div class="row">
			<div class="col-md-12">
				<!-- Custom Tabs -->
				<div class="nav-tabs-custom">
				
					<ul class="nav nav-tabs" style="font-size: 15px; font-weight: bold;">
						<li class="active"><a href="#tab_1" data-toggle="tab">Introduction</a></li>
						<li><a href="#tab_2" data-toggle="tab">App-Base-Config</a></li>
						<li><a href="#tab_3" data-toggle="tab">Security-Base-Config</a></li>
						<li><a href="#tab_4" data-toggle="tab">Web-Security-Config</a></li>
						<li><a href="#tab_5" data-toggle="tab">ACL-Security-Config</a></li>
					</ul>
					
					<div class="tab-content">
						<!-- =============================================== -->
						<!-- sec_intro.jsp -->
						<%@ include file="doc/sec_intro.jsp" %>
						<!-- =============================================== -->
                         
                         <!-- =============================================== -->
						<!-- app_base_config.jsp -->
						<%@ include file="doc/app_base_config.jsp" %>
						<!-- =============================================== -->
                         
                        <!-- =============================================== -->
						<!-- security_base_config.jsp -->
						<%@ include file="doc/security_base_config.jsp" %>
						<!-- =============================================== --> 
						
						
						<!-- =============================================== -->
						<!-- web_security_config.jsp -->
						<%@ include file="doc/web_security_config.jsp" %>
						<!-- =============================================== --> 
						
						<!-- =============================================== -->
						<!-- web_security_config.jsp -->
						<%@ include file="doc/acl_security_config.jsp" %>
						<!-- =============================================== --> 
						
					</div>
					<!-- /.tab-content -->
				</div>
				<!-- nav-tabs-custom -->
			</div>
			<!-- /.col -->

		</div>
		<!-- /.row -->
		<!-- END CUSTOM TABS -->




	</section>
	<!-- /.content -->
	



</body>
</html>