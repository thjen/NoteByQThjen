package com.example.qthjen.mynote.Activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.qthjen.mynote.Fragment.FragmentAddImage;
import com.example.qthjen.mynote.Fragment.FragmentNoteAndImage;
import com.example.qthjen.mynote.Model.Notes;
import com.example.qthjen.mynote.R;

public class ActivityNotes extends AppCompatActivity {

    Toolbar tbar_notes;
    EditText et_note_title;
    ImageView iv_trashNote, iv_takeImage;

    boolean showTakeImage = false;

    FragmentManager fragmentManager = getFragmentManager();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        FindView();
        FinishToolbar(tbar_notes);
        GetIntentSer();
        Event();

    }

    private void Event() {

        iv_trashNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        iv_takeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if ( showTakeImage == false) {
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.addToBackStack("takeImage");    // add into stack
                    fragmentTransaction.add(R.id.fragment_father, new FragmentAddImage(), "TakeImage");
                    fragmentTransaction.commit();
                    showTakeImage = true;
                } else {
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    FragmentAddImage fragmentAddImage = (FragmentAddImage) getFragmentManager().findFragmentByTag("TakeImage");

                    if ( fragmentAddImage != null) {
                        fragmentTransaction.remove(fragmentAddImage);
                        fragmentTransaction.commit();
                    } else {
                        Toast.makeText(ActivityNotes.this, "Fragment is null", Toast.LENGTH_SHORT).show();
                    }

                    showTakeImage = false;
                }

            }
        });

    }

    public void TakeByCamera(View view) {

        Toast.makeText(this, "Camera", Toast.LENGTH_SHORT).show();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.addToBackStack("takeByCamera");
        fragmentTransaction.add(R.id.fragment_father, new FragmentNoteAndImage(), "TakeByCamera");

        FragmentAddImage fragmentAddImage = (FragmentAddImage) getFragmentManager().findFragmentByTag("TakeImage");

        if ( showTakeImage == true) {
            fragmentTransaction.remove(fragmentAddImage);
            showTakeImage = false;
        } else {
            Toast.makeText(ActivityNotes.this, "Fragment is null", Toast.LENGTH_SHORT).show();
        }
        fragmentTransaction.commit();

    }

    public void TakeByFile(View view) {
        Toast.makeText(this, "File", Toast.LENGTH_SHORT).show();
    }

    private void FindView() {
        tbar_notes = (Toolbar) findViewById(R.id.tbar_notes);
        et_note_title = (EditText) findViewById(R.id.et_note_title);
        iv_trashNote = (ImageView) findViewById(R.id.iv_trashNote);
        iv_takeImage = (ImageView) findViewById(R.id.iv_takeImage);
    }

    private void FinishToolbar(Toolbar toolbar) {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void GetIntentSer() {

        Intent intent = getIntent();
        Notes notes = (Notes) intent.getSerializableExtra("mynotes");

        Toast.makeText(this, String.valueOf(notes.getId()) + "\n" + notes.getTitle()
                + "\n" + notes.getNote() + "\n" + notes.getDate(), Toast.LENGTH_SHORT).show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.menu_done) {

        }

        return super.onOptionsItemSelected(item);
    }

}
