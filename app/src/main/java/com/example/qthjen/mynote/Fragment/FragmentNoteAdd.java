package com.example.qthjen.mynote.Fragment;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.qthjen.mynote.Activity.MainActivity;
import com.example.qthjen.mynote.Util.InsertDataBase;
import com.example.qthjen.mynote.R;

public class FragmentNoteAdd extends Fragment {

    EditText et_note;
    Button bt_add;

    InsertDataBase insertDatabase;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_note_add, container, false);

        insertDatabase = (InsertDataBase) getActivity();

        et_note = view.findViewById(R.id.et_note);
        bt_add = view.findViewById(R.id.bt_add);

        bt_add.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.N)
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                String title = FragmentTitle.textinput_title.getText().toString();
                String note = et_note.getText().toString();
                String mdate;

                /** set date **/
                Calendar calendar = Calendar.getInstance();

                int date = calendar.get(Calendar.DAY_OF_WEEK);
                String nameDate = "";

                switch ( date) {

                    case 2: nameDate = "Monday";break;
                    case 3: nameDate = "Tuesday";break;
                    case 4: nameDate = "Wednesday";break;
                    case 5: nameDate = "Thursday";break;
                    case 6: nameDate = "Friday";break;
                    case 7: nameDate = "Saturday";break;

                    default: nameDate = "Sunday";break;
                }

                mdate = nameDate + " " + calendar.get(Calendar.DATE) + "/" + calendar.get(Calendar.MONTH) + "/" + calendar.get(Calendar.YEAR);

                insertDatabase.InsertDatabase(title, note, mdate);
                startActivity(new Intent(getContext(), MainActivity.class));
            }
        });

        return view;
    }

}
