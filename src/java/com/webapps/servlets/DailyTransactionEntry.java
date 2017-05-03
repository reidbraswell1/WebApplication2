/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webapps.servlets;

import com.opencsv.CSVReader;
import com.utilities.date_utilities.DateUtilities;
//import com.utilities.constants.WebConstants;
import com.webapps.constants.WebConstants;
import com.webapps.transactionline.TransactionLine;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author reid
 */
@WebServlet(name = "DailyTransactionEntry", urlPatterns = {"/DailyTransactionEntry"})
public class DailyTransactionEntry extends AbstractServlet {
    
    private static boolean isJSTL;
    
    private static Log log = LogFactory.getLog(DailyTransactionEntry.class);
    private static final Logger LOGGER = Logger.getLogger(DailyTransactionEntry.class.getName());
    
    private static final String CLASS_NAME        = DailyTransactionEntry.class.getName();
    private static final String TRAN_DATE         = CLASS_NAME.concat(".tran_date");
    private static final String TRAN_DATE_P       = "tran_date";
    private static final String TRAN_TIME         = CLASS_NAME.concat(".tran_time");
    private static final String TRAN_TIME_P       = "tran_time";
    private static final String TRAN_AMOUNT       = CLASS_NAME.concat(".tran_amount");
    private static final String TRAN_AMOUNT_P     = "tran_amount";
    private static final String TRAN_ACCOUNT      = CLASS_NAME.concat(".tran_account");
    private static final String TRAN_ACCOUNT_P    = "tran_account";
    private static final String TRAN_NEW_ACCOUNT_P= "tran_new_account";
    private static final String TRAN_NOTE         = CLASS_NAME.concat(".tran_note");
    private static final String TRAN_NOTE_P       = "tran_note";
    private static final String TRAN_OTHER_V      = "Other";
    private static final String TRAN_DATE_TIME_FORMAT = CLASS_NAME.concat(".tran_date_time_format");
    private static final String DEBUG_JSP         = "debugJSP";
    private static final String DEBUG_PARAM       = CLASS_NAME.concat(".debug_param");
    private static final String DEBUG_PARAMETERS  = CLASS_NAME.concat(".debugParameters");
    private static final String DEBUG_REQUEST     = CLASS_NAME.concat(".debugRequest");
    private static final String DEBUG_SESSION     = CLASS_NAME.concat(".debugSession");
    private static final String DROP_DOWN_LIST    = CLASS_NAME.concat(".dropDownList");
    private static final String SESSION           = CLASS_NAME.concat(".session");
    private static final String TRANSACTION_LINE  = CLASS_NAME.concat(".transaction_line");
    private static final String MESSAGE           = CLASS_NAME.concat(".message");
    
    private static final String CONFIRM           = "Confirm";
    private static final String CANCEL            = "Cancel";
    private static final String DEBUG             = "Debug";
    private static final String DEBUG_ERROR       = "DebugError";
    private static final String SUBMIT            = "Submit";
    
    private static final String DAILY_TRANSACTION_ENTRY_PAGE_JSTL = "/WEB-INF/webpages/DailyTransactionsJSTL.jsp";
    private static final String DAILY_TRANSACTION_ENTRY_PAGE      = "/WEB-INF/webpages/DailyTransactions.jsp";
    private static final String CONFIRM_PAGE_JSTL = "/WEB-INF/webpages/DailyTransactionsConfirmJSTL.jsp";
    private static final String CONFIRM_PAGE      = "/WEB-INF/webpages/DailyTransactionsConfirm.jsp";
    
    private static final String JSTL              = "JSTL";

    /**
     * 
     */
    public DailyTransactionEntry() {
        super();
        Level level = WebConstants.getLogLevel();
        LOGGER.setLevel(level);
    }//constructor//S

