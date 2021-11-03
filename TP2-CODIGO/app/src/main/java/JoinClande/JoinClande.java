package JoinClande;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.Authentication.R;

import Asincrono.AsyncTimer;
import utils.BatteryReceiver;
import utils.Clande;
import utils.ClandeAdapter;
import utils.db.AdminSQLiteOperHelper;

public class JoinClande extends AppCompatActivity {

    private RecyclerView recyclerViewClandes;
    private ClandeAdapter adapterClandes;
    private TextView batteryLevel;
    private BatteryReceiver battery;
    private AdminSQLiteOperHelper db;
    private String emailParticipant;
    private SharedPreferences dataUser;
    private AsyncTimer asyncTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_clande);
        
        dataUser = this.getSharedPreferences("SharedUser", Context.MODE_PRIVATE);

        Toast.makeText(this, "Debe hacer click sobre una clande para indicar que asistirá", Toast.LENGTH_LONG).show();

        emailParticipant = getIntent().getStringExtra("email");

        recyclerViewClandes = findViewById(R.id.recyclerClandes);
        recyclerViewClandes.setLayoutManager(new LinearLayoutManager(this));

        db = new AdminSQLiteOperHelper(getApplicationContext());
        List<Clande> c = db.getClandes();
        adapterClandes = new ClandeAdapter(c);
        recyclerViewClandes.setAdapter(adapterClandes);

        batteryLevel = findViewById(R.id.batteryLevel6);
        battery = new BatteryReceiver(batteryLevel);
        registerReceiver(battery, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));

        adapterClandes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!db.joinInClande(
                        c.get(recyclerViewClandes.getChildAdapterPosition(v)).getCodClande(),
                        emailParticipant,
                        c.get(recyclerViewClandes.getChildAdapterPosition(v)).getFromHourClande(),
                        c.get(recyclerViewClandes.getChildAdapterPosition(v)).getToHourClande(),
                        c.get(recyclerViewClandes.getChildAdapterPosition(v)).getDateClande()
                )){
                    Toast.makeText(getApplicationContext(), "Ya se encuentra en la clande seleccionada, elija otra", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Se unió correctamente a la clande: " + c.get(recyclerViewClandes.getChildAdapterPosition(v)).getCodClande(), Toast.LENGTH_SHORT).show();
                }

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
    protected void onDestroy(){
        unregisterReceiver(battery);
        db.close();
        super.onDestroy();
    }

}