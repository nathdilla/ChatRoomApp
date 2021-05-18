package com.example.chatroomapp;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class Chat_Room extends AppCompatActivity
{
    private Button send_button;
    private EditText message_input;
    private TextView text_conversation;
    private LinearLayout convo_layout;

    private String user_name,room_name;
    private DatabaseReference root;
    private String temp_key;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_room);

        send_button = findViewById(R.id.btn_send);
        message_input = findViewById(R.id.msg_input);
        text_conversation = findViewById(R.id.textView);
        convo_layout = findViewById(R.id.conversation);

        user_name = getIntent().getExtras().get("user_name").toString();
        room_name = getIntent().getExtras().get("room_name").toString();

        setTitle("Room - " + room_name);

        root = FirebaseDatabase.getInstance().getReference().child(room_name);

        send_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String,Object> map =new HashMap<String,Object>();
                temp_key = root.push().getKey();
                root.updateChildren(map);

                DatabaseReference message_root = root.child(temp_key);

                Map<String,Object> map2 = new HashMap<String,Object>();
                map2.put("name", user_name);
                map2.put("msg", message_input.getText().toString());

                message_root.updateChildren(map2);
            }
        });

        root.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                append_chat_conversation(snapshot);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                append_chat_conversation(snapshot);
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private String chat_msg,chat_username;
    private void append_chat_conversation(DataSnapshot snapshot) {
        //Iterator i = snapshot.getChildren().iterator();

        Query query = root.orderByKey().limitToLast(1);
        Log.i("newest child",query.toString());

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot data : snapshot.getChildren())
                {
                    //if you call methods on dataSnapshot it gives you the required values
                    chat_msg = data.child("msg").getValue().toString(); // then it has the value "somekey4"
                    user_name = data.child("name").getValue().toString();
                    String key = data.getKey(); // then it has the value "4:"
                    //as per your given snapshot of firebase database data
                }
                createNewBubble(chat_msg, user_name);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
//        while(i.hasNext())
//        {
//            chat_msg = (String)((DataSnapshot)i.next()).getValue();
//            chat_username = (String)((DataSnapshot)i.next()).getValue();
//
//            text_conversation.append(chat_username + ": " + chat_msg + "\n");
//        }
       // createNewBubble(chat_msg);
    }

    private void createNewBubble(String message, String user)
    {
        TextView bubble = new TextView(this);
        bubble.setText(user + ": " + message);
        convo_layout.addView(bubble);
    }
}
