/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webapps.servlets;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory; 
import java.util.logging.Logger;
import com.webapps.constants.WebConstants;
import com.opencsv.CSVReader;
import com.utilities.date_utilities.DateUtilities;
import com.webapps.transactionline.TransactionLineList;
import com.webapps.transactionline.TransactionListObject;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author reid
 */
public class DailyTransactionSearchServlet extends AbstractServlet {

     private static Log log = LogFactory.getLog(DailyTransactionSearchServlet.class);
     private static Logger LOGGER = Logger.getLogger(DailyTransactionSearchServlet.class.getName());
     
     private static final String CLASS_NAME        = DailyTransactionSearchServlet.class.getName();
     private static final String MESSAGE           = CLASS_NAME.concat(".message");
     private static final String MESSAGE_P         = "message";
     private static final String RESULT_SET        = CLASS_NAME.concat(".resultSet");
     private static final String TRAN_ITEMS_PAGE   = CLASS_NAME.concat(".tranItemsPage");
     
     private static final int DATE    = 0;
     private static final int TIME    = 1;
     private static final int AMOUNT  = 2;
     private static final int ACCOUNT = 3;
     private static final int NOTE    = 4;
     
     private static final String ACCOUNT_SEARCH_P   = "account_search";     
     private static final String DATE_SEARCH_P      = "date_search";
     private static final String DEBUG_P            = "Debug";
     private static final String DEBUG_ERROR_P      = "DebugError";
     private static final String END_DATE_P         = "end_date";
     private static final String NOTE_SEARCH_P      = "note_search";
     private static final String START_DATE_P       = "start_date";
     private static final String SUBMIT_P           = "Submit";
     private static final String TRAN_ACCOUNT_P     = "tran_account";
     private static final String TRAN_NOTE_P        = "tran_note";
     private static final String TRAN_NOTE_S        = "searchLikeInput";
     private static final String TRAN_SEARCH_P      = "tran_search";
     private static final String TRAN_SEARCH_EQUAL  = "tran_searchEqual";
     private static final String TRAN_SEARCH_LIKE   = "tran_searchLike";
     private static final String TRAN_ITEMS_PAGE_P  = "tran_items_page";
     
     private static final String SEARCH_PAGE_WEB_INF  = "/WEB-INF/webpages/DailyTransactionSearch.jsp";
     private static final String SEARCH_PAGE_ROOT     = "/jsp/DailyTransactionSearch.jsp";
     private static final String RESULTS_PAGE_WEB_INF = "/WEB-INF/webpages/DailyTransactionsSearchResults.jsp";
     private static final String RESULTS_PAGE_ROOT    = "/jsp/DailyTransactionsSearchResults.jsp";
     private static final String DEBUG_PAGE_WEB_INF   =  "/WEB-INF/webpages/DailyTransactionSearchDebug.jsp";
     
     public DailyTransactionSearchServlet() {
        super();
        Level level = WebConstants.getLogLevel();
        LOGGER.setLevel(level);
     }
     
     /**
      * 
      * Build search parameters message.
      * 
      * @param request
      * @param response
      * @param map 
      */
     private void buildSearchParametersMessage(HttpServletRequest request,
                                              HttpServletResponse response,
                                              Map<String,String>map) {
         
         StringBuilder sb = new StringBuilder();
         if(map.containsKey(DATE_SEARCH_P)) {
             sb.append("Start Date=");
             sb.append(map.get(START_DATE_P));
             sb.append(" End Date=");
             sb.append(map.get(END_DATE_P));
             sb.append(",");
         }//if//
         if(map.containsKey(ACCOUNT_SEARCH_P)) {
             sb.append("Account=");
             sb.append(map.get(TRAN_ACCOUNT_P));
             sb.append(",");
         }//if//
         if(map.containsKey(NOTE_SEARCH_P)) {
             sb.append("Note=");
             sb.append(map.get(TRAN_NOTE_P));
             sb.append(",");
         }//if//
         if(map.containsKey(TRAN_ITEMS_PAGE_P)) {
             sb.append("Items per page=");
             sb.append(map.get(TRAN_ITEMS_PAGE_P));
         }//if//
         LOGGER.log(Level.INFO,"Search Parameters Message = " + sb.toString());
         request.getSession().setAttribute(MESSAGE,sb.toString());
     }//buildSearchParametersMessage//
    
