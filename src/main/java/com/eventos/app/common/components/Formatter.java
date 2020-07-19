package com.eventos.app.common.components;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Formatter {
    public static Boolean StrToBool(String status){
        switch (status){
            case "true": return true;
            case "false": return false;
            default: return null;
        }
    }

    public static Date StrToFormat(String date, SimpleDateFormat formatter){
        try
        {
            return formatter.parse(date);
        }
        catch(Exception exception)
        {
            return null;
        }
    }

    public static Date StrToTimestamp(String date){
        try
        {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date);
        }
        catch(Exception exception)
        {
            return null;
        }
    }

    public static Date StrToDate(String date){
        try
        {
            return new SimpleDateFormat("yyyy-MM-dd").parse(date);
        }
        catch(Exception exception)
        {
            return null;
        }
    }
}
