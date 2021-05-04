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
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import java.util.Random;

import funix.prm.alarm.R;
import funix.prm.alarm.data.Alarm;

public class CreateAlarmFragment extends Fragment {

//    @BindView(R.id.fragment_createalarm_recurring) CheckBox recurring;
//    @BindView(R.id.fragment_createalarm_checkMon) CheckBox mon;
//    @BindView(R.id.fragment_createalarm_checkTue) CheckBox tue;
//    @BindView(R.id.fragment_createalarm_checkWed) CheckBox wed;
//    @BindView(R.id.fragment_createalarm_checkThu) CheckBox thu;
//    @BindView(R.id.fragment_createalarm_checkFri) CheckBox fri;
//    @BindView(R.id.fragment_createalarm_checkSat) CheckBox sat;
//    @BindView(R.id.fragment_createalarm_checkSun) CheckBox sun;
//    @BindView(R.id.fragment_createalarm_recurring_options) LinearLayout recurringOptions;
    TimePicker timePicker;
    EditText title;
    Button scheduleAlarm;

    private CreateAlarmViewModel createAlarmViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        createAlarmViewModel = ViewModelProviders.of(this).get(CreateAlarmViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_createalarm, container, false);

        timePicker = view.findViewById(R.id.fragment_createalarm_timePicker);
        title = view.findViewById(R.id.fragment_createalarm_title);
        scheduleAlarm = view.findViewById(R.id.fragment_createalarm_scheduleAlarm);

//        recurring.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (isChecked) {
//                    recurringOptions.setVisibility(View.VISIBLE);
//                } else {
//                    recurringOptions.setVisibility(View.GONE);
//                }
//            }
//        });

        scheduleAlarm.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                scheduleAlarm();
                Navigation.findNavController(v).navigate(R.id.action_createAlarmFragment_to_alarmsListFragment);
            }
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
//                recurring.isChecked(),
//                mon.isChecked(),
//                tue.isChecked(),
//                wed.isChecked(),
//                thu.isChecked(),
//                fri.isChecked(),
//                sat.isChecked(),
//                sun.isChecked()
        );

        createAlarmViewModel.insert(alarm);

        alarm.schedule(getContext());
    }
}