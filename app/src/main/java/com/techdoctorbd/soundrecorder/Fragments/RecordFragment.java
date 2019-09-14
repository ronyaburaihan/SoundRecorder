package com.techdoctorbd.soundrecorder.Fragments;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Chronometer;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.techdoctorbd.soundrecorder.R;
import com.techdoctorbd.soundrecorder.Services.RecordingServices;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecordFragment extends Fragment {

    private Chronometer chronometer;
    private FloatingActionButton record;
    private boolean mStartRecording = true;
    private boolean mPauseRecording = true;
    long timeWhenPaused = 0;

    private static RecordFragment INSTANCE = null;

    public RecordFragment() {

    }

    public static RecordFragment getInstance()
    {
        if (INSTANCE == null)
            INSTANCE = new RecordFragment();
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
        View recordView = inflater.inflate(R.layout.fragment_record, container, false);

        chronometer = recordView.findViewById(R.id.chronometer_record);
        record = recordView.findViewById(R.id.button_record);

        record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startRecord(mStartRecording);
                mStartRecording =! mStartRecording;
            }
        });


        return recordView;
    }

    private void startRecord(boolean mStart) {

        Intent intent = new Intent(getActivity(), RecordingServices.class);
        if (mStart)
        {
            record.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            record.setImageResource(R.drawable.ic_stop_black_24dp);
            Toast.makeText(getContext(), "Recording Started", Toast.LENGTH_LONG).show();

            File folder = new File(Environment.getExternalStorageDirectory() + "/Sound Recorder Pro");

            if(!folder.exists()){
                folder.mkdir();
            }

            chronometer.setBase(SystemClock.elapsedRealtime());
            chronometer.start();
            getActivity().startService(intent);

            getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        } else {

            record.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            record.setImageResource(R.drawable.ic_keyboard_voice_black_24dp);
            timeWhenPaused = 0;
            chronometer.stop();
            chronometer.setBase(SystemClock.elapsedRealtime());

            getActivity().stopService(intent);
        }
    }

}
