package com.example.Authentication;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.Session;

import Asincrono.AsyncSendEmail;
import login.LoginActivity;
import utils.BatteryReceiver;

public class MainActivity extends Activity {

    private Session session = null;
    private Context context = null;
    private EditText receiver, textCode;
    private String rec, subject, textMessage, codeValidate;
    private int code = new Random().nextInt(9999) + 1000;
    private TextView batteryLevel;
    private int REQUEST_CODE = 200;
    private BatteryReceiver battery;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        batteryLevel = findViewById(R.id.batteryLevel);
        battery = new BatteryReceiver(batteryLevel);
        registerReceiver(battery, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));

        context = this;

        Button buttonSend = findViewById(R.id.buttonSendEmail);
        receiver = findViewById(R.id.EmailAddress);

        Button verCode = findViewById(R.id.buttonVerificationCode);

        checkPermissions();

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
                    Intent loginActivity = new Intent(MainActivity.this, LoginActivity.class);
                    loginActivity.putExtra("email", rec);
                    startActivity(loginActivity);
                } else {
                    Toast.makeText(getBaseContext(), "Código erroneo", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    protected void onDestroy(){
        unregisterReceiver(battery);
        super.onDestroy();
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

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void checkPermissions() {
        int permissionWriterExternal = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int permissionReadExternal = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        int permissionManageExternal = ContextCompat.checkSelfPermission(this,  android.Manifest.permission.MANAGE_EXTERNAL_STORAGE);

        if(permissionWriterExternal == PackageManager.PERMISSION_DENIED){
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE);
        }

        if(permissionReadExternal == PackageManager.PERMISSION_DENIED){
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE);
        }

        if(permissionManageExternal == PackageManager.PERMISSION_DENIED){
            requestPermissions(new String[]{Manifest.permission.MANAGE_EXTERNAL_STORAGE}, REQUEST_CODE);
        }

    }
}