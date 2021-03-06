package Models.Asincrono;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;

import androidx.annotation.RequiresApi;
import Models.Communication;
import Models.User;
import org.json.JSONException;
import org.json.JSONObject;
import Views.LoginActivity;
import Views.RegisterActivity;

public class AsyncRegister extends AsyncTask<Object, Void, Boolean> {
    private RegisterActivity registerActivity;
    private String message;
    private User user;
    private SharedPreferences dataUser;

    public AsyncRegister(RegisterActivity registerActivity){
        this.registerActivity = registerActivity;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected Boolean doInBackground(Object... objects) {
        // Log.d("Debug","inicia execute");
        Communication communication = new Communication();
        String response = communication.communicationRegister(
                objects[0].toString(),
                objects[1].toString(),
                objects[2].toString(),
                objects[3].toString(),
                objects[4].toString(),
                objects[5].toString(),
                objects[6].toString());


        // Log.d("Debug", response);

        if(response.compareTo(communication.ERROR_MSG) == 0){
            this.message = "Error en la conexion.";
            return false;
        }

        JSONObject result = null;
        try{
            result = new JSONObject(response);

            if(result.get("success").toString().compareTo("true") == 0){
                this.user = new User(objects[3].toString(), result.get("token").toString(), result.get("token_refresh").toString());
                // Log.d("Debug", this.user.getEmail());
                // Log.d("Debug", this.user.getToken());
                // Log.d("Debug", this.user.getTokenRefresh());
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
            this.registerActivity.showMessage("Registro exitoso.");
            dataUser = this.registerActivity.getSharedPreferences("SharedUser", Context.MODE_PRIVATE);
            new AsyncEvent(this.registerActivity).execute("Actividad Background", "Se registra evento de registro de usuario background", dataUser.getString("tokenRefresh", ""));
            Intent Login = new Intent(this.registerActivity, LoginActivity.class);
            registerActivity.startActivity(Login);
        }else {
            this.registerActivity.showMessage(this.message);
        }
        super.onPostExecute(aBoolean);
    }
}
