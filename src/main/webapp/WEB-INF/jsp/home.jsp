
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">	
</head>
<body>
		<!-- Full Width Column -->
		<div class="content-wrapper">
			<div class="container">

				<section class="content">
                  <div class="row">
					
							<div class="col-md-6">						
									<div class="box box-default" style="width: 100%;height: 430px;">
										<div class="box-header with-border formHeader" >
											<h3 class="box-title"><strong>Domain based ACL Security [ SpringBoot
												].</strong></h3>
										</div>
										<div class="box-body" style="font-size: large; font-weight: bold;">
										
											<ul>
												<li>Using this domain based security, an object of a domain
													can be secured either at a user-level or at a role-level or at
													both the levels.</li>
												<li>A user can assign permissions on its object either to a
													role or to an another user, provided it has
													ADMINISTRATION-Permission.</li>
				
												<li>This is used for securing an access of an object at
													method level only.</li>
												<li>Only a create method can't be secured by ACl. It can be
													secured either at web (Url) level or at method level using
													spring SEL.</li>
											</ul>
										
										</div>
										<!-- /.box-body -->
									</div>
									<!-- /.box -->					
		                    </div>
		                    
		                    <div class="col-md-6">
								<div class="box box-default" style="width: 100%;height: 430px;">
									<div class="box-header with-border formHeader" >
										<h3 class="box-title" ><strong>ACL- Operations Implemented.</strong></h3>
									</div>
									<div class="box-body" style="font-size: medium;font-weight: bold;">
										<ul>
											<li>Domain-Recording</li>
											<li>Permission-Recording
												<ul>
													<li>CRUD [ Mask-Value:- 2<sup>0</sup>,2<sup>1</sup>,2<sup>2</sup>,2<sup>3</sup>
														] Base
													</li>
													<li>ADMINISTRATION [ Mask-Value:- 2<sup>4</sup> ] Base
													</li>
													<li>REPORT [ Mask-Value:- 2<sup>5</sup> ] Custom
													</li>
													<li>APPROVE [ Mask-Value:- 2<sup>6</sup> ] Custom
													</li>
												</ul>
											</li>
			
											<li>Permission-Policy
												<ul>
													<li>Can be defined at Role/Owner level.</li>
													<li>ADMINISTRATION permission can be assigned only to
														Owner/ROLE_SUPER_ADMIN</li>
													<li>CRUD can be assigned to the Owner and other Roles.</li>
												</ul>
											</li>
			
											<li>A user with ADMINISTRATION permission
												<ul>
													<li>can assign CRUD permission to a Role or a User.</li>
													<li>and with ROLE_SUPER_ADMIN
														<ul>
															<li>can perform REPORT</li>
															<li>can perform APPROVE</li>
														</ul>
													</li>
												</ul>
											</li>
										</ul>
			
									</div>
									<!-- /.box-body -->
								</div>
								<!-- /.box -->
							 </div>
					 </div>
					 
					 
					
						
				</section>
				<!-- /.content -->
			</div>
			<!-- /.container -->
		</div>
		<!-- /.content-wrapper -->	
	</body>
</html>
