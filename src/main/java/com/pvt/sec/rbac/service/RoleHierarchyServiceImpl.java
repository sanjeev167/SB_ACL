/**
 * 
 */
package com.pvt.sec.rbac.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.config.db.AppPersistentConstant;
import com.config.exception.common.CustomRuntimeException;
import com.config.exception.common.ExceptionApplicationUtility;
import com.pvt.sec.rbac.dto.RoleHierarchyDTO;
import com.pvt.sec.rbac.dto.RoleHierarchyJSON;
import com.pvt.sec.rbac.dto.RoleHierarchyJSONLeaf;
import com.pvt.sec.rbac.entity.RoleHierarchy;
import com.pvt.sec.env.repo.DepartmentRepository;
import com.pvt.sec.rbac.repo.RoleHierarchyRepository;

import com.pvt.sec.rbac.entity.AppRole;
import com.pvt.sec.rbac.repo.AppRoleRepository;

import com.share.AppUtil;
import com.share.NameValueM;
import com.share.grid_pagination.DataTableRequest;
import com.share.grid_pagination.DataTableResults;
import com.share.grid_pagination.PaginationCriteria;




/**
 * @author Sanjeev
 *
 */
@Transactional("appTransactionManager")
 @Service 
public class RoleHierarchyServiceImpl implements RoleHierarchyService {
	 static final Logger log = LoggerFactory.getLogger(RoleHierarchyServiceImpl.class);
		
		/** The entity manager. */
		@Autowired
		@PersistenceContext(unitName = AppPersistentConstant.APP_JPA_UNIT)
		private EntityManager entityManager;
		
		@Autowired
		DepartmentRepository departmentRepository;
		
		@Autowired
		RoleHierarchyRepository roleHierarchyRepository;
		
		@Autowired
		AppRoleRepository appRoleRepository;


		@Override
		public DataTableResults<RoleHierarchyDTO> loadGrid(HttpServletRequest request, String whereClauseForBaseQuery)
				throws CustomRuntimeException {
			log.info("     RoleHierarchyServiceImpl :==> loadGrid ==> Started");
			DataTableResults<RoleHierarchyDTO> dataTableResult;
			try {
				DataTableRequest<RoleHierarchy> dataTableInRQ = new DataTableRequest<RoleHierarchy>(request);
				
				PaginationCriteria pagination = dataTableInRQ.getPaginationRequest();
				String baseQuery="";

				if (whereClauseForBaseQuery.equals("")) {                        
					baseQuery=  "select"+ 
						    " child.id as id,"+
							" dm.name as contextName,"+					     
							" parent.head as parentName, "+
						    // " (select ar.role_name from app_role ar, role_hierarchy rh where ar.id=rh.parent_id)as parentName,"+
						     "  child.head as childName, " + 
						    // " (select ar.role_name from app_role ar, role_hierarchy rh where ar.id=rh.child_id)as childName,"+ 
						                   
						     " child.parent_in_hierarchy_id as parentId,"+
						     
						     " child.contents   as contents,"+                            
						     " child.active_c as activeC,"+              
						     " (SELECT COUNT(1)-1 FROM role_hierarchy ) AS totalrecords"+
										                
						     " from role_hierarchy child,role_hierarchy parent, department_master dm"+
										                
						     " where child.app_context_id=dm.id"+
						     " and child.parent_in_hierarchy_id=parent.id"+
						     " ORDER BY child.id";
					
			        }else {
			        	baseQuery=  "select"+ 
							    " child.id as id,"+
								" dm.name as contextName,"+					     
								" parent.head as parentName, "+
							    // " (select ar.role_name from app_role ar, role_hierarchy rh where ar.id=rh.parent_id)as parentName,"+
							     "  child.head as childName, " + 
							    // " (select ar.role_name from app_role ar, role_hierarchy rh where ar.id=rh.child_id)as childName,"+ 
							                   
							     " child.parent_in_hierarchy_id as parentId,"+
							     
							     " child.contents   as contents,"+                            
							     " child.active_c as activeC,"+              
							     " (SELECT COUNT(1)-1 FROM role_hierarchy ) AS totalrecords"+
											                
							     " from role_hierarchy child,role_hierarchy parent, department_master dm"+
											                
							     " where child.app_context_id=dm.id"+
							     " and child.parent_in_hierarchy_id=parent.id"+
							     " ORDER BY child.id";
			        }			
					
				//log.info("baseQuery ="+baseQuery);
				String paginatedQuery = AppUtil.buildPaginatedQuery(baseQuery, pagination);			
				//log.info("paginatedQuery ="+paginatedQuery);
				Query query = entityManager.createNativeQuery(paginatedQuery, "RoleHierarchyDTOMapping");
				@SuppressWarnings("unchecked")
				List<RoleHierarchyDTO> roleHierarchyList = query.getResultList();			
				dataTableResult = new DataTableResults<RoleHierarchyDTO>();
				dataTableResult.setDraw(dataTableInRQ.getDraw());
				dataTableResult.setListOfDataObjects(roleHierarchyList);
				if (!AppUtil.isObjectEmpty(roleHierarchyList)) {
					dataTableResult.setRecordsTotal(roleHierarchyList.get(0).getTotalrecords().toString());
					if (dataTableInRQ.getPaginationRequest().isFilterByEmpty()) {
						dataTableResult.setRecordsFiltered(roleHierarchyList.get(0).getTotalrecords().toString());
					} else {
						dataTableResult.setRecordsFiltered(Integer.toString(roleHierarchyList.size()));
					}
				}

			} catch (Exception ex) {
				throw ExceptionApplicationUtility.wrapRunTimeException(ex);
			}
			log.info("     RoleHierarchyServiceImpl :==> loadGrid ==> Ended");
			return dataTableResult;
		}

