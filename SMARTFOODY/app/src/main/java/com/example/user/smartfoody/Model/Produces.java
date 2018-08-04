package com.example.user.smartfoody.Model;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ImageView;

import java.io.Serializable;

/**
 * Created by User on 1/1/2018.
 */

public class Produces {
    private String Id;
    private String Image;
    private String Name;
    private String Price;
    private String New;
    private String Sale;

    public Produces(){}

    public Produces(String image,String name, String price, String id, String spnew, String sale)
    {
        this.Name = name;
        this.Price = price;
        this.Image = image;
        this.Id = id;
        this.New = spnew;
        this.Sale = sale;
    }

    public String getImage() {
        return Image;
    }

    public String getName() {
        return Name;
    }

    public String getPrice() {
        return Price;
    }

    public String getId() {
        return Id;
    }

    public String getNew() {
        return New;
    }

    public void setNew(String aNew) {
        New = aNew;
    }

    public String getSale() {
        return Sale;
    }

    public void setSale(String sale) {
        Sale = sale;
    }
}
