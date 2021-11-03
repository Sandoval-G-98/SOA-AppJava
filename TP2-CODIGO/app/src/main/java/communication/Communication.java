package communication;

import android.os.Build;

import androidx.annotation.RequiresApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class Communication {
    private static String URL_LOGIN = "http://so-unlam.net.ar/api/api/login";
    private static String URL_REGISTER = "http://so-unlam.net.ar/api/api/register";
    private static String URL_REFRESH = "http://so-unlam.net.ar/api/api/refresh";
    private static String URL_EVENT = "http://so-unlam.net.ar/api/api/event";
    private static final String VAR_ENV = "PROD";
    public static String ERROR_MSG = "ERROR EN LA COMUNICACION";

    public String communicationLogin(String email, String password) {
        String result = "";
        JSONObject data = new JSONObject();

        try{
            data.put("email", email);
            data.put("password", password);
            URL url = new URL(URL_LOGIN);
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            http.setDoOutput(true);
            http.setDoInput(true);
            http.setConnectTimeout(5000);
            http.setRequestMethod("POST");

            DataOutputStream dataOutputStream = new DataOutputStream(http.getOutputStream());
            dataOutputStream.write(data.toString().getBytes("UTF-8"));

            dataOutputStream.flush();
            http.connect();

            if(http.getResponseCode() == HttpURLConnection.HTTP_OK || http.getResponseCode() == HttpURLConnection.HTTP_CREATED){
                InputStreamReader inputStreamReader = new InputStreamReader(http.getInputStream());
                result = convertInputStreamToString(inputStreamReader).toString();
            } else if(http.getResponseCode() == HttpURLConnection.HTTP_BAD_REQUEST){
                InputStreamReader inputStreamReader = new InputStreamReader(http.getErrorStream());
                result = convertInputStreamToString(inputStreamReader).toString();
            }else {
                result = ERROR_MSG;
                return result;
            }
            dataOutputStream.close();
            http.disconnect();

        } catch (IOException | JSONException e) {
            e.printStackTrace();
            result = ERROR_MSG;
        }
        return result;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public String communicationRegister(
            String name,
            String lastname,
            String dni,
            String email,
            String password,
            String commission,
            String group) {
        String result = "";
        JSONObject data = new JSONObject();
        // Log.d("Debug", "Entrando a communication");

        try{
            data.put("env",VAR_ENV);
            data.put("name",name);
            data.put("lastname",lastname);
            data.put("dni",dni);
            data.put("email",email);
            data.put("password",password);
            data.put("commission",commission);
            data.put("group",group);


            // Log.d("Debug", "Paso el data.put");
            // Log.d("Debug", data.toString());

            URL url = new URL(URL_REGISTER);
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            http.setDoOutput(true);
            http.setDoInput(true);
            http.setConnectTimeout(5000);
            http.setRequestMethod("POST");
            // Log.d("Debug", "Paso el URL");

            DataOutputStream dataOutputStream = new DataOutputStream(http.getOutputStream());
            // Log.d("Debug", "Paso el dataOutputStream");
            dataOutputStream.write(data.toString().getBytes(StandardCharsets.UTF_8));
            // Log.d("Debug", "Paso el dataOutputStream.write");

            dataOutputStream.flush();
            http.connect();
            // Log.d("Debug", "Inicia conexion");

            if(http.getResponseCode() == HttpURLConnection.HTTP_OK || http.getResponseCode() == HttpURLConnection.HTTP_CREATED){
                InputStreamReader inputStreamReader = new InputStreamReader(http.getInputStream());
                result = convertInputStreamToString(inputStreamReader).toString();
                // Log.d("Debug", "Peticion correcta");
            } else if(http.getResponseCode() == HttpURLConnection.HTTP_BAD_REQUEST){
                InputStreamReader inputStreamReader = new InputStreamReader(http.getErrorStream());
                result = convertInputStreamToString(inputStreamReader).toString();
                // Log.d("Debug", "Peticion incorrecta");
            }else {
                result = ERROR_MSG;
                return result;
            }

            dataOutputStream.close();
            http.disconnect();
        } catch (IOException | JSONException e) {
            // Log.d("Debug", "ERROR en catch communication");
            // Log.d("Debug", String.valueOf(e));
            e.printStackTrace();
            result = ERROR_MSG;
        }
        return result;
    }

    public String communicationRefreshToken(String tokenRefresh) {
        String result = null;

        try{
            URL url = new URL(URL_REFRESH);
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestProperty("Authorization", "Bearer " + tokenRefresh);
            http.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            http.setDoOutput(true);
            http.setDoInput(true);
            http.setConnectTimeout(5000);
            http.setRequestMethod("PUT");

            DataOutputStream dataOutputStream = new DataOutputStream(http.getOutputStream());

            dataOutputStream.flush();
            http.connect();

            if(http.getResponseCode() == HttpURLConnection.HTTP_OK || http.getResponseCode() == HttpURLConnection.HTTP_CREATED){
                InputStreamReader inputStreamReader = new InputStreamReader(http.getInputStream());
                result = convertInputStreamToString(inputStreamReader).toString();
            } else if(http.getResponseCode() == HttpURLConnection.HTTP_BAD_REQUEST){
                InputStreamReader inputStreamReader = new InputStreamReader(http.getErrorStream());
                result = convertInputStreamToString(inputStreamReader).toString();
            }else {
                result = ERROR_MSG;
                return result;
            }
            dataOutputStream.close();
            http.disconnect();

        } catch (IOException e) {
            e.printStackTrace();
            result = ERROR_MSG;
        }
        return result;
    }


    private static StringBuilder convertInputStreamToString(InputStreamReader inputStreamReader) throws IOException {
        BufferedReader br = new BufferedReader(inputStreamReader);
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while ( (line = br.readLine()) != null ){
            stringBuilder.append(line + "\n");
        }
        br.close();
        return stringBuilder;
    }
}
