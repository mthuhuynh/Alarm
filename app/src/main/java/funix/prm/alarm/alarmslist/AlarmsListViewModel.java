package funix.prm.alarm.alarmslist;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import funix.prm.alarm.data.Alarm;
import funix.prm.alarm.data.MyDBHelper;

public class AlarmsListViewModel extends AndroidViewModel {
    private final LiveData<List<Alarm>> alarmsLiveData;
    private final MyDBHelper mDB;

    public AlarmsListViewModel(@NonNull Application application) {
        super(application);
        mDB = new MyDBHelper(getApplication());
        alarmsLiveData = mDB.read();
    }

    public void update(Alarm alarm) {
        mDB.update(alarm);
    }

    public LiveData<List<Alarm>> getAlarmsLiveData() {
        return alarmsLiveData;
    }
}