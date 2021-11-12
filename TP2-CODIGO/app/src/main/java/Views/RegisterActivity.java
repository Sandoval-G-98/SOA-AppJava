package Views;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.Authentication.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Models.Asincrono.AsyncRegister;
import Models.BatteryReceiver;
import Presenters.RegisterPresenter;

public class RegisterActivity extends AppCompatActivity {
    private EditText inputEmail;
    private EditText inputName;
    private EditText inputLastName;
    private EditText inputPassword;
    private EditText inputDNI;
    private Spinner spinnerCommission;
    private EditText inputGroup;
    private TextView batteryLevel;
    private BatteryReceiver battery;
    private RegisterPresenter presenter;
    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        inputEmail = findViewById(R.id.email);
        inputName = findViewById(R.id.name);
        inputLastName = findViewById(R.id.lastname);
        inputPassword = findViewById(R.id.password);
        inputDNI = findViewById(R.id.dni);
        spinnerCommission = findViewById(R.id.commission);
        inputGroup = findViewById(R.id.group);

        batteryLevel = findViewById(R.id.batteryLevel3);
        battery = new BatteryReceiver(batteryLevel);
        registerReceiver(battery, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));

        presenter = new RegisterPresenter(this);

        Button handleRegister = findViewById(R.id.handleRegister);

        TextView buttonToBack = findViewById(R.id.toBack);
        buttonToBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent login = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(login);
            }
        });

        handleRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!presenter.handleRegister(inputName.getText().toString(), inputLastName.getText().toString(), inputDNI.getText().toString(),
                                   inputEmail.getText().toString(), inputPassword.getText().toString() , spinnerCommission.getSelectedItem().toString(), inputGroup.getText().toString())){
                    Toast.makeText(context,"Error en el registro",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    protected void onDestroy(){
        unregisterReceiver(battery);
        super.onDestroy();
    }

    public void showMessage(String message){
        Toast.makeText(this,message,Toast.LENGTH_LONG).show();
    }
}