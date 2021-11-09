package Models.Asincrono;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import Models.Communication;
import Models.Refresh;

public class AsyncRefresh extends AsyncTask<Object, Void, Boolean> {
    private Context context;
    private String message;
    private String token;
    private String tokenRefresh;

    public AsyncRefresh( Context context){
        this.context = context;
    }

    @Override
    protected Boolean doInBackground(Object... objects) {
        Communication communication = new Communication();

        String response = communication.communicationRefreshToken(objects[0].toString());

        if(response.compareTo(communication.ERROR_MSG) == 0){
            this.message = "Error en la conexion RefreshToken.";
            return false;
        }

        JSONObject result = null;
        try {
            result = new JSONObject(response);
            Log.d("Debug", "Json: " + result.toString());
            if(result.get("success").toString().compareTo("true") == 0){
                Log.d("Debug", "Entre al success");
                this.token = result.get("token").toString();
                this.tokenRefresh = result.get("token_refresh").toString();
                return true;
            }else {
                this.message = result.get("msg").toString();
                return false;
            }

        } catch (JSONException e) {
            e.printStackTrace();
            this.message = "Error inesperado.";
        }

        return false;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);

        if(aBoolean){
            new Refresh().showMessage(this.context,"Token refrescado.");
            new Refresh().refreshTokenUser(token, tokenRefresh, context);
        }else {
            new Refresh().showMessage(context,"Error al refrescar token. Loguee devuelta.");
            new Refresh().backToLogin(this.context);
            //Debo resetear todo y volver al login
            //Intent login = new Intent(this.registerOrCreateClande, LoginActivity.class);
            //this.registerOrCreateClande.startActivity(login);
        }
    }
}
