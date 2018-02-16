package com.example.rifka.contacts;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.mikepenz.iconics.context.IconicsLayoutInflater2;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    String[] daftar;
    ListView listview;
    Menu menu;
    protected Cursor cursor;
    DBHelper dbcenter;
    public static MainActivity ma;
    TextView tambah_kontak;
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LayoutInflaterCompat.setFactory2(getLayoutInflater(), new IconicsLayoutInflater2(getDelegate()));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tambah_kontak = (TextView)findViewById(R.id.btn_tambah_kontak);
        listview = (ListView) findViewById(R.id.list_contact);

        tambah_kontak.setOnClickListener(this);

        ma = this;
        dbcenter = new DBHelper(this);
        RefreshList();
    }

    public void RefreshList() {
        SQLiteDatabase db = dbcenter.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM contact", null);
        daftar = new String[cursor.getCount()];
        cursor.moveToFirst();
        for (int cc = 0; cc < cursor.getCount(); cc++) {
            cursor.moveToPosition(cc);
            daftar[cc]= cursor.getString(1).toString();
        }

        listview.setAdapter(new ArrayAdapter(this, android.R.layout.simple_list_item_1, daftar));
        listview.setSelected(true);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView arg0, View arg1, int arg2, long arg3) {
                final String selection = daftar[arg2];
                i = new Intent(MainActivity.this, TampilData.class);
                i.putExtra("name",selection);
                startActivity(i);
            }
        });

        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView arg0, View arg1, int arg2, long arg3) {
                final String selection = daftar[arg2];
                final CharSequence[] dialogitem = {"Edit","Delete"};
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Action");
                builder.setItems(dialogitem, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        switch (item) {
                            case 0 :
                                i = new Intent(getApplicationContext(), EditData.class);
                                i.putExtra("name", selection);
                                startActivity(i);
                                break;
                            case 1 :
                                SQLiteDatabase db = dbcenter.getWritableDatabase();
                                db.execSQL("delete from contact where name ='"+selection+"'");
                                RefreshList();
                                break;
                        }
                    }
                });
                builder.create().show();

                return false;
            }
        });
        ((ArrayAdapter) listview.getAdapter()).notifyDataSetInvalidated();
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btn_tambah_kontak :
                i = new Intent(MainActivity.this, TambahData.class);
                startActivity(i);
                break;
            default:
                break;
        }
    }
}
