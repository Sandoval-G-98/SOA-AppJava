package com.example.laclande.register;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.laclande.R;
import com.example.laclande.asyncRegister.AsyncRegister;
import com.example.laclande.login.LoginActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {
    private EditText inputEmail;
    private EditText inputName;
    private EditText inputLastName;
    private EditText inputPassword;
    private EditText inputDNI;
    private Spinner spinnerCommission;
    private EditText inputGroup;
    private String email;
    private String name;
    private String lastname;
    private String password;
    private String dni;
    private String commission;
    private String group;

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

        TextView buttonToBack = findViewById(R.id.toBack);
        buttonToBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent login = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(login);
            }
        });
    }

    public void handleRegister(View view) {
        if(validateForm()){
            Log.d("Debug", "TRUE");
            new AsyncRegister(RegisterActivity.this).execute(this.name,
                    this.lastname,
                    this.dni,
                    this.email,
                    this.password,
                    this.commission,
                    this.group);
            //Llamado a la api
        }
    }

    public boolean validateForm() {
        this.email = inputEmail.getText().toString();
        this.name = inputName.getText().toString();
        this.lastname = inputLastName.getText().toString();
        this.password = inputPassword.getText().toString();
        this.dni = inputDNI.getText().toString();
        this.commission = spinnerCommission.getSelectedItem().toString();
        this.group = inputGroup.getText().toString();
        boolean isValid = true;

        Pattern pattern = Patterns.EMAIL_ADDRESS;

        Matcher matcherDNI = Pattern.compile("^\\d+$").matcher(this.dni);
        Matcher matcherGroup = Pattern.compile("^\\d+$").matcher(this.group);

        if(!pattern.matcher(this.email).matches()){
            inputEmail.setError("Formato de email invalido.");
            isValid = false;
        }

        if(this.name.isEmpty()){
            inputName.setError("El campo Name es requerido.");
            isValid = false;
        }

        if(this.lastname.isEmpty()){
            inputLastName.setError("El campo LastName es requerido.");
            isValid = false;
        }

        if(this.password.length() < 8){
            inputPassword.setError("La password debe contener al menos 8 caracteres.");
            isValid = false;
        }

        if(!matcherDNI.find()){
            inputDNI.setError("El campo DNI debe ser numerico.");
            isValid = false;
        }

        if(!matcherGroup.find()){
            inputGroup.setError("El campo Group debe ser numerico.");
            isValid = false;
        }

        return isValid;
    }

    public void showMessage(String message){
        Toast.makeText(this,message,Toast.LENGTH_LONG).show();
    }
}