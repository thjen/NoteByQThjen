package com.example.qthjen.mynote.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.qthjen.mynote.Util.InsertDataBase;
import com.example.qthjen.mynote.R;

public class AddNote extends AppCompatActivity implements InsertDataBase {

    Toolbar tbar_addnote;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addnote);

        tbar_addnote = (Toolbar) findViewById(R.id.tbar_addnote);

        FinishToolbar(tbar_addnote);

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

    @Override
    public void InsertDatabase(String mTitle, String mNote, String mydate) {

        MainActivity.dataBase.INSERT_ITEMS(mTitle, mNote, mydate);
    }

}
