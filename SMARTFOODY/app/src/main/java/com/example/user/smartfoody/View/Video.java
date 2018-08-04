package com.example.user.smartfoody.View;

/**
 * Created by User on 12/11/2017.
 */

public class Video {

    private String Title;
    private String Thumnail;
    private String Videoid;


    public Video(String title, String thumnail, String videoid) {
        Title = title;
        Thumnail = thumnail;
        Videoid = videoid;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getThumnail() {
        return Thumnail;
    }

    public void setThumnail(String thumnail) {
        Thumnail = thumnail;
    }

    public String getVideoid() {
        return Videoid;
    }

    public void setVideoid(String videoid) {
        Videoid = videoid;
    }
}
