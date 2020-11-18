<div class="tab-pane" id="tab_4">

	<b>How to use:</b>
	<!-- Default box -->
	<div class="box bg-orange color-palette">
		
		<div class="box-body">
			This configuration could be started after configuring App-Base and
			Security-Base.
			<ul>
				<li>Use Role-Hierarchy link to configure a role-hierarchy
					within a context.
					<ul>
						<li>Each role-hierarchy corresponds to a context and it will
							be applicable accordingly.</li>
						<li>While configuring a role hierarchy, parent and child
							role-name must be similar to the role-name that have been
							recorded earlier through a role master.</li>
						<li>Keeping parent and child role label name similar to its
							role-name is mandatory as the code has been written accordingly.
							In fact, role hierarchy based role listing for permissions
							assignment was made through this parent or child role label name
							mentioned in the hierarchy. So, name it with caution.</li>
						<li>While creating a role hierarchy, you can keep watching
							the created hierarchy side by side in its graphical
							representation. If the hierarchy has a big depth; there is a
							provision of zoom-in and zoom-out through a slider. You can also
							hide and show children at a particular level.</li>

					</ul>
				</li>
				<li>Use Web-User-Rights link to assign a web level permission
					to a role.
					<ul>
						<li>While assigning web-rights, we select a resource[page]
							and a role list will get displayed side by side and asking you to
							select roles for assigning permission on the selected page.</li>
						<li>Listed roles will keep showing you the total page access
							no.[TPA]. There is an edit link adjacent to each role for editing
							page permission.</li>
						<li>Duplicate page assignment has been restricted.</li>
						<li>Assign pages to roles intelligently as the role-hierarchy
							has been applied on web-layer security. Any page assigned to a
							role in the lower role hierarchy will be accessible to its all
							parent roles in the hierarchy.</li>
						<li>No role will be listed here if roles have not been
							configured in the role hierarchy. Come here only when the role
							hierarchy is ready.</li>
						<li>Listing of roles will also be made in hierarchical
							manner. It means a role who has rights to assign permission will
							see those roles who are its children only.</li>
						<li>Super-Admin has permission to access all the
							configuration pages; but is rights can't be seen. He can create a
							role hierarchy and delegate permissions to its subordinate. A
							subordinate can create a separate role hierarchy within a context
							whose head is the role itself. He can't see its parent role in
							the hierarchy listing.</li>
						<li>All the page level permissions assigned to different
							roles will be accessible in hierarchical manner only.</li>
					</ul>
				</li>
				<li>Hierarchical security has not been implemented at ACL
					level.</li>
			</ul>
		</div>
		<!-- /.box-body -->
		<div class="box-footer bg-orange color-palette">&nbsp;</div>
		<!-- /.box-footer-->
	</div>
	<!-- /.box -->
</div>
<!-- /.tab-pane -->