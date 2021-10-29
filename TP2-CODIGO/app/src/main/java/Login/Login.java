package Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Authentication.R;

import RegisterCreateClande.RegisterOrCreateClande;
import Battery.Battery;

public class Login extends AppCompatActivity {

    TextView batteryLevel;

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

                EditText userName = findViewById(R.id.TextUser);
                EditText userPassword = findViewById(R.id.TextPassword);
                String user = userName.getText().toString();
                String password = userPassword.getText().toString();

                if(user.isEmpty())
                    Toast.makeText(getBaseContext(), "El nombre de usuario no puede estar vacio", Toast.LENGTH_LONG).show();
                else if(password.isEmpty())
                    Toast.makeText(getBaseContext(), "La contraseña no puede estar vacia", Toast.LENGTH_LONG).show();
                else {
                    Boolean isCorrectCrdentials = checkCredentials("Gabriel", "Sandoval");
                    if(isCorrectCrdentials){
                        Intent appActivity = new Intent(Login.this, RegisterOrCreateClande.class);
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
        private boolean checkCredentials(String userName, String password){
            Toast.makeText(getBaseContext(), "Checkeando credenciales", Toast.LENGTH_LONG).show();
            Toast.makeText(getBaseContext(), "Usuario: " + userName, Toast.LENGTH_LONG).show();
            Toast.makeText(getBaseContext(), "Contraseña: " + password, Toast.LENGTH_LONG).show();
            return true;
        }

}
