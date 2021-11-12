package Models;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import Models.Asincrono.AsyncEvent;
import Models.db.AdminSQLiteOperHelper;
import Views.CreateClandeActivity;
import Views.RegisterOrCreateClandeActivity;

public class SensorAcelerometerDetector implements SensorEventListener {

    private CreateClandeActivity activity;
    private int mov = 0;
    private EditText edViewProvince, edViewLocality, edViewPostalCode, edViewStreetName, edViewAltitudeStreet, edViewDescription, edViewFromHourClande, edViewToHourClande, edViewDateClande;
    private SharedPreferences dataUser;

    public SensorAcelerometerDetector(CreateClandeActivity activity, EditText edViewProvince, EditText edViewLocality, EditText edViewPostalCode, EditText edViewStreetName, EditText edViewAltitudeStreet, EditText edViewFromHourClande,
                                      EditText edViewToHourClande,EditText edViewDateClande, EditText edViewDescription){
        this.activity = activity;
        this.edViewPostalCode = edViewPostalCode;
        this.edViewProvince = edViewProvince;
        this.edViewLocality = edViewLocality;
        this.edViewAltitudeStreet = edViewAltitudeStreet;
        this.edViewStreetName = edViewStreetName;
        this.edViewFromHourClande = edViewFromHourClande;
        this.edViewToHourClande =  edViewToHourClande;
        this.edViewDateClande = edViewDateClande;
        this.edViewDescription = edViewDescription;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {



        if(event.values[0] > 5 && mov == 0) {
            mov++;
        } else if(event.values[0] < -5 && mov == 1) {
            mov++;
        }

        if(mov == 2) {
            mov=-1;
            dataUser = this.activity.getSharedPreferences("SharedUser", Context.MODE_PRIVATE);
            new AsyncEvent(this.activity).execute("Actividad del sensor", "Se registra cambios en el acelerometro", dataUser.getString("tokenRefresh", ""));
            this.activity.storePreference();
            String province = edViewProvince.getText().toString();
            String locality = edViewLocality.getText().toString();
            String postalCode = edViewPostalCode.getText().toString();
            String streetName = edViewStreetName.getText().toString();
            String altitudeStreet = edViewAltitudeStreet.getText().toString();
            String fromHourClande = edViewFromHourClande.getText().toString();
            String toHourClande = edViewToHourClande.getText().toString();
            String dateClande = edViewDateClande.getText().toString();
            String description = edViewDescription.getText().toString();
            AdminSQLiteOperHelper db = new AdminSQLiteOperHelper(this.activity);
            db.addInTableAllClandes(dataUser.getString("email", ""),province,locality,postalCode,streetName,altitudeStreet,description,fromHourClande,toHourClande,dateClande);
            //Log.d("Debug", "Guarde la clande en todas");
            AdminSQLiteOperHelper db2 = new AdminSQLiteOperHelper(this.activity);
            db2.addInMyTableClandes(dataUser.getString("email", ""),province,locality,postalCode,streetName,altitudeStreet,description,fromHourClande,toHourClande,dateClande);
            //Log.d("Debug", "Guarde la clande en las mias");
            Toast.makeText(this.activity, "Se creó la clande correctamente", Toast.LENGTH_LONG).show();
            Intent createOrJoinClandeActivity = new Intent(this.activity, RegisterOrCreateClandeActivity.class);
            createOrJoinClandeActivity.putExtra("email",dataUser.getString("email", ""));
            createOrJoinClandeActivity.putExtra("token",dataUser.getString("token", ""));
            createOrJoinClandeActivity.putExtra("tokenRefresh",dataUser.getString("tokenRefresh", ""));
            this.activity.startActivity(createOrJoinClandeActivity);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
