/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webapps.transactionline;

/**
 *
 * @author reid
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.webapps.constants.WebConstants;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 *
 * @author reid
 */
public class TransactionLine {
    
    private Date tranDate;
    private double tranAmount;
    private String tranAccount;
    private String tranNote;

    public TransactionLine(Date tranDate, String tranAmount, 
                           String tranAccount, String tranNote) {
    
        this.tranDate = tranDate;
        this.tranAmount = Double.parseDouble(tranAmount);
        this.tranAccount = tranAccount;
        this.tranNote = tranNote;
    }

    public String writeTransactionLine(boolean writeFile) throws IOException {
        
        StringBuilder sb = new StringBuilder();
        Locale loc = Locale.US;
//      SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy hh:mma", loc);
        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yy hh:mma", loc);
        String dtString = df.format(tranDate);
        
        String tranDate = dtString.substring(0,dtString.indexOf(" "));
        String tranTime = dtString.substring(dtString.indexOf(" ")+1).toLowerCase();
        
        String[] nextLine = { tranDate, tranTime, String.format("%1.2f", (double)this.tranAmount), this.tranAccount, this.tranNote };
        
        char delimiterChar = '\054';
        char quoteChar = '\000';
        String lineEnd = "\r\n";
        
        if(writeFile) {
            CSVWriter writer = new CSVWriter(new FileWriter(WebConstants.getFilePath(), true),delimiterChar,quoteChar,lineEnd);
            writer.writeNext(nextLine);
            writer.flush();
            writer.close();
        }//if//
        for(int i=0; i < nextLine.length; i++) {
            sb.append(nextLine[i]);
            if(i+1 < nextLine.length)
               sb.append(",");
        }//for//
        return sb.toString();
    }
    
    public Date getTranDate() {
        return tranDate;
    }

    public void setTranDate(Date tranDate) {
        this.tranDate = tranDate;
    }
    

    public double getTranAmount() {
        return tranAmount;
    }

    public void setTranAmount(Float tranAmount) {
        this.tranAmount = tranAmount;
    }
 
    public String getTranAccount() {
        return tranAccount;
    }

    public void setTranAccount(String tranAccount) {
        this.tranAccount = tranAccount;
    }

    public String getTranNote() {
        return tranNote;
    }

    public void setTranNote(String tranNote) {
        this.tranNote = tranNote;
    }    
    
    public static void main(String[] args) {
        WebConstants.setFilePath("/home/reid/Documents/Banking/DailyTransactions.txt");
        TransactionLine tl = new TransactionLine(new Date(), "12.34", "0876", "tran note");
        try {
           tl.writeTransactionLine(true);
        }
        catch (Exception exc) {
            exc.printStackTrace();
        }
    }
}//TransactionLine//

