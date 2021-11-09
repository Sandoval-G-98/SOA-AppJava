package Presenters;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.SensorManager;
import android.os.Build;
import android.widget.EditText;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import Views.MainActivity;
import com.example.Authentication.R;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.mail.Session;
import Models.Asincrono.AsyncSendEmail;
import Views.LoginActivity;

public class MainPresenter  {

    private MainActivity activity;
    private SensorManager sensor;
    private int REQUEST_CODE = 200;
    private int code = new Random().nextInt(9999) + 1000;

    public MainPresenter (MainActivity activity){
        this.activity = activity;
    }


    public boolean checkEmail(String email){
        // Validar email
        Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        Matcher mather = pattern.matcher(email);
        return mather.find();
    }

    public void sendEmail(Session session, String subject, String rec, EditText reciep, Context context) {
        String message = this.activity.getString(R.string.msgCodeVerification).concat(" ").concat(Integer.toString(code));
        AsyncSendEmail task = new AsyncSendEmail(session, message, subject, rec, reciep, context);
        task.execute();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void checkPermissions() {
        int permissionWriterExternal = ContextCompat.checkSelfPermission(this.activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int permissionReadExternal = ContextCompat.checkSelfPermission(this.activity, Manifest.permission.READ_EXTERNAL_STORAGE);
        int permissionManageExternal = ContextCompat.checkSelfPermission(this.activity,  android.Manifest.permission.MANAGE_EXTERNAL_STORAGE);

        if(permissionWriterExternal == PackageManager.PERMISSION_DENIED){
            this.activity.requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE);
        }

        if(permissionReadExternal == PackageManager.PERMISSION_DENIED){
            this.activity.requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE);
        }

        if(permissionManageExternal == PackageManager.PERMISSION_DENIED){
            this.activity.requestPermissions(new String[]{Manifest.permission.MANAGE_EXTERNAL_STORAGE}, REQUEST_CODE);
        }

    }

    public boolean checkCode(String codeValidate){
        // Validar codigo
        return (!codeValidate.isEmpty() && codeValidate.matches("[0-9]*") && Integer.parseInt(codeValidate) == code);
    }

    public void changeActivity(String email){
        code = new Random().nextInt(9999) + 1000;
        Intent loginActivity = new Intent(this.activity, LoginActivity.class);
        loginActivity.putExtra("email", email);
        this.activity.startActivity(loginActivity);
    }
}