		// This will directly put your result into your mapped dto
		@Transactional("appTransactionManager")
		@Override
		public RoleHierarchyDTO getReordById(Integer id) throws CustomRuntimeException {
			log.info("     RoleHierarchyServiceImpl :==> getReordById ==> Started");
			RoleHierarchyDTO roleHierarchyDTO;
			try {
				RoleHierarchy roleHierarchy = roleHierarchyRepository.getOne(id);
				roleHierarchyDTO = new RoleHierarchyDTO();
				roleHierarchyDTO.setId(roleHierarchy.getId());
				roleHierarchyDTO.setContextId(roleHierarchy.getDepartmentMaster().getId() + "");
				
				roleHierarchyDTO.setParentId(roleHierarchy.getRoleHierarchy().getId() + "");			
				roleHierarchyDTO.setChildName(roleHierarchy.getHead());	
				
				roleHierarchyDTO.setRoleName(roleHierarchy.getAppRole().getRoleName());
				roleHierarchyDTO.setRoleNameId(roleHierarchy.getAppRole().getId()+"");
				
				roleHierarchyDTO.setContents(roleHierarchy.getContents());
				roleHierarchyDTO.setActiveC(roleHierarchy.getActiveC());
				
			} catch (Exception ex) {
				throw ExceptionApplicationUtility.wrapRunTimeException(ex);
			}
			log.info("     RoleHierarchyServiceImpl :==> getReordById ==> Ended");
			return roleHierarchyDTO;
		}

