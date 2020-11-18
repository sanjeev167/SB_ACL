<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>



<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
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
<link rel="stylesheet"
	href="<%request.getContextPath();%>/resources/assets/dist/css/skins/_all-skins.min.css">
<!-- iCheck for checkboxes and radio inputs -->
<link rel="stylesheet"
	href="<%request.getContextPath();%>/resources/assets/plugins/iCheck/all.css">

<!-- Data table css styling using data table -->
<link rel="stylesheet"
	href="https://code.jquery.com/ui/1.12.1/themes/trontastic/jquery-ui.css">


<link rel="stylesheet"
	href="https://cdn.datatables.net/1.10.21/css/jquery.dataTables.min.css">

<!--	
<link rel="stylesheet"
	href="https://www.jqueryscript.net/demo/DataTables-Jquery-Table-Plugin/media/css/jquery.dataTables_themeroller.css">	
-->
<!-- Theme style -->
<link rel="stylesheet"
	href="<%request.getContextPath();%>/resources/assets/dist/css/AdminLTE.min.css">
<!-- AdminLTE Skins. Choose a skin from the css/skins
       folder instead of downloading all of them to reduce the load. -->
<link rel="stylesheet"
	href="<%request.getContextPath();%>/resources/assets/dist/css/skins/_all-skins.min.css">
<!-- iCheck for checkboxes and radio inputs -->
<link rel="stylesheet"
	href="<%request.getContextPath();%>/resources/assets/plugins/iCheck/all.css">

<!-- Bootstrap Color Picker -->
<link rel="stylesheet"
	href="<%request.getContextPath();%>/resources/assets/bower_components/bootstrap-colorpicker/dist/css/bootstrap-colorpicker.min.css">
<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
  <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
  <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
  <![endif]-->

<!-- Google Font  -->
<link rel="stylesheet"
	href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,600,700,300italic,400italic,600italic">
	
<style type="text/css">
.iconColor {
	
}

.formHeader {
	background-color: #cc0000;
	color: white;
}

table {
	border-radius: 20px;
}

.error {
	font-weight: bold;
	color: red;
}

.success {
	font-weight: bold;
	color: green;
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
<!-- ADD THE CLASS fixed TO GET A FIXED HEADER AND SIDEBAR LAYOUT -->

<body class="hold-transition skin-green sidebar-mini">
	<!-- Site wrapper -->
	<div class="wrapper">

		<!-- =============================================== -->
		<!-- Main Header -->
		<%@ include file="includes/header.jsp"%>
		<!-- =============================================== -->

		<!-- =============================================== -->
		<!-- Left side column. contains the sidebar -->
		<%@ include file="includes/left_menu.jsp"%>
		<!-- =============================================== -->


		<!-- =============================================== -->
		<!-- Start: Page main content will go here -->
		<!-- Content Wrapper. Contains page content -->
		<div class="content-wrapper">
			<sitemesh:write property="body" />
		</div>
		<!-- End: Page main content will go here -->
		<!-- =============================================== -->


		<!-- =============================================== -->
		<!-- Footer -->
		<%@ include file="includes/footer.jsp"%>
		<!-- =============================================== -->

		<!-- =============================================== -->
		<!-- Control Sidebar -->
		<%@ include file="includes/control_sidebar.jsp"%>
		<!-- =============================================== -->


	</div>
	<!-- ./wrapper -->




	<!-- REQUIRED JS SCRIPTS -->
	
	<!-- Data table js requirement -->
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
	<!-- Jquery based modal -->
	<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
	<script src="<%request.getContextPath();%>/resources/pagejs/jQueryGridFeature.js"></script>
   
    
	<!-- page script -->

	<script>
		//alert("Decorator");
		//This function is used to keep your selected link active and remain opened the tree
		$(function() {
			//document.body.style.zoom="80%"
			var url = window.location;

			// for sidebar menu entirely but not cover treeview
			$('ul.sidebar-menu a').filter(function() {
				return this.href == url;
			}).parent().addClass('active');

			// for treeview
			$('ul.treeview-menu a').filter(function() {
				return this.href == url;
			}).parentsUntil(".sidebar-menu > .treeview-menu")
					.addClass('active');
		});

		//This is required for make button click on left menu
		$(document).ready(function() {
			//This is required for loading tree via ajax
			$('[data-widget="tree"]').each(function() {
				$.fn.tree.call($(this));
			});
			//

		});

		//at bottom/footer of the page
		$(document).ready(function() {
			$(document).trigger("resize");
		});

		var loginPageLink = '/pub/user/loginPage?invalid=true';
		/*$(document).ajaxError(function (event, xhr) {		
		if(xhr.status=="440"){
		//alert("Some message...........");
		document.location.href = loginPageLink;
		}});*/

		//This will be used for formatting error when there is an error in ajax call
		function formatErrorMessage(jqXHR, exception) {
			//alert("Error Code coming  = "+jqXHR.status);
			if (jqXHR.status === 440) {
				//alert("Ajax call found");
				document.location.href = loginPageLink;
			} else if (jqXHR.status === 0) {
				return ('Not connected.\nPlease verify your network connection.');
				document.location.href = "/403";
			} else if (jqXHR.status == 404) {
				return ('The requested page not found. [404]');
			} else if (jqXHR.status == 500) {
				return ('Internal Server Error [500].');
			} else if (exception === 'parsererror') {
				return ('Requested JSON parse failed.');
			} else if (exception === 'timeout') {
				return ('Time out error.');
			} else if (exception === 'abort') {
				return ('Ajax request aborted.');
			} else {
				return ('Uncaught Error.\n' + jqXHR.responseText);
			}
		}
	</script>


</body>
</html>














