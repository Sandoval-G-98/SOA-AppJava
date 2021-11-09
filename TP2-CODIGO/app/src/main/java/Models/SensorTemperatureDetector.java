package Models;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.widget.TextView;

import com.example.Authentication.R;

import Views.RegisterOrCreateClandeActivity;

public class SensorTemperatureDetector implements SensorEventListener {

    private RegisterOrCreateClandeActivity activity;
    private TextView ambientTemperature;
    private TextView temperatureText;

    public SensorTemperatureDetector(RegisterOrCreateClandeActivity activity, TextView ambientTemperature, TextView temperatureText){
        this.activity = activity;
        this.ambientTemperature = ambientTemperature;
        this.temperatureText = temperatureText;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        String tabuenoString = "La temperatura esta perfecto para una clande!!";
        String tamaloString = "La temperatura esta mala pero se puede escabiar!!";

        float ambient_temperature = event.values[0];

        ambientTemperature.setText(ambient_temperature + this.activity.getResources().getString(R.string.celsius));
        temperatureText.setText((ambient_temperature > 23 ? tabuenoString : tamaloString));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