		@Transactional("appTransactionManager")
		@Override
		public RoleHierarchyDTO saveAndUpdate(RoleHierarchyDTO roleHierarchyDTO) throws CustomRuntimeException {
			log.info("     RoleHierarchyServiceImpl :==> saveAndUpdate ==> Started");
			RoleHierarchy roleHierarchy;
			RoleHierarchyDTO roleHierarchyDTONew;		
			try {			
				if (roleHierarchyDTO.getId() != null) {// Edit case				
					roleHierarchy = roleHierarchyRepository.getOne(roleHierarchyDTO.getId());						
				} else { // Add case
					roleHierarchy = new RoleHierarchy();
					}//End of Add
				
				///
				if (roleHierarchyDTO.getParentId() != null && !roleHierarchyDTO.getParentId().equals("")) {
					
					roleHierarchy.setRoleHierarchy(
							roleHierarchyRepository.getOne(Integer.parseInt(roleHierarchyDTO.getParentId())));
				}
				else {
					RoleHierarchy firstRoleHierarchy = new RoleHierarchy();
					firstRoleHierarchy.setHead("None");	
					
					roleHierarchy.setRoleHierarchy(firstRoleHierarchy);
				}
				
				
				//
				//Set the rest field
				int departmentId=Integer.parseInt(roleHierarchyDTO.getContextId());
				roleHierarchy.setDepartmentMaster(departmentRepository.getOne(departmentId));	
				if(!roleHierarchyDTO.getRoleNameId().equals("")&&roleHierarchyDTO.getRoleNameId()!=null)
				roleHierarchy.setAppRole(appRoleRepository.getOne(Integer.parseInt(roleHierarchyDTO.getRoleNameId())));
				else 
					{//This will execute at the start of role hierarchy preparation 
					AppRole dummyRole=null;
					String roleName=appRoleRepository.findAllRoleListWithSpecificNodeName().get(0).getRoleName();
					if(roleName!=null)
						dummyRole=appRoleRepository.findAllRoleListWithSpecificNodeName().get(0);
					else {
					dummyRole=new AppRole();
					dummyRole.setRoleName("No Role");
					dummyRole.setDepartmentMaster(departmentRepository.getOne(departmentId));				
					   }
					roleHierarchy.setAppRole(dummyRole);
					}
				roleHierarchy.setHead(roleHierarchyDTO.getChildName());
				roleHierarchy.setContents(roleHierarchyDTO.getContents());
				roleHierarchy.setActiveC("Y");
				
				//Now roleHierarchy is ready for saving or update
				RoleHierarchy returnedRoleHierarchy = roleHierarchyRepository.saveAndFlush(roleHierarchy);			
				roleHierarchyDTONew = new RoleHierarchyDTO(
						returnedRoleHierarchy.getId(),//Record id						
						returnedRoleHierarchy.getDepartmentMaster().getId()+"",//Context Id
						returnedRoleHierarchy.getRoleHierarchy().getId()+"",//Tree Parent Id
						returnedRoleHierarchy.getHead(), //Tree Child
						//Role Details
						returnedRoleHierarchy.getAppRole().getRoleName(),
						returnedRoleHierarchy.getAppRole().getId()+"",
						
						returnedRoleHierarchy.getContents(),//Content name
						returnedRoleHierarchy.getActiveC()//Active C
						);
				
			} catch (Exception ex) {
				ex.printStackTrace();
				throw ExceptionApplicationUtility.wrapRunTimeException(ex);
			}
			log.info("     RoleHierarchyServiceImpl :==> saveAndUpdate ==> Ended");
			return roleHierarchyDTONew;
		}

		@Override
		public boolean deleteOneRecord(Integer id) throws CustomRuntimeException {
			log.info("     RoleHierarchyServiceImpl :==> deleteOneRecord ==> Started");
			boolean isRecordDelete = true;
			try {
				roleHierarchyRepository.deleteById(id);
			} catch (Exception ex) {
				isRecordDelete = false;
				throw ExceptionApplicationUtility.wrapRunTimeException(ex);
			}
			log.info("     RoleHierarchyServiceImpl :==> deleteOneRecord ==> Ended");
			return isRecordDelete;
		}

		
		@Override
		public boolean deleteMultipleRecords(Integer[] recordIdArray) throws CustomRuntimeException {
			log.info("     RoleHierarchyServiceImpl :==> deleteMultipleRecords ==> Started");
			boolean isRecordDelete = true;
			try {
				roleHierarchyRepository.deleteRoleHierarchyWithIds(recordIdArray);
			} catch (Exception ex) {
				isRecordDelete = false;
				throw ExceptionApplicationUtility.wrapRunTimeException(ex);
			}
			log.info("     RoleHierarchyServiceImpl :==> deleteMultipleRecords ==> Started");
			return isRecordDelete;
		}

		@Override
		public List<NameValueM> getTreeParentNodeList() throws CustomRuntimeException {
			log.info("     RoleHierarchyServiceImpl :==> getTreeParentNodeList ==> Started");
			List<NameValueM> treeParentListForCombo = new ArrayList<NameValueM>();		
			NameValueM nameValue;
			try {
				List<RoleHierarchy> roleHierarchyList = roleHierarchyRepository.findAll();
				for (RoleHierarchy roleHierarchy : roleHierarchyList) {
					//if(!"None".equals(roleHierarchy.getHead())) {
					nameValue = new NameValueM(roleHierarchy.getId(), roleHierarchy.getHead());
					treeParentListForCombo.add(nameValue);
					//}
				}
			} catch (Exception ex) {
				throw ExceptionApplicationUtility.wrapRunTimeException(ex);
			}

			log.info("     RoleHierarchyServiceImpl :==> getTreeParentNodeList ==> Ended");

			return treeParentListForCombo;
		}
		
