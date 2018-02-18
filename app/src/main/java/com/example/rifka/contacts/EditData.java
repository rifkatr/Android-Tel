package com.example.rifka.contacts;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.mikepenz.iconics.context.IconicsLayoutInflater2;

import static android.R.id.text1;

/**
 * Created by Rifka on 16/02/2018.
 */

public class EditData extends AppCompatActivity {

    protected Cursor cursor;
    DBHelper dbHelper;

    LinearLayout cancel, save, calendar;
    EditText eevent, ename, ephone, eemail, egroup, eaddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LayoutInflaterCompat.setFactory2(getLayoutInflater(), new IconicsLayoutInflater2(getDelegate()));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_data);

        dbHelper = new DBHelper(this);

        cancel = (LinearLayout)findViewById(R.id.btnCancel);
        save = (LinearLayout)findViewById(R.id.btnSave);
        calendar = (LinearLayout)findViewById(R.id.Event);

        eevent = (EditText) findViewById(R.id.edEvent);
        ename = (EditText) findViewById(R.id.edName);
        ephone = (EditText) findViewById(R.id.edPhone);
        eemail = (EditText) findViewById(R.id.edEmail);
        egroup = (EditText) findViewById(R.id.edGroup);
        eaddress = (EditText) findViewById(R.id.edAddress);

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM contact WHERE id_contact = '" +
                getIntent().getStringExtra("id") + "'",null);
        cursor.moveToFirst();


        if (cursor.getCount()>0)
        {
            cursor.moveToPosition(0);
            ename.setText(cursor.getString(1).toString());
            ephone.setText(cursor.getString(2).toString());
            eemail.setText(cursor.getString(3).toString());
            egroup.setText(cursor.getString(6).toString());
            eevent.setText(cursor.getString(5).toString());
            eaddress.setText(cursor.getString(4).toString());
        }

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                db.execSQL("update contact set name='"+
                        ename.getText().toString() +"', phone='" +
                        ephone.getText().toString() +"', email='" +
                        eemail.getText().toString()+"', address='"+
                        eaddress.getText().toString() +"', event='" +
                        eevent.getText().toString() +"', name_group='" +
                        egroup.getText().toString() + "' where id_contact=" +
                        cursor.getString(0)+"");

                Toast.makeText(getApplicationContext(), "Berhasil", Toast.LENGTH_LONG).show();
                MainActivity.ma.RefreshList();
                finish();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                finish();
            }
        });
    }
}
