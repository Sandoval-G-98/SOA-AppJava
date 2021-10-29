package RegisterCreateClande;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.example.Authentication.R;
import Battery.Battery;
import CreateClande.CreateClande;
import JoinClande.JoinClande;

public class RegisterOrCreateClande extends AppCompatActivity {

    private TextView batteryLevel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_or_create_clande);

        Button registerClande = findViewById(R.id.buttonRegisterClande);
        Button joinClande = findViewById(R.id.buttonJoinClande);

        batteryLevel = findViewById(R.id.batteryLevel3);
        Battery bat = new Battery(batteryLevel);
        this.registerReceiver(bat.setBatInfo(), new IntentFilter(Intent.ACTION_BATTERY_CHANGED));

        registerClande.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerClandeActivity = new Intent(RegisterOrCreateClande.this, CreateClande.class);
                startActivity(registerClandeActivity);
            }
        });

        joinClande.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent joinClandeActivity = new Intent(RegisterOrCreateClande.this, JoinClande.class);
                startActivity(joinClandeActivity);
            }
        });

    }
}