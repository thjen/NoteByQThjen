package com.example.qthjen.mynote.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.example.qthjen.mynote.Fragment.FragmentNodeAndReminder;
import com.example.qthjen.mynote.R;

public class Reminders extends AppCompatActivity {

    Toolbar tbar_reminder;
    AutoCompleteTextView search_reminder;
    FloatingActionButton fab_addReminder;
    TextView tv_add_reminder;
    SwipeMenuListView swipe_listviewReminder;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminders);

        FindView();
        EventClick();

    }

    private void EventClick() {

        FragmentNodeAndReminder.iv_note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Reminders.this, MainActivity.class));
            }
        });

        fab_addReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Reminders.this, AddReminder.class));
            }
        });

        tv_add_reminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Reminders.this, AddReminder.class));
            }
        });

    }

    private void FindView() {

        tbar_reminder = (Toolbar) findViewById(R.id.tbar_Reminder);
        search_reminder = (AutoCompleteTextView) findViewById(R.id.search_reminder);
        fab_addReminder = (FloatingActionButton) findViewById(R.id.fab_AddReminder);
        tv_add_reminder = (TextView) findViewById(R.id.tv_addReminder);
        swipe_listviewReminder = (SwipeMenuListView) findViewById(R.id.swipe_listviewReminder);

    }

//    private void ToolbarFinish(Toolbar toolbar) {
//
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                finish();
//            }
//        });
//
//    }

}
