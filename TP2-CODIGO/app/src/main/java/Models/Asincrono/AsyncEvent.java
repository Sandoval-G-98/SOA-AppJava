package Models.Asincrono;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import Models.Communication;
import Models.Refresh;

public class AsyncEvent extends AsyncTask<Object, Void, Boolean> {
    private Context context;
    private String message;
    private String messageEvent;

    public AsyncEvent(Context context){
        this.context = context;
    }

    @Override
    protected Boolean doInBackground(Object... objects) {
        Communication communication = new Communication();
                                                            //Type event, description event, tokenRefresh que necesita el event
        String response = communication.communicationEvent(objects[0].toString(), objects[1].toString(),objects[2].toString());
        this.messageEvent = objects[0].toString();

        if(response.compareTo(communication.ERROR_MSG) == 0){
            this.message= "Error en la conexion.";
            return false;
        }

        JSONObject result = null;
        try {
            result = new JSONObject(response);

            if(result.get("success").toString().compareTo("true") != 0){
                this.message = result.get("msg").toString();
                return false;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            this.message = "Error inesperado.";
            return false;
        }

        return true;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        if(aBoolean){
            new Refresh().showMessage(this.context,"Evento registrado en " + this.messageEvent);
            Log.d("Debug", "evento: " + this.messageEvent);
        }else {
            new Refresh().showMessage(this.context,"No se pudo registrar el evento correctamente!.");
        }
        super.onPostExecute(aBoolean);
    }

}

