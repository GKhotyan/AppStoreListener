package utils;

import javax.mail.MessagingException;

/**
 * Created by user on 07.09.2016.
 */
public interface MessageSender {
    String send(String subject, String text);
}
