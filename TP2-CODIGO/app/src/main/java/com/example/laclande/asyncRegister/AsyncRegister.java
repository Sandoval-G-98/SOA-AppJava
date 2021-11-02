package com.example.laclande.asyncRegister;

import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.example.laclande.communication.Communication;
import com.example.laclande.register.RegisterActivity;
import com.example.laclande.user.User;

import org.json.JSONException;
import org.json.JSONObject;

public class AsyncRegister extends AsyncTask<Object, Void, Boolean> {
    private RegisterActivity registerActivity;
    private String message;
    private User user;

    public AsyncRegister(RegisterActivity registerActivity){
        this.registerActivity = registerActivity;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected Boolean doInBackground(Object... objects) {
        Log.d("Debug","inicia execute");
        Communication communication = new Communication();
        String response = communication.communicationRegister(
                objects[0].toString(),
                objects[1].toString(),
                objects[2].toString(),
                objects[3].toString(),
                objects[4].toString(),
                objects[5].toString(),
                objects[6].toString());

        if(response.compareTo(communication.ERROR_MSG) == 0){
            this.message = "Error en la conexion.";
            return false;
        }

        JSONObject result = null;
        try{
            result = new JSONObject(response);

            if(result.get("success").toString().compareTo("true") == 0){
                this.user = new User(objects[3].toString(), result.get("token").toString(), result.get("token_refresh").toString());
                return true;
            } else {
                this.message = result.get("msg").toString();
                return false;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            this.message = "Error inesperado.";
            return false;
        }
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        if(aBoolean){
            this.registerActivity.showMessage("PETICION EXITOSA");
        }else {
            this.registerActivity.showMessage(this.message);
        }
        super.onPostExecute(aBoolean);
    }
}
