package com.techdoctorbd.soundrecorder.Adapters;

import android.content.Context;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.techdoctorbd.soundrecorder.Database.DatabaseHelper;
import com.techdoctorbd.soundrecorder.Interfaces.OnItemClickListener;
import com.techdoctorbd.soundrecorder.Model.RecordingItem;
import com.techdoctorbd.soundrecorder.R;

import java.text.Format;
import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class FileListAdapter extends RecyclerView.Adapter<FileListAdapter.MyViewHolder> implements OnItemClickListener {


    private Context context;
    private ArrayList<RecordingItem> items;

    public FileListAdapter(Context context, ArrayList<RecordingItem> items) {
        this.context = context;
        this.items = items;
        DatabaseHelper.setClickListener(this);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.file_item_layout,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        RecordingItem recordingItem = items.get(position);

        long minutes = TimeUnit.MILLISECONDS.toMinutes(recordingItem.getLength());
        long seconds = TimeUnit.MILLISECONDS.toSeconds(recordingItem.getLength()) - TimeUnit.MINUTES.toSeconds(minutes);

        String fileInfo = String.format("%02d:%02d",minutes,seconds)+" - "+ DateUtils.formatDateTime(context,recordingItem.getTime_added(),
                DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_MONTH | DateUtils.FORMAT_SHOW_TIME |
                        DateUtils.FORMAT_SHOW_YEAR);
        holder.title.setText(recordingItem.getName());
        holder.details.setText(fileInfo);


    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public void onItemClick(RecordingItem recordingItem) {
        items.add(recordingItem);
        notifyItemInserted(items.size() - 1);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView title,details;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title_file_list);
            details = itemView.findViewById(R.id.details_file_list);
        }
    }
}
