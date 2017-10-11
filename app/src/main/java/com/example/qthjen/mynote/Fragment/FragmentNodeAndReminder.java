package com.example.qthjen.mynote.Fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.qthjen.mynote.R;

public class FragmentNodeAndReminder extends Fragment {

    public static ImageView iv_note;
    public static ImageView iv_calendar;
    public static ImageView iv_reminder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_node_and_calendar, container, false);

        iv_note = (ImageView) view.findViewById(R.id.iv_notes);
        iv_calendar = (ImageView) view.findViewById(R.id.iv_calendar);
        iv_reminder = (ImageView) view.findViewById(R.id.iv_reminder);

        return view;
    }
}
