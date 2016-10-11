package common;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.mongodb.Mongo;
import db.entities.ApplicationInfo;
import db.repositories.IMongoApplicationInfoRepository;
import db.repositories.IMongoSimpleEntityRepository;
import org.mongeez.Mongeez;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import utils.MessageSender;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Component
public class ScheduledTasks {
    @Autowired
    IMongoApplicationInfoRepository repository;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private MessageSender telegramSender;

    @Value("${mail.list.to}")
    private String emailsList;

    @Value("classpath:mongeez/mongeez.xml")
    private Resource res;

    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Scheduled(fixedRate = 5000)
    public void reportCurrentTime() {

        Mongeez mongeez = new Mongeez();
        mongeez.setFile(res);
        mongeez.setMongo(new Mongo("localhost", 27017));
        mongeez.setDbName("applistener");
        mongeez.process();

//        repository.deleteAll();
          List<ApplicationInfo> applicationInfoList = repository.findAll();
//        repository.save(new SimpleEntity(1, "text1"));
//        repository.save(new SimpleEntity(2, "text2"));
//        repository.save(new SimpleEntity(3, "text3"));
//
//        System.out.println("Mongo: "+repository.findById(3).getText());
        System.out.println("The time is now: " + dateFormat.format(new Date()));
        log.info("The time is now {}", dateFormat.format(new Date()));
    }

    private void sendEmail() {
        MimeMessage mail = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mail, true);
            helper.setTo(emailsList.split(","));
            helper.setSubject("Lorem ipsum");
            helper.setText("Lorem ipsum dolor sit amet [...]");
            javaMailSender.send(mail);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }
}