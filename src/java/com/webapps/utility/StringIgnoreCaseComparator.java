/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webapps.utility;

import java.util.Comparator;

/**
 *
 * @author reid
 */
public class StringIgnoreCaseComparator implements Comparator<String> {

    @Override
    public int compare(String s1, String s2) {
        String s1Lower = s1.toLowerCase();
        String s2Lower = s2.toLowerCase();
        return s1Lower.compareTo(s2Lower);
    }//compare//
}//StringIgnoreCaseComparator//
