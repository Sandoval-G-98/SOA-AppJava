package Presenters;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import Models.Clande;
import Models.ClandeAdapter;
import Models.db.AdminSQLiteOperHelper;
import Views.JoinClandeActivity;

public class JoinClandePresenter {

    private JoinClandeActivity activity;
    private ClandeAdapter adapterClandes;
    private RecyclerView recyclerViewClandes;

    public JoinClandePresenter(JoinClandeActivity activity){
        this.activity = activity;
    }

    public boolean joinInClande(String codigo, String emailParticipant, String fromHourClande, String toHourClande, String dateClande){
        AdminSQLiteOperHelper db = new AdminSQLiteOperHelper(this.activity);
        boolean isInClande = db.joinInClande(codigo, emailParticipant, fromHourClande, toHourClande, dateClande);
        db.close();
        return isInClande;
    }

    public List<Clande> getClandes(){
        AdminSQLiteOperHelper db = new AdminSQLiteOperHelper(this.activity);
        List<Clande> c = db.getClandes();

        return c;
    }

}
