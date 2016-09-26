package common;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import utils.JsoupPageParser;

@Component
public class ScheduledTasks {

    @Autowired
    private JsoupPageParser jsoupPageParser;

    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Scheduled(fixedRate = 5000)
    public void reportCurrentTime() {
        System.out.println("emailSender!!! "+ jsoupPageParser);
        System.out.println("The time is now: " + dateFormat.format(new Date()));
        log.info("The time is now {}", dateFormat.format(new Date()));
    }
}