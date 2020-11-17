package com.example.android.androidassignments;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
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
    public String addMessage = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);
        mListView = findViewById(R.id.mListView);
        mEditText = findViewById(R.id.mEditText);
        mButton = findViewById(R.id.sendButton);

        final ChatAdapter messageAdapter = new ChatAdapter(this);
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


        dbHelper = new ChatDatabaseHelper(this);
        db = dbHelper.getWritableDatabase();
        temp = "SELECT * FROM messages";
        Cursor mCursor = db.rawQuery(temp, null);
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
        mCursor.close();
    }


    @Override
    public void onDestroy(){
        super.onDestroy();
        db.close();
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

        public String getItem(int position) {
            //Log.e("ISHMEET", mArrayList.get(position));
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
