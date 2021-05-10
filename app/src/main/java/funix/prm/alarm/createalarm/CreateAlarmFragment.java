package funix.prm.alarm.createalarm;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import java.util.Random;

import funix.prm.alarm.R;
import funix.prm.alarm.data.Alarm;
import funix.prm.alarm.data.MyDBHelper;

public class CreateAlarmFragment extends Fragment {

    TimePicker timePicker;
    EditText title;
    Button scheduleAlarm;
    MyDBHelper mDB;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_createalarm, container, false);

        timePicker = view.findViewById(R.id.fragment_create_alarm_timePicker);
        title = view.findViewById(R.id.fragment_create_alarm_title);
        scheduleAlarm = view.findViewById(R.id.fragment_create_alarm_scheduleAlarm);
        mDB = new MyDBHelper(getContext());

        scheduleAlarm.setOnClickListener(v -> {
            scheduleAlarm();
            Navigation.findNavController(v).navigate(R.id.action_createAlarmFragment_to_alarmsListFragment);
        });

        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void scheduleAlarm() {
        int alarmId = new Random().nextInt(Integer.MAX_VALUE);

        Alarm alarm = new Alarm(
                alarmId,
                TimePickerUtil.getTimePickerHour(timePicker),
                TimePickerUtil.getTimePickerMinute(timePicker),
                title.getText().toString(),
                System.currentTimeMillis(),
                true
        );

        //Add to Database
        mDB.insert(alarm);

        //Schedule
        alarm.schedule(getContext());
    }
}