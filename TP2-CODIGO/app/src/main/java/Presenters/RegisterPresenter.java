package Presenters;

import android.util.Patterns;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import Models.Asincrono.AsyncRegister;
import Views.RegisterActivity;

public class RegisterPresenter {

    private RegisterActivity activity;

    public RegisterPresenter(RegisterActivity activity){
        this.activity = activity;
    }

    public boolean handleRegister(String name, String lastname, String dni, String email, String password, String commission, String group) {
        if(validateForm( name,  lastname,  dni,  email,  password,  group)){
            // Log.d("Debug", "TRUE");
            new AsyncRegister(this.activity).execute(name,
                    lastname,
                    dni,
                    email,
                    password,
                    commission,
                    group);
            //Llamado a la api
            return true;
        }
        return false;
    }

    public boolean validateForm(String name, String lastname, String dni, String email, String password, String group) {

        boolean isValid = true;

        Pattern pattern = Patterns.EMAIL_ADDRESS;

        Matcher matcherDNI = Pattern.compile("^\\d+$").matcher(dni);
        Matcher matcherGroup = Pattern.compile("^\\d+$").matcher(group);

        if(!pattern.matcher(email).matches()){
            isValid = false;
        }

        if(name.isEmpty()){
            isValid = false;
        }

        if(lastname.isEmpty()){
            isValid = false;
        }

        if(password.length() < 8){
            isValid = false;
        }

        if(!matcherDNI.find()){
            isValid = false;
        }

        if(!matcherGroup.find()){
            isValid = false;
        }

        return isValid;
    }

}
