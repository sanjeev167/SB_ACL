<div class="tab-pane" id="tab_2">

	<b>How to use:</b>
	<!-- Default box -->
	<div class="box bg-blue color-palette">
		
		<div class="box-body">
			<ul>
				<li>This is the starting point of configuration.</li>

				<li>Use App-Context link: --
					<ul>
						<li>First create two Application-Contexts as [ Admin-Context
							] and [ Web-User-Context ]</li>
						<li>Admin-Context will be used for all Admin related modules'
							stuffs.</li>
						<li>Web-User-Context will be used for all Web-Users related
							modules' stuffs.</li>

						<li>Menu-tree creation and Role-Hierarchy creation will be
							made under these contexts.</li>

					</ul>

				</li>
				<li>Use Module-Master link for module creation under a specific
					context.</li>
				<li>Use Resource [Page] link for recording pages of a module.
					<ul>
						<li>Each page recording is made with a page name of your
							choice; but the base_url must match with the respective
							request-mapping url of its controller.</li>

						<li>Page opening must be kept at root url.i.e. <br>Base_url
							must be like /xyz/abc/** and <br> Page-opening url must be
							"" [blank] only. <br> This base-url will play a role of
							opening the page and imposing a web level security while
							configuring web-level security through UI.
						</li>

						<li>Any ajax call made for a combo or any other thing must be
							called within the same page's controller. It is mandatory,
							otherwise these ajax url call will be restricted by the security.
							For allowing it to be called, you have to create a separate link
							as a dummy-page and provide a permission to the respective roles.
							So, for a better management, keep all ajax calls of a page
							through its controller only, and let them to be accessed if the
							role has access permission on the page.</li>
						<li>This base-url is utilized for preparing ant-matcher for
							applying access rights to one or more role.</li>
						<li>While configuring web-level access rights, we select a
							page and one or more roles and associate permission.</li>
						<li>The same base-url of a page is used for creating a role
							based menu-tree. Wild-card used in the base-url is removed within
							the code for creating a link for menu-tree.</li>
					</ul>
				</li>
				<li>Use Tree-Menu link for creating a tree menu for a context.
					<ul>
						<li>It can be created before or after assigning a web-level
							permission.</li>
						<li>But, unless or until you create a page link in menu-tree,
							a role will not see the page link in the tree-menu even if it has
							a permission on the page.</li>
						<li>Advisable to create a tree-menu for different orientation
							in advance for all resources[pages]. It will help you to see the
							page link if you are assigning a permission to a role.</li>
						<li>Any change in tree-menu [node/leaf/image] or
							permission-alteration for a page will be reflected in the
							menu-tree after restarting the application as it is created as
							per a role; but loaded from the context.</li>
					</ul>

				</li>
			</ul>
		</div>
		<!-- /.box-body -->
		<div class="box-footer bg-blue color-palette">&nbsp;</div>
		<!-- /.box-footer-->
	</div>
	<!-- /.box -->

</div>
<!-- /.tab-pane -->