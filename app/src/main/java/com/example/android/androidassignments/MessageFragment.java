package com.example.android.androidassignments;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class MessageFragment extends Fragment {

    private static final String ACTIVITY_NAME = "MessageFragment";

    private boolean mVisible;
    public Button delBtn;
    public Bundle mBundle;
    TextView txtMsg;
    public TextView txtId ;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message, container,false);
        txtMsg = view.findViewById(R.id.msgHere);
        txtId = view.findViewById(R.id.msgID);
        //mBundle =
        delBtn = view.findViewById(R.id.deleteMsg);
        txtMsg.setText("Message = " + getArguments().getString(ChatDatabaseHelper.KEY_MESSAGE));
        txtId.setText("ID = " + getArguments().getLong(ChatDatabaseHelper.KEY_ID));
        delBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChatWindow chat = new ChatWindow();
                chat.deleteItem(Math.toIntExact(getArguments().getLong(ChatDatabaseHelper.KEY_ID)));
                Log.i(ACTIVITY_NAME, "Deelete clicked");
                Intent mIntent = new Intent();
                mIntent.putExtra("deleteThis", txtId.getText());
                getActivity().setResult(Activity.RESULT_OK, mIntent);
                getActivity().getSupportFragmentManager().beginTransaction().remove(MessageFragment.this).commit();
                getActivity().finish();
            }
        });
        return view;
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Intent mIntent;
        mVisible = true;
        //deleteBtn = view.findViewById(R.id.deleteMsg);
        mBundle = getArguments().getBundle(ChatWindow.BUNDLE_NAME);
        //Log.i(ACTIVITY_NAME, "Message: " + mBundle.getString(ChatDatabaseHelper.KEY_MESSAGE));
        final MessageFragment messageFragment = new MessageFragment();
//        txtMsg.setText("Message = " + mBundle.getString(ChatDatabaseHelper.KEY_MESSAGE));
//        txtId.setText("ID = " + mBundle.getLong(ChatDatabaseHelper.KEY_ID));

//        delBtn.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view) {
//                Log.i(ACTIVITY_NAME, "Deelete clicked");
//                Intent mIntent = new Intent();
//                mIntent.putExtra("deleteThis", txtId.getText());
//                getActivity().setResult(Activity.RESULT_OK, mIntent);
//                getActivity().getSupportFragmentManager().beginTransaction().remove(messageFragment).commit();
//                getActivity().finish();
//            }
//        });



    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


}