    /**
     * 
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException 
     */
    private void debugRequestParameters(HttpServletRequest request, HttpServletResponse response)
                                 throws ServletException, IOException
    {
        
        Map<String, String[]> map = request.getParameterMap();
        
        for (Map.Entry<String, String[]> entry : map.entrySet()) {
             LOGGER.log(Level.FINE, "Request Parameters = " + entry.getKey() + "=" + entry.getValue()[0]);
        }//for

        Enumeration<String> e1 = request.getAttributeNames();
        
        while(e1.hasMoreElements()) {
            LOGGER.log(Level.FINE, "Request Attribute Names = " + e1.nextElement().toString());
        }
        
        Enumeration<String> e2 = request.getServletContext().getAttributeNames();
        
        while(e2.hasMoreElements()) {
            LOGGER.log(Level.FINE, "Servlet Context Attribute Names = " + e2.nextElement().toString());
        }
        
        forward(request,response,"/WEB-INF/webpages/DailyTransactionsDebug.jsp");
    }//debugRequestParmeters
    
    /**
     * 
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException 
     */
    private void loadDropDownBox(HttpServletRequest request, HttpServletResponse response)
                          throws ServletException, IOException
    {
        if(WebConstants.getAcctNbr() == null || WebConstants.getAcctNbr().equals("")) {
            LOGGER.log(Level.SEVERE,"Missing Account Numbers For Drop Down List in properties file");
            throw new ServletException("Missing Account Numbers For Drop Down List in properties file");
        }
        if(WebConstants.getAcctNbrDesc() == null || WebConstants.getAcctNbrDesc().equals("")) {
            LOGGER.log(Level.SEVERE,"Missing Account Number Description For Drop Down List in properties file");
            throw new ServletException("Missing Account Number Description For Drop Down List in properties file");
        }
            
        String[] acctNbr = WebConstants.getAcctNbr().split(",");
        String[] acctNbrDesc = WebConstants.getAcctNbrDesc().split(",");
        
        SortedMap<String,String> sortedMap = new TreeMap();
 
        StringBuilder sb = new StringBuilder();
        sb.append("Acct Nbrs = {");
        for(int i=0; i < acctNbr.length; i++) {
            sb.append(acctNbr[i] + " ");
            for(int k=0; k < acctNbrDesc.length; k++)
            {
                if(acctNbrDesc[k].indexOf(acctNbr[i]) > -1) {
                    sortedMap.put(acctNbr[i],acctNbrDesc[k]);
                    break;
                }//if//
            }//for//
        }//for//
        sb.append("}");
        LOGGER.log(Level.FINE,sb.toString());
        sb.replace(0,sb.length(),"");
        sb.append("Acct Nbrs Desc = {");
        for(int i=0; i < acctNbrDesc.length; i++) {
            sb.append(acctNbrDesc[i] + " ");
        }//for//
        LOGGER.log(Level.FINE,sb.toString());
        
        for(String k:sortedMap.keySet())
           LOGGER.log(Level.FINE,"Account Number Description Key Values: K = " + k + " V = " + sortedMap.get(k));
           
        for(Map.Entry<String,String> entry: sortedMap.entrySet())
           LOGGER.log(Level.FINE,"Account Number Description Key Values: K = " + entry.getKey() + ": V = " + entry.getValue());
        
        if(sortedMap.isEmpty())
           LOGGER.log(Level.INFO,"Account Number Map is Empty!");
        request.setAttribute(DROP_DOWN_LIST, sortedMap);
    }//loadDropDownBox//
    
    /**
     * 
     * @param map
     * @return 
     */
    private String buildInvalidDateTimeMessage(Map<String,String>map) {

        StringBuilder sb  = new StringBuilder();
        sb.append("Invalid Date Time Combination ");
        sb.append(map.get(TRAN_DATE_P));
        sb.append(" ");
        sb.append(map.get(TRAN_TIME_P));
        String message = sb.toString();

        return message;
    }//buildInvalidDateTimeMessage//
    
