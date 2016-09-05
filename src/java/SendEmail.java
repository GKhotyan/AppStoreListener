import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendEmail {
    public void send(String application, String version) {
        Properties props = new Properties();
        InputStream commonInput = null;
        InputStream privateInput = null;
        try {

            commonInput = this.getClass().getResourceAsStream("common.properties");
            privateInput = this.getClass().getResourceAsStream("private.properties");
            props.load(commonInput);
            props.load(privateInput);

            Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(props.getProperty("mail.login.from"),props.getProperty("mail.password.from"));
                    }
                });

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(props.getProperty("mail.login.from")));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(props.getProperty("mail.list.to")));
            message.setSubject(application+" got a new version "+version);
            message.setText("Refresh it quickly!");

            Transport.send(message);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}