package com.example.laclande;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import java.util.Properties;
import java.util.Random;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    Session session = null;
    ProgressDialog pdialog = null;
    Context context = null;
    EditText reciep, textCode;
    String rec, subject, textMessage, codeValidate;
    int code = new Random().nextInt(9999) + 1000;
    TextView batteryLevel;

    private BroadcastReceiver batInfo = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent){
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL,0);
            String batteryMessage = "Bateria: " + level + "%";
            batteryLevel.setText(batteryMessage);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        batteryLevel = findViewById(R.id.batteryLevel1);
        this.registerReceiver(this.batInfo, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));

        context = this;

        Button login = findViewById(R.id.buttonLogin);
        reciep = findViewById(R.id.editTextTextEmailAddress);

        Button verCode = findViewById(R.id.buttonVerificationCode);

        login.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                rec = reciep.getText().toString();
                subject = getString(R.string.subject);
                textMessage = getString(R.string.msgCodeVerification).concat(" ").concat(Integer.toString(code));

                Properties props = new Properties();
                props.put("mail.smtp.host", "smtp.gmail.com");
                props.put("mail.smtp.socketFactory.port", "465");
                props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
                props.put("mail.smtp.auth", "true");
                props.put("mail.smtp.port", "465");

                session = Session.getDefaultInstance(props, new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication("laclandeparty@gmail.com", "Clande1234@\n");
                    }
                });

                pdialog = ProgressDialog.show(context, "", "Enviando email...", true);

                RetreiveFeedTask task = new RetreiveFeedTask();
                task.execute();
            }
        });

        verCode.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                textCode = findViewById(R.id.codeToVerificate);
                codeValidate = textCode.getText().toString();
                if(Integer.parseInt(codeValidate) == code) {
                    Intent loginActivity = new Intent(MainActivity.this, Login.class);
                    startActivity(loginActivity);
                } else {
                    Toast.makeText(getBaseContext(), "Código erroneo", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    /*@Override
    public void onClick(View v) {
        rec = reciep.getText().toString();
        subject = getString(R.string.subject);
        textMessage = getString(R.string.msgCodeVerification).concat(" ").concat(Integer.toString(code));

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        session = Session.getDefaultInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("laclandeparty@gmail.com", "Clande1234@\n");
            }
        });

        pdialog = ProgressDialog.show(context, "", "Sending Mail...", true);

        RetreiveFeedTask task = new RetreiveFeedTask();
        task.execute();

    }*/

    class RetreiveFeedTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            try{
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress("testfrom354@gmail.com"));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(rec));
                message.setSubject(subject);
                message.setContent(textMessage, "text/html; charset=utf-8");
                Transport.send(message);
            } catch(MessagingException e) {
                e.printStackTrace();
            } catch(Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            pdialog.dismiss();
            reciep.setText("");
            Toast.makeText(getApplicationContext(), "Email enviado", Toast.LENGTH_LONG).show();
        }
    }
}