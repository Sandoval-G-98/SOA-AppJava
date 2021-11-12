package Views;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.Authentication.R;

import Presenters.LoginPresenter;
import Models.BatteryReceiver;

public class LoginActivity extends AppCompatActivity {

    private TextView batteryLevel;
    private BatteryReceiver battery;
    private Button buttonLogin;
    private LoginPresenter presenter;
    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        batteryLevel = findViewById(R.id.batteryLevel2);
        battery = new BatteryReceiver(batteryLevel);
        registerReceiver(battery, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));

        buttonLogin = findViewById(R.id.buttonLogin);

        presenter = new LoginPresenter(this);

        TextView linkToRegister = findViewById(R.id.toRegister);
        linkToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent register = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(register);
            }
        });

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText inputEmail = findViewById(R.id.loginEmail);
                EditText inputPassword = findViewById(R.id.loginPassword);
                if(!presenter.handleLogin(inputEmail.getText().toString(), inputPassword.getText().toString())){
                    Toast.makeText(context, "Credenciales incorrectas", Toast.LENGTH_LONG).show();
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

    public void storePreferencesLogin(){
        presenter.storePreference();
    }
}