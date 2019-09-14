package com.techdoctorbd.soundrecorder.Fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.techdoctorbd.soundrecorder.Adapters.FileListAdapter;
import com.techdoctorbd.soundrecorder.Database.DatabaseHelper;
import com.techdoctorbd.soundrecorder.Model.RecordingItem;
import com.techdoctorbd.soundrecorder.R;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class FileListFragment extends Fragment {

    private RecyclerView recyclerView;
    private TextView textView;
    private LinearLayoutManager layoutManager;
    private DatabaseHelper databaseHelper;
    ArrayList<RecordingItem> recordingItems;
    private FileListAdapter fileListAdapter;
    private static FileListFragment INSTANCE = null;

    public FileListFragment() {

    }

    public static FileListFragment getInstance()
    {
        if (INSTANCE == null)
            INSTANCE = new FileListFragment();
        return INSTANCE;

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_file_list, container, false);

        recyclerView = view.findViewById(R.id.recyclerView_file);
        textView = view.findViewById(R.id.textView_list);
        databaseHelper = new DatabaseHelper(getContext());
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);

        recordingItems = databaseHelper.getAllRecordings();

        if (recordingItems == null){
            recyclerView.setVisibility(View.GONE);
            textView.setVisibility(View.VISIBLE);
        } else {
            textView.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            fileListAdapter = new FileListAdapter(getContext(),recordingItems);
            recyclerView.setAdapter(fileListAdapter);

        }

        return view;
    }

}
