package RegisterCreateClande;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.example.Authentication.R;
import utils.BatteryReceiver;
import CreateClande.CreateClande;
import JoinClande.JoinClande;

public class RegisterOrCreateClande extends AppCompatActivity {

    private TextView batteryLevel;
    private BatteryReceiver battery;
    private String email;
    private TextView loggers;
    private TextView clandesCreated;
    private SharedPreferences preferences_register;
    private SharedPreferences preferences_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_or_create_clande);

        Button registerClande = findViewById(R.id.buttonRegisterClande);
        Button joinClande = findViewById(R.id.buttonJoinClande);
        loggers = findViewById(R.id.idLogueados10a18);
        clandesCreated = findViewById(R.id.idClandesCreadas10a18);

        email = getIntent().getStringExtra("email");

        batteryLevel = findViewById(R.id.batteryLevel4);
        battery = new BatteryReceiver(batteryLevel);
        registerReceiver(battery, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));


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
        unregisterReceiver(battery);
        super.onDestroy();
    }

    public void readPreferences(){

        preferences_register = getSharedPreferences("metrics_register_clandes_prod_1", Context.MODE_PRIVATE);
        preferences_login = getSharedPreferences("metrics_login_clanders_prod_1", Context.MODE_PRIVATE);

        loggers.setText("Logueados de 10 a 18: "+ String.valueOf(preferences_login.getInt("logingClanders_10_to_18", 0)));
        clandesCreated.setText("Clandes creadas de 10 a 18: " + String.valueOf(preferences_register.getInt("registerClande_10_to_18", 0)));
    }
}