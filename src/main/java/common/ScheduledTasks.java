package common;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import com.mongodb.*;
import db.entities.ApplicationInfo;
import db.repositories.ApplicationInfoRepository;
import org.mongeez.Mongeez;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.core.io.Resource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import utils.MessageSender;
import utils.PageParser;

import javax.annotation.PostConstruct;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@EnableScheduling
@Component
public class ScheduledTasks {
    private PageParser pageParser;
    private Resource resource;
    private Mongeez mongeez;
    private MongoURI mongoURI;

    @Autowired
    ApplicationInfoRepository repository;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private MessageSender telegramSender;

    @Value("${spring.data.mongodb.uri}")
    private String mongodbUri;

    @Value("${spring.data.mongodb.database}")
    private String mongoDbName;

    @Value("${mail.list.to}")
    private String emailsList;

//    @Value("classpath:mongeez/mongeez.xml")
//    private Resource res;

    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);
    private static DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    List<ApplicationInfo> applicationInfoList;

    @PostConstruct
    public void initialization() {
        applicationInfoList = repository.findAll();
        if(applicationInfoList.size()==0){
            mongeez.setMongo(new Mongo(mongoURI));
            mongeez.process();
        }
    }

    @Scheduled(cron="0 0/5 6-23,0 * * MON-SAT")
    public void reportCurrentTime() {
        if(applicationInfoList.size()==0){
            applicationInfoList = repository.findAll();
        }

        for (ApplicationInfo appItem : applicationInfoList) {
            try {
                String version = getPageParser().getVersionFromHtmlByUrl(appItem.getUrl());
                if (appItem.getVersions()==null || appItem.getVersions().size() == 0) {
                    repository.pushVersion(appItem.getId(), version);
                    printWithTime(appItem.getName() + ". Version = " + version);
                } else if(appItem.getVersions().size()>0 &&
                        appItem.getVersions().contains(version)){
                    printWithTime("Ok. "+appItem.getName()+" still have version "+ version);
                } else if (StringUtils.hasText(version)){
                    repository.pushVersion(appItem.getId(), version);
                    printWithTime(appItem.getName()+" have a new version "+ version);

                    //send to Telegram
                    telegramSender.send(appItem.getName()+" have a new version "+ version);

                    //refresh appList
                    applicationInfoList = repository.findAll();

                    //send to Email(s)
//                    sendEmail(appItem.getName()+" version changed to "+version);

                }

                int randomPeriod = ThreadLocalRandom.current().nextInt(0, 1000);
                Thread.sleep(5000+randomPeriod);
            } catch (InterruptedException ex){
                ex.printStackTrace();
            } catch (IOException e) {
                printWithTime(appItem.getName() + " have a problem with url connection. "+e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private void sendEmail(String message) {
        MimeMessage mail = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mail, true);
            helper.setTo(emailsList.split(","));
            helper.setSubject(message);
            helper.setText(message);
            javaMailSender.send(mail);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    private static void printWithTime(String message){
        System.out.println(dateFormat.format(Calendar.getInstance().getTime())+" - "+message);
    }

    public PageParser getPageParser() {
        return pageParser;
    }

    public void setPageParser(PageParser pageParser) {
        this.pageParser = pageParser;
    }

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    public Mongeez getMongeez() {
        return mongeez;
    }

    public void setMongeez(Mongeez mongeez) {
        this.mongeez = mongeez;
    }

    public MongoURI getMongoURI() {
        return mongoURI;
    }

    public void setMongoURI(MongoURI mongoURI) {
        this.mongoURI = mongoURI;
    }
}