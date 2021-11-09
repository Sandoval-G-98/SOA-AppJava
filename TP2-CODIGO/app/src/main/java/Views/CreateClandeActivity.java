package Views;
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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.Authentication.R;

import java.util.Calendar;

import Models.Asincrono.AsyncTimer;
import Models.BatteryReceiver;
import Models.SensorAcelerometerDetector;
import Models.db.AdminSQLiteOperHelper;
import Presenters.CreateClandePresenter;

public class CreateClandeActivity extends AppCompatActivity  {

    private TextView batteryLevel;
    private BatteryReceiver battery;
    private Button buttonCreateClande;
    private EditText edViewProvince, edViewLocality, edViewPostalCode, edViewStreetName, edViewAltitudeStreet, edViewDescription, edViewFromHourClande, edViewToHourClande, edViewDateClande;
    private String province;
    private String locality;
    private String postalCode;
    private String streetName;
    private String altitudeStreet;
    private String description;
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
    private CreateClandePresenter presenter;

    private SharedPreferences dataUser;
    private SensorAcelerometerDetector acelerometerDetector;

    private AsyncTimer asyncTimer;
    int mov=0;

    private AdminSQLiteOperHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_clande);
        db = new AdminSQLiteOperHelper(this);

        dataUser = this.getSharedPreferences("SharedUser", Context.MODE_PRIVATE);

        batteryLevel = findViewById(R.id.batteryLevel5);
        battery = new BatteryReceiver(batteryLevel);
        registerReceiver(battery, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));

        sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        if(sensor == null) {
            Toast.makeText(context, "No posee acelerometro, no puede crear clandes", Toast.LENGTH_LONG).show();
            finish();
        }

        edViewProvince = findViewById(R.id.provinceClande);
        edViewLocality = findViewById(R.id.localityClande);
        edViewPostalCode = findViewById(R.id.postalCodeClande);
        edViewStreetName = findViewById(R.id.streetName);
        edViewAltitudeStreet = findViewById(R.id.altitudeStreetClande);
        edViewFromHourClande = findViewById(R.id.TextFromHourClande);
        edViewToHourClande = findViewById(R.id.TextToHourClande);
        edViewDateClande = findViewById(R.id.TextDateClande);
        edViewDescription = findViewById(R.id.descriptionClande);

        presenter = new CreateClandePresenter(this);
        acelerometerDetector = new SensorAcelerometerDetector(this, edViewProvince, edViewLocality, edViewPostalCode, edViewStreetName, edViewAltitudeStreet, edViewFromHourClande,
                edViewToHourClande, edViewDateClande, edViewDescription);

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
                province = edViewProvince.getText().toString();
                locality = edViewLocality.getText().toString();
                postalCode = edViewPostalCode.getText().toString();
                streetName = edViewStreetName.getText().toString();
                altitudeStreet = edViewAltitudeStreet.getText().toString();
                fromHourClande = edViewFromHourClande.getText().toString();
                toHourClande = edViewToHourClande.getText().toString();
                dateHourClande = edViewDateClande.getText().toString();
                description = edViewDescription.getText().toString();
                if(presenter.checkFields(province, locality, postalCode, streetName, altitudeStreet, fromHourClande, toHourClande, dateHourClande)){
                    Toast.makeText(context, "Gire el telefono de izquierda a derecha para confirmar", Toast.LENGTH_LONG).show();
                    sm.registerListener( acelerometerDetector, sensor, SensorManager.SENSOR_DELAY_NORMAL);
                }
            }
        });

        buttonDateClande.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dpd = new DatePickerDialog(CreateClandeActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String date = dayOfMonth + "/" + (month+1) + "/" + year;
                        edViewDateClande.setText(date);
                    }
                }, 2021, 10, 26);
                dpd.show();
            }
        });

        buttonFromHourClande.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog tmd = new TimePickerDialog(CreateClandeActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String hourFromClande = hourOfDay + ":" + minute;
                        edViewFromHourClande.setText(hourFromClande);
                    }
                }, 00, 00, true);
                tmd.show();
            }
        });

        buttonToHourClande.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog tmd = new TimePickerDialog(CreateClandeActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String hourToClande = hourOfDay + ":" + minute;
                        edViewToHourClande.setText(hourToClande);
                    }
                }, 06, 00, true);
                tmd.show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        dataUser = this.getSharedPreferences("SharedUser", Context.MODE_PRIVATE);
        this.asyncTimer = new AsyncTimer( this);
        this.asyncTimer.execute(dataUser.getLong("timeActually",0));
    }

    @Override
    public void onBackPressed() {
        asyncTimer.cancel(true);
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        sm.unregisterListener(acelerometerDetector);
        unregisterReceiver(battery);
        db.close();
        super.onDestroy();
    }

    public void storePreference() {
        presenter.storePreference();
    }
}