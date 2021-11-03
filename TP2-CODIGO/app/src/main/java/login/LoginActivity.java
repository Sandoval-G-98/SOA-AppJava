package login;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.Authentication.R;

import java.util.Calendar;
import java.util.regex.Pattern;

import Asincrono.AsyncLogin;
import register.RegisterActivity;
import utils.BatteryReceiver;

public class LoginActivity extends AppCompatActivity {
    private EditText inputEmail;
    private EditText inputPassword;
    private String email;
    private String password;
    private TextView batteryLevel;
    private BatteryReceiver battery;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        batteryLevel = findViewById(R.id.batteryLevel2);
        battery = new BatteryReceiver(batteryLevel);
        registerReceiver(battery, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));

        storePreference();

        //Funcionalidad de Atras, vuelve a Login
        TextView linkToRegister = findViewById(R.id.toRegister);
        linkToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent register = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(register);
            }
        });
    }

    @Override
    protected void onDestroy(){
        unregisterReceiver(battery);
        super.onDestroy();
    }

    public void handleLogin(View view) {
        this.inputEmail = findViewById(R.id.loginEmail);
        this.inputPassword = findViewById(R.id.loginPassword);

        if(validateForm()){
            new AsyncLogin(LoginActivity.this).execute(email,password);
        }
    };

    public boolean validateForm() {
        boolean isValid = true;
        this.email = inputEmail.getText().toString();
        this.password = inputPassword.getText().toString();

        Pattern pattern = Patterns.EMAIL_ADDRESS;

        if(!pattern.matcher(this.email).matches()){
            inputEmail.setError("Formato de email invalido.");
            isValid = false;
        }
        if(this.password.length() < 8){
            inputPassword.setError("La password debe contener al menos 8 caracteres.");
            isValid = false;
        }
        return isValid;
    }

    public void showMessage(String message){
        Toast.makeText(this,message,Toast.LENGTH_LONG).show();
    }

    private void storePreference() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        final int LIMIT_SUP_HOUR = 18;
        final int LIMIT_INF_HOUR = 10;

        if( LIMIT_INF_HOUR <= hour && hour <= LIMIT_SUP_HOUR ) {
            preferences = getSharedPreferences("metrics_login_clanders_prod_1", Context.MODE_PRIVATE);
            editor = preferences.edit();

            int info = preferences.getInt("logingClanders_10_to_18", 0);
            info+= 1;

            editor.putInt("logingClanders_10_to_18",info);
            editor.commit();
        }
    }
}