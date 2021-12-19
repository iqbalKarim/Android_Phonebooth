package com.iqbal.karim.iqbalkarim_hw2;

import android.content.ContentValues;
import android.database.Cursor;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class PhonesTable {
    public static final String TABLE_NAME="phones";
    public static final String FIELD_ID="id";
    public static final String FIELD_NAME="name";
    public static final String FIELD_PRICE="price";
    public static final String FIELD_MEMORY="memory";
    public static final String FIELD_STORAGE="storage";
    public static final String FIELD_IMAGE="image";
    public static final String FIELD_DESC="description";

    public static final String CREATE_TABLE = "CREATE TABLE  " + TABLE_NAME  +" (" + FIELD_ID  +" INTEGER," + FIELD_NAME  +" TEXT NOT NULL," + FIELD_MEMORY  +" INTEGER NOT NULL," + FIELD_STORAGE + " INTEGER NOT NULL," + FIELD_DESC  + " TEXT NOT NULL," + FIELD_PRICE + " REAL NOT NULL," + FIELD_IMAGE + " BLOB," + " PRIMARY KEY( " + FIELD_ID  +" AUTOINCREMENT)" + ");";
    public static final String DROP_TABLE = "DROP TABLE if exists " + TABLE_NAME;

    public static List<Phone> getAllPhones(DatabaseHelper db){

        Cursor cursor = db.getAllRecords(TABLE_NAME, null);
        List<Phone> data=new ArrayList<Phone>();
        Phone pho = null;
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            int memory = cursor.getInt(2);
            int storage = cursor.getInt(3);
            String description = cursor.getString(4);
            double price = cursor.getDouble(5);
            byte[] image = cursor.getBlob(6);

            pho = new Phone(id, name, storage, memory, price, description, image);
            data.add(pho);
        }
        return data;
    }

    public static Phone findPhone(DatabaseHelper db, String key){

        String where = FIELD_NAME+" like '%"+key+"%'";
        Cursor cursor = db.getSomeRecords(TABLE_NAME,null, where);
        Phone data = new Phone();
        Phone pho = null;
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            int memory = cursor.getInt(2);
            int storage = cursor.getInt(3);
            String description = cursor.getString(4);
            double price = cursor.getDouble(5);
            byte[] image = cursor.getBlob(6);

            pho = new Phone(id, name, storage, memory, price, description, image);
            data = pho;
        }

        return data;
    }

    public static boolean insertPho(DatabaseHelper db, String name, int storage, int memory, double price, String description, byte[] img){
        ContentValues contentValues = new ContentValues( );

        contentValues.put(FIELD_NAME, name);
        contentValues.put(FIELD_PRICE, price);
        contentValues.put(FIELD_DESC, description);
        contentValues.put(FIELD_STORAGE, storage);
        contentValues.put(FIELD_MEMORY, memory);
        contentValues.put(FIELD_IMAGE, img);

        boolean res = db.insert(TABLE_NAME,contentValues);

        return res;
    }

    public static boolean deletePho(DatabaseHelper db, int id){
        String where = FIELD_ID +" = "+ id;
        boolean res = db.delete(TABLE_NAME,where);
        return res;
    }
}
