package common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ImportResource;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ImportResource("classpath:/context.groovy")
@EnableScheduling
public class Application {
    @Autowired
    private static ApplicationContext context;

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class);
        System.out.println("emailSender "+ context.containsBean("emailSender"));
    }
}