    /**
     * Return true or false to indicate that the form  date
     * and time are valid. The form date can be entered either 
     * as YYYY-MM-DD and time as HH:mm (24 Hour) or
     * MM/DD/YYYY and time as hh:mm am or pm.
     * 
     * @param request
     * @param response
     * @return 
     */
    private boolean isFormDateTimeValid(HttpServletRequest request,
                                        HttpServletResponse response) {
        
        String tranDate = request.getParameter(TRAN_DATE_P);
        String tranTime = request.getParameter(TRAN_TIME_P);
                
        Locale locale = Locale.US;
        if(tranDate.indexOf("/") > -1) {
           if(DateUtilities.isDateValid(tranDate + " " + tranTime,
                                        WebConstants.DATE_FORMAT_MM_DD_YYYY_HH_MM_AM_PM,
                                        locale)) {
               return true;
           }//if//
           else {
               return false;
           }//else//
        }//if//
        if(tranDate.indexOf("-") > -1) {
           if(DateUtilities.isDateValid(tranDate + " " + tranTime,
                                        WebConstants.DATE_FORMAT_YYYY_MM_DD_HH_MM,
                                        locale)) {
               return true;
           }//if//
           else {
               return false;
           }//else//
        }//if//
        if(DateUtilities.isDateValid(tranDate + " " + tranTime,
                                     WebConstants.DATE_FORMAT_MM_DD_YYYY_HH_MM_AM_PM,
                                     locale)) {
            return true;
        }//if//
        else {
            return false;
        }//else//
    }//isFormDateTimeValid//
    
