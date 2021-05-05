package funix.prm.alarm.service;

import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.LifecycleService;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import java.util.List;

import funix.prm.alarm.data.Alarm;
import funix.prm.alarm.data.MyDBHelper;

public class RescheduleAlarmsService extends LifecycleService {
    private LiveData<List<Alarm>> alarmsLiveData;
    private MyDBHelper mDB;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);

        mDB = new MyDBHelper(getApplication());
        alarmsLiveData = mDB.read();

        alarmsLiveData.observe(this, new Observer<List<Alarm>>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onChanged(List<Alarm> alarms) {
                for (Alarm a : alarms) {
                    if (a.isStarted()) {
                        a.schedule(getApplicationContext());
                    }
                }
            }
        });

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        super.onBind(intent);
        return null;
    }
}
