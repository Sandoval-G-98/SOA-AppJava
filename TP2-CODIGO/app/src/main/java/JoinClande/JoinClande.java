package JoinClande;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.Authentication.R;

import utils.BatteryReceiver;

public class JoinClande extends AppCompatActivity {

    private TextView batteryLevel;
    private BatteryReceiver battery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_clande);

        batteryLevel = findViewById(R.id.batteryLevel6);
        battery = new BatteryReceiver(batteryLevel);
        registerReceiver(battery, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
    }

    @Override
    protected void onDestroy(){
        unregisterReceiver(battery);
        super.onDestroy();
    }

}