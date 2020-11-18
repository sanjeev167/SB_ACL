
<div class="tab-pane active" id="tab_1">
    <b>How to use:</b>
	<!-- Default box -->
	<div class="box bg-green color-palette">
		<div class="box-header with-border">

			<div class="nav-tabs-custom">

				<ul class="nav nav-tabs" style="font-size: 15px; font-weight: bold;">
					<li class="active"><a href="#tab_11" data-toggle="tab">Technologis
							Used</a></li>
					<li><a href="#tab_12" data-toggle="tab">Security</a></li>
					<li><a href="#tab_13" data-toggle="tab">Hierarchical-Security</a></li>
					<li><a href="#tab_14" data-toggle="tab">Role-Hierarchical</a></li>
					<li><a href="#tab_15" data-toggle="tab">Dynamic-User-Menu</a></li>
				</ul>

				<div class="tab-content">

					<div class="tab-pane active" id="tab_11">

						<div class="box-body">
							This application is using following J2EE stuffs
							<ul>
								<li>JSP & Jquery</li>
								<li>Spring Boot.</li>
								<li>Spring Data JPA.</li>
								<li>Postgres Database.</li>
							</ul>
						</div>

					</div>


					<div class="tab-pane" id="tab_12">
						<div class="box-body">
							<ul>
								<li>At Web-Layer:-- Spring-Boot Web Security
									<ul>
										<li>Configuration is made through UI.</li>
										<li>No hard coded configuration is required.</li>
										<li>After configuration, application is required to be
											started to see the effect as the access rights have been
											fetched through context.<br>Role based access rights are
											stuffed into context for menu design. So permission on a page
											will be get reflected into menu after restarting the
											application. Any method level permission will be reflected
											after a fresh login.
										</li>
										<li>After a fresh login, change in access rights will be
											reflected.</li>
									</ul>
								</li>
								<li>At Method-Label:-- Spring Boot Method Security [<strong
									style="color: white;"> Optional </strong>].
									<ul>
										<li>If you are using ACL, you hardly require to use
											it.But if needed you can.</li>

										<li>This can also be controlled at web layer by securing
											complete url of method through UI.</li>
									</ul>
								</li>
								<li>At Domain-Level:-- ACL.
									<ul>
										<li>Each domain object can be secured using this
											implemented ACL security.</li>

										<li>This can be applied either at controller or service
											layer's method.</li>
										<li>It is advisable to apply at service-interface.</li>
										<li>ACL is not applied over a method of creating an
											object.</li>
										<li>A create method can be secured either at web layer or
											method layer using springBoot classical approach.</li>
									</ul>
								</li>
							</ul>
						</div>
					</div>
					<div class="tab-pane" id="tab_13">
						<div class="box-body">
							<ul>
								<li>Hierarchical Security: --
									<ul>
										<li>Application is secured with Hierarchical Security.</li>
										<li>Hierarchical Security has been implemented at web and
											method level.</li>
										<li>Hierarchical Security has not been implemented at ACL
											i.e. at domain level to avoid complexity.</li>
										<li>It can be implemented; but ACL permission and web
											level permission configuration through UI require special
											attention.</li>
										<li>So, better to avoid Hierarchical security at
											Domain[ACL] level and use it at web and method level using
											spring classical security approach.</li>

									</ul>
								</li>


							</ul>

						</div>
					</div>

					<div class="tab-pane" id="tab_14">
						<div class="box-body">
							<ul>
								<li>Role-Hierarchy:--
									<ul>
										<li>Before configuring web-level security, you are
											required to create a role hierarchy. You can create a role
											hierarchy at any depth.</li>

										<li>Role hierarchy is configured for a context
											[department/office/section]. Here, it has not been mentioned
											any department/office/section; but temporarily it has been
											simple considered as a context.</li>

										<li>In final implementation, we have different
											department/office/section under a context. Usually, an
											application has two contexts. One is app-admin-context and
											other is web-user-context context.</li>
									</ul>
								</li>


							</ul>
						</div>
					</div>


					<div class="tab-pane" id="tab_15">
						<div class="box-body">
							<ul>
								<li>Dynamic User Menu:--
									<ul>
										<li>It can be created using UI. It requires availability
											of followings in advance:-<br>
											<ul>
												<li>Must have an application-context configured in
													advance.</li>
												<li>Must have a module within a application-context
													configured in advance.</li>
												<li>Must have recorded pages within a module in
													advance.</li>
												<li>No role requirement here.</li>
											</ul>
										</li>

										<li>You could keep watching tree creation adjacent to the
											form.</li>
										<li>If menu-tree is not created and you have configured
											web-access rights, it will not be reflected in the menu.</li>
										<li>Menu-tree can be created in vertical and horizontal
											orientation. Each menu-tree corresponds to an
											application-context and an orientation. So, you have a
											previlage to create a vertical and a horizontal menu-tree for
											each context.</li>
									</ul>
								</li>


							</ul>


						</div>
					</div>

				</div>
			</div>

			<div class="box-footer bg-green color-palette">&nbsp;</div>
			<!-- /.box-footer-->
		</div>
		<!-- /.box -->
	</div>

</div>

<!-- /.tab-pane -->