     /**
      * 
      * Build invalid date message.
      * 
      * @param map
      * @param key
      * @return 
      */
     private String buildInvalidDateMessage(Map<String,String>map, String key) {

        StringBuilder sb  = new StringBuilder();
        sb.append("Invalid " + key + " ");
        sb.append(map.get(key));
        String message = sb.toString();

        return message;
    }//buildInvalidDateTimeMessage//
    
     /**
      * Validate the Start and End Dates.
      * Return a null message if there were no problems otherwise
      * return a message indicating the invalid date.
      * 
      * @param parameterMap
      * @return 
      */
    private String validateStartEndDate(Map<String,String> parameterMap) {

        String message = null;
        String startDateString = parameterMap.get(START_DATE_P);
        String endDateString   = parameterMap.get(END_DATE_P);
        boolean isValidStartDate = isFormDateValid(startDateString);
        boolean isValidEndDate   = isFormDateValid(endDateString);
        if(isValidStartDate) {
            if(isValidEndDate) {
                ;
            }//if//
            else {
                message = buildInvalidDateMessage(parameterMap,END_DATE_P);
            }//else//
        }//if//
        else  {
            message = buildInvalidDateMessage(parameterMap,START_DATE_P);
        }//else//
        return message;
    }//validateStartEndDate//

    
    /**
     * Validate the check boxes to make sure at least 1 search type 
     * was selected.
     * 
     * @param request
     * @param response
     * @param map
     * @return 
     */
    private String validateCheckBoxes(HttpServletRequest request, 
                                      HttpServletResponse response,
                                      Map<String,String>map) {
        
        String message = null;
        if(!map.containsKey(DATE_SEARCH_P) && !map.containsKey(ACCOUNT_SEARCH_P) && !map.containsKey(NOTE_SEARCH_P)) {
            message = "Must choose at least 1 method of searching from the check boxes";
        }
        request.setAttribute(MESSAGE,message);
        return message;
    }//validateCheckBoxes//
    

    /**
     * Debug method to forward request parameters,
     * attributes and servlet context attributes to
     * the debug jsp page.
     * 
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     * @throws IllegalStateException 
     */
    private void debugRequestParameters(HttpServletRequest request, HttpServletResponse response)
                                       throws ServletException,
                                              IOException,
                                              IllegalStateException
    {
        request.getServletContext().getAttributeNames();
        request.getAttributeNames();
        Map<String, String[]> map = request.getParameterMap();

        for (Map.Entry<String, String[]> entry : map.entrySet()) {
                LOGGER.log(Level.FINE, "Request Parameter Map = " + entry.getKey() + "=" + entry.getValue()[0]);
        }//for//

        Enumeration<String> e1 = request.getAttributeNames();
        
        while(e1.hasMoreElements()) {
            LOGGER.log(Level.FINE, "Request Attribute Names = " + e1.nextElement().toString());
        }
        
        Enumeration<String> e2 = request.getServletContext().getAttributeNames();
        
        while(e2.hasMoreElements()) {
            LOGGER.log(Level.FINE, "Servlet Context Attribute Names = " + e2.nextElement().toString());
        }

        forward(request,response, DEBUG_PAGE_WEB_INF);

    }//debugRequestParmeters  
    
    /**
     * 
     * Filter the incoming lines from the text file
     * producing a matching result set based on search
     * criteria.
     * 
     * @param map
     * @return
     * @throws FileNotFoundException
     * @throws IOException 
     */
    private List<String[]> filterResultSet(Map<String,String>map) 
                                     throws FileNotFoundException,
                                            IOException {

       LOGGER.log(Level.FINE,"Begin Filter");
    
       List<String[]> resultList = new ArrayList();
       CSVReader reader = new CSVReader(new FileReader(WebConstants.getFilePath()));
       String [] nextLine = null;
       int k = 1;
       while ((nextLine = reader.readNext()) != null) {
         //  nextLine[] is an array of values from the line
         StringBuilder sb = new StringBuilder();
         sb.append("Raw Line " + k + ". = "); 
         for(int i = 0; i < nextLine.length; i++) {
             sb.append(nextLine[i] + " ");
         }//for//
         LOGGER.log(Level.FINE,sb.toString());
         k++;
         sb.append("Raw Line " + k + ". = ");
         if(isDateSearchValid(map, nextLine) && isAccountSearchValid(map, nextLine) && isNoteSearchValid(map, nextLine))
             resultList.add(nextLine);
       }//while//
       
       // Log Result Set      
       ListIterator<String[]> itr = resultList.listIterator();
       String[] line = null;
       int j = 1;
       StringBuilder sb = new StringBuilder();
       while(itr.hasNext()) {
           sb.append("Result Set " + j + ". = ");
           line = itr.next();
           for(int i=0; i < line.length; i++) {
               sb.append(line[i]);
               if(i < line.length - 1)
                  sb.append(",");
           }//for//
           LOGGER.log(Level.FINE,sb.toString());
           sb.replace(0,sb.length(),"");
           j++;
       }//while//
       return resultList;
    }//filterResultSet//

