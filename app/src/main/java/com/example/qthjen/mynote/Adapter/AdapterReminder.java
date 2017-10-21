package com.example.qthjen.mynote.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.qthjen.mynote.Model.Reminder;
import com.example.qthjen.mynote.R;

import java.util.List;

public class AdapterReminder extends BaseAdapter {

    Context context;
    List<Reminder> list;
    int layout;

    public AdapterReminder(int layout, Context context, List<Reminder> list) {
        this.layout = layout;
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    private class ViewHolder {

        TextView tv_noteReminder, tv_dateReminder;

    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder viewHolder = new ViewHolder();

        if ( view == null ) {

            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(layout, null);
            viewHolder.tv_noteReminder = view.findViewById(R.id.tv_noteReminder);
            viewHolder.tv_dateReminder = view.findViewById(R.id.tv_dateReminder);
            view.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.tv_noteReminder.setText(list.get(i).getNote());
        viewHolder.tv_dateReminder.setText("Reminder: " + list.get(i).getDate());

        return view;
    }

}
