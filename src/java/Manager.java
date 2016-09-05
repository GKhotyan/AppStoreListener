import javax.mail.MessagingException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.*;

/**
 * Created by user on 05.09.2016.
 */
public class Manager {
    private static Properties props = new Properties();
    private static SendEmail sendEmail = new SendEmail();
    private static PageParser pageParser = new PageParser();

    private static void init(){
        InputStream commonInput = null;
        InputStream privateInput = null;
        try {
            commonInput = Manager.class.getClassLoader().getResourceAsStream("common.properties");
            privateInput = Manager.class.getClassLoader().getResourceAsStream("private.properties");
            props.load(commonInput);
            props.load(privateInput);
        } catch(FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        System.out.println("Hello");
        init();

        List<AppItem> appItemList = new ArrayList<>();
        appItemList.add(new AppItem("Uber", "https://itunes.apple.com/ru/app/uber-lucse-cem-taksi/id368677368"));
        appItemList.add(new AppItem("Gett", "https://itunes.apple.com/ru/app/gett-gettaxi-zakaz-taksi-onlajn/id449655162"));
        appItemList.add(new AppItem("Delimobil", "https://itunes.apple.com/ru/app/delimobil/id1038254296"));
        appItemList.add(new AppItem("YouDrive", "https://itunes.apple.com/ru/app/youdrive/id989836024?l=en"));
        appItemList.add(new AppItem("BurgerKing", "https://itunes.apple.com/ru/app/burger-king-russia/id875569110"));

        ScheduledExecutorService scheduledExecutorService =
                Executors.newSingleThreadScheduledExecutor();

        ScheduledFuture scheduledFuture =
                scheduledExecutorService.scheduleAtFixedRate (new Runnable() {
                                                      public void run() {

                                                              for (AppItem appItem : appItemList) {
                                                                  try {
                                                                      String version = pageParser.getVersion(appItem.getHttpPath());
                                                                      if (appItem.getVersion() == null) {
                                                                          appItem.setVersion(version);
                                                                          System.out.println(appItem.getName() + ". Version = " + appItem.getVersion());
                                                                      } else if(appItem.getVersion().equals(version)){
                                                                          System.out.println("Ok. "+appItem.getName()+" still have version "+ version);
                                                                      } else if (version!=null&&!version.trim().equals("")){
                                                                          System.out.println(appItem.getName()+" have a new version "+ version);
                                                                          appItem.setVersion(version);
                                                                          sendEmail.send(props, appItem.getName(), appItem.getVersion());
                                                                          System.out.println(appItem.getName()+" have a new version "+ version);
                                                                      }
                                                                      Thread.sleep(10000);
                                                                  } catch (InterruptedException ex){
                                                                      ex.printStackTrace();
                                                                  } catch (MessagingException e) {
                                                                      e.printStackTrace();
                                                                  }
                                                              }

                                                      }
                                                  },
                        5,
                        60,
                        TimeUnit.SECONDS);
    }
}
