package com.example.qthjen.mynote.Activity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qthjen.mynote.Adapter.AdapterNotes;
import com.example.qthjen.mynote.Database.DataBase;
import com.example.qthjen.mynote.Fragment.FragmentNodeAndReminder;
import com.example.qthjen.mynote.Model.Notes;
import com.example.qthjen.mynote.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton fab_mainPlus;

    AutoCompleteTextView autoComplete;

    public static TextView tv_addNote;
    public static RecyclerView recyclerView_note;

    public static ArrayList<Notes> arrayNote;

    AdapterNotes adapterNotes;

    public static DataBase dataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FindView();

        recyclerView_note.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView_note.setLayoutManager(layoutManager);
        recyclerView_note.setItemAnimator(new DefaultItemAnimator());

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, layoutManager.getOrientation());
        recyclerView_note.addItemDecoration(dividerItemDecoration);

        arrayNote = new ArrayList<Notes>();
        adapterNotes = new AdapterNotes(MainActivity.this, arrayNote, R.layout.row_adapter_note);

        dataBase = new DataBase(this, "mynote.sqlite", null, 1);
        dataBase.QueryData("CREATE TABLE IF NOT EXISTS Notes(Id INTEGER PRIMARY KEY AUTOINCREMENT, Title VARCHAR, Note VARCHAR, Date VARCHAR, Image BLOB)");
        dataBase.QueryData("CREATE TABLE IF NOT EXISTS Reminders(id INTEGER PRIMARY KEY AUTOINCREMENT, note VARCHAR, date VARCHAR)");

        EventClick();

        ReadDatabase();

        CheckData();
        processedFragment();
        SetupCompleteTextView();

    }
    /** search **/
    private void SetupCompleteTextView() {

        String array[] = new String[arrayNote.size()];
        int Id[] = new int[arrayNote.size()];
        String note[] = new String[arrayNote.size()];
        String Date[] = new String[arrayNote.size()];
        byte[] Image[] = new byte[arrayNote.size()][arrayNote.size()];

        /** hashMap để put dữ liệu theo key trong autoCompleteTextView vì không thể put theo position hay id **/
        Notes notes;
        final HashMap map = new HashMap();
        
        for (int i = 0; i < arrayNote.size(); i++) {
            array[i] = arrayNote.get(i).getTitle().trim();
            Id[i] = arrayNote.get(i).getId();
            note[i] = arrayNote.get(i).getNote().trim();
            Date[i] = arrayNote.get(i).getDate().trim();
            Image[i] = arrayNote.get(i).getImage();
            notes = new Notes(Id[i], array[i], note[i], Date[i], Image[i]);
            map.put(array[i], notes);
        }

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, array);
        autoComplete.setAdapter(arrayAdapter);
        autoComplete.setThreshold(1);

        autoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, ActivityNotes.class);
                intent.putExtra("mynotes", (Serializable) map.get(autoComplete.getAdapter().getItem(i)));
                startActivity(intent);
            }
        });

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
            arrayNote.add(new Notes(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getBlob(4)));
        }
        adapterNotes.notifyDataSetChanged();
        recyclerView_note.setAdapter(adapterNotes);

    }

    private void processedFragment() {

        FragmentNodeAndReminder fragmentNodeAndReminder = (FragmentNodeAndReminder) getFragmentManager().findFragmentById(R.id.fragmen_nodeAndReminders);

        fragmentNodeAndReminder.iv_reminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Reminders.class));
            }
        });

    }

    private void EventClick() {

        fab_mainPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AddNote.class));
            }
        });

        tv_addNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AddNote.class));
            }
        });

    }

    private void FindView() {

        autoComplete = (AutoCompleteTextView) findViewById(R.id.autoComplete);

        fab_mainPlus = (FloatingActionButton) findViewById(R.id.fab_mainPlus);
        tv_addNote = (TextView) findViewById(R.id.tv_addNote);
        recyclerView_note = (RecyclerView) findViewById(R.id.recyclerview_notes);

    }

}
