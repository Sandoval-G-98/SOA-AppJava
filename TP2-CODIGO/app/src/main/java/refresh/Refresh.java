package refresh;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import Asincrono.AsyncRefresh;
import Asincrono.AsyncTimer;
import login.LoginActivity;

public class Refresh {
    private AsyncTimer asyncTimer;
    private SharedPreferences dataUser;
    private long timeActually;

    public void askToRefresh(Context context){
        dataUser = context.getSharedPreferences("SharedUser", Context.MODE_PRIVATE);
        // Log.d("Debug", "MOSTRAR DATAUSER ASK TO REFRESH:::");
        // Log.d("Debug", dataUser.getString("email",""));
        // Log.d("Debug", dataUser.getString("token",""));

        new AlertDialog.Builder(context)
                .setTitle("Token caducado.")
                .setMessage("¿Desea seguir navegando en la app?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        // Log.d("Debug", dataUser.getString("email",""));
                        new AsyncRefresh(context).execute(dataUser.getString("tokenRefresh", ""));
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        showMessage(context,"Sesion terminada.");
                        backToLogin(context);
                    }
                }).show();
    }

    public void backToLogin(Context context){
        asyncTimer.cancel(true);
        Activity activity = (Activity) context;
        activity.finish();
        Intent login = new Intent(context, LoginActivity.class);
        activity.startActivity(login);
    }

    public void refreshTokenUser(String token, String tokenRefresh, Context context){
        dataUser = context.getSharedPreferences("SharedUser", Context.MODE_PRIVATE);
        SharedPreferences.Editor myEdit = dataUser.edit();
        myEdit.putString("token", token);
        myEdit.putString("tokenRefresh",tokenRefresh);
        myEdit.putLong("timeActually", System.currentTimeMillis());
        myEdit.apply();

        //Lanzamos el timer nuevamente
        this.asyncTimer = new AsyncTimer(context);
        this.asyncTimer.execute(dataUser.getLong("timeActually",0));
    }

    public void showMessage(Context context,String message){
        Toast.makeText(context,message,Toast.LENGTH_LONG).show();
    }
}
