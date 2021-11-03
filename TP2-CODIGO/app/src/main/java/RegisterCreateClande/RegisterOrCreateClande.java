package RegisterCreateClande;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Authentication.R;
import utils.BatteryReceiver;
import CreateClande.CreateClande;
import JoinClande.JoinClande;

public class RegisterOrCreateClande extends AppCompatActivity implements SensorEventListener {

    private TextView batteryLevel;
    private BatteryReceiver battery;
    private String email;
    private TextView clanders10to18;
    private TextView clandes10to18;
    private TextView ambientTemperature;
    private TextView temperatureText;
    private SharedPreferences preferences_register;
    private SharedPreferences preferences_login;

    private Context context = this;
    private SensorManager sm;
    private Sensor sensorTemperature;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_or_create_clande);

        Button registerClande = findViewById(R.id.buttonRegisterClande);
        Button joinClande = findViewById(R.id.buttonJoinClande);

        email = getIntent().getStringExtra("email");

        batteryLevel = findViewById(R.id.batteryLevel4);
        battery = new BatteryReceiver(batteryLevel);
        registerReceiver(battery, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        clandes10to18 = findViewById(R.id.clandesCreate10To18hours);
        clanders10to18 = findViewById(R.id.clanders10to18hours);
        ambientTemperature = findViewById(R.id.ambientTemperature);
        temperatureText = findViewById(R.id.temperatureText);

        sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensorTemperature = sm.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);

        if(sensorTemperature == null) {
            Toast.makeText(context, "No posee sensor de temperatura", Toast.LENGTH_LONG).show();
            finish();
        }

        //sm.registerListener( (SensorEventListener) context, sensorTemperature, SensorManager.SENSOR_TEMPERATURE);

        readPreferences();

        registerClande.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerClandeActivity = new Intent(RegisterOrCreateClande.this, CreateClande.class);
                registerClandeActivity.putExtra("email", email);
                startActivity(registerClandeActivity);
            }
        });

        joinClande.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent joinClandeActivity = new Intent(RegisterOrCreateClande.this, JoinClande.class);
                joinClandeActivity.putExtra("email", email);
                startActivity(joinClandeActivity);
            }
        });

    }

    @Override
    protected void onDestroy(){
        sm.unregisterListener(this);
        unregisterReceiver(battery);
        super.onDestroy();
    }

    public void readPreferences(){

        preferences_register = getSharedPreferences("metrics_register_clandes", Context.MODE_PRIVATE);
        preferences_login = getSharedPreferences("metrics_login_clanders", Context.MODE_PRIVATE);

        clanders10to18.setText(String.valueOf(preferences_login.getInt("logingClanders_10_to_18", 0)));
        clandes10to18.setText(String.valueOf(preferences_register.getInt("registerClande_10_to_18", 0)));
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        String tabuenoString = "La temperatura esta perfecto para una clande!!";
        String tamaloString = "La temperatura es una garcha pero se puede escabiar!!";

        float ambient_temperature = event.values[0];

        ambientTemperature.setText(ambient_temperature + getResources().getString(R.string.celsius));
        temperatureText.setText(String.valueOf(ambient_temperature > 23 ? tabuenoString : tamaloString));
    }

    @Override
    protected void onResume() {
        super.onResume();
        sm.registerListener(this, sensorTemperature, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}