package com.example.qthjen.mynote.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.example.qthjen.mynote.Activity.ActivityNotes;
import com.example.qthjen.mynote.Activity.MainActivity;
import com.example.qthjen.mynote.Model.Notes;
import com.example.qthjen.mynote.R;

import java.io.Serializable;
import java.util.List;

public class AdapterNotes extends RecyclerView.Adapter<AdapterNotes.ViewHolder> {

    Context context;
    List<Notes> list;
    int layout;

    int id = 0;
    String title;
    String note;
    String date;

    public AdapterNotes(Context context, List<Notes> list, int layout) {
        this.context = context;
        this.list = list;
        this.layout = layout;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(layout, parent, false);

        return new ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.tv_title.setMaxLines(1);
        holder.tv_title.setEllipsize(TextUtils.TruncateAt.END);
        holder.tv_title.setText("Title: " + list.get(position).getTitle());

        holder.tv_note.setMaxLines(1);
        holder.tv_note.setEllipsize(TextUtils.TruncateAt.END);
        holder.tv_note.setText(list.get(position).getNote());

        holder.tv_date.setText(list.get(position).getDate());

    }

    public void RemoveItem(int position) {

        list.remove(position);
        notifyItemRemoved(position);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_note, tv_title, tv_date;

        public ViewHolder(View itemView) {
            super(itemView);

            tv_note = (TextView) itemView.findViewById(R.id.tv_note);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            tv_date = itemView.findViewById(R.id.tv_date);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {

                    final Dialog dialog  = new Dialog(context);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.alert_dialog);
                    dialog.setCanceledOnTouchOutside(false);

                    TextView tv_titleDialog = dialog.findViewById(R.id.tv_titleDialog);
                    TextView tv_messageDialog = dialog.findViewById(R.id.tv_messageDialog);

                    Button bt_Yes = dialog.findViewById(R.id.bt_yesDialog);
                    Button bt_No = dialog.findViewById(R.id.bt_noDialog);

                    /** chú ý title và id phải được gán ở trong hàm này ko được gán ở ngoài nếu ko sẽ bị lỗi **/
                    title = list.get(getAdapterPosition()).getTitle();
                    id = list.get(getAdapterPosition()).getId();

                    tv_titleDialog.setText("Delete");
                    tv_messageDialog.setText("Would you like to delete note " + title + " ?");

                    bt_Yes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            RemoveItem(getAdapterPosition());
                            MainActivity.dataBase.QueryData("DELETE FROM Notes WHERE Id = '" + id + "'");
                            notifyDataSetChanged();

                            if (MainActivity.arrayNote.size() > 0) {
                                MainActivity.tv_addNote.setVisibility(View.INVISIBLE);
                                MainActivity.recyclerView_note.setVisibility(View.VISIBLE);
                            } else {
                                MainActivity.tv_addNote.setVisibility(View.VISIBLE);
                                MainActivity.recyclerView_note.setVisibility(View.INVISIBLE);
                            }

                            dialog.dismiss();
                        }
                    });

                    bt_No.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });

                    dialog.show();

                    return false;
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    id = list.get(getAdapterPosition()).getId();
                    title = list.get(getAdapterPosition()).getTitle();
                    note = list.get(getAdapterPosition()).getNote();
                    date = list.get(getAdapterPosition()).getDate();

                    Notes notes = new Notes(id, title, note, date);
                    Intent intent = new Intent(context, ActivityNotes.class);
                    intent.putExtra("mynotes", notes);
                    context.startActivity(intent);

                }
            });

        }
    }

}

