/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webapps.servlets;

import com.utilities.date_utilities.DateUtilities;
import com.webapps.constants.WebConstants;
import com.webapps.utility.StringIgnoreCaseComparator;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author reid
 */
@WebServlet(name = "AbstractServlet", urlPatterns = {"/AbstractServlet"})
public abstract class AbstractServlet extends HttpServlet {

    private static Log log = LogFactory.getLog(AbstractServlet.class);
    private static Logger LOGGER = Logger.getLogger(AbstractServlet.class.getName());
    
    private static final String CLASS_NAME          = DailyTransactionEntry.class.getName();
    private static final String NOTE_DROP_DOWN_LIST = CLASS_NAME.concat(".noteDropDownList");
    
    private static final int DATE = 0;
    private static final int TIME = 1;
    private static final int AMOUNT = 2;
    private static final int ACCOUNT = 3;
    private static final int NOTE = 4;
               
     public AbstractServlet()
     {
         super();
         Level level = WebConstants.getLogLevel();
         LOGGER.setLevel(level);
     }//constructor//    
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @param title
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response, String title, List<String[]> list)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            Map<String,String> sortedMap = getParameters(request,response);
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.print("<title>");
            out.print(title);
            out.println("</title>");            
            out.println("</head>");
            out.println("<body>");
            out.print("<h1>Servlet ");
            out.print(title);
            out.println(" at " + request.getContextPath() + "</h1>");
            out.println("<br/>");
            out.println("<h5>Request Parameters</h5>");
            out.println("<ol>");
            for (Map.Entry<String,String> entry : sortedMap.entrySet()) {
                out.print("<li>");
                out.print(entry.getKey() + "=" + entry.getValue());
                out.println("</li>");
            }//for//
            out.println("</ol>");
            out.println("<br/>");
            if(list != null) {
                out.println("<ol>");
                ListIterator<String[]> itr = list.listIterator();
                String[] line = null;
                int j = 1;
                while(itr.hasNext()) {
                    line = itr.next();
                    out.print("<li>");
                    j=1;
                    for(int i=0; i < line.length; i++) {
                        out.print(line[i]);
                        if(j < line.length)
                            out.print(",");
                        j++;
                    }//for//
                    out.println("</li>");
                }//while//
                out.println("</ol>");
            }//if//
            out.println("</body>");
            out.println("</html>");
        }//try//
    }//processRequest//
    
    protected void forward(HttpServletRequest request, 
                          HttpServletResponse response,
                          String path) throws ServletException,
                                              IOException,
                                              IllegalStateException
    {
        ServletContext context = getServletContext();
        RequestDispatcher dispatch = context.getRequestDispatcher(path);
        dispatch.forward(request, response);
    }
    
    /**
     * 
     * @param request servlet request
     * @param response servlet response
     * @param myList 
     */
    protected void loadNoteDropDownList(HttpServletRequest request,
                                      HttpServletResponse response,
                                      List<String[]> myList) {
        
       Iterator<String[]> myListIterator = myList.iterator();
       String[] temp;
       StringBuilder sb = new StringBuilder();
       List<String>searchList = new ArrayList();
       sb.append("{");
       while(myListIterator.hasNext()) {
           temp = myListIterator.next();           
           if(searchList.indexOf(temp[NOTE]) < 0) {
                  searchList.add(temp[NOTE]);
                  sb.append(temp[NOTE] + ", ");
           }//if//
       }//while//
       sb.append("}");
       LOGGER.log(Level.FINE,"Note Drop Down List Before Sort = " + sb.toString());
       Collections.sort(searchList);
       
       Iterator<String> searchListIterator = searchList.iterator();
       sb.replace(0, sb.length(), "");  // Clear sb
       sb.append("{");
       while(searchListIterator.hasNext()) {
           sb.append(searchListIterator.next() + ", ");
       }//while//
       sb.append("}");
       LOGGER.log(Level.FINE,"Note Drop Down List After Sort = " + sb.toString());
       request.setAttribute(NOTE_DROP_DOWN_LIST, searchList);        
    }//loadNoteDropDownList//
    
    /**
     * 
     * @param request servlet request
     * @param response servlet response
     * @param myList 
     */
    protected void loadAccountDropDownList(HttpServletRequest request,
                                           HttpServletResponse response,
                                           List<String[]> myList) {
        
       Iterator<String[]> myListIterator = myList.iterator();
       String[] temp;
       StringBuilder sb = new StringBuilder();
       List<String>searchList = new ArrayList();
       sb.append("{");
       while(myListIterator.hasNext()) {
           temp = myListIterator.next();           
           if(searchList.indexOf(temp[ACCOUNT]) < 0) {
                  searchList.add(temp[ACCOUNT]);
                  sb.append(temp[ACCOUNT] + ", ");
           }//if//
       }//while//
       sb.append("}");
       LOGGER.log(Level.FINE,"Account Drop Down List Before Sort = " + sb.toString());
       Collections.sort(searchList);
       
       Iterator<String> searchListIterator = searchList.iterator();
       sb.replace(0, sb.length(), "");  // Clear sb
       sb.append("{");
       while(searchListIterator.hasNext()) {
           sb.append(searchListIterator.next() + ", ");
       }//while//
       sb.append("}");
       LOGGER.log(Level.FINE,"Account Drop Down List After Sort = " + sb.toString());
       request.setAttribute("accountDropDownList", searchList);        
    }//loadNoteDropDownList//
    
    protected Map getParameters(HttpServletRequest request, HttpServletResponse response) {
        
        int ZERO = 0;
        
        Map<String,String[]> map = request.getParameterMap();
        StringIgnoreCaseComparator sc = new StringIgnoreCaseComparator();
        TreeMap<String,String> sortedMap = new TreeMap<String,String>(sc); 
        StringBuilder sb = new StringBuilder();
        sb.append("Parameter Map Unsorted = ");
        sb.append("{");
        String key = null;
        String value = null;
        for (Map.Entry<String, String[]> entry : map.entrySet()) {
            key = entry.getKey();
            value = entry.getValue()[ZERO];
	    sortedMap.put(key, value);
            sb.append(key + "=" + value + ",");
        }//for//
        sb.append("}");
        LOGGER.log(Level.FINE,sb.toString());
        
        sb.replace(0, sb.length(), "");
        sb.append("Parameter Map Sorted = ");
        sb.append("{");
        for (Map.Entry<String,String> entry : sortedMap.entrySet()) {
            key = entry.getKey();
            value = entry.getValue();
            sb.append(key + "=" + value + ",");
        }//for//
        sb.append("}");
        LOGGER.log(Level.FINE, sb.toString());
        return sortedMap;
    }//getParameters//
    
    /**
     * Return true or false to indicate that the form  date
     * and time are valid. The form date can be entered either 
     * as YYYY-MM-DD or MM/DD/YYYY.
     * 
     * @param request
     * @param response
     * @return 
     */
    protected boolean isFormDateValid(String dateString) {
                        
        Locale locale = Locale.US;
        if(dateString.indexOf("/") > -1) {
           if(DateUtilities.isDateValid(dateString,
                                        WebConstants.DATE_FORMAT_MM_DD_YY,
                                        locale)) {
               return true;
           }//if//
           else {
               return false;
           }//else//
        }//if//
        if(dateString.indexOf("-") > -1) {
           if(DateUtilities.isDateValid(dateString,
                                        WebConstants.DATE_FORMAT_YYYY_MM_DD,
                                        locale)) {
               return true;
           }//if//
           else {
               return false;
           }//else//
        }//if//
        if(DateUtilities.isDateValid(dateString,
                                     WebConstants.DATE_FORMAT_MM_DD_YY,
                                     locale)) {
            return true;
        }//if//
        else {
            return false;
        }//else//
    }//isFormDateValid// 
}//AbstractServlet//
