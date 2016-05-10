/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dacar.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 *
 * @author jonwetherbee
 */
public class DacarUtils {

  private static final Map<String, String> addressMap = new HashMap();

  public static Date formatDate(String inputDate) throws ParseException {
    SimpleDateFormat input = new SimpleDateFormat("M-d-yy H:mm z", Locale.US);
    SimpleDateFormat output = new SimpleDateFormat("EEE MMM dd hh:mm a z yyyy", Locale.US);
    Date date = input.parse(inputDate);
    String text = output.format(date);
    System.out.println(text);

    return date;
  }
}
