package com.pvt.post.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.pvt.sec.acl.adm.service.CustomSecurityService;
import com.pvt.post.domain.PersonalPost;
import com.pvt.post.domain.Post;

/**
 * Service for processing Personal related posts.
 * <p>
 * For a complete reference to Spring JDBC and getJdbcTemplate() see
 * http://static.springsource.org/spring/docs/3.0.x/spring-framework-reference/html/jdbc.html
 * <p>
 * For transactions, see
 * http://static.springsource.org/spring/docs/3.0.x/spring-framework-reference/html/transaction.html
 */
@Service("personalService")
@Transactional("appTransactionManager")
public class PersonalService extends JdbcDaoSupport implements GenericService {

	protected static Logger logger = LoggerFactory.getLogger(PersonalService.class);

	@Autowired
	@Qualifier("appDataSource")
	DataSource dataSource;

	@Autowired
	CustomSecurityService customSecurityService;

	@PostConstruct
	private void initialize() {
		logger.info("Start: Initializing jdbac template with datasource");
		setDataSource(dataSource);
		logger.info("End: Initializing jdbac template with datasource");
	}

	public Post getSingle(Long id) {
		try {
			logger.debug("Retrieving single personal post");

			// Prepare SQL statement
			String sql = "select id, date, message from personal_post where id = ?";

			// Assign values to parameters
			Object[] parameters = new Object[] { id };

			// Map SQL result to a Java object
			RowMapper<Post> mapper = new RowMapper<Post>() {
				public Post mapRow(ResultSet rs, int rowNum) throws SQLException {
					Post post = new PersonalPost();
					post.setId(rs.getLong("id"));
					post.setDate(rs.getDate("date"));
					post.setMessage(rs.getString("message"));
					return post;
				}
			};

			// Run query then return result
			return getJdbcTemplate().queryForObject(sql, mapper, parameters);

		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}

	public List<Post> getAll() {
		logger.debug("Retrieving all personal posts");

		// Prepare the SQL statement
		String sql = "select id, date, message from personal_post";

		// Map SQL result to a Java object
		RowMapper<Post> mapper = new RowMapper<Post>() {
			public Post mapRow(ResultSet rs, int rowNum) throws SQLException {
				Post post = new PersonalPost();
				post.setId(rs.getLong("id"));
				post.setDate(rs.getDate("date"));
				post.setMessage(rs.getString("message"));
				return post;
			}
		};

		// Run query then return result
		return getJdbcTemplate().query(sql, mapper);
	}

	public Boolean add(Post post) {
		try {
			logger.debug("Adding new post");

			SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(dataSource).withTableName("personal_post")
					.usingGeneratedKeyColumns("id");
			;
			java.util.Calendar cal = Calendar.getInstance();
			java.sql.Date sqlDate = new java.sql.Date(cal.getTime().getTime()); // your sql date

			Map<String, Object> parameters = new HashMap<>(2);

			parameters.put("date", sqlDate);
			
			parameters.put("message", getLoggedInUser()+" "+getAuthoritiesOfLoggedInUser()+" => "+post.getMessage());

			long recordId = (long) simpleJdbcInsert.executeAndReturnKey(parameters);

			//Add Acl permissions
			post.setId(recordId);
			customSecurityService.addAclPermissions(post);	

			// Return
			return true;

		} catch (Exception e) {
			logger.error(e.getMessage());
			return false;
		}
	}

	public Boolean edit(Post post) {
		try {
			logger.debug("Adding new post");
			// Prepare our SQL statement
			String updateSql = "update personal_post set date = ?, " + "message = ? where id = ?";

			// Assign values to parameters
			Object[] params = { post.getDate(), post.getMessage(), post.getId() };

			// define SQL types of the arguments
			int[] types = { Types.DATE, Types.CHAR, Types.INTEGER };
			int rows = getJdbcTemplate().update(updateSql, params, types);

			// Return
			return true;

		} catch (Exception e) {
			logger.error(e.getMessage());
			return false;
		}
	}

	public Boolean delete(Post post) {
		try {
			logger.debug("Deleting existing post");

			// Prepare our SQL statement
			String sql = "delete from personal_post where id = ?";

			// Assign values to parameters
			Object[] parameters = new Object[] { post.getId() };

			// Delete
			getJdbcTemplate().update(sql, parameters);

			// Remove ACL with objectId
			customSecurityService.removeACLByObject(post);
			return true;

		} catch (Exception e) {
			logger.error(e.getMessage());
			return false;
		}
	}
private String getLoggedInUser() {
		
		String username;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			username = ((UserDetails) principal).getUsername();
		} else {username = principal.toString();}		
		return username;
	}

	private String getAuthoritiesOfLoggedInUser() {

		return SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
	}

@Override
public Boolean addAclPermission(Post post, String userOrRoleName, List permissionArrList,boolean principal) {
	// TODO Auto-generated method stub
	boolean hasRecordBeenAdded =customSecurityService.addBulkAclPermissionsForRoleOrUser( post,userOrRoleName,permissionArrList,principal);
	return hasRecordBeenAdded;
}

@Override
public Boolean updateAclPermission(Post post) {
	// TODO Auto-generated method stub
	boolean hasRecordBeenUpdated=false;
    hasRecordBeenUpdated =customSecurityService.removeACLByObject(post);
	return hasRecordBeenUpdated;
}
}