package com.example.android.androidassignments;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
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
                mEditText.setText("");
                messageAdapter.notifyDataSetChanged();
            }
        });

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
