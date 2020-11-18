<div class="tab-pane" id="tab_5">

	<b>How to use:</b>
	<!-- Default box -->
	<div class="box bg-black color-palette">
		<div class="box-header with-border">			
			<div class="nav-tabs-custom">

				<ul class="nav nav-tabs" style="font-size: 15px; font-weight: bold;">
					<li class="active"><a href="#tab_51" data-toggle="tab">Introduction</a></li>
					<li><a href="#tab_52" data-toggle="tab">Domain &
							Associated-Permission</a></li>
					<li><a href="#tab_53" data-toggle="tab">ACL-Policy-Config</a></li>
					<li><a href="#tab_54" data-toggle="tab">Permission-Management</a></li>
				</ul>

				<div class="tab-content">
					<div class="tab-pane active" id="tab_51">
						<div class="box-body">
							Configuration of ACL [Domain-Based] security is totally
							independent of web level security. It is applied on an already
							created object. A default implementation of ACL permission
							evaluator available with SpringBoot has been used for securing
							this application. A separate ACL named database has been created
							which has four ACL security supporting tables. Permission related
							operations on ACL tables are performed through MutableAclService
							which comes with the ACL jar. Only this service bean is required
							to be configured with an existing data source. A separate data
							source is required for it. Two wrapper service classes have been
							written for ACL table manipulation. One has all the generic
							methods required for manipulating ACL tables. The second service
							class has been written with an objective of serving application
							specific need. <br>Generic Service method has been written
							are <br> [1.] Get a Sid for a role or user<br> [2.] Get
							objectIdentityId with an objectWithId<br> [3.] Get Owner of
							an object using objectWithId<br> [4.] Persisting single
							permission with an objectId, userOrRoleName, isPrincipal and
							permission mask or label<br> [5.] Persisting bulk
							permissions or Permission array with an objectId, userOrRoleName,
							isPrincipal.<br> [6.] Updating single or bulk userOrRole's
							permission.<br> [7.] Removing a single or multiple
							permissions for a userOrRole.<br> [8.] Change Ownership of
							an object is allowed to SUPER_ADMIN only. While changing owner, a
							new owner is created with similar permissions of old-owner. The
							old-owner is made a normal user without Administration
							permissions. This normal user has still all the previous
							permission except Administration. <br> [9.] Get Object-Owner
							permission with objectWithId.<br> [10.] Deleting complete
							ACL of an object if the object is deleted by its owner.<br>
							[11.] If SUPER_ADMIN role user is deleting complete ACL, it will
							delete all ACL permission except SUPER_ADMIN's permission. <br>
							[12.] An Owner could share permissions on an object with a user
							only not with a role.<br> [13.] For sharing permissions on
							an object with a role or user, a super-admin user is required.<br>
							[14.] An owner couldn't share ADMINISTRATION permission with any
							other user.<br>

							<ul>

								<li>ACL Configuration required at application end:--
									<ul>
										<li>First record permission type context.
											<ul>
												<li>Base-Permission-Context [Mandatory]. It has CRUDA
													with mask value[2^0, 2^1,2^2,2^3,2^4,2^5]</li>
												<li>Other-Context can be recorded as per need.</li>
												<li>Only five permissions can be recorded in each
													context. It has been restricted just because of the design
													of the page. Only 15 permissions can be shown on the page
													at a time.</li>
											</ul>
										</li>
										<li>Permissions are recorded within a specific
											permission-context.</li>
										<li>Base-permissions are not allowed to be created by the
											user so that permission label spelling entering can be
											avoided</li>
										<li>Base-permissions can't be edited,deleted or added
											from front-end.</li>
										<li>Other permission in different context can be added,
											deleted or edited.</li>

									</ul>
								</li>
							</ul>
						</div>
					</div>
					<div class="tab-pane" id="tab_52">
						<div class="box-body">
						<ul>
							<li>Domain recording:--

								<ul>
									<li>Domain with complete package is recorded</li>
									<li>CRUD operation can be performed on a domain.</li>
								</ul>
							</li>
							<li>Domain-Permission-Association:--
								<ul>
									<li>Each domain must be associated with different
										permissions.</li>
									<li>Associate different permissions with a domain.
										Permissions will be listed there from the table where the
										permissions are already recorded.</li>
									<li>Update permission will add or update a domain
										permission if it exists or not. If any permission is already
										associated with a domain, it will be shown there selected.</li>
									<li>While updating or adding , selected base-permissions
										and one set of other selected context permissions are sent for
										the same.</li>
									<li>Grid will keep showing you all the associated
										permission of different domain.</li>
									<li>Deleting and editing of a particular domain-associated
										permission list through grid. Editing of
										permission-association of a domain can also be done with
										adding/updating option, too.</li>
								</ul>
							</li>
							</ul>
						</div>
					</div>
					<div class="tab-pane" id="tab_53">
						<div class="box-body">
						 Acl-Policy is defined on a domain at two levels.
						 <ul>
						    <li> At Owner-Level
						         <ul>
						             <li>Permissions defined at Owner-Level will be available to the object owner only.</li>
						             <li>An owner may be assigned following operations' access.
						                <ul>
						                    <li>READ</li>
						                    <li>WRITE</li>
						                    <li>DELETE</li>
						                    <li>ADMINISTRATION</li>
						                    <li>Any other Custom permission if it is defined on a domain.</li>
						                    <li>Owner ADMINISTRATION permission allows him to share new permission or update/delete already shared permissions only.</li>
						                </ul>
						             </li>
						         </ul>
						    
						    </li>
						    
						    
						    <li>At Role-Level.
						        <ul>
						            <li>Permissions defined at role level will be available to all users in that ROLE.</li>
						            <li>ROLE-LEVEL ADMINISTRATION permission allow users of that role to do administration on the domain objects.</li>
						            <li>ADMINISTRATION permission should be assigned to a ROLE with care.</li>
						        </ul>
						    </li>
						    <li>This ACL-Policy page is secured at web and method level only. This is not secured under ACL for avoiding complexity.</li>
						    <li>ACL-Policy definition page should be assigned to SUPER_ADMIN.</li>
						 </ul>
						
						</div>
					</div>
					<div class="tab-pane" id="tab_54">
						<div class="box-body">
						   This permission management page is for following things:-- 
						   <ul>
						        
						       <li>
						           An object owner can do the followings:--
						           <ul>
						               <li>Can share permission on its object with an user only.</li>
						               <li>Can update already shared permissions on its object at an user level.</li>
						               <li>Can delete a share permission with a user.</li>
						           </ul>
						       </li>
						       <li> SUPER_ADMIN or any other role having ADMINISTRATION rights can do the followings:--
						           <ul>
						               <li>Can share/update/delete permissions on an object for a role/user/Owner</li>
						               <li>Can change ownership of the object.</li>
						           </ul>
						       
						       </li>
						   </ul>
						   
						</div>
					</div>
					
				</div>
			</div>
		</div>

		<div class="box-footer bg-black color-palette">&nbsp;</div>
		<!-- /.box-footer-->
	</div>
	<!-- /.box -->

</div>
<!-- /.tab-pane -->