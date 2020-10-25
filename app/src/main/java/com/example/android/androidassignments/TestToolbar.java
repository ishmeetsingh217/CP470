package com.example.android.androidassignments;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.renderscript.ScriptGroup;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.view.inputmethod.InputMethodManager;

public class TestToolbar extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_toolbar);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Ishmeet", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

            }
        });


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.pickcolour);
// Add the buttons
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
                finish();
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });
// Create the AlertDialog
        AlertDialog dialog = builder.create();
        dialog.show();

    }

    public boolean onCreateOptionsMenu(Menu m){
        getMenuInflater().inflate(R.menu.toolbar_menu,m);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem mi){
        switch(mi.getItemId()){
            case R.id.action_one:
                Log.d("Toolbar","Option 1 selected");
                Snackbar.make(this.findViewById(R.id.action_one),"You Selected item 1",Snackbar.LENGTH_LONG).show();


                break;
            case R.id.action_two:
                LayoutInflater minflater = LayoutInflater.from(this);
                final View mgetDialogView = minflater.inflate(R.layout.moveactivity, null);
                final AlertDialog mmDialog = new AlertDialog.Builder(this).create();
                mmDialog.setView(mgetDialogView);
                mgetDialogView.findViewById(R.id.moveNO).setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        Intent moveIntent = new Intent(TestToolbar.this, MainActivity.class);
                        startActivity(moveIntent);
                    }
                });
                mgetDialogView.findViewById(R.id.moveOK).setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    finish();
                }
            });
                mmDialog.show();
                break;
            case R.id.action_three:
                LayoutInflater inflater = LayoutInflater.from(this);
                final View getDialogView = inflater.inflate(R.layout.dialog_signin, null);
                final AlertDialog mDialog = new AlertDialog.Builder(this).create();
                mDialog.setView(getDialogView);
                getDialogView.findViewById(R.id.dialogOK).setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v){

                        final EditText mEdit = getDialogView.findViewById(R.id.editText);
                        Snackbar.make(findViewById(R.id.action_three),mEdit.getText().toString(), Snackbar.LENGTH_LONG).show();
                    }
                });
                mDialog.show();
//                final AlertDialog.Builder builder = new AlertDialog.Builder(this);
//                LayoutInflater inflater = LayoutInflater.from(this);
//                final EditText input = findViewById(R.id.editText);
//                builder.setView(inflater.inflate(R.layout.dialog_signin,null))
//                        .setPositiveButton(R.string.editMsg, new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//
//                            }
//                        });
                //builder.create();
                break;
            case R.id.aboutMenu:
                Toast.makeText(this, "Version 1.0, by Ishmeet", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
        return true;
    }
}