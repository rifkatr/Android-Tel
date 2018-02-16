package com.example.rifka.contacts
        ;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

public class DBHelper extends SQLiteOpenHelper {
    private static final String LOGCAT = null;

    static final String TABLE_CONTACT = "contact";
    static final String TABLE_GROUP = "group";

    static final String C_ID = "id_contact";
    static final String C_NAME = "name";
    static final String C_PHONE = "phone";
    static final String C_EMAIL = "email";
    static final String C_ADDRESS = "address";
    static final String C_EVENT = "event";
    static final String C_NAME_GROUP = "name_group";

    static final String G_ID = "_id_group";
    static final String G_NAME = "name";

    public DBHelper(Context aplicationcontext) {
        super(aplicationcontext, "contact_telp.db", null, 1);
        Log.d(LOGCAT, "Created");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query_contact, query_group;
        query_contact = "CREATE TABLE contact( id_contact INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, phone TEXT, email TEXT, address TEXT, event TEXT, name_group TEXT)";
        query_group = "CREATE TABLE group(id_group INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT)";
        db.execSQL(query_contact);
        db.execSQL(query_group);
        Log.d(LOGCAT, "Table Created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int version_old, int current_version) {
        String query_contact, query_group;
        query_contact = "DROP TABLE contact";
        query_group = "DROP TABLE group";
        db.execSQL(query_contact);
        db.execSQL(query_group);
        onCreate(db);
    }

//    public void insertAnimals(HashMap<String, String> queryValues) {
//        SQLiteDatabase database = this.getReadableDatabase();
//        ContentValues values = new ContentValues();
//        values.put("animalName", queryValues.get("animalName"));
//        database.insert("animals", null, values);
//        database.close();
//    }
//
//    public int updateAnimal(HashMap<String, String> queryValues) {
//        SQLiteDatabase database = this.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put("animalName", queryValues.get("animalName"));
//        return database.update("animals", values, "animalId" + "=?", new String[] {queryValues.get("animalId")});
//
//    }

    public void insertContact(HashMap<String, String> queryValues) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", queryValues.get("name"));
        values.put("phone", queryValues.get("phone"));
        values.put("email", queryValues.get("email"));
        values.put("address", queryValues.get("address"));
        values.put("event", queryValues.get("event"));
        values.put("name_group", queryValues.get("name_group"));

        db.insert("contact", null, values);
        db.close();
    }

    public void deleteContact(String id) {
        Log.d(LOGCAT,"delete");
        SQLiteDatabase database = this.getWritableDatabase();
        String deleteQuery = "DELETE FROM contact where id_contact='"+id+"'";
        Log.d("query",deleteQuery);
        database.execSQL(deleteQuery);
    }

    public ArrayList<HashMap<String, String>> getAllContact() {
        ArrayList<HashMap<String, String>> wordList;
        wordList = new ArrayList<HashMap<String, String>>();
        String selectQuery = "SELECT c.name, c.phone, c.email, g.name, c.address, c.event from contact c inner join group g on c.id_group = g.id_group";
        SQLiteDatabase database  = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String>map = new HashMap<String, String>();
                map.put("c.name", cursor.getString(0));
                map.put("c.phone", cursor.getString(1));
                map.put("c.email", cursor.getString(2));
                map.put("g.name", cursor.getString(3));
                map.put("c.address", cursor.getString(4));
                map.put("c.event", cursor.getString(5));
                wordList.add(map);
            } while (cursor.moveToNext());
        }
        return wordList;
    }

    public HashMap<String, String> getContactInfo(String id) {
        HashMap<String, String> wordList = new HashMap<String, String>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM contact where id_contact ='"+id+"'";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                wordList.put("name", cursor.getString(1));
            } while (cursor.moveToNext());
        }
        return wordList;
    }

    public ArrayList<HashMap<String, String>> getAllGroup() {
        ArrayList<HashMap<String, String>> wordList;
        wordList = new ArrayList<HashMap<String, String>>();
        String selectQuery = "SELECT * FROM group";
        SQLiteDatabase database  = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String>map = new HashMap<String, String>();
                map.put("id_group", cursor.getString(0));
                map.put("name", cursor.getString(1));
                wordList.add(map);
            } while (cursor.moveToNext());
        }
        return wordList;
    }

    public HashMap<String, String> getGroupInfo(String id) {
        HashMap<String, String> wordList = new HashMap<String, String>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM group where id_contact ='"+id+"'";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                wordList.put("name", cursor.getString(1));
            } while (cursor.moveToNext());
        }
        return wordList;
    }
}