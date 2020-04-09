package com.example.notepad;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity {

    SQLiteDatabase db;
    TextView t1;
    ListView list;
    ArrayAdapter<String> arrayAdapter;
    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final View v = findViewById(R.id.i1);
        final View v2 = v.findViewById(R.id.i2);
        final View v3 = v2.findViewById(R.id.fragment);


        list = v3.findViewById(R.id.list);
        final ArrayList<String> arrayList = new ArrayList<>();

        Log.d("Kuch chal bhi raha hai", "onCreate: Moshi");


        db =this.openOrCreateDatabase("notepad",MODE_PRIVATE,null);
        db.execSQL("CREATE TABLE IF NOT EXISTS notepad (body varchar)");
        Cursor c = db.rawQuery("SELECT * FROM notepad",null);
        int bodyindex = c.getColumnIndex("body");
        try{
            Log.d("YOSuccess", "onCreate: Moshi");
            c.moveToFirst();
            if(c==null)
            {
                t1.setText("Wow this note is freaking Empty!");
            }
            while (c!=null)
            {
                t1.append("\n "+c.getString(bodyindex));
                c.moveToNext();
            }
        }
        catch (Exception e){
            Log.d("YO", "onCreate: Moshi");
        }


        notes(arrayList,db);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newNote();
            }
        });


        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                final int itemToRemove = position;
                new AlertDialog.Builder(MainActivity.this)
                        .setIcon(android.R.drawable.alert_dark_frame)
                        .setTitle("Delete Note")
                        .setMessage("Do you want to delete this note?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
//                                notes.remove(itemToRemove);
//                                arrayAdapter.notifyDataSetChanged();
//                                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.homework.notepad", Context.MODE_PRIVATE);
//                                HashSet<String> set = new HashSet<String>(MainActivity.notes);
//                                sharedPreferences.edit().putStringSet("notes", set).apply();
                            }
                        }).setNegativeButton("No", null).show();
                return true;
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration) || super.onSupportNavigateUp();
    }

    void newNote()
    {
        Intent intent = new Intent(this,newNote.class);
        startActivity(intent);
    }

    void notes(ArrayList<String> arrayList, SQLiteDatabase db)
    {

        Cursor c = db.rawQuery("SELECT * FROM notepad",null);
        int bodyindex = c.getColumnIndex("body");
        try{
            c.moveToFirst();
            while ((!c.isAfterLast()))
            {
                Log.d("Yes","running");
                arrayList.add(c.getString(bodyindex));
                c.moveToNext();
            }
            c.close();

            Log.d("Toto","Ye chal rha hai");

            arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, arrayList);
            list.setAdapter(arrayAdapter);
        }
        catch (Exception e){Log.d("YOError",e.getMessage());}
    }

}
