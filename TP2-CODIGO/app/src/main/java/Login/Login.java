package Login;

import androidx.appcompat.app.AppCompatActivity;

import android.app.SharedElementCallback;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Authentication.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import RegisterCreateClande.RegisterOrCreateClande;
import utils.Battery;

public class Login extends AppCompatActivity {

    private TextView batteryLevel;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        batteryLevel = findViewById(R.id.batteryLevel2);
        Battery bat = new Battery(batteryLevel);
        this.registerReceiver(bat.setBatInfo(), new IntentFilter(Intent.ACTION_BATTERY_CHANGED));

        Button login = findViewById(R.id.buttonLogin);
        Button register = findViewById(R.id.buttonRegister);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText userEmail = findViewById(R.id.TextUserEmail);
                EditText userPassword = findViewById(R.id.TextPassword);
                String email = userEmail.getText().toString();
                String password = userPassword.getText().toString();

                if(email.isEmpty() || !checkEmail(email))
                    Toast.makeText(getBaseContext(), "Email invalido", Toast.LENGTH_LONG).show();
                else if(password.isEmpty())
                    Toast.makeText(getBaseContext(), "La contraseña no puede estar vacia", Toast.LENGTH_LONG).show();
                else {
                    Boolean isCorrectCredentials = checkCredentials(email, password);
                    if(isCorrectCredentials){
                        storePreference();
                        Intent appActivity = new Intent(Login.this, RegisterOrCreateClande.class);
                        appActivity.putExtra("email", email);
                        startActivity(appActivity);
                    }

                }
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getBaseContext(), "A registrarse", Toast.LENGTH_LONG).show();
            }
        });
    }
        private boolean checkCredentials(String userEmail, String password){
            Toast.makeText(getBaseContext(), "Checkeando credenciales", Toast.LENGTH_LONG).show();
            Toast.makeText(getBaseContext(), "Email: " + userEmail, Toast.LENGTH_LONG).show();
            Toast.makeText(getBaseContext(), "Contraseña: " + password, Toast.LENGTH_LONG).show();
            return true;
        }

    public boolean checkEmail(String email){
        // Validar email
        Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        Matcher mather = pattern.matcher(email);
        return mather.find();
    }

    public void storePreference(){
        preferences = getSharedPreferences("metrics", Context.MODE_PRIVATE);
        editor = preferences.edit();
        int info;

        info = preferences.getInt("logingClanders_10_to_18", 0);
        if(info==0){
            info = 1;
        } else {
            info+= info;
        }
        editor.putInt("logingClanders_10_to_18",info);
        editor.commit();
    }


}
