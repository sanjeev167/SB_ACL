/**
 * 
 */
package com.share;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Sanjeev
 *
 */
public class LoadPropertiesFileStatically {
	private static final Logger log=LoggerFactory.getLogger(LoadPropertiesFileStatically.class);
	private static Properties prop;    
	static {
		InputStream is = null;
		try {
			prop = new Properties();
			is = LoadPropertiesFileStatically.class.getClassLoader().getResourceAsStream("businessError.properties");
			prop.load(is);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
     
    public static String getPropertyValue(String key){
        return prop.getProperty(key);
    }
     
   /* public static void main(String a[]){         
        log.info("Sanju be_1.error: "+getPropertyValue("be_1.error"));
        
    }*/
}