		@Override
		public List<NameValueM> getRoleList() throws CustomRuntimeException {
			log.info("     RoleHierarchyServiceImpl :==> getRoleList ==> Started");
			List<NameValueM> roleListForCombo = new ArrayList<NameValueM>();		
			NameValueM nameValue;
			try {
				List<AppRole> roleList = appRoleRepository.findAll();			
				for (AppRole appRole : roleList) {
					if(!"No Role".equals(appRole.getRoleName())) {
					nameValue = new NameValueM(appRole.getId(), appRole.getRoleName());
					roleListForCombo.add(nameValue);
					}
				}
			} catch (Exception ex) {
				throw ExceptionApplicationUtility.wrapRunTimeException(ex);
			}

			log.info("     RoleHierarchyServiceImpl :==> getRoleList ==> Ended");

			return roleListForCombo;
		}

		@Override
		public Integer getMinId() throws CustomRuntimeException {
			log.info("     RoleHierarchyServiceImpl :==> getMinId ==> Started");
			Integer recordId = roleHierarchyRepository.getMinId();
			log.info("     RoleHierarchyServiceImpl :==> getMinId ==> Ended");
			return recordId;
		}

		
		@Override
		public ArrayList<Object> getRoleHierarchyLevelWiseStructure() throws CustomRuntimeException {
			log.info("     RoleHierarchyServiceImpl :==> getSpecificTreeTypeStructure ==> Started");
			
			RoleHierarchy roleHierarchy = roleHierarchyRepository.findAllTreeNodeListWithSpecificNodeName().get(0);
			RoleHierarchy roleHierarchyTop=roleHierarchy.getRoleHierarchies().get(0);
			ArrayList<Object> roleStructureList = new ArrayList<Object>();
			RoleHierarchyJSON roleHierarchyJSONHasLeaf = null;			
			int pid=0;
							
				if(roleHierarchyTop.getRoleHierarchy()!=null)
				   pid=roleHierarchyTop.getRoleHierarchy().getId();            
	        	int id=roleHierarchyTop.getId();  
	        	roleHierarchyJSONHasLeaf=new RoleHierarchyJSON();	
	        	//Stuff unique parent details            	
	        	roleHierarchyJSONHasLeaf.setpId(pid+"");
	        	roleHierarchyJSONHasLeaf.setId(id+"");
	        	roleHierarchyJSONHasLeaf.setHead(roleHierarchyTop.getHead());
	        	roleHierarchyJSONHasLeaf.setContents(roleHierarchyTop.getContents());     	       	
	        	roleHierarchyJSONHasLeaf.setChildren(prepareItsChildren(roleHierarchyTop.getRoleHierarchies()));       	
				
			log.info("     RoleHierarchyServiceImpl :==> getSpecificTreeTypeStructure ==> Ended");
			roleStructureList.add(roleHierarchyJSONHasLeaf);		
			return roleStructureList;
		}
		
