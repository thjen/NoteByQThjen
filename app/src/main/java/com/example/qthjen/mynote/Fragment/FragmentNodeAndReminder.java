package com.example.qthjen.mynote.Fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.qthjen.mynote.R;

public class FragmentNodeAndReminder extends Fragment {

    public static ImageView bt_notes;
    public static ImageView bt_reminders;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_node_and_calendar, container, false);

        bt_notes = (ImageView) view.findViewById(R.id.bt_notes);
        bt_reminders = (ImageView) view.findViewById(R.id.bt_remenders);

        return view;
    }
}
