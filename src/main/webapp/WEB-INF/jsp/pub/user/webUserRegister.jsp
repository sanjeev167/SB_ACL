<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!-- iCheck -->

<style type="text/css">
<!--
.error{

color: red;font-weight: bold;
}
-->
</style>

<link rel="stylesheet"
	href="${contextPath}/resources/assets/plugins/iCheck/square/blue.css">

<div class="register-box" style="padding-top: 2px; margin-top: 2px;">
	<div class="register-logo">
		<a href="#"><b>Practice</b>OnNet</a>
	</div>

	<div class="register-box-body">
		<p class="login-box-msg">
			User Sign Up.
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;OR
			<a href="loginPage" class="text-center" style="color: red">Sign In</a>
		</p>

        <span style="color: red;font-weight: bold;">${message}</span> 
        	
		<form:form action="register" method="post"
			modelAttribute="webUserDTO">
			<div class="form-group has-feedback">
				<form:input type="text" class="form-control" path="name"
					placeholder="Full name" />
				<span class="glyphicon glyphicon-user form-control-feedback"></span>
				<form:errors path="name" cssClass="error" />
			</div>
			<div class="form-group has-feedback">
				<form:input type="email" class="form-control" path="userLoginId"
					placeholder="Email" />
				<span class="glyphicon glyphicon-envelope form-control-feedback"></span>
				<form:errors path="userLoginId" cssClass="error" />
			</div>
			<div class="form-group has-feedback">
				<form:input type="password" class="form-control" path="password"
					placeholder="Password" />
				<span class="glyphicon glyphicon-lock form-control-feedback"></span>
				<form:errors path="password" cssClass="error" />
			</div>
			<div class="form-group has-feedback">
				<form:input type="password" class="form-control" path="passwordConf"
					placeholder="Retype password" />
				<span class="glyphicon glyphicon-log-in form-control-feedback"></span>
				<form:errors path="passwordConf" cssClass="error" />
			</div>
			<div class="row">
				<div class="col-xs-8">
					<div class="checkbox icheck">
						<label style="margin-left: 22px;"> <input type="checkbox">
							I agree to the <a href="terms">terms</a>
						</label>
					</div>
				</div>
				<!-- /.col -->
				<div class="col-xs-4">
					<button type="submit" class="btn btn-primary btn-block btn-flat">Register</button>
				</div>
				<!-- /.col -->
			</div>
		</form:form>

		<div class="social-auth-links text-center">
			<p>- OR -</p>
			<a href="registerfb" class="btn btn-block btn-social btn-facebook btn-flat"><i
				class="fa fa-facebook"></i> Sign up using Facebook</a>
		    <a href="registergg" class="btn btn-block btn-social btn-google btn-flat"><i
				class="fa fa-google-plus"></i> Sign up using Google+
		   </a>
		</div>

	</div>
	<!-- /.form-box -->
</div>
<!-- /.register-box -->



<!-- jQuery 3 -->
<script
	src="${contextPath}/resources/assets/bower_components/jquery/dist/jquery.min.js"></script>
<!-- iCheck -->
<script
	src="${contextPath}/resources/assets/plugins/iCheck/icheck.min.js"
	type="text/javascript"></script>
<script type="text/javascript">
	$(function() {
		$('input').iCheck({
			checkboxClass : 'icheckbox_square-blue',
			radioClass : 'iradio_square-blue',
			increaseArea : '20%' /* optional */
		});
	});
</script>
