package com.example.user.smartfoody.CartDB;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;

import com.example.user.smartfoody.Model.Oder;
import com.example.user.smartfoody.Model.User;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteAssetHelper {

    private static final String DATABASE_NAME = "CartOderDatabase.db";
    private static final int DATABASE_VERSION = 1;

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }




    public List<Oder> getCart()
    {
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] sqlselect = {"ID","ProduceID", "ProduceImage", "ProduceName", "ProducePrice", "ProduceQuantity"};
        String sqltable = "CartProduce";

        qb.setTables(sqltable);

        Cursor c = qb.query(db, sqlselect, null,null,null, null, null);
        final  List<Oder> result = new ArrayList<>();

        if (c.moveToFirst())
        {
            do {
                result.add(new Oder(
                        c.getInt(c.getColumnIndex("ID")),
                        c.getString(c.getColumnIndex("ProduceID")),
                        c.getString(c.getColumnIndex("ProduceImage")),
                        c.getString(c.getColumnIndex("ProduceName")),
                        c.getString(c.getColumnIndex("ProducePrice")),
                        c.getString(c.getColumnIndex("ProduceQuantity"))
                        ));
            }while (c.moveToNext());
        }
        return  result;
    }

    // create function add cart

    public void AddToCart(Oder oder)
    {
        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("INSERT INTO CartProduce(ProduceID, ProduceImage,ProduceName,ProducePrice,ProduceQuantity) VALUES('%s','%s','%s','%s','%s');",
                oder.getProduceId(), oder.getProduceImage(),oder.getProduceName(),oder.getProducePrice(),oder.getProduceQuantity());
        db.execSQL(query);
    }

    //clean cart
    public  void CleanCart()
    {
        SQLiteDatabase db = getReadableDatabase();
        String clean = String.format("DELETE FROM CartProduce");
        db.execSQL(clean);
    }

    // update cart
    public void UpdateCart(Oder oder)
    {
        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("UPDATE CartProduce SET ProduceQuantity = %s WHERE ID = %d", oder.getProduceQuantity(), oder.getID());
        db.execSQL(query);
    }

    // delete item cart
    public void DeleteItem(int id)
    {
        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("DELETE FROM CartProduce WHERE ID = %s", id);
        db.execSQL(query);
    }

    //
    public void SaveUserPhone(String phonenumber)
    {
        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("INSERT INTO UserInfo(UserPhone) VALUES('%s');", phonenumber );
        db.execSQL(query);
    }

    //
    public List<User> getUserPhone()
    {
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] sqlselect2 = {"UserPhone"};
        String sqltable2 = "UserInfo";
        qb.setTables(sqltable2);

        Cursor c = qb.query(db, sqlselect2, null,null,null, null, null);
        final  List<User> result = new ArrayList<>();

        if (c.moveToFirst())
        {
            do {
                result.add(new User(
                        c.getString(c.getColumnIndex("UserPhone"))
                ));
            }while (c.moveToNext());
        }
        return  result;
    }

    //
    public void CleanUserPhone()
    {
        SQLiteDatabase db = getReadableDatabase();
        String clean = String.format("DELETE FROM UserInfo");
        db.execSQL(clean);
    }

}
