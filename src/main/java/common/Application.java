package common;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ImportResource("file:config/context.groovy")
@PropertySources({
        @PropertySource("classpath:application.yml"),
        @PropertySource("classpath:private.yml")
})
@EnableAutoConfiguration
@EnableMongoRepositories("db.repositories")
@EnableScheduling
@ComponentScan(basePackages = {"common", "utils"})
public class Application {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class);
    }
}