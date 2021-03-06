/**
 * 
 */
package com.pvt.post.domain;

import java.time.LocalDate;
/**
 * @author Sanjeev
 *
 */
import java.util.Date;

/**
 * A simple POJO representing public posts
 */
public class PublicPost implements Post {
	private Long id;
	private Date date;
	private String message;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}