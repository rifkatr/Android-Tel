package com.example.rifka.contacts;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.mikepenz.iconics.context.IconicsLayoutInflater2;

/**
 * Created by Rifka on 12/02/2018.
 */

public class TampilData extends AppCompatActivity {

    protected Cursor cursor;
    DBHelper dbHelper;
    TextView tname, tphone, temail,tgroup, taddress, tevent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LayoutInflaterCompat.setFactory2(getLayoutInflater(), new IconicsLayoutInflater2(getDelegate()));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tampil_data);

        dbHelper = new DBHelper(this);

        tname = (TextView) findViewById(R.id.tvName);
        tphone = (TextView) findViewById(R.id.tvPhone);
        temail = (TextView) findViewById(R.id.tvEmail);
        tgroup = (TextView) findViewById(R.id.tvGroup);
        taddress = (TextView) findViewById(R.id.tvAddress);
        tevent = (TextView) findViewById(R.id.tvEvent);

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM contact WHERE id_contact = '" +
                getIntent().getStringExtra("id") + "'",null);
        cursor.moveToFirst();
        if (cursor.getCount()>0)
        {
            cursor.moveToPosition(0);
            tname.setText(cursor.getString(1).toString());
            tphone.setText(cursor.getString(2).toString());
            temail.setText(cursor.getString(3).toString());
            tgroup.setText(cursor.getString(6).toString());
            tevent.setText(cursor.getString(5).toString());
            taddress.setText(cursor.getString(4).toString());
        }
        else{
            Log.d("Error", getIntent().getStringExtra("id"));
        }
    }
}