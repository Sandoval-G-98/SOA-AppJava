package Asincrono;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import refresh.Refresh;


public class AsyncTimer extends AsyncTask<Object, Void, Boolean> {
    private Context context;
    public static final long maxTimer = 15000;

    public AsyncTimer( Context context){
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Boolean doInBackground(Object... objects) {
        long timeActually = Long.parseLong(objects[0].toString());
        while(System.currentTimeMillis() - timeActually < maxTimer && !isCancelled()){
            //Sleep thread
        }

        if(isCancelled()){
            // Log.d("Debug", "IF doINBACK Cancelamos ejecucion del timer");
            return false;
        }
        // Log.d("Debug", String.valueOf(context));
        // Log.d("Debug", "FIN TIMER");
        return true;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        if(aBoolean){
            new Refresh().askToRefresh(this.context);
            // Log.d("Debug", "Se ejecuto el timer 1 minuto");
        }else {
            // Log.d("Debug", "Cancelamos ejecucion del timer");
        }
        super.onPostExecute(aBoolean);
    }
}
