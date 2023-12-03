package com.example.happyapp.localDatabase;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String dbName = "happyApp";
    public static final int version = 1;
    ////////////////////////////////////
    private SQLiteDatabase myDb;
    /////////////////////////////////////
    public static String db_Create_Table1 = "" +
            "CREATE TABLE SHOPPINGLIST(" +
            "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
            "TITLE TEXT NOT NULL," +
            "TOTALPRICE DOUBLE)";

    public static String db_Create_Table2 = "" +
            "CREATE TABLE SHOPPINGLISTITEM(" +
            "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
            "NAME TEXT NOT NULL," +
            "QUANTITY INTEGER NOT NULL," +
            "PRICE DOUBLE NOT NULL," +
            "SHOPPINGLISTID INTEGER NOT NULL," +
            "FOREIGN KEY(SHOPPINGLISTID) REFERENCES SHOPPINGLIST (ID)" +
            "ON DELETE CASCADE ON UPDATE CASCADE)";

    public DatabaseHelper(@Nullable Context context) {
        super(context, dbName, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(db_Create_Table1);
        sqLiteDatabase.execSQL(db_Create_Table2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        if (i1 > i) {
            sqLiteDatabase.execSQL("DROP TABLE SHOPPINGLIST");
            sqLiteDatabase.execSQL("DROP TABLE SHOPPINGLISTITEM");
            sqLiteDatabase.execSQL(db_Create_Table1);
            sqLiteDatabase.execSQL(db_Create_Table2);
        }
    }

    public void insertShoppingList(ShoppingList shoppingList){
        String q = "INSERT INTO SHOPPINGLIST(TITLE, TOTALPRICE) VALUES(?, ?)";
        this.getWritableDatabase().execSQL(q,
                new Object[]
                        {
                                shoppingList.getTitle(),
                                shoppingList.getTotalPrice()
                        }
        );

    }
    public List<ShoppingList> select() {
        List<ShoppingList> list = new ArrayList<>();
        String selectQ = "SELECT * FROM SHOPPINGLIST";
        Cursor c = this.getWritableDatabase().rawQuery(selectQ, null);
        while (c.moveToNext()) {
            @SuppressLint("Range") ShoppingList shoppingList = new ShoppingList(
                    c.getString(c.getColumnIndex("ID")),
                    c.getString(c.getColumnIndex("TITLE")),//приема индекса на текущата колона
                    c.getDouble(c.getColumnIndex("TOTALPRICE"))
            );
            list.add(shoppingList);
        }
        c.close();
        return list;
    }

    public void updateShoppingList(int id, double totalPrice) {
        String updateQ = "UPDATE SHOPPINGLIST SET TOTALPRICE = ? WHERE ID=?";
        this.getWritableDatabase().execSQL(updateQ,
                new Object[]
                        {
                                totalPrice,
                                id
                        }
        );
    }


    @SuppressLint("Range")
    public List<ShoppingList> selectByTitle(String title) {
        List<ShoppingList> list = new ArrayList<>();
        String selectQ = "SELECT * FROM SHOPPINGLIST WHERE TITLE = ?";
        Cursor c = this.getReadableDatabase().rawQuery(selectQ, new String[]{title});

        while (c.moveToNext()) {
            @SuppressLint("Range") ShoppingList shoppingList = new ShoppingList(
                    c.getString(c.getColumnIndex("ID")),
                    c.getString(c.getColumnIndex("TITLE")),//приема индекса на текущата колона
                    c.getDouble(c.getColumnIndex("TOTALPRICE"))
            );
            list.add(shoppingList);
        }
        c.close();
        return list;
    }

    public void insertShoppingListItem(ShoppingListItem shoppingListitem){
        String q = "INSERT INTO SHOPPINGLISTITEM(NAME, QUANTITY, PRICE, SHOPPINGLISTID) VALUES(?, ?, ?, ?)";
        this.getWritableDatabase().execSQL(q,
                new Object[]
                        {
                                shoppingListitem.getName(),
                                shoppingListitem.getQuantity(),
                                shoppingListitem.getPrice(),
                                shoppingListitem.getShoppingListId()
                        }
        );
    }
    public List<ShoppingListItem> selectShoppingListItemsByShoppingListId(String id) {
        List<ShoppingListItem> list = new ArrayList<>();
        String selectQ = "SELECT * FROM SHOPPINGLISTITEM WHERE SHOPPINGLISTID=?";
        Cursor c = this.getWritableDatabase().rawQuery(selectQ, new String[]{
                id
        });
        while (c.moveToNext()) {
            @SuppressLint("Range") ShoppingListItem shoppingListItem = new ShoppingListItem(
                    c.getInt(c.getColumnIndex("ID")),
                    c.getString(c.getColumnIndex("NAME")),//приема индекса на текущата колона
                    c.getInt(c.getColumnIndex("QUANTITY")),
                    c.getDouble(c.getColumnIndex("PRICE")),
                    c.getInt(c.getColumnIndex("SHOPPINGLISTID"))
            );
            list.add(shoppingListItem);
        }
        c.close();
        return list;
    }

    public void deleteShoppingList(int id) {
        String deleteQ = "DELETE FROM SHOPPINGLIST WHERE ID=?";
        this.getWritableDatabase().execSQL(deleteQ,
                new Object[]
                        {
                                id
                        }
        );
    }
}
