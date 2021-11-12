package Views;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.RequiresApi;

import com.example.Authentication.R;

import javax.mail.Session;
import Presenters.MainPresenter;
import Models.BatteryReceiver;

public class MainActivity extends Activity {

    private Session session = null;
    private Context context = null;
    private EditText receiver, textCode;
    private String rec, subject, codeValidate;
    private TextView batteryLevel;
    private BatteryReceiver battery;
    private MainPresenter presenter;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        batteryLevel = findViewById(R.id.batteryLevel);
        battery = new BatteryReceiver(batteryLevel);
        registerReceiver(battery, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));

        presenter = new MainPresenter(this);

        context = this;

        Button buttonSend = findViewById(R.id.buttonSendEmail);
        receiver = findViewById(R.id.EmailAddress);

        Button verCode = findViewById(R.id.buttonVerificationCode);

        presenter.checkPermissions();

        buttonSend.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                rec = receiver.getText().toString();
                subject = getString(R.string.subject);
                if(presenter.checkEmail(rec)){
                    presenter.sendEmail(session, subject, rec, receiver, context);
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
                if( presenter.checkCode(codeValidate) ) {
                    presenter.changeActivity(rec);
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
}