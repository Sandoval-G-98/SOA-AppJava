package CreateClande;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.Authentication.R;

import java.util.Calendar;

import utils.Battery;
import RegisterCreateClande.RegisterOrCreateClande;

public class CreateClande extends AppCompatActivity implements SensorEventListener {

    private TextView batteryLevel;
    private Button buttonCreateClande;
    private EditText edViewProvince, edViewLocality, edViewPostalCode, edViewStreetName, edViewAltitudeStreet, edViewDescription, edViewFromHourClande, edViewToHourClande, edViewDateClande;
    private String province;
    private String locality;
    private String postalCode;
    private String streetName;
    private String altitudeStreet;
    private String description;
    private String email;
    private Context context = this;
    private SensorManager sm;
    private Sensor sensor;
    private SensorEventListener event;
    private Button buttonFromHourClande;
    private Button buttonToHourClande;
    private Button buttonDateClande;
    private String fromHourClande;
    private String toHourClande;
    private String dateHourClande;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    int mov=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_clande);

        email = getIntent().getStringExtra("email");

        batteryLevel = findViewById(R.id.batteryLevel4);
        Battery bat = new Battery(batteryLevel);
        this.registerReceiver(bat.setBatInfo(), new IntentFilter(Intent.ACTION_BATTERY_CHANGED));

        sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        if(sensor == null) {
            Toast.makeText(context, "No posee acelerometro, no puede crear clandes", Toast.LENGTH_LONG).show();
            finish();
        }

        buttonCreateClande = findViewById(R.id.createClande);
        buttonFromHourClande = findViewById(R.id.buttonFromHour);
        buttonToHourClande = findViewById(R.id.buttonToHour);
        buttonDateClande = findViewById(R.id.buttonDateClande);
        edViewFromHourClande = findViewById(R.id.TextFromHourClande);
        edViewToHourClande = findViewById(R.id.TextToHourClande);
        edViewDateClande = findViewById(R.id.TextDateClande);


        buttonCreateClande.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkFields()){
                    Toast.makeText(context, "Gire el telefono de izquierda a derecha para confirmar", Toast.LENGTH_LONG).show();
                    sm.registerListener( (SensorEventListener) context, sensor, SensorManager.SENSOR_DELAY_NORMAL);
                }
            }
        });

        buttonDateClande.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dpd = new DatePickerDialog(CreateClande.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String date = dayOfMonth + "/" + month + "/" + year;
                        edViewDateClande.setText(date);
                    }
                }, 2021, 10, 26);
                dpd.show();
            }
        });

        buttonFromHourClande.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog tmd = new TimePickerDialog(CreateClande.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String hourFromClande = hourOfDay + " : " + minute;
                        edViewFromHourClande.setText(hourFromClande);
                    }
                }, 00, 00, true);
                tmd.show();
            }
        });

        buttonToHourClande.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog tmd = new TimePickerDialog(CreateClande.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String hourToClande = hourOfDay + " : " + minute;
                        edViewToHourClande.setText(hourToClande);
                    }
                }, 06, 00, true);
                tmd.show();
            }
        });
    }


    private boolean checkFields(){
        boolean fieldsCorrects = true;

        edViewProvince = findViewById(R.id.provinceClande);
        edViewLocality = findViewById(R.id.localityClande);
        edViewPostalCode = findViewById(R.id.postalCodeClande);
        edViewStreetName = findViewById(R.id.streetName);
        edViewAltitudeStreet = findViewById(R.id.altitudeStreetClande);
        edViewFromHourClande = findViewById(R.id.TextFromHourClande);
        edViewToHourClande = findViewById(R.id.TextToHourClande);
        edViewDateClande = findViewById(R.id.TextDateClande);
        edViewDescription = findViewById(R.id.descriptionClande);

        province = edViewProvince.getText().toString();
        locality = edViewLocality.getText().toString();
        postalCode = edViewPostalCode.getText().toString();
        streetName = edViewStreetName.getText().toString();
        altitudeStreet = edViewAltitudeStreet.getText().toString();
        fromHourClande = edViewFromHourClande.getText().toString();
        toHourClande = edViewToHourClande.getText().toString();
        dateHourClande = edViewDateClande.getText().toString();
        description = edViewDescription.getText().toString();


        if(province.isEmpty() || locality.isEmpty() || postalCode.isEmpty() || streetName.isEmpty()
                || altitudeStreet.isEmpty() || fromHourClande.isEmpty() || toHourClande.isEmpty() || dateHourClande.isEmpty()){
            Toast.makeText(this, "No pueden haber campos obligatorios vacíos", Toast.LENGTH_LONG).show();
            fieldsCorrects = false;
        }

        return fieldsCorrects;
    }

    @Override
    protected void onPause() {
        super.onPause();
        sm.unregisterListener(CreateClande.this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        System.out.println("Valor de x: " + event.values[0]);

        if(event.values[0] > 5 && mov == 0) {
            mov++;
        } else if(event.values[0] < -5 && mov == 1) {
            mov++;
        }

        if(mov == 2) {
            mov=0;
            Toast.makeText(context, "Se creó la clande correctamente", Toast.LENGTH_LONG).show();
            storePreference();
            Intent createOrJoinClandeActivity = new Intent(CreateClande.this, RegisterOrCreateClande.class);
            createOrJoinClandeActivity.putExtra("email",email);
            startActivity(createOrJoinClandeActivity);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    public void storePreference() {
        preferences = getSharedPreferences("metrics", Context.MODE_PRIVATE);
        editor = preferences.edit();
        int info;

        info = preferences.getInt("registerClande_10_to_18", 0);
        if(info==0){
            info = 1;
        } else {
            info+= info;
        }

        editor.putInt("registerClande_10_to_18",info);
        editor.commit();
    }
}