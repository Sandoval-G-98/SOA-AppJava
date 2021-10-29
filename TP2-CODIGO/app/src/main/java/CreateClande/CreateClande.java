package CreateClande;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Authentication.R;

import Asincrono.AsyncSendEmail;
import Battery.Battery;
import RegisterCreateClande.RegisterOrCreateClande;

public class CreateClande extends AppCompatActivity {

    private TextView batteryLevel;
    private Spinner spinnerFromHour;
    private Spinner spinnerToHour;
    private Button buttonCreateClande;
    private EditText edViewProvince, edViewLocality, edViewPostalCode, edViewStreetName, edViewAltitudeStreet, edViewDescription;
    private String province;
    private String locality;
    private String postalCode;
    private String streetName;
    private String altitudeStreet;
    private String description;
    private String fromHour;
    private String toHour;
    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_clande);
        initializeSpinners();

        batteryLevel = findViewById(R.id.batteryLevel4);
        Battery bat = new Battery(batteryLevel);
        this.registerReceiver(bat.setBatInfo(), new IntentFilter(Intent.ACTION_BATTERY_CHANGED));

        buttonCreateClande = findViewById(R.id.createClande);

        buttonCreateClande.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkFields()){
                    Toast.makeText(context, "Se creó la clande correctamente", Toast.LENGTH_LONG).show();
                    Intent createOrJoinClandeActivity = new Intent(CreateClande.this, RegisterOrCreateClande.class);
                    startActivity(createOrJoinClandeActivity);
                }
            }
        });


    }

    private void initializeSpinners(){
        String[] hoursSpinners = {"00", "23", "22", "21", "20", "19", "18", "17", "16", "15", "14", "13", "12", "11", "10", "09", "08", "07", "06", "05", "04", "02", "01"};
        spinnerFromHour = findViewById(R.id.spinnerFromHourClande);
        spinnerToHour = findViewById(R.id.spinnerToHourClande);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, hoursSpinners);
        spinnerFromHour.setAdapter(adapter);
        spinnerToHour.setAdapter(adapter);
    }

    private boolean checkFields(){
        boolean fieldsCorrects = true;

        edViewProvince = findViewById(R.id.provinceClande);
        edViewLocality = findViewById(R.id.localityClande);
        edViewPostalCode = findViewById(R.id.postalCodeClande);
        edViewStreetName = findViewById(R.id.streetName);
        edViewAltitudeStreet = findViewById(R.id.altitudeStreetClande);
        fromHour = spinnerFromHour.getSelectedItem().toString();
        toHour = spinnerToHour.getSelectedItem().toString();

        province = edViewProvince.getText().toString();
        locality = edViewLocality.getText().toString();
        postalCode = edViewPostalCode.getText().toString();
        streetName = edViewStreetName.getText().toString();
        altitudeStreet = edViewAltitudeStreet.getText().toString();

        if(province.isEmpty() || locality.isEmpty() || postalCode.isEmpty() || streetName.isEmpty()
                || altitudeStreet.isEmpty() || fromHour.isEmpty() || toHour.isEmpty()){
            Toast.makeText(this, "No pueden haber campos obligatorios vacíos", Toast.LENGTH_LONG).show();
            fieldsCorrects = false;
        }

        return fieldsCorrects;
    }
}