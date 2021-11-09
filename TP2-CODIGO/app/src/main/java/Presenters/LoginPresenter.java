package Presenters;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Patterns;
import java.util.Calendar;
import java.util.regex.Pattern;
import Models.Asincrono.AsyncLogin;
import Views.LoginActivity;

public class LoginPresenter  {

    private LoginActivity activity;

    public LoginPresenter (LoginActivity activity){
        this.activity = activity;
    }

    public boolean handleLogin(String email, String password) {

        if(validateForm(email, password)){
            new AsyncLogin(this.activity).execute(email,password);
            return true;
        }
        return false;
    }

    public boolean validateForm(String email, String password) {
        boolean isValid = true;

        Pattern pattern = Patterns.EMAIL_ADDRESS;

        if(!pattern.matcher(email).matches()){
            isValid = false;
        }
        if(password.length() < 8){
            isValid = false;
        }
        return isValid;
    }

    public void storePreference() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        final int LIMIT_SUP_HOUR = 18;
        final int LIMIT_INF_HOUR = 10;

        if( LIMIT_INF_HOUR <= hour && hour <= LIMIT_SUP_HOUR ) {
            SharedPreferences preferences = this.activity.getSharedPreferences("SharedMetricsLogins", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();

            int info = preferences.getInt("logins", 0);
            info = info + 1;

            editor.putInt("logins",info);
            editor.commit();
        }
    }

}
