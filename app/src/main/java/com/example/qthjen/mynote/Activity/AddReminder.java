package com.example.qthjen.mynote.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.example.qthjen.mynote.R;

public class AddReminder extends AppCompatActivity {

    Toolbar tbar_addReminder;
    EditText et_noteReminder;
    TimePicker timePicker;
    DatePicker datePicker;
    Button bt_doneReminder;
    Button bt_chooseDate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addreminder);

        FindView();

    }

    private void FindView() {

        tbar_addReminder = (Toolbar) findViewById(R.id.tbar_addReminder);
        et_noteReminder = (EditText) findViewById(R.id.et_noteReminder);
        timePicker = (TimePicker) findViewById(R.id.timePicker);
        datePicker = (DatePicker) findViewById(R.id.datePicker);
        bt_doneReminder = (Button) findViewById(R.id.bt_doneReminder);
        bt_chooseDate = (Button) findViewById(R.id.bt_chooseDate);

    }

}
