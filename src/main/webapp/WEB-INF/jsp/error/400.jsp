
<!-- <section class="content-header">
				<h1>Error Page</h1>
				<ol class="breadcrumb">
					<li><a href="<%request.getContextPath();%>/"><i
							class="fa fa-dashboard"></i> Home</a></li>
					<li><a href="<%request.getContextPath();%>/">Examples</a></li>
					<li class="active">400 error</li>
				</ol>
			</section>
     -->
<!-- Main content -->
<section class="content">
	<div class="error-page">
		<h2 class="headline text-blue">400</h2>

		<div class="error-content">
			<h2 class="text-blue">
				<i class="fa fa-warning "></i> <strong>Oops! Bad
					Request.</strong>
			</h2>

			<p style="font-size: 20px;">The 400 Bad Request Error is an HTTP
				response status code that indicates that the server was unable to
				process the request sent by the client due to invalid syntax.
			<h2>
				Meanwhile, you may return to <a
					href="<%request.getContextPath();%>/"><i class="fa fa-home"></i></a>
				Or
				<button class="btn btn-default" onclick="javascript:history.go(-1)">
					<strong>Go-Back</strong>
				</button>

			</h2>

			</p>






		</div>
		<!-- /.error-content -->
	</div>
	<!-- /.error-page -->
</section>
<!-- /.content -->
<script
	src="<%request.getContextPath();%>/resources/assets/bower_components/jquery/dist/jquery.min.js"
	type="text/javascript"></script>
