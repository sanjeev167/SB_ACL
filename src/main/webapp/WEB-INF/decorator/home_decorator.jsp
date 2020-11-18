<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec"	uri="http://www.springframework.org/security/tags"%>


<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<!--
This is a starter template page. Use this page to start your new project from
scratch. This page gets rid of all links and provides the needed markup only.
-->
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>PracticeOnNet</title>
<!-- Tell the browser to be responsive to screen width -->
<meta
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"
	name="viewport">
<!-- Bootstrap 3.3.7 -->
<link rel="stylesheet"
	href="<%request.getContextPath();%>/resources/assets/bower_components/bootstrap/dist/css/bootstrap.min.css">
<!-- Font Awesome -->
<link rel="stylesheet"
	href="<%request.getContextPath();%>/resources/assets/bower_components/font-awesome/css/font-awesome.min.css">
<!-- Ionicons -->
<link rel="stylesheet"
	href="<%request.getContextPath();%>/resources/assets/bower_components/Ionicons/css/ionicons.min.css">
<!-- Theme style -->
<link rel="stylesheet"
	href="<%request.getContextPath();%>/resources/assets/dist/css/AdminLTE.min.css">
<!-- iCheck -->
<link rel="stylesheet"
	href="<%request.getContextPath();%>/resources/assets/plugins/iCheck/square/blue.css">	
		
<!-- AdminLTE Skins. Choose a skin from the css/skins
       folder instead of downloading all of them to reduce the load. -->
<link rel="stylesheet"
	href="<%request.getContextPath();%>/resources/assets/dist/css/skins/_all-skins.min.css">

	
 

<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
  <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
  <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
  <![endif]-->

<!-- Google Font -->
<link rel="stylesheet"
	href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,600,700,300italic,400italic,600italic">

<style type="text/css">
.iconColor {
	color: black;
}

.formHeader {
	background-color: #cc0000;
	color: white;
}

table {
	border-radius: 20px;
}
</style>
</head>
<!--
BODY TAG OPTIONS:
=================
Apply one or more of the following classes to get the
desired effect
|---------------------------------------------------------|
| SKINS         | skin-blue                               |
|               | skin-black                              |
|               | skin-purple                             |
|               | skin-yellow                             |
|               | skin-red                                |
|               | skin-green                              |
|---------------------------------------------------------|
|LAYOUT OPTIONS | fixed                                   |
|               | layout-boxed                            |
|               | layout-top-nav                          |
|               | sidebar-collapse                        |
|               | sidebar-mini                            |
|---------------------------------------------------------|
-->
<body class="hold-transition skin-red layout-top-nav ">
	<div class="wrapper">

		<!-- Main Header -->
		<%@ include file="includes/headerH.jsp"%>



		<!-- Content Wrapper. Contains page content -->
		<div class="content-wrapper">

			<!-- Main content -->
			<section class="content container-fluid">

				<!-------------------------- | Your Page Content Here | -------------------------->

				<sitemesh:write property="body" />

			</section>
			<!-- /.content -->
		</div>
		<!-- /.content-wrapper -->

		<!-- Main Footer -->
		<%@ include file="includes/footer.jsp"%>

		<!-- Control Sidebar -->

		<%@ include file="includes/control_sidebar.jsp"%>

	</div>
	<!-- ./wrapper -->

	<!-- REQUIRED JS SCRIPTS 
	<script
		src="<%request.getContextPath();%>/resources/assets/bower_components/jquery/dist/jquery.min.js"
		type="text/javascript"></script>

	<!-- SlimScroll -->

	<script
		src="<%request.getContextPath();%>/resources/assets/bower_components/jquery-slimscroll/jquery.slimscroll.min.js"></script>
	<!-- FastClick -->
	<script
		src="<%request.getContextPath();%>/resources/assets/bower_components/fastclick/lib/fastclick.js"></script>
	<!-- Bootstrap 3.3.7 -->
	<script
		src="<%request.getContextPath();%>/resources/assets/bower_components/bootstrap/dist/js/bootstrap.min.js"
		type="text/javascript"></script>


	<!-- SlimScroll -->
	<script
		src="<%request.getContextPath();%>/resources/assets/bower_components/jquery-slimscroll/jquery.slimscroll.min.js"
		type="text/javascript"></script>
	<!-- FastClick -->
	<script
		src="<%request.getContextPath();%>/resources/assets/bower_components/fastclick/lib/fastclick.js"
		type="text/javascript"></script>
	<!-- AdminLTE App -->
	<script
		src="<%request.getContextPath();%>/resources/assets/dist/js/adminlte.min.js"
		type="text/javascript"></script>
	<!-- page script -->
	
   <!-- iCheck -->
<script src="<%request.getContextPath();%>/resources/assets/plugins/iCheck/icheck.min.js"></script>

</body>
</html>