package common;

import components.AppItem;
import org.springframework.stereotype.Component;
import utils.MessageSender;
import utils.PageParser;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.*;

@Component
public class Executor {
    private MessageSender messageSender;
    private PageParser pageParser;
    private static DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    private static final String RESULT_SUCCESS = "SUCCESS";

    public void exec(){
        System.out.println("Hello!");

        List<AppItem> appItemList = new ArrayList<>();
        appItemList.add(new AppItem("Uber", "https://itunes.apple.com/ru/app/uber-lucse-cem-taksi/id368677368"));
        appItemList.add(new AppItem("Gett", "https://itunes.apple.com/ru/app/gett-gettaxi-zakaz-taksi-onlajn/id449655162"));
        appItemList.add(new AppItem("Delimobil", "https://itunes.apple.com/ru/app/delimobil/id1038254296"));
        appItemList.add(new AppItem("YouDrive", "https://itunes.apple.com/ru/app/youdrive/id989836024?l=en"));
        appItemList.add(new AppItem("BurgerKing", "https://itunes.apple.com/ru/app/burger-king-russia/id875569110"));
        appItemList.add(new AppItem("Airbnb", "https://itunes.apple.com/ru/app/airbnb/id401626263"));

        ScheduledExecutorService scheduledExecutorService =
                Executors.newSingleThreadScheduledExecutor();

        ScheduledFuture scheduledFuture =
                scheduledExecutorService.scheduleAtFixedRate (new Runnable() {
                                                      public void run() {

                                                              for (AppItem appItem : appItemList) {
                                                                  try {
                                                                      String version = getPageParser().getVersionFromHtmlByUrl(appItem.getHttpPath());
                                                                      if (appItem.getVersion() == null) {
                                                                          appItem.setVersion(version);
                                                                          printWithTime(appItem.getName() + ". Version = " + appItem.getVersion());
                                                                      } else if(appItem.getVersion().equals(version)){
                                                                          printWithTime("Ok. "+appItem.getName()+" still have version "+ version);
                                                                      } else if (version!=null&&!version.trim().equals("")){
                                                                          printWithTime(appItem.getName()+" have a new version "+ version);

                                                                          String result = getMessageSender().send(appItem.getName()+" version changed from "+appItem.getVersion()+" to "+version);
                                                                          if(RESULT_SUCCESS.equals(result))
                                                                            appItem.setVersion(version);
                                                                      }
                                                                      Thread.sleep(60000);
                                                                  } catch (InterruptedException ex){
                                                                      ex.printStackTrace();
                                                                  } catch (IOException e) {
                                                                      printWithTime(appItem.getName() + " have a problem with url connection. "+e.getMessage());
                                                                      e.printStackTrace();
                                                                  }
                                                              }

                                                      }
                                                  },
                        5,
                        600,
                        TimeUnit.SECONDS);
    }

    private static void printWithTime(String message){
        System.out.println(dateFormat.format(Calendar.getInstance().getTime())+" - "+message);
    }


    public MessageSender getMessageSender() {
        return messageSender;
    }

    public void setMessageSender(MessageSender messageSender) {
        this.messageSender = messageSender;
    }

    public PageParser getPageParser() {
        return pageParser;
    }

    public void setPageParser(PageParser pageParser) {
        this.pageParser = pageParser;
    }
}
