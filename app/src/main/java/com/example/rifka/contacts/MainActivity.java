package com.example.rifka.contacts;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.mikepenz.iconics.context.IconicsLayoutInflater2;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity{

    String[] daftar;
    String[] contact_id;
    String[] resId;
    ListView listview;
    protected Cursor cursor;
    DBHelper dbcenter;
    public static MainActivity ma;
    EditText edSearch;
    Intent i;
    ArrayAdapter<String> adapter;
    private FloatingActionButton tambah_contact;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LayoutInflaterCompat.setFactory2(getLayoutInflater(), new IconicsLayoutInflater2(getDelegate()));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listview = (ListView) findViewById(R.id.list_contact);
        edSearch = (EditText) findViewById(R.id.edSearch);
        tambah_contact = (FloatingActionButton) findViewById(R.id.tambahdata);

        ma = this;
        dbcenter = new DBHelper(this);
        RefreshList();
        longitem_click();

        //function searching
        edSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                MainActivity.this.adapter.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        //floating action button
        tambah_contact.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                i = new Intent(MainActivity.this, TambahData.class);
                startActivity(i);
            }
        });
    }

    public void RefreshList() {
        db = dbcenter.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM contact ORDER BY name ASC", null);
        daftar = new String[cursor.getCount()];
        contact_id = new String[cursor.getCount()];
        resId = contact_id;
        cursor.moveToFirst();
        for (int cc = 0; cc < cursor.getCount(); cc++) {
            cursor.moveToPosition(cc);
            daftar[cc]= cursor.getString(1).toString();
            contact_id[cc] = cursor.getString(0).toString();
            Log.d("id_contact", "ini id : " + Arrays.toString(daftar));
            Log.d("id_contact", "ini id : " + Arrays.toString(contact_id));
        }

        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, daftar);
        listview.setAdapter(adapter);
        listview.setSelected(true);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView arg0, View arg1, int arg2, long arg3) {
                String id_contact = resId[arg2];
                i = new Intent(MainActivity.this, TampilData.class);
                i.putExtra("id",id_contact);
                startActivity(i);
            }
        });
        ((ArrayAdapter) listview.getAdapter()).notifyDataSetInvalidated();
    }

    public void longitem_click() {
        db = dbcenter.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM contact ORDER BY name ASC", null);
        daftar = new String[cursor.getCount()];
        contact_id = new String[cursor.getCount()];
        cursor.moveToFirst();
        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView arg0, View arg1, int arg2, long arg3) {
                final String selection = daftar[arg2];
                final String id_contact = resId[arg2];
                final CharSequence[] dialogitem = {"Edit","Delete"};
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Action");
                builder.setItems(dialogitem, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        switch (item) {
                            case 0 :
                                i = new Intent(getApplicationContext(), EditData.class);
                                i.putExtra("id",id_contact);
                                startActivity(i);
                                break;
                            case 1 :
                                SQLiteDatabase db = dbcenter.getWritableDatabase();
                                db.execSQL("delete from contact where id_contact ="+ id_contact +"");
                                RefreshList();
                                break;
                        }
                    }
                });
                builder.create().show();
                return true;
            }
        });
        ((ArrayAdapter) listview.getAdapter()).notifyDataSetInvalidated();
    }
}
