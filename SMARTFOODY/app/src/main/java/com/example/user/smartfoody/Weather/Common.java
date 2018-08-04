package com.example.user.smartfoody.Weather;

import android.support.annotation.NonNull;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Common {

    public static String API_KEY= "2c9dfa6a5fce428c2bb65705b067206a";
    public static String API_LINK = "http://api.openweathermap.org/data/2.5/weather";


    @NonNull
    public static String apiRequest(String lat, String lon)
    {
        StringBuilder sb = new StringBuilder(API_LINK);
        sb.append(String.format("?lat=%s&lon=%s&APPID=%s&units=metrics", lat, lon, API_KEY));
        return sb.toString();
    }


    public static String formatTime(double time)
    {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        Date date = new Date();
        date.setTime((long)time * 1000);
        return dateFormat.format(date);
    }

    public static String getImage(String icon)
    {
        return String.format("http://openweathermap.org/img/w/%s.png", icon);
    }

    public static String getDateNow()
    {
        DateFormat dateFormat = new SimpleDateFormat("dd MM yyyy HH:mm ");
        Date date = new Date();
        return dateFormat.format(date);
    }
}
