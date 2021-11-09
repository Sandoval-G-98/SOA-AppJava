package Models;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;
import android.widget.TextView;


public class BatteryReceiver extends BroadcastReceiver {

    private TextView batteryLevel;

    public BatteryReceiver(TextView batteryLevel) {
        this.batteryLevel = batteryLevel;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL,0);
        String batteryMessage = "Bateria: " + level + "%";
        batteryLevel.setText(batteryMessage);
    }
}
