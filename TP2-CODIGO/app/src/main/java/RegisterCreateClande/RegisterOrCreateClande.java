package RegisterCreateClande;

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

import androidx.appcompat.app.AppCompatActivity;

import com.example.Authentication.R;

import Asincrono.AsyncTimer;
import CreateClande.CreateClande;
import JoinClande.JoinClande;
import utils.BatteryReceiver;

public class RegisterOrCreateClande extends AppCompatActivity implements SensorEventListener {

    private TextView batteryLevel;
    private BatteryReceiver battery;
    private String email;
    private TextView loggers;
    private TextView clandesCreated;
    private TextView ambientTemperature;
    private TextView temperatureText;
    private SharedPreferences preferences_register;
    private SharedPreferences preferences_login;

    private Context context = this;
    private SensorManager sm;
    private Sensor sensorTemperature;

    private SharedPreferences dataUser;
    private AsyncTimer asyncTimer;

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

        readPreferences();
        setPreferencesUser();

        // Log.d("Debug", "MOSTRAR DATAUSER REGISTER OR CREATE CLANDE:::");
        // Log.d("Debug", dataUser.getString("email",""));
        // Log.d("Debug", dataUser.getString("token",""));
        // Log.d("Debug","VALOR DEL TIMER CUANDO SE CREA:" + String.valueOf(dataUser.getLong("timeActually",0)));

        registerClande.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                asyncTimer.cancel(true);
                Intent registerClandeActivity = new Intent(RegisterOrCreateClande.this, CreateClande.class);
                startActivity(registerClandeActivity);
            }
        });

        joinClande.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                asyncTimer.cancel(true);
                Intent joinClandeActivity = new Intent(RegisterOrCreateClande.this, JoinClande.class);
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

        preferences_register = getSharedPreferences("metrics_register_clandes_prod_1", Context.MODE_PRIVATE);
        preferences_login = getSharedPreferences("metrics_login_clanders_prod_1", Context.MODE_PRIVATE);

        loggers.setText("Logueados de 10 a 18: "+ String.valueOf(preferences_login.getInt("logingClanders_10_to_18", 0)));
        clandesCreated.setText("Clandes creadas de 10 a 18: " + String.valueOf(preferences_register.getInt("registerClande_10_to_18", 0)));
    }

    public void setPreferencesUser(){
        dataUser = getSharedPreferences("SharedUser", Context.MODE_PRIVATE);
        SharedPreferences.Editor myEdit = dataUser.edit();
        myEdit.putLong("timeActually", System.currentTimeMillis());
        myEdit.apply();
    }

    @Override
    protected void onResume() {
        super.onResume();
        dataUser = getSharedPreferences("SharedUser", Context.MODE_PRIVATE);
        this.asyncTimer = new AsyncTimer( RegisterOrCreateClande.this);
        this.asyncTimer.execute(dataUser.getLong("timeActually",0));
    }

    public void showMessage(String message){
        Toast.makeText(this,message,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        String tabuenoString = "La temperatura esta perfecto para una clande!!";
        String tamaloString = "La temperatura esta mala pero se puede escabiar!!";

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