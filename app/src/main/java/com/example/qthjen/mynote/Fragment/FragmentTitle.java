package com.example.qthjen.mynote.Fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.qthjen.mynote.R;

public class FragmentTitle extends Fragment {

    public static TextInputEditText textinput_title;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_title, container, false);

        textinput_title = view.findViewById(R.id.textinput_title);

        return view;
    }
}
