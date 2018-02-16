package com.example.rifka.contacts;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.Calendar;
import java.util.HashMap;

import com.mikepenz.iconics.context.IconicsLayoutInflater2;

import static com.example.rifka.contacts.MainActivity.ma;

public class TambahData extends AppCompatActivity implements View.OnClickListener{

    protected Cursor cursor;
    DBHelper dbHelper;

    LinearLayout cancel, save, calendar;
    EditText eevent, ename, ephone, eemail, egroup, eaddress;
    private int mYear, mMonth, mDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LayoutInflaterCompat.setFactory2(getLayoutInflater(), new IconicsLayoutInflater2(getDelegate()));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_data);

        dbHelper = new DBHelper(this);

        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        cancel = (LinearLayout)findViewById(R.id.btnCancel);
        save = (LinearLayout)findViewById(R.id.btnSave);
        calendar = (LinearLayout)findViewById(R.id.Event);

        eevent = (EditText) findViewById(R.id.edEvent);
        ename = (EditText) findViewById(R.id.edName);
        ephone = (EditText) findViewById(R.id.edPhone);
        eemail = (EditText) findViewById(R.id.edEmail);
        egroup = (EditText) findViewById(R.id.edGroup);
        eaddress = (EditText) findViewById(R.id.edAddress);

        cancel.setOnClickListener(this);
        calendar.setOnClickListener(this);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                db.execSQL("insert into contact(name, phone, email, address, event, name_group) values('" +
                        ename.getText().toString() + "','" +
                        ephone.getText().toString() + "','" +
                        eemail.getText().toString() + "','" +
                        eaddress.getText().toString() + "','" +
                        eevent.getText().toString() + "','" +
                        egroup.getText().toString() + "')");

                Toast.makeText(getApplicationContext(), "Berhasil", Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }

    public void Calendar_Event(View v) {
        //Launch Date Picker Dialog
        DatePickerDialog dpd = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                //Tampilkan selected date in pada EditText
                eevent.setText(dayOfMonth+"-"+(monthOfYear+1)+"-"+year);
            }
        }, mYear, mMonth, mDay);
        dpd.show();
    }

//    public void bSave (View v) {
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//        db.execSQL("insert into contact(name, phone, email, address, event, name_group) values('" +
//                ename.getText().toString() + "','" +
//                ephone.getText().toString() + "','" +
//                eemail.getText().toString() + "','" +
//                eaddress.getText().toString() + "','" +
//                eevent.getText().toString() + "','" +
//                egroup.getText().toString() + "')");
//
//        Toast.makeText(getApplicationContext(), "Berhasil", Toast.LENGTH_LONG).show();
//        MainActivity.ma.RefreshList();
//        finish();
//    }

    @Override
    public void onClick(View view) {
        Intent i;
        switch (view.getId()) {
            case R.id.btnCancel :
                i = new Intent(TambahData.this, MainActivity.class);
                Toast.makeText(this, "Back", Toast.LENGTH_LONG).show();
                startActivity(i);
                finish();
                break;
            default:
                break;
        }
    }
}