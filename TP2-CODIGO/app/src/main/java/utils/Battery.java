package utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;
import android.widget.TextView;


public class Battery {

    private TextView batteryLevel;

    public Battery(TextView batteryLevel) {
        this.batteryLevel = batteryLevel;
    }

    public BroadcastReceiver setBatInfo (){
        return new BroadcastReceiver() {
           @Override
           public void onReceive(Context context, Intent intent){
               int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL,0);
               String batteryMessage = "Bateria: " + level + "%";
               batteryLevel.setText(batteryMessage);
           }
       };
    }

}
