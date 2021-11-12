package Presenters;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;
import java.util.Calendar;

import Models.db.AdminSQLiteOperHelper;
import Views.CreateClandeActivity;

public class CreateClandePresenter {

    private CreateClandeActivity activity;
    private SharedPreferences dataUser;

    public CreateClandePresenter(CreateClandeActivity activity){
        this.activity = activity;
    }

    public void storePreference() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        final int LIMIT_SUP_HOUR = 18;
        final int LIMIT_INF_HOUR = 10;

        if( LIMIT_INF_HOUR <= hour && hour <= LIMIT_SUP_HOUR ) {
            SharedPreferences preferences = this.activity.getSharedPreferences("SharedMetricsRegisters", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();

            int info = preferences.getInt("registers", 0);
            info = info + 1;

            editor.putInt("registers",info);
            editor.commit();
        }
    }

    public boolean checkFields(String province, String locality, String  postalCode, String streetName, String altitudeStreet, String fromHourClande,
                                String toHourClande,String dateClande ){

        if(province.isEmpty() || locality.isEmpty() || postalCode.isEmpty() || streetName.isEmpty()
                || altitudeStreet.isEmpty() || fromHourClande.isEmpty() || toHourClande.isEmpty() || dateClande.isEmpty()){
            Toast.makeText(this.activity, "No pueden haber campos obligatorios vacíos", Toast.LENGTH_LONG).show();
            return false;
        }

        AdminSQLiteOperHelper db = new AdminSQLiteOperHelper(this.activity);
        dataUser = this.activity.getSharedPreferences("SharedUser", Context.MODE_PRIVATE);
        if(db.isInMyClandes(dataUser.getString("email", ""), fromHourClande, toHourClande, dateClande)){
            Toast.makeText(this.activity, "Ya creó una clande con la misma fecha y hora", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

}
