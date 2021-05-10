package funix.prm.alarm.data;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

public class MyDBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "alarmManager";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "alarms";

    private static final String KEY_ALARMID = "alarmId";
    private static final String KEY_HOUR = "hour";
    private static final String KEY_MINUTE = "minute";
    private static final String KEY_STARTED = "started";
    private static final String KEY_TITLE = "title";
    private static final String KEY_CREATED = "created";
    private Object Alarm;

    public MyDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create_alarms_table = String.format(
                "CREATE TABLE %s(%s INTEGER PRIMARY KEY, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT)",
                TABLE_NAME, KEY_ALARMID, KEY_HOUR, KEY_MINUTE, KEY_STARTED, KEY_TITLE, KEY_CREATED);
        db.execSQL(create_alarms_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String drop_alarms_table = String.format("DROP TABLE IF EXISTS %s", TABLE_NAME);
        db.execSQL(drop_alarms_table);

        onCreate(db);
    }

    public void insert(Alarm alarm) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_HOUR, alarm.getHour());
        values.put(KEY_MINUTE, alarm.getMinute());
        values.put(KEY_STARTED, alarm.isStarted());
        values.put(KEY_TITLE, alarm.getTitle());
        values.put(KEY_CREATED, alarm.getCreated());

        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public void update(Alarm alarm) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_HOUR, alarm.getHour());
        values.put(KEY_MINUTE, alarm.getMinute());
        values.put(KEY_STARTED, alarm.isStarted());
        values.put(KEY_TITLE, alarm.getTitle());
        values.put(KEY_CREATED, alarm.getCreated());

        db.update(TABLE_NAME, values, KEY_ALARMID + " = ?", new String[]{
                String.valueOf(alarm.getAlarmId())});

        db.close();
    }

    public LiveData<List<Alarm>> read() {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor c = db.query(
                this.TABLE_NAME,   // The table to query
                null,             // The array of columns to return (pass null to get all)
                null,              // The columns for the WHERE clause
                null,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null               // The sort order
        );

        MutableLiveData<List<funix.prm.alarm.data.Alarm>> alarmsLD = new MutableLiveData<>();
        List<Alarm> alarms = new ArrayList<>();

        while (c.moveToNext()) {
            int alarmId = c.getInt(c.getColumnIndex(KEY_ALARMID));
            int hour = c.getInt(c.getColumnIndex(KEY_HOUR));
            int minute = c.getInt(c.getColumnIndex(KEY_MINUTE));
            String title = c.getString(c.getColumnIndex(KEY_TITLE));
            long created = c.getLong(c.getColumnIndex(KEY_CREATED));
            boolean started = Boolean.parseBoolean(c.getString(c.getColumnIndex(KEY_STARTED)));
            Alarm alarm = new Alarm(alarmId, hour, minute, title, created, started);
            alarms.add(alarm);
        }
        c.close();
        alarmsLD.postValue(alarms);

        return alarmsLD;
    }

    public void delete(Alarm alarm) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_NAME,KEY_ALARMID + " = " + alarm.getAlarmId(), null);
    }
}
