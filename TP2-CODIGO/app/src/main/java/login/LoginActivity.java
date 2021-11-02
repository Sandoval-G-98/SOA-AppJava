package login;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.Authentication.R;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        batteryLevel = findViewById(R.id.batteryLevel2);
        battery = new BatteryReceiver(batteryLevel);
        registerReceiver(battery, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));

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
}