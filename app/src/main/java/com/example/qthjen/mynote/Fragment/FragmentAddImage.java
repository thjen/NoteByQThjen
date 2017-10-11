package com.example.qthjen.mynote.Fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.qthjen.mynote.R;

public class FragmentAddImage extends Fragment {

    public static TextView iv_camera, iv_takefile;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_image_add, container, false);

        iv_camera = view.findViewById(R.id.iv_camera);
        iv_takefile = view.findViewById(R.id.iv_takefile);

        return view;
    }
}
