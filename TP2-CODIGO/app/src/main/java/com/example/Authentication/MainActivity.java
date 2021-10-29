package com.example.Authentication;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.Session;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import Asincrono.AsyncSendEmail;
import Battery.Battery;
import Login.Login;

public class MainActivity extends Activity {

    Session session = null;
    Context context = null;
    EditText receiver, textCode;
    String rec, subject, textMessage, codeValidate;
    int code = new Random().nextInt(9999) + 1000;
    TextView batteryLevel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        batteryLevel = findViewById(R.id.batteryLevel);
        Battery bat = new Battery(batteryLevel);
        this.registerReceiver(bat.setBatInfo(), new IntentFilter(Intent.ACTION_BATTERY_CHANGED));

        context = this;

        Button buttonSend = findViewById(R.id.buttonSendEmail);
        receiver = findViewById(R.id.EmailAddress);

        Button verCode = findViewById(R.id.buttonVerificationCode);

        buttonSend.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                rec = receiver.getText().toString();
                subject = getString(R.string.subject);
                textMessage = getString(R.string.msgCodeVerification).concat(" ").concat(Integer.toString(code));

                if(checkEmail(rec)){
                    AsyncSendEmail task = new AsyncSendEmail(session, textMessage, subject, rec, receiver, context);
                    task.execute();
                } else {
                    Toast.makeText(context, "Correo inválido", Toast.LENGTH_LONG).show();
                }

            }
        });

        verCode.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                textCode = findViewById(R.id.codeToVerificate);
                codeValidate = textCode.getText().toString();
                if( checkCode(codeValidate) ) {
                    code = new Random().nextInt(9999) + 1000;
                    Intent loginActivity = new Intent(MainActivity.this, Login.class);
                    startActivity(loginActivity);
                } else {
                    Toast.makeText(getBaseContext(), "Código erroneo", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public boolean checkEmail(String email){
        // Validar email
        Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        Matcher mather = pattern.matcher(email);
        return mather.find();
    }

    public boolean checkCode(String codeValidate){
        // Validar codigo
        return (!codeValidate.isEmpty() && codeValidate.matches("[0-9]*") && Integer.parseInt(codeValidate) == code);
    }
}