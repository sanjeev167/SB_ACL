
<!-- jQuery 3 -->
<script src="https://code.jquery.com/jquery-3.3.1.js"></script>
<script type="text/javascript">

$(document).ready(function() {
	
	var data = [
		        //Home
		         {"id": "0", "name": "Home", "parent_id": "-1","leafUrl":"#","nodeType":"N" ,"nodeImgUrl":"fa fa-home"},
		       
		        //Dashboard
		        {"id": "1", "name": "Dashboard", "parent_id": "0","leafUrl":"#","nodeType":"N" ,"nodeImgUrl":"fa fa-dashboard"},
		        {"id": "2", "name": "Dashboard v1", "parent_id": "1","leafUrl":"<%=request.getContextPath() %>/admin/pvt/db","nodeType":"L" ,"nodeImgUrl":"fa fa-files-o"},
		        
		        {"id": "3", "name": "Profile", "parent_id": "0","leafUrl":"<%=request.getContextPath() %>/admin/pvt/profile","nodeType":"L" ,"nodeImgUrl":"fa fa-user"},
		        
		        //API Manager
		        {"id": "4", "name": "API Manager", "parent_id": "0","leafUrl":"#","nodeType":"N" ,"nodeImgUrl":"fa fa-user-secret"},
		        {"id": "5", "name": "Manage Client", "parent_id": "4","leafUrl":"<%=request.getContextPath() %>/admin/pvt/api_mgr/manage_client/","nodeType":"L" ,"nodeImgUrl":"fa fa-files-o"},
		        {"id": "6", "name": "Manage API", "parent_id": "4","leafUrl":"<%=request.getContextPath() %>/admin/pvt/api_mgr/manage_api/","nodeType":"L" ,"nodeImgUrl":"fa fa-files-o"},
		        {"id": "7", "name": "Assign API Permission", "parent_id": "4","leafUrl":"<%=request.getContextPath() %>/admin/pvt/api_mgr/api_permission/","nodeType":"L" ,"nodeImgUrl":"fa fa-files-o"},
		        {"id": "8", "name": "Test API", "parent_id": "4","leafUrl":"<%=request.getContextPath() %>/admin/pvt/api_mgr/test_api/","nodeType":"L" ,"nodeImgUrl":"fa fa-files-o"},
		        
		        //Menu Manager
		        {"id": "9", "name": "Menu Manager", "parent_id": "0","leafUrl":"#","nodeType":"N" ,"nodeImgUrl":"fa fa-tree"},
		        {"id": "10", "name": "Manage Menu", "parent_id": "9","leafUrl":"<%=request.getContextPath() %>/admin/pvt/menu_mgr/manage_menu/","nodeType":"L" ,"nodeImgUrl":"fa fa-files-o"},
		        
		        //Masters
		        {"id": "11", "name": "Master", "parent_id": "0","leafUrl":"#","nodeType":"N" ,"nodeImgUrl":"fa fa-table"},
		        {"id": "12", "name": "Country Master", "parent_id": "11","leafUrl":"<%=request.getContextPath() %>/admin/pvt/masters/country/","nodeType":"L" ,"nodeImgUrl":"fa fa-files-o"},
		        {"id": "13", "name": "State Master", "parent_id": "11","leafUrl":"<%=request.getContextPath() %>/admin/pvt/masters/state/","nodeType":"L" ,"nodeImgUrl":"fa fa-files-o"},
		        {"id": "14", "name": "City Master", "parent_id": "11","leafUrl":"<%=request.getContextPath() %>/admin/pvt/masters/city/","nodeType":"L" ,"nodeImgUrl":"fa fa-files-o"},
		        {"id": "15", "name": "Name Value Master", "parent_id": "11","leafUrl":"<%=request.getContextPath() %>/admin/pvt/masters/city/","nodeType":"L" ,"nodeImgUrl":"fa fa-files-o"},
		        
		        //Security
		        {"id": "16", "name": "Security", "parent_id": "0","leafUrl":"#","nodeType":"N" ,"nodeImgUrl":"fa fa-wrench"},
		        
		        //Environment
		        {"id": "18", "name": "Environment", "parent_id": "16","leafUrl":"#","nodeType":"N" ,"nodeImgUrl":"fa fa-gear"},
		        {"id": "19", "name": "Create App Context", "parent_id": "18","leafUrl":"<%=request.getContextPath() %>/admin/pvt/sec/env/department/","nodeType":"L" ,"nodeImgUrl":"fa fa-files-o"},
		        {"id": "20", "name": "Create Module", "parent_id": "18","leafUrl":"<%=request.getContextPath() %>/admin/pvt/sec/env/module/","nodeType":"L" ,"nodeImgUrl":"fa fa-files-o"},
		        {"id": "21", "name": "Record Page", "parent_id": "18","leafUrl":"<%=request.getContextPath() %>/admin/pvt/sec/env/page/","nodeType":"L" ,"nodeImgUrl":"fa fa-files-o"},
		        {"id": "22", "name": "Record Page Operation", "parent_id": "18","leafUrl":"<%=request.getContextPath() %>/admin/pvt/sec/env/op/","nodeType":"L" ,"nodeImgUrl":"fa fa-files-o"},
		       
		        //RBAC
		        {"id": "23", "name": "RBAC", "parent_id": "16","leafUrl":"#","nodeType":"N" ,"nodeImgUrl":"fa fa-gear"},
		        {"id": "24", "name": "Manage Role", "parent_id": "23","leafUrl":"<%=request.getContextPath() %>/admin/pvt/sec/rbac/role/","nodeType":"L" ,"nodeImgUrl":"fa fa-files-o"},
		        {"id": "25", "name": "Manage Group", "parent_id": "23","leafUrl":"<%=request.getContextPath() %>/admin/pvt/sec/rbac/group/","nodeType":"L" ,"nodeImgUrl":"fa fa-files-o"},
		        {"id": "26", "name": "Assign role to group", "parent_id": "23","leafUrl":"<%=request.getContextPath() %>/admin/pvt/sec/rbac/roleToGroup/","nodeType":"L" ,"nodeImgUrl":"fa fa-files-o"},
		        {"id": "27", "name": "User Category", "parent_id": "23","leafUrl":"<%=request.getContextPath() %>/admin/pvt/sec/rbac/userCategory/","nodeType":"L" ,"nodeImgUrl":"fa fa-files-o"},
		        {"id": "28", "name": "Internal User", "parent_id": "23","leafUrl":"<%=request.getContextPath() %>/admin/pvt/sec/rbac/appAdminUser/","nodeType":"L" ,"nodeImgUrl":"fa fa-files-o"},
		        {"id": "29", "name": "Web Users", "parent_id": "23","leafUrl":"<%=request.getContextPath() %>/admin/pvt/sec/rbac/webUser/","nodeType":"L" ,"nodeImgUrl":"fa fa-files-o"},
		        {"id": "30", "name": "Assign group to users", "parent_id": "23","leafUrl":"<%=request.getContextPath() %>/admin/pvt/sec/rbac/groupToUser/","nodeType":"L" ,"nodeImgUrl":"fa fa-files-o"},
		        {"id": "31", "name": "Assign RBAC", "parent_id": "23","leafUrl":"<%=request.getContextPath() %>/admin/pvt/sec/rbac/monitor/","nodeType":"L" ,"nodeImgUrl":"fa fa-files-o"},	       
		        
		        
		        //ACl
		        {"id": "32", "name": "ACL", "parent_id": "16","leafUrl":"#","nodeType":"N" ,"nodeImgUrl":"fa fa-gear"},
		        {"id": "33", "name": "Domain Master", "parent_id": "32","leafUrl":"<%=request.getContextPath() %>/admin/pvt/sec/acl/dmnMaster/","nodeType":"L" ,"nodeImgUrl":"fa fa-files-o"},
		        {"id": "34", "name": "Domain Action Master", "parent_id": "32","leafUrl":"<%=request.getContextPath() %>/admin/pvt/sec/acl/dmnActionMaster/","nodeType":"L" ,"nodeImgUrl":"fa fa-files-o"},
		        {"id": "35", "name": "Assign Permission", "parent_id": "32","leafUrl":"<%=request.getContextPath() %>/admin/pvt/sec/acl/aclPermissions/","nodeType":"L" ,"nodeImgUrl":"fa fa-files-o"},
		        {"id": "36", "name": "View ACL Tables", "parent_id": "32","leafUrl":"<%=request.getContextPath() %>/admin/pvt/sec/acl/monitor/aclInOne/","nodeType":"L" ,"nodeImgUrl":"fa fa-files-o"},
		       
		        //ABAC
		        {"id": "37", "name": "ABAC", "parent_id": "16","leafUrl":"#","nodeType":"N" ,"nodeImgUrl":"fa fa-gear"},
		        {"id": "38", "name": "Dummy", "parent_id": "37","leafUrl":"#","nodeType":"L" ,"nodeImgUrl":"fa fa-files-o"},
			        
	            ];
	
	var endMenu =getMenu("-1");
	function getMenu( parentID ){
	           return data.filter(function(node){ return ( node.parent_id === parentID ) ; }).map(function(node){
	               var exists = data.some(function(childNode){  return childNode.parent_id === node.id; });	                
	               var subMenu = (exists) ? '<ul class="treeview-menu">'+ getMenu(node.id) + '</ul>' : "";
	               var menu;                
	               if(exists)	            	 
	            	   menu= '<li class="treeview">'
		                          +'<a href="'+node.leafUrl+'"> <i class="'+node.nodeImgUrl+'"></i>'
		                          + '<span>' +node.name  +'</span>'
		                          + '<span class="pull-right-container">' 
		                          +'<i class="fa fa-angle-left pull-right"></i>'
		                          +'</span>'
		                          +'</a>'
		                          + subMenu
		                          + '</li>';
		          else		        	
		        	  menu= '<li>'
                      +'<a href="'+node.leafUrl+'"> <i class="'+node.nodeImgUrl+'"></i>'
                      + '<span>' +node.name  +'</span>'                      
                      +'</a>'
                   + '</li>' ;
	             
                   return menu;
		                    	   
	           });
	       }	
	    //Comma after each li has been inserted. It needs to be removed. g is used for global removal
	    var finalMenu=endMenu.join('').replace(/,/g, "");	   
	$('#menu2').html('<ul class="sidebar-menu" data-widget="tree" >'+finalMenu+ '</ul>');	
});// End of document ready




</script>
<span id="menu2"></span>
