package com.example.android.androidassignments;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ChatWindow extends AppCompatActivity {

    private ListView mListView;
    EditText mEditText;
    private Button mButton;
    private ArrayList<String> mArrayList = new ArrayList<String>();
    public static ChatDatabaseHelper dbHelper;
    public static SQLiteDatabase db;
    public static String temp = "";
    public static String ACTIVITY_NAME = "ChatWindow";
    public static final String BUNDLE_NAME = "newBundle";
    public String addMessage = "";
    public boolean isFrame = false;
    public Cursor mCursor;
    public static final int VALID_CODE = 15;
    public ChatAdapter messageAdapter;
    public FragmentTransaction fragmentTrans;
    public MessageFragment msgFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);
        mListView = findViewById(R.id.mListView);
        mEditText = findViewById(R.id.mEditText);
        mButton = findViewById(R.id.sendButton);
        if(findViewById(R.id.frame) != null) {
            isFrame = true;
            Log.e(ACTIVITY_NAME,"Frame Loaded");
        }else{
            Log.e(ACTIVITY_NAME,"Frame not Loaded");
        }
        messageAdapter = new ChatAdapter(this);
        mListView.setAdapter(messageAdapter);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(ChatWindow.this, mEditText.getText().toString(), Toast.LENGTH_SHORT).show();
                mArrayList.add(mEditText.getText().toString());
                messageAdapter.notifyDataSetChanged();
                db.execSQL("INSERT INTO " + ChatDatabaseHelper.TABLE_NAME + " (" + ChatDatabaseHelper.KEY_MESSAGE+") VALUES ('" + mEditText.getText()+"')");
                mEditText.setText("");

            }
        });

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                String message = messageAdapter.getItem(position);
                Bundle newBundle = new Bundle();

                newBundle.putString(ChatDatabaseHelper.KEY_MESSAGE, message);
                newBundle.putLong(ChatDatabaseHelper.KEY_ID, messageAdapter.getItemid(position));
                Log.i(ACTIVITY_NAME, "Message " + message + "  id " + messageAdapter.getItemid(position) );
                if(findViewById(R.id.frame) != null){
                    Log.i(ACTIVITY_NAME, "Currently on tablet");
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    msgFragment = new MessageFragment();
                    fragmentTrans = fragmentManager.beginTransaction();
                    msgFragment.setArguments(newBundle);
                    fragmentTrans.replace(R.id.frame, msgFragment).addToBackStack(null).commit();
                }else{
                    Log.i(ACTIVITY_NAME, "Currently not on tablet");
                    Intent chatWindow = new Intent(ChatWindow.this, MessageDetails.class);
                    chatWindow.putExtra("newBundle", newBundle);
                    startActivityForResult(chatWindow, Activity.RESULT_OK);
                }
            }
        });


        dbHelper = new ChatDatabaseHelper(this);
        db = dbHelper.getWritableDatabase();
        temp = "SELECT * FROM messages";
        mCursor = db.rawQuery(temp, null);
        //Toast.makeText(this, "Count: " + mCursor.getColumnCount(), Toast.LENGTH_SHORT).show();
        mCursor.moveToFirst();
        while(!mCursor.isAfterLast()){
            mArrayList.add(mCursor.getString(mCursor.getColumnIndex(ChatDatabaseHelper.KEY_MESSAGE)));
            Log.i(ACTIVITY_NAME,"SQL MESSAGE: " + mCursor.getString(mCursor.getColumnIndex(ChatDatabaseHelper.KEY_MESSAGE)));
            Log.i(ACTIVITY_NAME, "Cursor's column count = " + mCursor.getColumnCount());
            mCursor.moveToNext();
        }
        for(int i = 0; i < mCursor.getColumnCount(); i++){
            Log.i(ACTIVITY_NAME, "Column's name: " + mCursor.getColumnName(i));
        }
        //mCursor.close();
    }


    @Override
    public void onDestroy(){
        super.onDestroy();
        db.close();
    }

    public void deleteItem(int pos){
        FragmentManager manager = getSupportFragmentManager();
        db.delete(ChatDatabaseHelper.TABLE_NAME, ChatDatabaseHelper.KEY_ID + " = " + pos, null);
        Log.i(ACTIVITY_NAME, "pos = " + pos);
        mArrayList.remove(pos);
        fragmentTrans = manager.beginTransaction();
        fragmentTrans.remove(msgFragment).commit();
        messageAdapter.notifyDataSetChanged();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i(ACTIVITY_NAME, "We came back");
        if (resultCode == Activity.RESULT_OK){
            String row = data.getStringExtra("deleteThis");
            Log.i("delete row: ", ""+row);
            db.delete(ChatDatabaseHelper.TABLE_NAME,ChatDatabaseHelper.KEY_ID+ "="+row,null);
            deleteItem(Integer.valueOf(row));
            mArrayList.remove(Integer.parseInt(row)-1);
            messageAdapter.notifyDataSetChanged();
        }

    }

    public void testList(){
        Toast.makeText(ChatWindow.this, "Its here", Toast.LENGTH_SHORT).show();
    }
    private class ChatAdapter extends ArrayAdapter<String> {
        public ChatAdapter(Context ctx) {
            super(ctx, 0);
        }


         public int getCount() {
            return mArrayList.size();
        }
        public long getItemid(int position){
            mCursor.moveToPosition(position);

            return mCursor.getLong(mCursor.getColumnIndex(ChatDatabaseHelper.KEY_ID));
        }
        public String getItem(int position) {
            Log.e("ISHMEET", mArrayList.get(position));
            return mArrayList.get(position);
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = ChatWindow.this.getLayoutInflater();
            View result = null;
            if (position % 2 == 0) {
                result = inflater.inflate(R.layout.chat_row_incoming, null);
            } else {
                result = inflater.inflate(R.layout.chat_row_outgoing, null);
            }
            TextView message = (TextView) result.findViewById(R.id.message_text); //TODO: change message_text to messageText
            message.setText(getItem(position));
            return result;
        }
    }

}
