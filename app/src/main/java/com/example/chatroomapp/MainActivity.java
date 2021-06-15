package com.example.chatroomapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private Button add_room;
    private EditText room_name;

    //private ListView listView;
    private LinearLayout linearScroll;
    private ArrayAdapter<String> arrayAdapter;
    private ArrayList<String> list_of_rooms = new ArrayList<>();

    private String name;
    private DatabaseReference root = FirebaseDatabase.getInstance().getReference().getRoot();

    ArrayList<Button> roomBtns = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();
        add_room = findViewById(R.id.enter);
        room_name = findViewById(R.id.room_name_edittext);
        //listView = findViewById(R.id.listView);
        linearScroll = findViewById(R.id.linearScroll);

        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,list_of_rooms);

        //listView.setAdapter(arrayAdapter);


        //request_username();
        name = getIntent().getExtras().get("user_name").toString();


        add_room.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String,Object> map = new HashMap<String, Object>();
                map.put(room_name.getText().toString(),"");
                root.updateChildren(map);
            }
        });

        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Iterator i = snapshot.getChildren().iterator();

                linearScroll.removeAllViewsInLayout();

                for (DataSnapshot dsp : snapshot.getChildren()) {
                    //Userlist.add(); //add result into array list
                    Button newRoomButton = new Button(getApplicationContext());
                    newRoomButton.setText(String.valueOf(dsp.getKey()));
                    newRoomButton.setTextAlignment(Button.TEXT_ALIGNMENT_VIEW_START);
                    newRoomButton.setBackgroundTintList(getResources().getColorStateList(R.color.darker_yellow));
                    newRoomButton.setTextColor(getResources().getColor(R.color.black));
                    newRoomButton.setPadding(50,60,0,60);
                    newRoomButton.setTextSize(20);
                    linearScroll.addView(newRoomButton);

                    newRoomButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getApplicationContext(), Chat_Room.class);
                            intent.putExtra("room_name",(newRoomButton.getText().toString()));
                            intent.putExtra("user_name",name);
                            startActivity(intent);
                        }
                    });
                }


//                Set<String> set = new HashSet<String>();
//                HashMap<String,Object> Map = new HashMap<>();
//
//                while(i.hasNext())
//                {
//                    set.add(((DataSnapshot)i.next()).getKey());
//                    list_of_rooms.clear();
//                    list_of_rooms.addAll(set);
//
//                    //arrayAdapter.notifyDataSetChanged();
//                    Button newRoomButton = new Button(getApplicationContext());
//                    newRoomButton.setText((String)((DataSnapshot)i.next()).getValue());
//                    linearScroll.addView(newRoomButton);
//
//                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent intent = new Intent(getApplicationContext(), Chat_Room.class);
//                intent.putExtra("room_name",((TextView)view).getText().toString());
//                intent.putExtra("user_name",name);
//                startActivity(intent);
//            }
//        });
    }

    private void request_username() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter name:");

        EditText input_field = new EditText(this);

        builder.setView(input_field);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                name = input_field.getText().toString();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                request_username();
            }
        });

        builder.show();
    }
}