    /**
     * Gets form parameters and put them in a hash map.
     * Map is returned.
     * 
     * @param request
     * @param response
     * @return map
     */
    private Map getFormParameters(HttpServletRequest request, HttpServletResponse response) {
        
        Map <String, String>map = new HashMap();
        
        map.put(CONFIRM,request.getParameter(CONFIRM));
        map.put(DEBUG,request.getParameter(DEBUG));
        map.put(DEBUG_ERROR,request.getParameter(DEBUG_ERROR));
        map.put(SUBMIT,request.getParameter(SUBMIT));
        map.put(CANCEL,request.getParameter(CANCEL));

        map.put(TRAN_DATE_P,request.getParameter(TRAN_DATE_P));
        map.put("tran_date_time_format",request.getParameter("tran_date_time_format"));
        map.put(TRAN_TIME_P,request.getParameter(TRAN_TIME_P));
        map.put(TRAN_AMOUNT_P,request.getParameter(TRAN_AMOUNT_P));
        map.put(TRAN_ACCOUNT_P,request.getParameter(TRAN_ACCOUNT_P));
        map.put(TRAN_NOTE_P,request.getParameter(TRAN_NOTE_P));
        
        map.put(JSTL, request.getParameter(JSTL));
        map.put(DEBUG_JSP, request.getParameter(DEBUG_JSP));
       
        for(Map.Entry<String,String> entry : map.entrySet()) {
            if(entry.getValue() == null || entry.getValue().equals("null")) {
                LOGGER.log(Level.FINE,"getFormParameters - Setting entry " + entry.getKey() + " to empty string");
                entry.setValue("");
            }//if//
        }//for//
        map.forEach((k,v)-> 
                LOGGER.log(Level.FINE,"getFormParameters - Key : " + k + " Value : " + v));
        return map;
    }//getFormParameters//
    

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
            throws ServletException, IOException {
        
        LOGGER.log(Level.INFO,"Beginning doPost Log Level is set to " + LOGGER.getLevel().toString());
        
        HttpSession session = request.getSession();
        Map<String, String> mapParameters = getParameters(request,response);

        isJSTL = false;
        if(mapParameters.containsKey(JSTL) && mapParameters.get(JSTL).equalsIgnoreCase("True")) {
            isJSTL = true;
        }//if//
        LOGGER.log(Level.INFO,"Setting JSTL to " + mapParameters.get(JSTL) + " isJSTL = " + isJSTL);        

     // Map<String,String>map = getParameters(request,response);
        
        if(mapParameters.containsKey(DEBUG))
        {
            LOGGER.log(Level.INFO,"doPost debug submission");
            request.setAttribute(DEBUG_PARAM, "debugit");
            request.getServletContext().setAttribute(DEBUG_PARAM, "debugit");
            debugRequestParameters(request, response);
        }
        if(mapParameters.containsKey(DEBUG_ERROR))
        {
            LOGGER.log(Level.INFO,"DebugError Submission Detected");
            throw new ServletException("Test of Error Page - DailyTransactionEntry");
        }

        if(mapParameters.containsKey(CANCEL)) {
            LOGGER.log(Level.INFO,"Cancel Submission Detected");
            session.setAttribute(SESSION, "complete");
            /*
            request.removeAttribute(TRAN_DATE);
            request.removeAttribute(TRAN_DATE_TIME_FORMAT);
            request.removeAttribute(TRAN_TIME);
            request.removeAttribute(TRAN_AMOUNT);
            request.removeAttribute(TRAN_ACCOUNT);
            request.removeAttribute(TRAN_NOTE);
            */
            doGet(request,response);
        }
        
        if(mapParameters.containsKey(CONFIRM)) {
            LOGGER.log(Level.INFO,"Confirm Submission Detected");
            
            Date dt = null;
            TransactionLine tl = null;
            String lineWritten = null;
            LOGGER.log(Level.INFO,"Session attribute confirm = " + session.getAttribute(SESSION));            
            if(session.getAttribute(SESSION) != null &&
               session.getAttribute(SESSION).equals("incomplete")) {
                /*
                   dt = DateUtilities.getDate(map.get(TRAN_DATE_P) + " " + map.get(TRAN_TIME_P),
                                              map.get("tran_date_time_format"), Locale.US);
                   tl = new TransactionLine(dt, map.get(TRAN_AMOUNT_P),
                                                map.get(TRAN_ACCOUNT_P), 
                                                map.get(TRAN_NOTE_P));
                */
                   tl = (TransactionLine)request.getServletContext().getAttribute(TRANSACTION_LINE);
                   if(tl != null) {
                      lineWritten = tl.writeTransactionLine(true);
                   }
                   request.getServletContext().removeAttribute(TRANSACTION_LINE);
                   request.removeAttribute(TRAN_DATE);
                   request.removeAttribute(TRAN_DATE_TIME_FORMAT);
                   request.removeAttribute(TRAN_TIME);
                   request.removeAttribute(TRAN_AMOUNT);
                   request.removeAttribute(TRAN_ACCOUNT);
                   request.removeAttribute(TRAN_NOTE); 
            }
            else {
                request.setAttribute(MESSAGE,"Cannot re-post previously posted data.");
                if(isJSTL)
                    forward(request,response,CONFIRM_PAGE_JSTL);
                else
                    forward(request,response,CONFIRM_PAGE);
                return;
            }
            session.setAttribute(SESSION, "complete");

            /*
            loadDropDownBox(request, response);
            request.setAttribute(MESSAGE,"");
            if(isJSTL) {
               forward(request,response,DAILY_TRANSACTION_ENTRY_PAGE_JSTL);
            }//if//
            else {
               forward(request,response,DAILY_TRANSACTION_ENTRY_PAGE);
            }//else//
            */
            doGet(request,response);
            return;
            //doGet(request,response);
        }
        
        if(mapParameters.containsKey(SUBMIT)) {
            LOGGER.log(Level.INFO,"doPost submit submission detected");
            if(!isFormDateTimeValid(request,response)) {
               String message = buildInvalidDateTimeMessage(mapParameters);
               request.setAttribute(MESSAGE, message);
             //forward(request,response,"/DailyTransactionEntry");
             //response.sendRedirect(request.getHeader("Referer"));
               LOGGER.log(Level.INFO,"doPost JSTL = " + mapParameters.containsKey(JSTL)); 
               loadDropDownBox(request,response);
               request.removeAttribute(TRAN_DATE);
               request.removeAttribute(TRAN_DATE_TIME_FORMAT);
               request.removeAttribute(TRAN_TIME);
               request.removeAttribute(TRAN_AMOUNT);
               request.removeAttribute(TRAN_ACCOUNT);
               request.removeAttribute(TRAN_NOTE); 
               if(isJSTL)
                   forward(request,response,CONFIRM_PAGE_JSTL);
               else 
                   forward(request,response,CONFIRM_PAGE);
               
               request.setAttribute(MESSAGE, "");
               //doGet(request,response);
               return;
            }//if//
            else {
                Date dt = null;
                if(mapParameters.containsKey(TRAN_DATE_P) && mapParameters.get(TRAN_DATE_P).indexOf("/") > -1) {
                   dt=DateUtilities.getDate(mapParameters.get(TRAN_DATE_P) + " " + mapParameters.get(TRAN_TIME_P), 
                                            WebConstants.DATE_FORMAT_MM_DD_YYYY_HH_MM_AM_PM,
                                            Locale.US);
                //   request.setAttribute(TRAN_DATE_TIME_FORMAT,WebConstants.DATE_FORMAT_MM_DD_YYYY_HH_MM_AM_PM);
                }//if//
                if(mapParameters.containsKey(TRAN_DATE_P) && mapParameters.get(TRAN_DATE_P).indexOf("-") > -1) {
                   dt=DateUtilities.getDate(mapParameters.get(TRAN_DATE_P) + " " + mapParameters.get(TRAN_TIME_P), 
                                            WebConstants.DATE_FORMAT_YYYY_MM_DD_HH_MM,
                                            Locale.US);
                 //  request.setAttribute(TRAN_DATE_TIME_FORMAT,WebConstants.DATE_FORMAT_YYYY_MM_DD_HH_MM);
                }//if//
                TransactionLine tl = null;
                if(mapParameters.get(TRAN_ACCOUNT_P).equals(TRAN_OTHER_V)) {
                   tl = new TransactionLine(dt, mapParameters.get(TRAN_AMOUNT_P),
                                                                mapParameters.get(TRAN_NEW_ACCOUNT_P), 
                                                                mapParameters.get(TRAN_NOTE_P).trim());

                }//if//
                else {
                   tl = new TransactionLine(dt, mapParameters.get(TRAN_AMOUNT_P),
                                                                mapParameters.get(TRAN_ACCOUNT_P), 
                                                                mapParameters.get(TRAN_NOTE_P).trim());
                }//else//
                String lineWritten;
                lineWritten = tl.writeTransactionLine(false);
                request.setAttribute(MESSAGE, lineWritten);
                request.getServletContext().setAttribute(TRANSACTION_LINE, tl);
                //setFormParameters(request,response,map);
                /*
                request.setAttribute(CLASS_NAME.concat(TRAN_DATE,sortedMap.get(TRAN_DATE_P));
                request.setAttribute(CLASS_NAME.concat(".tran_time",sortedMap.get(TRAN_TIME_P));
                request.setAttribute(CLASS_NAME.concat(".tran_amount",sortedMap.get(TRAN_AMOUNT_P));
                request.setAttribute(CLASS_NAME.concat(".tran_account",sortedMap.get(TRAN_ACCOUNT_P));
                request.setAttribute(CLASS_NAME.concat(".tran_note",sortedMap.get(TRAN_NOTE_P));
                */
                session.setAttribute(SESSION, "incomplete");
                if(isJSTL)
                    forward(request,response,CONFIRM_PAGE_JSTL);
                else
                    forward(request,response,CONFIRM_PAGE);
            }
        }//if//
    }//doPost//
    
