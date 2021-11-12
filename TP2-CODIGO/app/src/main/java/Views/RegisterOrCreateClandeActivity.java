package Views;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.Authentication.R;

import Models.Asincrono.AsyncTimer;
import Models.BatteryReceiver;
import Models.SensorTemperatureDetector;
import Presenters.RegisterOrCreateClandePresenter;

public class RegisterOrCreateClandeActivity extends AppCompatActivity  {

    private TextView batteryLevel;
    private BatteryReceiver battery;
    private TextView loggers;
    private TextView clandesCreated;
    private TextView ambientTemperature;
    private TextView temperatureText;
    private SensorTemperatureDetector listenerTemp;
    private Context context = this;
    private SensorManager sm;
    private Sensor sensorTemperature;
    private SharedPreferences dataUser;
    private AsyncTimer asyncTimer;
    private RegisterOrCreateClandePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_or_create_clande);

        Button registerClande = findViewById(R.id.buttonRegisterClande);
        Button joinClande = findViewById(R.id.buttonJoinClande);
        loggers = findViewById(R.id.idLogueados10a18);
        clandesCreated = findViewById(R.id.idClandesCreadas10a18);

        batteryLevel = findViewById(R.id.batteryLevel4);
        battery = new BatteryReceiver(batteryLevel);
        registerReceiver(battery, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        ambientTemperature = findViewById(R.id.ambientTemperature);
        temperatureText = findViewById(R.id.temperatureText);

        sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensorTemperature = sm.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        listenerTemp = new SensorTemperatureDetector(this, ambientTemperature, temperatureText);

        presenter = new RegisterOrCreateClandePresenter(this);

        loggers.setText("Usuario logueados de 10 a 18: " + presenter.readPreferencesLogin());
        clandesCreated.setText("Clandes creadas de 10 a 18: " + presenter.readPreferencesRegisters());


        if(sensorTemperature == null) {
            Toast.makeText(context, "No posee sensor de temperatura, no podrá ver la temperatura", Toast.LENGTH_LONG).show();
            //finish();
        }

        presenter.setPreferencesUser();
        dataUser = this.getSharedPreferences("SharedUser", Context.MODE_PRIVATE);

        registerClande.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                asyncTimer.cancel(true);
                Intent registerClandeActivity = new Intent(RegisterOrCreateClandeActivity.this, CreateClandeActivity.class);
                startActivity(registerClandeActivity);
            }
        });

        joinClande.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                asyncTimer.cancel(true);
                Intent joinClandeActivity = new Intent(RegisterOrCreateClandeActivity.this, JoinClandeActivity.class);
                startActivity(joinClandeActivity);
            }
        });
    }

    @Override
    protected void onPause(){
        sm.unregisterListener(listenerTemp);
        super.onPause();
    }

    @Override
    protected void onDestroy(){
        unregisterReceiver(battery);
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        dataUser = this.getSharedPreferences("SharedUser", Context.MODE_PRIVATE);
        sm.registerListener(listenerTemp, sensorTemperature, SensorManager.SENSOR_DELAY_NORMAL);
        this.asyncTimer = new AsyncTimer( RegisterOrCreateClandeActivity.this);
        this.asyncTimer.execute(dataUser.getLong("timeActually",0));
    }

    public void showMessage(String message){
        Toast.makeText(this,message,Toast.LENGTH_LONG).show();
    }

}