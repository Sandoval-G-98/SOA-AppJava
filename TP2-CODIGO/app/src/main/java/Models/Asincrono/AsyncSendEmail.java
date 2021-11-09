package Models.Asincrono;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class AsyncSendEmail extends AsyncTask<String, Void, String> {

    private Session session;
    private String subject;
    private String textMessage;
    private String rec;
    private EditText reciep;
    private ProgressDialog pdialog = null;
    private Context context;


    public AsyncSendEmail(Session session, String message, String subject, String rec, EditText reciep, Context context) {
        this.session = session;
        this.textMessage = message;
        this.subject = subject;
        this.reciep = reciep;
        this.context = context;
        this.rec = rec;
    }

    @Override
    protected String doInBackground(String... params) {

        try{
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("testfrom354@gmail.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(rec));
            message.setSubject(subject);
            message.setContent(textMessage, "text/html; charset=utf-8");
            Transport.send(message);
        } catch(MessagingException e) {
            e.printStackTrace();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        session = Session.getDefaultInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("laclandeparty@gmail.com", "Clande1234@\n");
            }
        });

        pdialog = ProgressDialog.show(context, "", "Enviando email...", true);
    }

    @Override
    protected void onPostExecute(String result) {
        pdialog.dismiss();
        reciep.setText("");
        Toast.makeText(context, "Email enviado", Toast.LENGTH_LONG).show();
    }
}
