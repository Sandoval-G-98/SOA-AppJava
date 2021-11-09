package Models.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.Authentication.R;

import java.util.ArrayList;
import java.util.List;

import Models.Clande;

public class AdminSQLiteOperHelper extends SQLiteOpenHelper {

    private static final String NOMBRE_BD = "CLANDES_PROD_1";
    private static final int VERSION = 1;
    private static final String TABLE_ALL_CLANDES = "create table createClandeProd(codigo INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL , owner text, province text, locality text , postalCode text, street text, altitudeStreet text, description text, fromHour text, toHour text, dateClande text)";
    private static final String TABLE_MY_CLANDES = "create table myClandesProd(codigo INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL , owner text, province text, locality text , postalCode text, street text, altitudeStreet text, description text, fromHour text, toHour int, dateClande text)";
    private static final String TABLE_JOIN = "create table joinClandeProd(codigo INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL , owner text, participan text, province text, locality text , postalCode text, street text, altitudeStreet text, description text, fromHour text, toHour text, dateClande text)";
    private static final String NAME_TABLE_ALLL_CLANDES = "createClandeProd";
    private static final String NAME_TABLE_MY_CLANDES = "myClandesProd";
    private static final String NAME_TABLE_TABLE_JOIN = "joinClandeProd";

    public AdminSQLiteOperHelper(Context context) {
        super(context, NOMBRE_BD, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_ALL_CLANDES);
        db.execSQL(TABLE_MY_CLANDES);
        db.execSQL(TABLE_JOIN);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + NAME_TABLE_ALLL_CLANDES);
        db.execSQL("DROP TABLE IF EXISTS " + NAME_TABLE_MY_CLANDES);
        db.execSQL("DROP TABLE IF EXISTS " + NAME_TABLE_TABLE_JOIN);
    }

    public void addInTableAllClandes(String email, String provinceClande, String localityClande, String postalCodeClande, String streetClande,
                                     String altitudeClande, String descriptionClande, String fromHourClande, String toHourClande, String dateClande) {
        SQLiteDatabase db = this.getWritableDatabase();

        if (db != null) {
            db.execSQL("INSERT INTO createClandeProd(owner, province, locality , postalCode, street, altitudeStreet, description, fromHour, toHour, dateClande) VALUES('" + email + "','" + provinceClande + "','" + localityClande + "','" + postalCodeClande + "','" + streetClande + "','" + altitudeClande + "','" + descriptionClande + "','" + fromHourClande + "','" + toHourClande + "','" + dateClande + "')");
        }
        db.close();
    }

    public void addInMyTableClandes(String email, String provinceClande, String localityClande, String postalCodeClande, String streetClande,
                                    String altitudeClande, String descriptionClande, String fromHourClande, String toHourClande, String dateClande) {
        SQLiteDatabase db = this.getWritableDatabase();

        if (db != null) {
            db.execSQL("INSERT INTO myClandesProd(owner, province, locality , postalCode, street, altitudeStreet, description, fromHour, toHour, dateClande) VALUES('" + email + "','" + provinceClande + "','" + localityClande + "','" + postalCodeClande + "','" + streetClande + "','" + altitudeClande + "','" + descriptionClande + "','" + fromHourClande + "','" + toHourClande + "','" + dateClande + "')");
        }
        db.close();
    }

    public List<Clande> getClandes() {
        final int MIN_NIGHT = 20;
        String fromHourString;
        int fromHour;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT codigo, owner, province, locality, postalCode, street, altitudeStreet, " +
                "description, fromHour, toHour, dateClande FROM createClandeProd", null);

        List<Clande> clande = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                Clande c = new Clande(
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6),
                        cursor.getString(7),
                        cursor.getString(8),
                        cursor.getString(9),
                        cursor.getString(10));

                c.setCodClande(cursor.getString(0));
                fromHourString = cursor.getString(8).split(":")[0];
                fromHour = Integer.valueOf(fromHourString);
                if (MIN_NIGHT <= fromHour) {
                    c.setImageClande(R.drawable.noche);
                } else {
                    c.setImageClande(R.drawable.dia);
                }
                clande.add(c);
            } while (cursor.moveToNext());
        }
        db.close();
        cursor.close();
        return clande;
    }

    public boolean isInMyClandes(String email, String fromHourClande, String toHourClande, String dateClande) {
        boolean isInClande = false;

        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM myClandesProd WHERE owner='" + email + "' AND fromHour= '" + fromHourClande + "' AND toHour='" + toHourClande + "' AND dateClande='" + dateClande + "'";
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst())
            isInClande = true;

        db.close();
        cursor.close();
        return isInClande;
    }

    public boolean isJoined(String email, String fromHourClande, String toHourClande, String dateClande) {
        boolean isInClande = false;

        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM joinClandeProd WHERE participan='" + email + "' AND fromHour= '" + fromHourClande + "' AND toHour='" + toHourClande + "' AND dateClande='" + dateClande + "'";
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst())
            isInClande = true;

        db.close();
        cursor.close();
        return isInClande;
    }

    public boolean joinInClande(String codigo, String emailParticipant, String fromHourClande, String toHourClande, String dateClande) {

        boolean isAddedToClande = false;
        String emailOwner;
        String provinceOwner;
        String localityCodeOwner;
        String postalCodeOwner;
        String streetOwner;
        String altitudeStreetOwner;
        String descriptionOwner;
        String fromHourOwner;
        String toHourOwner;
        String dateClandeOwner;

        if(!isJoined(emailParticipant, fromHourClande, toHourClande, dateClande)){

            SQLiteDatabase db = this.getWritableDatabase();
            String sql = "SELECT codigo, owner, province, locality, postalCode, street, altitudeStreet, description, fromHour, toHour, dateClande FROM createClandeProd WHERE codigo= '" + codigo + "'";

            Cursor cursor = db.rawQuery(sql, null);

            if(cursor.moveToFirst()){
                emailOwner = cursor.getString(1);
                provinceOwner = cursor.getString(2);
                localityCodeOwner = cursor.getString(3);
                postalCodeOwner = cursor.getString(4);
                streetOwner = cursor.getString(5);
                altitudeStreetOwner = cursor.getString(6);
                descriptionOwner = cursor.getString(7);
                fromHourOwner = cursor.getString(8);
                toHourOwner = cursor.getString(9);
                dateClandeOwner = cursor.getString(10);

                db.close();
                cursor.close();

                db = this.getWritableDatabase();

                if (db!=null) {
                    db.execSQL("INSERT INTO joinClandeProd(owner, participan, province, locality , postalCode, street, altitudeStreet, description, fromHour, toHour, dateClande) VALUES('" + emailOwner + "','" + emailParticipant + "','" + provinceOwner + "','" + localityCodeOwner + "','" + postalCodeOwner + "','" + streetOwner + "','" + altitudeStreetOwner + "','" + descriptionOwner + "','" + fromHourOwner + "','" + toHourOwner + "','" + dateClandeOwner + "')");
                    isAddedToClande = true;
                }
            }

            db.close();
            cursor.close();
        }
        return isAddedToClande;
    }
}