package com.example.user.smartfoody.Weather;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Helper {
    public static String stream = null;


    public Helper(){}

    public String getHTTPdata(String urlstring)
    {
        try{
            URL url = new URL(urlstring);
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            if (httpURLConnection.getResponseCode() == 200)
            {
                BufferedReader reader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null)
                {
                    sb.append(line);
                    stream = sb.toString();
                    httpURLConnection.disconnect();
                }
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stream;
    }
}
