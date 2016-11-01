package context;

import com.mongodb.MongoURI;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericGroovyApplicationContext;
import common.ScheduledTasks;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

public class GroovyBeanBuilderTests {
    @Test
    public void testBeansCreation() {
        ApplicationContext context = new GenericGroovyApplicationContext("config/context.groovy");
        MongoURI mongoURI = context.getBean("mongoURI", MongoURI.class);
        ScheduledTasks scheduledTasks = context.getBean("scheduledTasks", ScheduledTasks.class);
        assertSame(scheduledTasks.getMongoURI(), mongoURI);
    }
}
