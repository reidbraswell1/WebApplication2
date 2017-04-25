/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webapps.transactionline;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import com.webapps.constants.WebConstants;
import com.utilities.date_utilities.DateUtilities;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author reid
 */
public class TransactionLineList extends ArrayList {
    
     private static Log log = LogFactory.getLog(TransactionLineList.class);
     private static Logger LOGGER = Logger.getLogger(TransactionLineList.class.getName());
     
    /**
     * Constructor
     * 
     */
    public TransactionLineList() {
        super();
        Level level = WebConstants.getLogLevel();
        LOGGER.setLevel(level);
    }
        
    /**
     * Convert incoming string variables to the correct type and 
     * add an array list object.
     * 
     * @param date String date mm/dd/yy format.
     * @param time Dtring time hh:mmam or hh:mmpm or HH:mm (24 Hour time).
     * @param amount String dollar amount.
     * @param account String account.
     * @param note  String note.
     */
    public void add(String date, String time, String amount, String account, String note) {
    
        
        Date dt = null;
        LOGGER.log(Level.FINE,"Parameters = " + date + " " + time + " " + amount + " " + account + " " + note);
        dt = DateUtilities.getDate(date + " " + time, WebConstants.DATE_FORMAT_MM_DD_YY_HH_MM_AA, Locale.US);
        if(dt == null) {
            LOGGER.log(Level.FINE,"Date is null " + date + " " + time);
            dt = DateUtilities.getDate(date + " " + time, WebConstants.DATE_FORMAT_MM_DD_YY_HH_MM, Locale.US);
        }//if//
        BigDecimal bd = new BigDecimal(amount);
        super.add(new TransactionListObject(dt,dt,bd,account,note));
    }//add//
}//TransactionLineList//