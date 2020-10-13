package com.example.android.androidassignments;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import com.example.android.androidassignments.R;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.Toast;
import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;

public class ListItemsActivity extends AppCompatActivity {
    protected static final String ACTIVITY_NAME = "ListItemsActivity";
    private CheckBox checkBox01;
    //	private RadioButton radioButton01;
    private Switch switch01;
    private ImageButton imageButton01;

    private int REQUEST_CAMERA = 1;
    private String[] PERMISSIONS_CAMERA = {Manifest.permission.CAMERA};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_items);
        Log.i(ACTIVITY_NAME, "In onCreate()");
        checkBox01 = findViewById(R.id.checkbox01);
        checkBox01.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if(checkBox01.isChecked())
                {
                    new AlertDialog.Builder(ListItemsActivity.this)
                            .setTitle(R.string.cofirm_label).setMessage(R.string.confirmation_label)
                            .setNegativeButton(R.string.no_label, null)
                            .setPositiveButton(R.string.yes_label, new DialogInterface.OnClickListener()
                            {
                                public void onClick(DialogInterface a, int b)
                                {
                                    Intent resultIntent = new Intent();
                                    resultIntent.putExtra(getString(R.string.res_label), getString((R.string.final_response)));
                                    setResult(Activity.RESULT_OK, resultIntent);
                                    finish();
                                }
                            }).create().show();
                }
            }
        });

        switch01 = findViewById(R.id.switch01);
        switch01.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if(switch01.isChecked())
                {
                    Toast.makeText(ListItemsActivity.this, R.string.on_switch, Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(ListItemsActivity.this, R.string.off_switch, Toast.LENGTH_LONG).show();
                }
            }
        });

        imageButton01 = findViewById(R.id.image_button01);
        imageButton01.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                int permissionCamera = ActivityCompat.checkSelfPermission(ListItemsActivity.this, Manifest.permission.CAMERA);
                if(permissionCamera != PackageManager.PERMISSION_GRANTED)
                {
                    ActivityCompat.requestPermissions(ListItemsActivity.this, PERMISSIONS_CAMERA, REQUEST_CAMERA);
                }
                else
                {
                    startActivity(new Intent("android.media.action.IMAGE_CAPTURE"));
                }
            }
        });
    }

    @Override
    public void onResume()
    {
        Log.i(ACTIVITY_NAME, "In onResume()");
        super.onResume();
    }

    @Override
    public void onStart()
    {
        Log.i(ACTIVITY_NAME, "In onStart()");
        super.onStart();
    }

    @Override
    public void onPause()
    {
        Log.i(ACTIVITY_NAME, "In onPause()");
        super.onPause();
    }

    @Override
    public void onStop()
    {
        Log.i(ACTIVITY_NAME, "In onStop()");
        super.onStop();
    }

    @Override
    public void onDestroy()
    {
        Log.i(ACTIVITY_NAME, "In onDestroy()");
        super.onDestroy();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
    {
        if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
        {
            startActivity(new Intent("android.media.action.IMAGE_CAPTURE"));
        }
        else
        {
            Toast.makeText(ListItemsActivity.this, R.string.permission, Toast.LENGTH_SHORT).show();
        }
    }
}