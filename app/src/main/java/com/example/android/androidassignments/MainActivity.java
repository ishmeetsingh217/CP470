package com.example.android.androidassignments;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.androidassignments.R;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private Button button;
    protected static final String ACTIVITY_NAME = "MainActivity";
    private Button chatButton;
    private Button toolBarBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(ACTIVITY_NAME,"In onCreate()");
        loadlocale();
        setContentView(R.layout.activity_main);
        chatButton = findViewById(R.id.chatBtn);
        toolBarBtn = findViewById(R.id.testToolbarbtn);
        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent listItemsActivity = new Intent(MainActivity.this, ListItemsActivity.class);
                startActivityForResult(listItemsActivity, 10);
            }

        });
        toolBarBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent toolBarActivity = new Intent(MainActivity.this, TestToolbar.class);
                startActivityForResult(toolBarActivity,5);
            }
        });

        chatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(MainActivity.ACTIVITY_NAME,"User clicked Start Chat");
                Intent chatWindowActivity = new Intent(MainActivity.this, ChatWindow.class);
                MainActivity.this.startActivity(chatWindowActivity);
            }

        });


    }

    @Override
    public void onResume(){
        super.onResume();
        Log.i(ACTIVITY_NAME,"In onResume()");

    }

    @Override
    public void onStart(){
        super.onStart();
        Log.i(ACTIVITY_NAME,"In onStart()");

    }

    @Override
    public void onPause(){
        super.onPause();
        Log.i(ACTIVITY_NAME,"In onPause()");

    }

    @Override
    public void onStop(){
        super.onStop();
        Log.i(ACTIVITY_NAME,"In onStop()");

    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.i(ACTIVITY_NAME,"In onDestroy()");

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 10)
        {
            Log.i(ACTIVITY_NAME, "Returned to MainActivity.onActivityResult");
            if(resultCode == Activity.RESULT_OK)
            {
                String messagePassed = data.getStringExtra("Response");
                Toast.makeText(MainActivity.this, "ListItemsActivity passed: " + messagePassed, Toast.LENGTH_SHORT).show();
            }
        }
    }



    public void loadlocale(){
        SharedPreferences prefs = getSharedPreferences("Settings", Activity.MODE_PRIVATE);
        String language = prefs.getString("My_Lang", "");
    }


}