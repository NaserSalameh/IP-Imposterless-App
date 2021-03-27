package com.nasersalameh.imposterphenomenoninterventionapp.helpers;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateConverter {

    private static DateFormat dateFormat = new SimpleDateFormat("dd/M/yyyy");;

    public static Long getUnixTimeFromData(String Date){
        try {
            Date date = dateFormat.parse(Date);
            long unixTime = (long) date.getTime()/1000;
            return unixTime;
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        return 0l;
    }

    public static String getDateFromUnixTime(Long unix) {
        Date date = new Date((long) unix * 1000);
        return dateFormat.format(date);
    }

    public static String getDateAndTimeFromUnixTime(Long unix){
        DateFormat dateFormat = new SimpleDateFormat("dd/M/yyyy HH:mm:ss");;
        Date date = new Date((long)unix*1000);
        return dateFormat.format(date);
    }

}
