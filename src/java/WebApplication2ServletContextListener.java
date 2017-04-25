
import com.webapps.constants.WebConstants;
import static com.webapps.constants.WebConstants.getServletContext;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author reid
 */
public class WebApplication2ServletContextListener implements ServletContextListener {
    
    private static Log log = LogFactory.getLog(ServletContextListener.class);
    private static Logger LOGGER = Logger.getLogger(ServletContextListener.class.getName());

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        LOGGER.info("Servlet Context Initialized");
        WebConstants.setServletContext(sce.getServletContext());
        getSetProperties();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        LOGGER.info("Servlet Context Destroyed");
    }
    
    /**
     * 
     * 
     */
    private void getSetProperties()
    {
       Properties prop = new Properties();
       LOGGER.info("WEB-INF Path=" + getServletContext().getRealPath("/WEB-INF/webpages"));
       InputStream in = null;
       try {
           String propFileName = getServletContext().getRealPath(WebConstants.CONFIG_FILE_PATH + WebConstants.CONFIG_FILE_NAME);
           in = new FileInputStream(propFileName);
           prop.load(in);
           WebConstants.setFilePath(prop.getProperty(WebConstants.FILE_PATH));
           WebConstants.setAcctNbr(prop.getProperty(WebConstants.ACCT_NBR));
           WebConstants.setAcctNbrDesc(prop.getProperty(WebConstants.ACCT_NBR_DESC));
           WebConstants.setLogLevel(prop.getProperty(WebConstants.LOG_LEVEL));
           LOGGER.info(WebConstants.FILE_PATH + "=" + WebConstants.getFilePath());
           LOGGER.info(WebConstants.ACCT_NBR + "=" + WebConstants.getAcctNbr());
           LOGGER.info(WebConstants.ACCT_NBR_DESC + "=" + WebConstants.getAcctNbrDesc());
           LOGGER.info(WebConstants.LOG_LEVEL + "=" + WebConstants.getLogLevel().toString());
       }
       catch(IOException ex) {
           LOGGER.severe(ex.getMessage());
           WebConstants.setFilePath(null);
           WebConstants.setAcctNbr(null);
           WebConstants.setAcctNbrDesc(null);
           LOGGER.severe(WebConstants.FILE_PATH + "=" + WebConstants.getFilePath());
           LOGGER.severe(WebConstants.ACCT_NBR + "=" + WebConstants.getAcctNbr());
           LOGGER.severe(WebConstants.ACCT_NBR_DESC + "=" + WebConstants.getAcctNbrDesc());
       }
       finally {
           if (in != null) {
               try {
                   in.close();
               }
               catch (IOException e) {
                   LOGGER.severe(e.getMessage());
               }//try//
           }//if//
       }//finally
    }//getSetProperties//
}//WebApplication2ServletContextListener