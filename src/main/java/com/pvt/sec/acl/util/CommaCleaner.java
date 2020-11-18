/**
 * 
 */
package com.pvt.sec.acl.util;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;

/**
 * @author Sanjeev
 *
 */
public class CommaCleaner {
	 Splitter splitter = Splitter.on(',').omitEmptyStrings().trimResults();
	    Joiner joiner = Joiner.on(',').skipNulls();

	    public String cleanUpCommas(String string) {
	        return joiner.join(splitter.split(string));
	    }
}
