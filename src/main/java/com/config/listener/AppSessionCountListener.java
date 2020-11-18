/**
 * 
 */
package com.config.listener;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author Sanjeev
 *
 */

@Component
public class AppSessionCountListener implements HttpSessionListener {
	private static final Logger log = LoggerFactory.getLogger(AppSessionCountListener.class);
	private int sessionCount = 0;

	@Override
	public void sessionCreated(HttpSessionEvent event) {
		log.info("\n######:--> Created: Session with session id [" + event.getSession().getId() + "]");
		event.getSession().setMaxInactiveInterval(1800);// In seconds
		++sessionCount;
		log.info("\n######:--> Total Active-Session = " + sessionCount);
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent event) {
		log.info("\n######:--> Destroyed: Session-Id [" + event.getSession().getId());
		--sessionCount;
		log.info("\n######:--> Total Active-Session = " + sessionCount);
	}
}