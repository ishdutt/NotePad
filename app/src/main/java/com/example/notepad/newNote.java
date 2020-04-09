package com.example.notepad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class newNote extends AppCompatActivity {
    TextView t1;
    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);

        t1=(TextView)findViewById(R.id.textView3);
        Button b = (Button)findViewById(R.id.save);

        try{
            db = this.openOrCreateDatabase("notepad",MODE_PRIVATE,null);
        }
        catch (Exception e){
            Log.d("Negative","Ye nai Vhalega");
        }

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                taskdone();
            }
        });
    }

    void taskdone()
    {
        String body = t1.getText().toString();
        Log.d("Inside",body);
        try {
            db.execSQL("INSERT INTO notepad (body) VALUES('"+ body +"')");
            Toast.makeText(this,"Data successfully entered",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
        }
        catch (Exception ex){
            Log.d("YO",ex.getMessage());
        };

    }
}
