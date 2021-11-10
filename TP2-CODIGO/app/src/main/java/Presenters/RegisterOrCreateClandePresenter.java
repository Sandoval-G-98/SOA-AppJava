package Presenters;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import Views.RegisterOrCreateClandeActivity;

public class RegisterOrCreateClandePresenter {

    private RegisterOrCreateClandeActivity activity;
    private SharedPreferences dataUser;
    private SharedPreferences metricsLogin;
    private SharedPreferences metricsRegister;

    public RegisterOrCreateClandePresenter(RegisterOrCreateClandeActivity activity) {
        this.activity = activity;
    }

    public void setPreferencesUser(){
        Log.d("Debug", "setPreference context::" + this.activity.toString());
        dataUser = this.activity.getSharedPreferences("SharedUser", Context.MODE_PRIVATE);
        SharedPreferences.Editor myEdit = dataUser.edit();
        myEdit.putLong("timeActually", System.currentTimeMillis());
        myEdit.apply();
    }

    public int readPreferencesLogin(){
        metricsLogin = this.activity.getSharedPreferences("SharedMetricsLogins", Context.MODE_PRIVATE);
        int logins = metricsLogin.getInt("logins",0);
        return logins;
    }

    public int readPreferencesRegisters(){
        metricsRegister = this.activity.getSharedPreferences("SharedMetricsRegisters", Context.MODE_PRIVATE);
        int registers = metricsRegister.getInt("registers",0);
        return registers;
    }

}
