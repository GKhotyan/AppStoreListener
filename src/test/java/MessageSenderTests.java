import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericGroovyApplicationContext;
import utils.MessageSender;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

public class MessageSenderTests {
    @Test
    public void testTelegramSender(){
        ApplicationContext context = new GenericGroovyApplicationContext("file:config/context.groovy");
        MessageSender telegramSender = context.getBean("telegramSender", MessageSender.class);
        String result = telegramSender.send("test");
        assertNotNull(result);
    }
}
