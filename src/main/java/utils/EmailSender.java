package utils;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class EmailSender implements MessageSender {
    private static final String RESULT_SUCCESS = "SUCCESS";
    private Properties properties;

    public String send(String subject, String text) {
        String result = null;
        try {
            Session session = Session.getDefaultInstance(properties,
                    new javax.mail.Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(properties.getProperty("mail.login.from"), properties.getProperty("mail.password.from"));
                        }
                    });

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(properties.getProperty("mail.login.from")));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(properties.getProperty("mail.list.to")));
            message.setSubject(subject);
            message.setText(text);

            Transport.send(message);
            result = RESULT_SUCCESS;
        } catch (MessagingException e){
            e.printStackTrace();
        }
        return result;
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }
}