    /**
     * 
     * 
     * 
     * @param map
     * @param nextLine
     * @return 
     */
    private boolean isDateSearchValid(Map<String,String>map, String[] nextLine) {

       boolean dateSearch = true;

       String startDateString = null;
       String endDateString = null;

       Date endDate = null;
       Date startDate = null;
       Date searchDate = null;
       
       if(map.containsKey(DATE_SEARCH_P)) {
          startDateString = (String)map.get(START_DATE_P);
          endDateString = (String)map.get(END_DATE_P);
          if(startDateString.indexOf("/") > -1) {
             startDate = DateUtilities.getDate(startDateString, WebConstants.DATE_FORMAT_MM_DD_YY, Locale.US);
          }//if//
          else {
             startDate = DateUtilities.getDate(startDateString, WebConstants.DATE_FORMAT_YYYY_MM_DD, Locale.US);
          }//else//
          if(endDateString.indexOf("/") > -1) {
             endDate = DateUtilities.getDate(endDateString, WebConstants.DATE_FORMAT_MM_DD_YY, Locale.US);
          }//if//
          else {
             endDate = DateUtilities.getDate(endDateString, WebConstants.DATE_FORMAT_YYYY_MM_DD, Locale.US);
          }//else//
          searchDate = DateUtilities.getDate(nextLine[DATE], WebConstants.DATE_FORMAT_MM_DD_YY, Locale.US);
          if(searchDate.compareTo(startDate) >= 0 && searchDate.compareTo(endDate) <= 0) {
              dateSearch = true;
          }//if//
          else {
              dateSearch = false;
          }//else///
       }//if//
       return dateSearch;
    }//isDateSearchValid//
       
    /**
     * 
     * 
     * 
     * @param map
     * @param nextLine
     * @return 
     */
    private boolean isAccountSearchValid(Map<String,String>map, String[] nextLine) {
           
        String accountNbrSearch = null;
        boolean accountSearch = true;
           
        if(map.containsKey(ACCOUNT_SEARCH_P)) {
            accountNbrSearch = (String)map.get(TRAN_ACCOUNT_P);
            if(nextLine[ACCOUNT].equals(accountNbrSearch))
                accountSearch = true;
            else
                accountSearch = false;
        } 
        return accountSearch;   
    }//isAccountSearchValid//
       
    /**
     * 
     * 
     * 
     * @param map
     * @param nextLine
     * @return 
     */
    private boolean isNoteSearchValid(Map<String,String>map, String[] nextLine) {
          
        String noteSearchString = null;
        boolean noteSearch = true;
           
        if(map.containsKey(NOTE_SEARCH_P)) {
            if(map.containsKey(TRAN_SEARCH_P)) {
                if(map.get(TRAN_SEARCH_P).equals(TRAN_SEARCH_EQUAL)) {
                   noteSearchString = (String)map.get(TRAN_NOTE_P).toUpperCase();
                   if(nextLine[NOTE].toUpperCase().equals(noteSearchString))
                      noteSearch = true;
                   else
                      noteSearch = false;
                }//if//
                if(map.get(TRAN_SEARCH_P).equals(TRAN_SEARCH_LIKE)) {
                   noteSearchString = (String)map.get(TRAN_NOTE_S).toUpperCase();
                   if(nextLine[NOTE].toUpperCase().indexOf(noteSearchString) > -1)
                      noteSearch = true;
                   else
                      noteSearch = false;
                }//if//
            }//if//
        }//if//
        return noteSearch;   
    }//isNoteSearchValid//
    
