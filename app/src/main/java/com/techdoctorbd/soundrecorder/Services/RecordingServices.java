package com.techdoctorbd.soundrecorder.Services;

import android.app.Service;
import android.content.Intent;
import android.media.MediaRecorder;
import android.os.Environment;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.Nullable;
import com.techdoctorbd.soundrecorder.Database.DatabaseHelper;
import com.techdoctorbd.soundrecorder.Model.RecordingItem;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class RecordingServices extends Service {

    MediaRecorder mediaRecorder;
    long mStartingTimeMills = 0;
    long mElapsedMills = 0;

    File file;
    String fileName;

    DatabaseHelper databaseHelper;

    @Override
    public void onCreate() {
        super.onCreate();
        databaseHelper = new DatabaseHelper(getApplicationContext());
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startRecording();
        return START_STICKY;
    }

    private void startRecording() {

        try {
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-dd-HH-mm-ss", Locale.getDefault());
            fileName = simpleDateFormat.format(calendar.getTime())+"_recording";
        } catch (Exception e){
            Long tsLong = System.currentTimeMillis()/1000;
            fileName ="recording_"+tsLong.toString();
        }
        file = new File(Environment.getExternalStorageDirectory()+ "/Sound Recorder Pro/"+fileName+".mp3");

        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        mediaRecorder.setOutputFile(file.getAbsolutePath());
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
        mediaRecorder.setAudioChannels(1);

        try {

            mediaRecorder.prepare();
            mediaRecorder.start();
            mStartingTimeMills = System.currentTimeMillis();

        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void stopRecording(){

        mediaRecorder.stop();
        mElapsedMills = (System.currentTimeMillis() - mStartingTimeMills);
        mediaRecorder.release();
        Toast.makeText(this, "Recording Saved Successfully at"+file.getAbsolutePath(), Toast.LENGTH_LONG).show();

        //For Database Add

        RecordingItem recordingItem = new RecordingItem(fileName,file.getAbsolutePath(),mElapsedMills,System.currentTimeMillis());

        databaseHelper.addRecording(recordingItem);
    }

    @Override
    public void onDestroy() {
        if (mediaRecorder != null){
            stopRecording();
        }
        super.onDestroy();
    }


}