    /**
   // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
   */
    
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

     // Map<String,String>map = getFormParameters(request,response);
        Map<String, String> mapParameters = getParameters(request,response);
        
        HttpSession session = request.getSession();
        //session.setAttribute(CLASS_NAME.concat(".session", "incomplete");
        isJSTL = false;
        if(mapParameters.containsKey(JSTL) && mapParameters.get(JSTL).equalsIgnoreCase("True")) {
            isJSTL = true;
        }//if//
        LOGGER.log(Level.INFO,"doGet Setting JSTL to " + mapParameters.get(JSTL) + " isJSTL = " + isJSTL);
        if(!mapParameters.containsKey(MESSAGE))
            request.setAttribute(MESSAGE, "");
        
        // Load Account Drop Down Box
        loadDropDownBox(request,response);
        
        // Load Note Drop Down Box
        CSVReader reader2 = new CSVReader(new FileReader(WebConstants.getFilePath()));
        List<String[]>myList = reader2.readAll();        
        loadNoteDropDownList(request,response,myList);

        if(isJSTL) {
            forward(request,response,DAILY_TRANSACTION_ENTRY_PAGE_JSTL);
        }//if//
        else {
            forward(request,response,DAILY_TRANSACTION_ENTRY_PAGE);
        }//else//
        request.setAttribute(MESSAGE, "");
    }    

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
