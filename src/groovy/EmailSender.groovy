import javax.mail.*
import javax.mail.internet.*

class EmailSender {
    public void send() throws Exception {

        def  d_email = "",
             d_uname = "georgiy.hotiyan@gmail.com",
             d_password = "w",
             d_host = "smtp.gmail.com",
             d_port  = "465", //465,587
             m_to = "kraskovo@gmail.com",
             m_subject = "Testing",
             m_text = "Hey, this is the testing email."

        def props = new Properties()
        props.put("mail.smtp.user", d_email)
        props.put("mail.smtp.host", d_host)
        props.put("mail.smtp.port", d_port)
        props.put("mail.smtp.starttls.enable","true")
        props.put("mail.smtp.debug", "true");
        props.put("mail.smtp.auth", "true")
        props.put("mail.smtp.socketFactory.port", d_port)
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory")
        props.put("mail.smtp.socketFactory.fallback", "false")

        def auth = new SMTPAuthenticator()
        def session = Session.getInstance(props, auth)
        session.setDebug(true);

        def msg = new MimeMessage(session)
        msg.setText(m_text)
        msg.setSubject(m_subject)
        msg.setFrom(new InternetAddress(d_email))
        msg.addRecipient(Message.RecipientType.TO, new InternetAddress(m_to))

        Transport transport = session.getTransport("smtps");
        transport.connect(d_host, 465, d_uname, d_password);
        transport.sendMessage(msg, msg.getAllRecipients());
        transport.close();
    }

}

