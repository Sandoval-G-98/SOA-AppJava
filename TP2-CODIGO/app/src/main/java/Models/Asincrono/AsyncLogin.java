package Models.Asincrono;

import android.content.Intent;
import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import Models.Communication;
import Models.Refresh;
import Models.User;
import Views.LoginActivity;
import Views.RegisterOrCreateClandeActivity;

public class AsyncLogin extends AsyncTask<Object, Void, Boolean> {
    private LoginActivity loginActivity;
    private String messageError;
    private User user;
    private JSONObject result = null;
    private String token;

    public AsyncLogin(LoginActivity loginActivity) {
        this.loginActivity = loginActivity;
    }

    @Override
    protected Boolean doInBackground(Object... objects) {
        // Log.d("Debug","inicia execute Login");
        Communication communication = new Communication();
        String response = communication.communicationLogin(
                objects[0].toString(),
                objects[1].toString());

        // Log.d("Debug", response);

        if(response.compareTo(communication.ERROR_MSG) == 0){
            this.messageError = "Error en la conexion.";
            return false;
        }

        try{
            result = new JSONObject(response);

            if(result.get("success").toString().compareTo("true") == 0){
                this.user = new User(objects[0].toString(), result.get("token").toString(), result.get("token_refresh").toString());
                this.token =   result.get("token_refresh").toString();
                // Log.d("Debug", this.user.getEmail());
                // Log.d("Debug", this.user.getToken());
                // Log.d("Debug", this.user.getTokenRefresh());
                return true;
            } else {
                this.messageError = result.get("msg").toString();
                return false;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            this.messageError = "Error inesperado.";
            return false;
        }
    }

    @Override
    protected void onPostExecute(Boolean aBoolean){
        if(aBoolean){
            this.loginActivity.showMessage("Credenciales correctas.");
            this.loginActivity.storePreferencesLogin();
            new Refresh().setDataUserShared(this.loginActivity, this.user);
            new AsyncEvent(this.loginActivity).execute("Login", "Se registra |in de usuario",  this.token);
            Intent registerOrCreateClande = new Intent(this.loginActivity, RegisterOrCreateClandeActivity.class);
            this.loginActivity.startActivity(registerOrCreateClande);
        }else {
            this.loginActivity.showMessage(this.messageError);
        }
        super.onPostExecute(aBoolean);
    }



}
