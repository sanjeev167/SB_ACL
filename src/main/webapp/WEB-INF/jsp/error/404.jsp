
<!-- <section class="content-header">
				<h1>Error Page</h1>
				<ol class="breadcrumb">
					<li><a href="<%request.getContextPath();%>/"><i
							class="fa fa-dashboard"></i> Home</a></li>
					<li><a href="<%request.getContextPath();%>/">Examples</a></li>
					<li class="active">404 error</li>
				</ol>
			</section>
     -->
<!-- Main content -->
<section class="content">
	<div class="error-page">
		<h2 class="headline text-yellow">404</h2>

		<div class="error-content">
			<h2 class="text-yellow">
				<i class="fa fa-warning"></i> <strong>Oops!
					Resource not found.</strong>
			</h2>

			<p style="font-size: 20px;">The error code is HTTP 404 (not
				found) and the description is: The origin server did not find a
				current representation for the target resource or is not willing to
				disclose that one exists. </p>	
				<h2>
				Meanwhile, you may return to 
							<a href="<%request.getContextPath();%>/"><i class="fa fa-home"></i></a>
						  Or	<button class="btn btn-default" onclick="javascript:history.go(-1)"><strong>Go-Back</strong></button>
							
			   </h2>

		</div>
		<!-- /.error-content -->
	</div>
	<!-- /.error-page -->
</section>
<!-- /.content -->
<script
	src="<%request.getContextPath();%>/resources/assets/bower_components/jquery/dist/jquery.min.js"
	type="text/javascript"></script>
