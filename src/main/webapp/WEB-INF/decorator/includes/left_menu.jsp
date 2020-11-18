<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<aside class="main-sidebar">
	<!-- sidebar: style can be found in sidebar.less -->
	<section class="sidebar">
	     <!-- Sidebar user panel (optional) -->
       <!-- Sidebar user panel (optional) -->
      <div class="user-panel" style="height: auto;">
        <div class="pull-left image">
          <img src="<%request.getContextPath();%>/resources/assets/dist/img/user2-160x160.jpg" class="img-circle" alt="User Image">
        </div>
        <div class="pull-left info">
          <p><sec:authentication property="principal.username" /><br>
          <sec:authentication property="principal.authorities" /></p>
          <!-- Status -->
          <a href="#"><i class="fa fa-circle text-success"></i> Online</a>
        </div>
      </div>

								
      <!-- search form (Optional) -->
      <form action="#" method="get" class="sidebar-form">
        <div class="input-group">
          <input type="text" name="q" class="form-control" placeholder="Search...">
          <span class="input-group-btn">
              <button type="submit" name="search" id="search-btn" class="btn btn-flat"><i class="fa fa-search"></i>
              </button>
            </span>
        </div>
      </form>
      <!-- /.search form -->
	

		
		<%@ include file="dnMenu.jsp"%>
	</section>
	<!-- /.sidebar -->
</aside>
