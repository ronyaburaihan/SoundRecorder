package com.techdoctorbd.soundrecorder.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.techdoctorbd.soundrecorder.Interfaces.OnItemClickListener;
import com.techdoctorbd.soundrecorder.Model.RecordingItem;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    private Context context;
    public static final String DATABASE_NAME = "Saved_Recordings.db";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "Saved_Recordings_Table";

    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_PATH = "path";
    public static final String COLUMN_LENGTH = "length";
    public static final String COLUMN_TIME_ADDED = "time_added";

    private static final String COMA_SEPARATION = ",";

    private static OnItemClickListener onItemClickListener;

    private static final String SQLITE_CREATE_TABLE = "CREATE TABLE "+TABLE_NAME +" ("+"id INTEGER PRIMARY KEY" + " AUTOINCREMENT" + COMA_SEPARATION+
            COLUMN_NAME+" TEXT" + COMA_SEPARATION+
            COLUMN_PATH+" TEXT" + COMA_SEPARATION+
            COLUMN_LENGTH+" INTEGER" + COMA_SEPARATION+
            COLUMN_TIME_ADDED+" INTEGER "+" )";

    public DatabaseHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
        this.context = context;
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQLITE_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
    }

    public boolean addRecording(RecordingItem recordingItem){
        try{
            SQLiteDatabase database = getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(COLUMN_NAME,recordingItem.getName());
            contentValues.put(COLUMN_PATH,recordingItem.getPath());
            contentValues.put(COLUMN_LENGTH,recordingItem.getLength());
            contentValues.put(COLUMN_TIME_ADDED,recordingItem.getTime_added());

            database.insert(TABLE_NAME,null,contentValues);

            if(onItemClickListener != null){
                onItemClickListener.onItemClick(recordingItem);
            }

            return true;

        }catch (Exception e){

            e.printStackTrace();
            return false;
        }
    }

    public ArrayList<RecordingItem> getAllRecordings(){
        ArrayList<RecordingItem> recordingList = new ArrayList<>();
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM "+TABLE_NAME,null);
        if (cursor != null){
            while (cursor.moveToNext()){
                String name = cursor.getString(1);
                String path = cursor.getString(2);
                long length = cursor.getLong(3);
                long timeAdded = cursor.getLong(4);

                RecordingItem recordingItem = new RecordingItem(name,path,length,timeAdded);
                recordingList.add(recordingItem);
            }

            cursor.close();
            return recordingList;
        } else {
            return null;
        }
    }

    public static void setClickListener(OnItemClickListener listener){

        onItemClickListener = listener;
    }
}
