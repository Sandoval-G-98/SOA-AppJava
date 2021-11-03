package RegisterCreateClande;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
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

public class RegisterOrCreateClande extends AppCompatActivity {
    private TextView batteryLevel;
    private BatteryReceiver battery;
    private TextView clanders10to18;
    private TextView clandes10to18;
    private SharedPreferences preferences_register;
    private SharedPreferences preferences_login;

    private SharedPreferences dataUser;

    private AsyncTimer asyncTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_or_create_clande);

        Button registerClande = findViewById(R.id.buttonRegisterClande);
        Button joinClande = findViewById(R.id.buttonJoinClande);

        batteryLevel = findViewById(R.id.batteryLevel4);
        battery = new BatteryReceiver(batteryLevel);
        registerReceiver(battery, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        clandes10to18 = findViewById(R.id.clandesCreate10To18hours);
        clanders10to18 = findViewById(R.id.clanders10to18hours);

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
        unregisterReceiver(battery);
        super.onDestroy();
    }

    public void readPreferences(){

        preferences_register = getSharedPreferences("metrics_register_clandes", Context.MODE_PRIVATE);
        preferences_login = getSharedPreferences("metrics_login_clanders", Context.MODE_PRIVATE);

        clanders10to18.setText(String.valueOf(preferences_login.getInt("logingClanders_10_to_18", 0)));
        clandes10to18.setText(String.valueOf(preferences_register.getInt("registerClande_10_to_18", 0)));
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
}