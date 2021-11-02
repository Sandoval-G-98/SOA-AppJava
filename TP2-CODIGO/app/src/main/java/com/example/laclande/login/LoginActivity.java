package com.example.laclande.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.laclande.R;
import com.example.laclande.register.RegisterActivity;

import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        //Funcionalidad de Atras, vuelve a Login
        TextView linkToRegister = findViewById(R.id.toRegister);
        linkToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent register = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(register);
            }
        });


        //Funcionalidad de submit, le pega a la api de Login
        TextView submitLogin = findViewById(R.id.buttonLogin);
        submitLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText inputEmail =findViewById(R.id.loginEmail);
                EditText inputPassword = findViewById(R.id.loginPassword);
                String valueEmail = String.valueOf(inputEmail.getText());
                String valuePassword = String.valueOf(inputPassword.getText());

                Pattern pattern = Patterns.EMAIL_ADDRESS;
                pattern.matcher(valueEmail).matches();


                //Toast.makeText(LoginActivity.this, valuePassword, Toast.LENGTH_SHORT).show();
                //Toast.makeText(LoginActivity.this, valuePassword, Toast.LENGTH_SHORT).show();
            }
        });
    }
    //Toast.makeText(LoginActivity.this, "Ir a registrarse", Toast.LENGTH_SHORT).show();
}