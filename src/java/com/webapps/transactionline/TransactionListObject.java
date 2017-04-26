/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webapps.transactionline;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author reid
 */
public class TransactionListObject implements Serializable {
    
    private static Log log = LogFactory.getLog(TransactionListObject.class);
    private static Logger LOGGER = Logger.getLogger(TransactionListObject.class.getName());
    
    private Date date;
    private Date time;
    private BigDecimal amount;
    private String account;
    private String note;
    
    public TransactionListObject(Date date, Date time, BigDecimal amount, String account, String note) {
        
        super();
        this.date = date;
        this.time = time;
        this.amount = amount;
        this.account = account;
        this.note = note;
    }

    /**
     * @return the date
     */
    public Date getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * @return the time
     */
    public Date getTime() {
        return time;
    }

    /**
     * @param time the time to set
     */
    public void setTime(Date time) {
        this.time = time;
    }

    /**
     * @return the amount
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * @param amount the amount to set
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    /**
     * @return the account
     */
    public String getAccount() {
        return account;
    }

    /**
     * @param account the account to set
     */
    public void setAccount(String account) {
        this.account = account;
    }

    /**
     * @return the note
     */
    public String getNote() {
        return note;
    }

    /**
     * @param note the note to set
     */
    public void setNote(String note) {
        this.note = note;
    }
    
    public static void main(String[] args) {
        LOGGER.setLevel(Level.ALL);
        
        DateFormat df1 = new SimpleDateFormat("MM/dd/yy");
        DateFormat df2 = new SimpleDateFormat("hh:mm aa");
        Date dt = new Date();
        TransactionListObject tl = new TransactionListObject(dt,dt,new BigDecimal(15.75),"0876","Note");
        LOGGER.info("Date = " + df1.format(tl.getDate()));
        LOGGER.info("Time = " + df2.format(tl.getTime()));
        LOGGER.info("Amount = " + tl.getAmount().toPlainString());
        LOGGER.info("Account = " + tl.getAccount());
        LOGGER.info("Note = " + tl.getNote());
    }//TransactionLineObject//
}