		private ArrayList<Object> prepareItsChildren(List<RoleHierarchy>roleHierarchyList){
			ArrayList<Object> roleStructureList = new ArrayList<Object>();
			
			RoleHierarchyJSONLeaf childHasNoLeaf=null;
			RoleHierarchyJSON childHasLeaf=null;
			//Stuff its all children recursively
	    	for(RoleHierarchy roleHierarchy:roleHierarchyList) {  
	    		if(roleHierarchy.getRoleHierarchies().size()!=0&&roleHierarchy.getRoleHierarchies()!=null) {	
	    			childHasLeaf=new RoleHierarchyJSON();
	    		childHasLeaf.setpId(roleHierarchy.getRoleHierarchy().getId()+"");
	    		childHasLeaf.setId(roleHierarchy.getId()+"");
	    		childHasLeaf.setHead(roleHierarchy.getHead());
	    		childHasLeaf.setContents(roleHierarchy.getContents());    		
	    		//if(roleHierarchy.getRoleHierarchies()!=null)		
	    		childHasLeaf.setChildren(prepareItsChildren(roleHierarchy.getRoleHierarchies()));      		
	    		roleStructureList.add(childHasLeaf);
	    		}
	    		else {
	    			childHasNoLeaf=new RoleHierarchyJSONLeaf();
	    			childHasNoLeaf.setpId(roleHierarchy.getRoleHierarchy().getId()+"");
	    			childHasNoLeaf.setId(roleHierarchy.getId()+"");
	    			childHasNoLeaf.setHead(roleHierarchy.getHead());
	    			childHasNoLeaf.setContents(roleHierarchy.getContents());    		
	        		//if(roleHierarchy.getRoleHierarchies()!=null)		
	        			//childHasNoLeaf.setChildren(prepareItsChildren(roleHierarchy.getRoleHierarchies()));      		
	        		roleStructureList.add(childHasNoLeaf);
	    			
	    		}
	    	}//End of children loop
			return roleStructureList;		
		}//End of prepareItsChildren
		
		
		@Override
		public Map<String, List<String>> getRoleHierarchyConfiguredForSecurity() throws CustomRuntimeException {
			Map<String, List<String>> roleHierarchyMap = new HashMap<>();		
			List<RoleHierarchy> roleHierarchyList = roleHierarchyRepository.findAll();		
			for(RoleHierarchy roleHierarchy:roleHierarchyList) {
			prepareParentAndChildRoleHieraricalRelationshipForSecurity(roleHierarchy,roleHierarchyMap);
			}		
			/*roleHierarchyMap.put("ROLE_MASTER_DEO", Arrays.asList("ROLE_SUPPORT"));
			roleHierarchyMap.put("ROLE_API_DEO", Arrays.asList("ROLE_SUPPORT"));
			roleHierarchyMap.put("ROLE_SECURITY_SUPPORT", Arrays.asList("ROLE_SUPPORT"));
					
			roleHierarchyMap.put("ROLE_API_MANAGER", Arrays.asList("ROLE_API_DEO"));
			
			roleHierarchyMap.put("ROLE_SECURITY_MANAGER", Arrays.asList("ROLE_SECURITY_SUPPORT","ROLE_MASTER_DEO"));
			
			roleHierarchyMap.put("ROLE_SUPER_ADMIN", Arrays.asList("ROLE_SECURITY_MANAGER","ROLE_API_MANAGER"));
			*/		
			return roleHierarchyMap;
		}
		
		
		private Map<String, List<String>> prepareParentAndChildRoleHieraricalRelationshipForSecurity(
				RoleHierarchy roleHierarchyParent, Map<String, List<String>> roleHierarchyMap) {
	       
	        ArrayList<String> childRoleArrayList=null;
	        if(roleHierarchyParent.getAppRole()!=null && !roleHierarchyParent.getAppRole().getRoleName().equals("None")) {
	           //Take out parent role
	      		String parentRole="ROLE_"+roleHierarchyParent.getAppRole().getRoleName();
	      		
	      		//Take its immediate children's role				
	      		childRoleArrayList=new ArrayList<String>();
	      		clollectAllChildRolesOfParent(childRoleArrayList,roleHierarchyParent.getRoleHierarchies());
	      		if(!parentRole.equals("")&&childRoleArrayList.size()!=0) {
	      		roleHierarchyMap.put(parentRole, childRoleArrayList);
	      		//System.out.print("\t Parent Role => "+parentRole);
	      		//log.info("\t\t Child Role => "+childRoleArrayList);
	      		}
	        }      		
			return roleHierarchyMap;
		}	
		
		private void clollectAllChildRolesOfParent(ArrayList<String> childRoleArrayList,List<RoleHierarchy> childRoleList){
			for(RoleHierarchy roleHierarchyChild:childRoleList) {
	  			String childRole="ROLE_"+roleHierarchyChild.getAppRole().getRoleName();
	  			childRoleArrayList.add(childRole);  			
	  		}		
		}

		@Override
		public boolean FieldValueWithParentIdAndChildExists(Object parentValue, String parentId, Object fieldValue,
				String fieldName) throws UnsupportedOperationException, CustomRuntimeException {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean FieldValueWithParentIdAndChildExists(Object parentIdValue, String parentId, Object fieldValue,
				String fieldName, Object idValue, String idFieldName) throws CustomRuntimeException {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean FirstChildValueWithParentIdExists(Object parentIdValue, String parentId, Object fieldValue,
				String fieldName, Object idValue, String idFieldName) throws CustomRuntimeException {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean SecondChildValueWithParentIdExists(Object parentIdValue, String parentId, Object fieldValue,
				String fieldName, Object idValue, String idFieldName) throws CustomRuntimeException {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean ThirdChildValueWithParentIdExists(Object parentIdValue, String parentId, Object fieldValue,
				String fieldName, Object idValue, String idFieldName) throws CustomRuntimeException {
			// TODO Auto-generated method stub
			return false;
		}

}
