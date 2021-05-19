package com.example.chatroomapp;
import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.print.PrintAttributes;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
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
    private ScrollView scrollView;

    private String user_name,room_name;
    private DatabaseReference root;
    private String temp_key;

    private boolean isLoaded = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_room);

        getSupportActionBar().hide();
        send_button = findViewById(R.id.btn_send);
        message_input = findViewById(R.id.msg_input);
        text_conversation = findViewById(R.id.textView);
        convo_layout = findViewById(R.id.conversation);
        scrollView = findViewById(R.id.scrollView);

        user_name = getIntent().getExtras().get("user_name").toString();
        room_name = getIntent().getExtras().get("room_name").toString();

        setTitle("Room - " + room_name);

        root = FirebaseDatabase.getInstance().getReference().child(room_name);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

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

                message_input.getText().clear();
            }
        });

        root.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                load_chat_conversation(snapshot);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                load_chat_conversation(snapshot);
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

    private void load_chat_conversation(DataSnapshot snapshot) {
        Iterator i = snapshot.getChildren().iterator();
        while(i.hasNext())
        {
            chat_msg = (String)((DataSnapshot)i.next()).getValue();
            chat_username = (String)((DataSnapshot)i.next()).getValue();

            createNewBubble(chat_msg, chat_username);

            scrollView.post(new Runnable() {
                @Override
                public void run() {
                    scrollView.fullScroll(ScrollView.FOCUS_DOWN);
                }
            });
        }
        //isLoaded = true;
    }


//    private void append_chat_conversation(DataSnapshot snapshot) {
//        //Iterator i = snapshot.getChildren().iterator();
//
//        Query query = root.orderByKey().limitToLast(1);
//        Log.i("newest child",query.toString());
//
//        query.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for(DataSnapshot data : snapshot.getChildren())
//                {
//                    //if you call methods on dataSnapshot it gives you the required values
//                    chat_msg = data.child("msg").getValue().toString(); // then it has the value "somekey4"
//                    user_name = data.child("name").getValue().toString();
//                    String key = data.getKey(); // then it has the value "4:"
//                    //as per your given snapshot of firebase database data
//                }
//                createNewBubble(chat_msg, user_name);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//    }

    private void createNewBubble(String message, String user)
    {
        TextView bubble = new TextView(this);

        String text = "<font color='#FFD22D'>"+user+"</font>";
        bubble.setLayoutParams(text_conversation.getLayoutParams());
        bubble.setBackground(text_conversation.getBackground());
        bubble.setTextColor(text_conversation.getTextColors());
        bubble.setTextSize(text_conversation.getTextSize());
        bubble.setText(Html.fromHtml(text, Html.FROM_HTML_MODE_LEGACY)+": " + message);
        if (user == user_name)
        {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.gravity = Gravity.RIGHT;
            params.rightMargin = 40;
            params.topMargin = 30;
            params.leftMargin = 100;
            bubble.setLayoutParams(params);
            bubble.setBackgroundTintList(this.getResources().getColorStateList(R.color.user_bubble_color));
        }
        convo_layout.addView(bubble);

//        int colorFrom = getResources().getColor(R.color.black);
//        int colorTo = getResources().getColor(R.color.white);
//        ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
//        colorAnimation.setDuration(1000); // milliseconds
//        colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//
//            @Override
//            public void onAnimationUpdate(ValueAnimator animator) {
//                bubble.setTextColor((int) animator.getAnimatedValue());
//            }
//
//        });
//        colorAnimation.start();
    }
}