    /**
     * Load the result set as an array list for 
     * display by the display tag library.
     * 
     * @param request
     * @param response
     * @param resultList
     * @return 
     * 
     */
    private void loadTransactionLineList(HttpServletRequest request,
                                         HttpServletResponse response,
                                         List<String[]>resultList) {
        
       String date    = null;
       String time    = null;
       String amount  = null;
       String account = null;
       String note    = null;
       TransactionLineList transactionLineList = new TransactionLineList();
       for(int i = 0; i < resultList.size(); i++) {
           StringBuilder sb = new StringBuilder();
           sb.append("Transaction Line List " + (i + 1) + ". = ");
           for(int j=0; j < resultList.get(i).length; j++) {
               date    = resultList.get(i)[DATE];
               time    = resultList.get(i)[TIME];
               amount  = resultList.get(i)[AMOUNT];
               account = resultList.get(i)[ACCOUNT];
               note    = resultList.get(i)[NOTE];
               sb.append(resultList.get(i)[j] + " ");
           }
           LOGGER.log(Level.FINE,sb.toString());
           sb.append("Transaction Line List " + (i + 1) +". = ");
           transactionLineList.add(date, time, amount, account, note);
       }
       //request.setAttribute(RESULT_SET,transactionLineList);
       request.getSession().setAttribute(RESULT_SET, transactionLineList);
    }//loadTransactionLineLise//
          

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        LOGGER.log(Level.INFO,"Beginning doGet Log Level is set to " + LOGGER.getLevel().toString());
        
        HttpSession session = request.getSession();
        
        session.removeAttribute(RESULT_SET);
        session.removeAttribute(MESSAGE);
        
        Map<String,String> map = getParameters(request,response);
        if(request.getAttribute(MESSAGE_P) == null) {
            request.setAttribute(MESSAGE_P, "");
        }
        
       //forward(request,response,"/WEB-INF/webpages/DailyTransactionsSearchResults.jsp");
       CSVReader reader2 = new CSVReader(new FileReader(WebConstants.getFilePath()));
       List<String[]>myList = reader2.readAll();
       
       //load drop down box//       
       loadNoteDropDownList(request,response,myList);
       //load drop down box//       
       loadAccountDropDownList(request,response,myList);
              
 //      request.setAttribute(RESULT_SET,transactionLineList);
       //forward(request,response,"/WEB-INF/webpages/DailyTransactionsSearchResults.jsp");
       forward(request,response,SEARCH_PAGE_WEB_INF);
       request.setAttribute(MESSAGE, "");
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException  {
        
        LOGGER.log(Level.INFO,"Beginning doPost Log Level is set to " + LOGGER.getLevel().toString());
        
        HttpSession session = request.getSession();
        
        session.removeAttribute(RESULT_SET);
        session.removeAttribute(MESSAGE);
        session.removeAttribute(TRAN_ITEMS_PAGE);
        
        Map<String,String> parameterMap = getParameters(request,response);
        
        String message = null;
        List<String[]> resultList = null;
        if(parameterMap.containsKey(SUBMIT_P)) {
            message = validateCheckBoxes(request,response,parameterMap);
            if(message != null) {
                LOGGER.log(Level.INFO, "Validate Check Boxes not filled in Begin foward back to sender. " + message);
                doGet(request,response);
                LOGGER.log(Level.INFO, "Validate Check Boxes not filled in End foward back to sender. " + message);
                return;
            }
            if(parameterMap.containsKey(DATE_SEARCH_P)) {
                message = validateStartEndDate(parameterMap);
                if(message != null) {
                    request.setAttribute(MESSAGE_P, message);
                    LOGGER.log(Level.INFO, "Start End Dates invalid or not filled in Begin foward back to sender. " + message);
                    doGet(request,response);
                    LOGGER.log(Level.INFO, "Start End Dates invalid or not filled in End foward back to sender. " + message);                    
                    return;
                }//if//
            }//if//
            resultList = filterResultSet(parameterMap);
            
            loadTransactionLineList(request,response,resultList);
            //forward(request,response,"/jsp/DailyTransactionsSearchResults.jsp");
            
            buildSearchParametersMessage(request,response,parameterMap);
            
            if(parameterMap.containsKey(TRAN_ITEMS_PAGE_P)) {
               session.setAttribute(TRAN_ITEMS_PAGE, parameterMap.get(TRAN_ITEMS_PAGE_P));
            }
            else {
               session.setAttribute(TRAN_ITEMS_PAGE,"0");
            }
            LOGGER.log(Level.INFO, "Fowarding to Results JSP page " + RESULTS_PAGE_WEB_INF);
            forward(request,response,RESULTS_PAGE_WEB_INF);
            LOGGER.log(Level.INFO, "Return from Fowarding to Results JSP page " + RESULTS_PAGE_WEB_INF);
            
            //processRequest(request, response, "DailyTransactionSearchServlet ", resultList);
        }//if//
        if(parameterMap.containsKey(DEBUG_P))
            debugRequestParameters(request, response);
        if(parameterMap.containsKey(DEBUG_ERROR_P))
            throw new ServletException("Test of Error Page - DailyTransactionSearchServlet");
    }//doPost//

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
