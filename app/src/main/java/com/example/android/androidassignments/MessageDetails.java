package com.example.android.androidassignments;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MessageDetails extends AppCompatActivity {
    public Bundle newBundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_details);

        FragmentManager fragmentManager =  getSupportFragmentManager();
        MessageFragment msgFragment = new MessageFragment();
         newBundle = getIntent().getExtras().getBundle("newBundle");
        TextView msgTxt = findViewById(R.id.msgHere);
        //msgTxt.setText(newBundle.getString(ChatDatabaseHelper.KEY_MESSAGE));

        //String test = newBundle.getString(ChatDatabaseHelper.KEY_MESSAGE);
        FragmentTransaction fragmentTrans = getSupportFragmentManager().beginTransaction();
        msgFragment.setArguments(newBundle);
        fragmentTrans.add(R.id.frame1, msgFragment).addToBackStack(null);
        fragmentTrans.replace(R.id.frame1, msgFragment);
        fragmentTrans.commit();

    }
    public void submit(){
        setResult(ChatWindow.VALID_CODE);
        finish();
    }


}