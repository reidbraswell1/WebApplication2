/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webapps.constants;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextListener;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author reid
 */
public class WebConstants {
    private static String         filePath;
    private static ServletContext servletContext;
    private static String         acctNbr;
    private static String         acctNbrDesc;
    private static Level          logLevel;
    public  static final String CONFIG_FILE_NAME="config.properties";
    public  static final String CONFIG_FILE_PATH="/WEB-INF/config/";
    public  static final String FILE_PATH="filepath";
    public  static final String ACCT_NBR="acctnbr";
    public  static final String ACCT_NBR_DESC="acctnbrdesc";
    public  static final String DATE_FORMAT_YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";
    public  static final String DATE_FORMAT_YYYY_MM_DD = "yyyy-MM-dd";
    public  static final String DATE_FORMAT_MM_DD_YYYY_HH_MM_AM_PM = "MM/dd/yyyy hh:mm aa";
    public  static final String DATE_FORMAT_MM_DD_YY_HH_MM_AA = "MM/dd/yy hh:mmaa";
    public  static final String DATE_FORMAT_MM_DD_YY_HH_MM = "MM/dd/yy HH:mm";
    public  static final String DATE_FORMAT_MM_DD_YY = "MM/dd/yy";
    public  static final String LOG_LEVEL = "loglevel";
    
    private static Log log = LogFactory.getLog(ServletContextListener.class);
    private static Logger LOGGER = Logger.getLogger(ServletContextListener.class.getName());
    
    public WebConstants() {
        
    }//constants//

    /**
     * @return the servletContext
     */
    public static ServletContext getServletContext() {
        return servletContext;
    }
    
    /**
     * @return the servletContext
     */
    public static String getFilePath() {
        return filePath;
    }


    /**
     * @param servletContext the servletContext to set
     */
    public static void setServletContext(ServletContext servletContext) {
        WebConstants.servletContext = servletContext;
    }
    
    /**
     * @param servletContext the servletContext to set
     */
    public static void setFilePath(String filePath) {
        WebConstants.filePath = filePath;
    }

    /**
     * @return the acctNbr
     */
    public static String getAcctNbr() {
        return acctNbr;
    }

    /**
     * @param aAcctNbr the acctNbr to set
     */
    public static void setAcctNbr(String aAcctNbr) {
        acctNbr = aAcctNbr;
    }

    /**
     * @return the acctNbrDesc
     */
    public static String getAcctNbrDesc() {
        return acctNbrDesc;
    }

    /**
     * @param aAcctNbrDesc the acctNbrDesc to set
     */
    public static void setAcctNbrDesc(String aAcctNbrDesc) {
        acctNbrDesc = aAcctNbrDesc;
    }  

    /**
     * @return the logLevel
     */
    public static Level getLogLevel() {
        return logLevel;
    }

    /**
     * @param aLoglevel the logLevel to set
     */
    public static void setLogLevel(String aLogLevel) {
        
        try {
            logLevel = Level.parse(aLogLevel);
        }
        catch (IllegalArgumentException e) {
            LOGGER.log(Level.SEVERE,"Log Level in properties file is not parsable - setting level to INFO");
            try {
                logLevel = Level.parse("INFO");
            }//try//
            catch (IllegalArgumentException ex) {
                LOGGER.log(Level.SEVERE,"Unable to set log level");
            }//catch//
        }//catch//
    }//setLogLevel//
    
    
}//Constants//
