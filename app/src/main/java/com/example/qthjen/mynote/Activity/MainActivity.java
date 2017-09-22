package com.example.qthjen.mynote.Activity;

import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qthjen.mynote.Adapter.AdapterNotes;
import com.example.qthjen.mynote.Database.DataBase;
import com.example.qthjen.mynote.Fragment.FragmentNodeAndReminder;
import com.example.qthjen.mynote.Model.Notes;
import com.example.qthjen.mynote.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton fab_mainPlus, fab_mainUnPlus, fab_add, fab_reminder;
    private Animation fabOne, fabTwo, fabOneCancel, fabTwoCancel;

    public static TextView tv_addNote;
    public static RecyclerView recyclerView_note;

    public static ArrayList<Notes> arrayNote;

    AdapterNotes adapterNotes;

    public static DataBase dataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fabOne = AnimationUtils.loadAnimation(MainActivity.this, R.anim.fab_one);
        fabTwo = AnimationUtils.loadAnimation(MainActivity.this, R.anim.fab_two);

        fabOneCancel = AnimationUtils.loadAnimation(MainActivity.this, R.anim.fab_one_cancel);
        fabTwoCancel = AnimationUtils.loadAnimation(MainActivity.this, R.anim.fab_two_cancel);


        FindView();

        recyclerView_note.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView_note.setLayoutManager(layoutManager);
        recyclerView_note.setItemAnimator(new DefaultItemAnimator());
        arrayNote = new ArrayList<Notes>();
        adapterNotes = new AdapterNotes(MainActivity.this, arrayNote, R.layout.row_adapter_note);

        dataBase = new DataBase(this, "mynote.sqlite", null, 1);
        dataBase.QueryData("CREATE TABLE IF NOT EXISTS Notes(Id INTEGER PRIMARY KEY AUTOINCREMENT, Title VARCHAR, Note VARCHAR, Date VARCHAR)");

        EventClick();

        ReadDatabase();

        CheckData();
        processedFragment();

    }

    private void CheckData() {

        if (arrayNote.size() > 0) {
            tv_addNote.setVisibility(View.INVISIBLE);
            recyclerView_note.setVisibility(View.VISIBLE);
        } else {
            tv_addNote.setVisibility(View.VISIBLE);
            recyclerView_note.setVisibility(View.INVISIBLE);
        }

    }

    public void ReadDatabase() {

        Cursor cursor = dataBase.GetData("SELECT * FROM Notes");
        while (cursor.moveToNext()) {
            arrayNote.add(new Notes(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3)));
        }
        adapterNotes.notifyDataSetChanged();
        recyclerView_note.setAdapter(adapterNotes);

    }

    private void processedFragment() {

        FragmentNodeAndReminder fragmentNodeAndReminder = (FragmentNodeAndReminder) getFragmentManager().findFragmentById(R.id.fragmen_nodeAndReminders);
        fragmentNodeAndReminder.bt_reminders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Calendar.class));
            }
        });

    }

    private void EventClick() {

        fab_mainPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Show();
                Move();
            }
        });

        fab_mainUnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Hide();
                Back();
            }
        });

        fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AddNote.class));
                Hide();
            }
        });

        fab_reminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Reminder", Toast.LENGTH_SHORT).show();
                Hide();
            }
        });

        tv_addNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AddNote.class));
            }
        });

    }

    private void Hide() {

        fab_add.hide();
        fab_reminder.hide();

        fab_mainPlus.setVisibility(View.VISIBLE);
        fab_mainUnPlus.setVisibility(View.INVISIBLE);

    }

    private void Show() {

        fab_reminder.show();
        fab_add.show();

        fab_mainPlus.setVisibility(View.INVISIBLE);
        fab_mainUnPlus.setVisibility(View.VISIBLE);

    }

    private void FindView() {

        fab_mainUnPlus = (FloatingActionButton) findViewById(R.id.fab_mainUnPlus);
        fab_mainPlus = (FloatingActionButton) findViewById(R.id.fab_mainPlus);
        fab_reminder = (FloatingActionButton) findViewById(R.id.fab_reminder);
        fab_add = (FloatingActionButton) findViewById(R.id.fab_add);
        tv_addNote = (TextView) findViewById(R.id.tv_addNote);
        recyclerView_note = (RecyclerView) findViewById(R.id.recyclerview_notes);

    }

    private void Move() {

        FrameLayout.LayoutParams params1 = (FrameLayout.LayoutParams) fab_add.getLayoutParams();
        params1.bottomMargin = (int) (fab_add.getWidth() * 2.3);
        fab_add.setLayoutParams(params1);
        fab_add.startAnimation(fabOne);

        FrameLayout.LayoutParams params2 = (FrameLayout.LayoutParams) fab_reminder.getLayoutParams();
        params2.bottomMargin = (int) (fab_reminder.getWidth() * 3.5);
        fab_reminder.setLayoutParams(params2);
        fab_reminder.startAnimation(fabTwo);

    }

    private void Back() {

        FrameLayout.LayoutParams params1 = (FrameLayout.LayoutParams) fab_add.getLayoutParams();
        params1.bottomMargin -= (int) (fab_add.getWidth() * 2.3);
        fab_add.setLayoutParams(params1);
        fab_add.startAnimation(fabOne);

        FrameLayout.LayoutParams params2 = (FrameLayout.LayoutParams) fab_reminder.getLayoutParams();
        params2.bottomMargin -= (int) (fab_reminder.getWidth() * 3.5);
        fab_reminder.setLayoutParams(params2);
        fab_reminder.startAnimation(fabTwo);

    }

}
