package com.nasersalameh.imposterphenomenoninterventionapp.helpers;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateConverter {

    private static DateFormat dateFormat;

    public DateConverter(){
        dateFormat = new SimpleDateFormat("dd/M/yyyy");
    }

    public static Long getUnixTimeFromData(String Date){
        try {
            java.util.Date date = dateFormat.parse(Date);
            long unixTime = (long) date.getTime()/1000;
            return unixTime;
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        return 0l;
    }

    public static String getDateFromUnixTime(Long unix){
       return new java.util.Date((long)unix*1000).toString();
    }

}
