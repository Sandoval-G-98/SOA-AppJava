package JoinClande;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.Authentication.R;

import Asincrono.AsyncTimer;
import utils.BatteryReceiver;

public class JoinClande extends AppCompatActivity {
    private SharedPreferences dataUser;
    private TextView batteryLevel;
    private BatteryReceiver battery;
    private AsyncTimer asyncTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_clande);

        dataUser = this.getSharedPreferences("SharedUser", Context.MODE_PRIVATE);

        batteryLevel = findViewById(R.id.batteryLevel6);
        battery = new BatteryReceiver(batteryLevel);
        registerReceiver(battery, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
    }

    @Override
    protected void onResume() {
        super.onResume();
        dataUser = this.getSharedPreferences("SharedUser", Context.MODE_PRIVATE);
        this.asyncTimer = new AsyncTimer( this);
        this.asyncTimer.execute(dataUser.getLong("timeActually",0));
    }

    @Override
    public void onBackPressed() {
        asyncTimer.cancel(true);
        super.onBackPressed();
    }


    @Override
    protected void onDestroy(){
        unregisterReceiver(battery);
        super.onDestroy();
    }

}