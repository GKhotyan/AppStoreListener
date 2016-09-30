import common.Executor;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericGroovyApplicationContext;
import common.ScheduledTasks;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

public class GroovyBeanBuilderTests {
    @Test
    public void testBeansCreation() {
        ApplicationContext context = new GenericGroovyApplicationContext("file:config/context.groovy");
        MessageSender emailSender = context.getBean("emailSender", MessageSender.class);
        Executor executor = context.getBean("executor", Executor.class);
        Launcher launcher = context.getBean("launcher", Launcher.class);
        ScheduledTasks scheduledTasks = context.getBean("scheduledTasks", ScheduledTasks.class);
        assertSame(launcher.getExecutor().getMessageSender(), emailSender);
    }
}
