<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- iCheck -->
<link rel="stylesheet"
	href="${contextPath}/resources/assets/plugins/iCheck/square/blue.css">

<div class="login-box" style="padding-top: 2px; margin-top: 2px;">
	<div class="login-logo">
		<a href="#"><b>Practice</b>OnNet</a>
	</div>
	<!-- /.login-logo -->
	<div class="login-box-body">
		<p class="login-box-msg">
			User Sign In.
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;OR
			<a href="register" class="text-center" style="color: red">Sign Up</a>
		</p>

		<span style="color: red; font-weight: bold;">${errorMessge}</span> <span
			style="color: red; font-weight: bold;">${expired}</span> <span
			style="color: red; font-weight: bold;">${invalid}</span>


		<form action="/doLogin" method="POST">
			<div class="form-group has-feedback">
				<input type="text" class="form-control" placeholder="Email"
					name="username"> <span
					class="glyphicon glyphicon-envelope form-control-feedback"></span>
			</div>
			<div class="form-group has-feedback">
				<input type="password" class="form-control" placeholder="Password"
					name="password"> <span
					class="glyphicon glyphicon-lock form-control-feedback"></span>
			</div>
			<div class="form-group has-feedback">
				<a href="forpwd">Forget Password</a>
			</div>
			<div class="row">
				<div class="col-xs-8">
					<div class="checkbox icheck">
						<label style="margin-left: 22px;"> <input type="checkbox"
							name="remember-me">&nbsp;&nbsp;Remember Me
						</label>
					</div>
				</div>


				<!-- /.col -->
				<div class="col-xs-4">
					<button type="submit" class="btn btn-primary btn-block btn-flat">Sign
						In</button>
				</div>
				<!-- /.col -->
			</div>
		</form>

		<div class="social-auth-links text-center">

			<p>- OR -</p>
			<a href="#" class="btn btn-block btn-social btn-facebook btn-flat"><i
				class="fa fa-facebook"></i> Sign in using Facebook</a> <a href="#"
				class="btn btn-block btn-social btn-google btn-flat"><i
				class="fa fa-google-plus"></i> Sign in using Google+</a>
		</div>
		<!-- /.social-auth-links -->


	</div>
	<!-- /.login-box-body -->
</div>
<!-- /.login-box -->

<!-- iCheck -->
<script
	src="${contextPath}/resources/assets/plugins/iCheck/icheck.min.js"></script>
<script>
	$(function() {
		$('input').iCheck({
			checkboxClass : 'icheckbox_square-blue',
			radioClass : 'iradio_square-blue',
			increaseArea : '20%' /* optional */
		});
	});
</script>

