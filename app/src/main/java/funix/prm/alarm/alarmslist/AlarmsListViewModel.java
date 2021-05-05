package funix.prm.alarm.alarmslist;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import funix.prm.alarm.data.Alarm;
import funix.prm.alarm.data.MyDBHelper;

public class AlarmsListViewModel extends AndroidViewModel {
//    private AlarmRepository alarmRepository;
    private LiveData<List<Alarm>> alarmsLiveData;
    private MyDBHelper mDB;

    public AlarmsListViewModel(@NonNull Application application) {
        super(application);

//        alarmRepository = new AlarmRepository(application);
        //update sql
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