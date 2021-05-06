package funix.prm.alarm.alarmslist;

import android.content.Intent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import funix.prm.alarm.R;
import funix.prm.alarm.data.Alarm;
import funix.prm.alarm.data.MyDBHelper;
import funix.prm.alarm.service.AlarmService;

public class AlarmViewHolder extends RecyclerView.ViewHolder {
    private TextView alarmTime;
    private TextView alarmTitle;
    Switch alarmStarted;
    private OnToggleAlarmListener listener;
    private ImageButton alarmDelete;
    private MyDBHelper mDB;

    public AlarmViewHolder(@NonNull View itemView, OnToggleAlarmListener listener) {
        super(itemView);

        alarmTime = itemView.findViewById(R.id.item_alarm_time);
        alarmStarted = itemView.findViewById(R.id.item_alarm_started);
        alarmTitle = itemView.findViewById(R.id.item_alarm_title);
        alarmDelete = itemView.findViewById(R.id.item_alarm_delete);

        mDB = new MyDBHelper(itemView.getContext());

        this.listener = listener;
    }

    public void bind(Alarm alarm) {
        String alarmText = String.format("%02d:%02d", alarm.getHour(), alarm.getMinute());

        alarmTime.setText(alarmText);
        alarmStarted.setChecked(alarm.isStarted());

        if (alarm.getTitle().length() != 0) {
            alarmTitle.setText(alarm.getTitle());
        } else {
            alarmTitle.setText("Alarm");
        }

        alarmStarted.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    listener.onToggle(alarm);
                if (!isChecked) {
                        Intent intentService = new Intent(itemView.getContext(), AlarmService.class);
                        intentService.setAction(AlarmService.ACTION_STOP);
                        itemView.getContext().stopService(intentService);
                }

            }
        });

        alarmDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "delete", Toast.LENGTH_SHORT).show();
                mDB.delete(alarm);
                listener.onDeleteIN(alarm);
            }
        });
    }
}
