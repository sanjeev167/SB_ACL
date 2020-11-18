<div class="tab-pane" id="tab_3">

	<b>How to use:</b>
	<!-- Default box -->
	<div class="box bg-yellow color-palette">
		
		<div class="box-body">
			After completing application-base configuration,
			Security-Base-Configuration can be made
			<ul>
				<li>Use Role-Master link to create a role within a context.
					<ul>
						<li>Role-Name must not be started with ROLE_. It is
							mandatory.</li>
					</ul>
				</li>
				<li>Use Group link to create a group within a context.</li>
				<li>Use Group-Role link for associating a group with a role.
					Associate each group with one or more roles as per need.</li>
				<li>Use User-Category link to create a user category within a
					context. It is not very much required for security purpose; but for
					filtering user details in different context, it will be used.</li>
				<li>Use Admin-User link to create administrative users within a
					context.
					<ul>
						<li>These users will work for the administrative job of the
							application</li>
						<li>or they may belong to such a users group who will use the
							application in hierarchical mode.</li>
						<li>Administrative job may include data feeding for masters
							and security imposing or handling some workflow related cases.</li>
						<li>These users will enjoy actual hierarchical security.</li>
						<li>Each user will be created within a user category.</li>

						<li>Web-Users are not created here, they will use be
							registered through a registration page.</li>
						<li>Since a web-user comes under a different application
							context,therefore
							<ul>
								<li>login through a separate loging page or using the same
									login page with a flag</li>
								<li>registration</li>
								<li>menu-tree</li>
								<li>role-hierarchy</li>
								<li>web-security</li>
								<li>and ACL</li>
								<li>will all be applied in its context; and the application
									will work for them, too.</li>
							</ul>
						</li>
					</ul>
				</li>
				<li>User User-Group link to associate a user with one or more
					groups.</li>
			</ul>

		</div>
		<!-- /.box-body -->
		<div class="box-footer bg-yellow color-palette">&nbsp;</div>
		<!-- /.box-footer-->
	</div>
	<!-- /.box -->

</div>
<!-- /.tab-pane -->
