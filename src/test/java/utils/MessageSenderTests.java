package utils;

import common.ScheduledTasks;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericGroovyApplicationContext;

import java.io.IOException;

import static org.junit.Assert.assertNotNull;


public class MessageSenderTests {
    @Ignore
    @Test
    public void testTelegramSender() throws Exception {
        ApplicationContext context = new GenericGroovyApplicationContext("config/context.groovy");
        ScheduledTasks scheduledTasks = context.getBean("scheduledTasks", ScheduledTasks.class);
        String result = scheduledTasks.getTelegramSender().send("test");
        assertNotNull(result);
    }
}
