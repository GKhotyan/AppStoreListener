package common;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import com.mongodb.*;

import db.entities.BaseApplicationInfo;
import db.entities.AppStoreApplicationInfo;
import db.repositories.ApplicationInfoRepository;
import db.repositories.PlayStoreApplicationInfoRepository;
import db.repositories.AppStoreApplicationInfoRepository;
import events.NewVersionEventProducer;
import factories.PageParserBeanFactory;
import org.mongeez.Mongeez;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.core.io.Resource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import utils.MessageSender;

import javax.annotation.PostConstruct;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@EnableScheduling
@Component
public class ScheduledTasks {
    private PageParserBeanFactory pageParserBeanFactory;
    private Resource resource;
    private Mongeez mongeez;
    private MongoURI mongoURI;

    @Autowired
    NewVersionEventProducer newVersionEventProducer;

    @Autowired
    AppStoreApplicationInfoRepository appStoreRepository;

    @Autowired
    PlayStoreApplicationInfoRepository playStoreRepository;

    @Autowired
    private ApplicationEventPublisher publisher;

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

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    List<AppStoreApplicationInfo> appStoreApplicationInfoList;
//    List<PlayStoreApplicationInfo> playStoreApplicationInfoList;

    @PostConstruct
    public void initialization() {
        log.info("Initialization");
        newVersionEventProducer.createNewVersionEvent("test", "test");

        //Mongeez remembers its actions and executes only new scripts,
        //so, we shouldn't worry about duplications
        mongeez.setMongo(new Mongo(mongoURI));
        mongeez.process();

        appStoreApplicationInfoList = appStoreRepository.findAll();
//        playStoreApplicationInfoList = playStoreRepository.findAll();
    }

    @Scheduled(cron="${version.refresh.cron}")
    public void appStoreSchedule() {
        if(appStoreApplicationInfoList.size()==0){
            appStoreApplicationInfoList = appStoreRepository.findAll();
        }

        for (BaseApplicationInfo appItem : appStoreApplicationInfoList) {
            checkForNewVersion(appStoreRepository, appItem, false);
        }

        //refresh appList
        appStoreApplicationInfoList = appStoreRepository.findAll();
    }

//    @Scheduled(cron="0 0/2 6-23,0 * * MON-SAT")
//    public void playStoreSchedule() {
//        if(playStoreApplicationInfoList.size()==0){
//            playStoreApplicationInfoList = playStoreRepository.findAll();
//        }
//
//        for (BaseApplicationInfo appItem : playStoreApplicationInfoList) {
//            checkForNewVersion(playStoreRepository, appItem, false);
//        }
//
//        //refresh appList
//        playStoreApplicationInfoList = playStoreRepository.findAll();
//    }

    private void checkForNewVersion(ApplicationInfoRepository repository,
                                    BaseApplicationInfo appItem, boolean sendEmail) {
        try {
            String version = getPageParserBeanFactory().getPageParser(appItem).getVersionFromHtmlByUrl(appItem.getUrl());
            if (appItem.getVersions()==null || appItem.getVersions().size() == 0) {
                repository.pushVersion(appItem.getId(), version);
                List<String> versionsList = new ArrayList<>();
                versionsList.add(version);
                appItem.setVersions(versionsList);
                log.info(appItem.getName() + ". Version = " + version);

                //send to Telegram
                getTelegramSender().send(appItem.getName()+" have a new version "+ version);

                //send to Email(s)
                if(sendEmail) {
                    sendEmail(appItem.getName() + " version changed to " + version);
                }
            } else if(appItem.getVersions().size()>0 &&
                    appItem.getVersions().contains(version)){
                log.info("Ok. "+appItem.getName()+" still have version "+ version);
            } else if (StringUtils.hasText(version) && appItem.getVersions().size()>0 &&
                    !appItem.getVersions().contains(version)){
                repository.pushVersion(appItem.getId(), version);
                appItem.getVersions().add(version);
                log.info(appItem.getName()+" have a new version "+ version);

                //send to Telegram
                getTelegramSender().send(appItem.getName()+" have a new version "+ version);

                //send to Email(s)
                if(sendEmail) {
                    sendEmail(appItem.getName() + " version changed to " + version);
                }
            }

            int randomPeriod = ThreadLocalRandom.current().nextInt(0, 1000);
            Thread.sleep(5000+randomPeriod);
        } catch (InterruptedException ex){
            log.error(appItem.getName() + " have a problem with concurrency. "+ex.getMessage());
        } catch (IOException e) {
            log.error(appItem.getName() + " have a problem with url connection. "+e.getMessage());
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
            log.error("Mail sending error", e);
        } catch (org.springframework.mail.MailAuthenticationException e){
            log.error("Mail sending authentication error", e);
        }
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

    public MessageSender getTelegramSender() {
        return telegramSender;
    }

    public void setTelegramSender(MessageSender telegramSender) {
        this.telegramSender = telegramSender;
    }

    public PageParserBeanFactory getPageParserBeanFactory() {
        return pageParserBeanFactory;
    }

    public void setPageParserBeanFactory(PageParserBeanFactory pageParserBeanFactory) {
        this.pageParserBeanFactory = pageParserBeanFactory;
    }
}