import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created by user on 05.09.2016.
 */
public class Manager {
    public static void main(String[] args){
        System.out.println("Hello");
        List<AppItem> appItemList = new ArrayList<>();
        appItemList.add(new AppItem("Uber", "https://itunes.apple.com/ru/app/uber-lucse-cem-taksi/id368677368"));
        appItemList.add(new AppItem("Gett", "https://itunes.apple.com/ru/app/gett-gettaxi-zakaz-taksi-onlajn/id449655162"));
        appItemList.add(new AppItem("Delimobil", "https://itunes.apple.com/ru/app/delimobil/id1038254296"));
        appItemList.add(new AppItem("YouDrive", "https://itunes.apple.com/ru/app/youdrive/id989836024?l=en"));

//        SendEmail sendEmail = new SendEmail();
//        sendEmail.send("Uber", "2.4.5");
        ScheduledExecutorService scheduledExecutorService =
                Executors.newSingleThreadScheduledExecutor();

        ScheduledFuture scheduledFuture =
                scheduledExecutorService.scheduleAtFixedRate (new Runnable() {
                                                      public void run() {
                                                          try {
                                                              for (AppItem appItem : appItemList) {
                                                                  if (appItem.getVersion() == null) {
                                                                      appItem.setVersion("2.2.2");
                                                                      System.out.println(appItem.getName() + ". Version = " + appItem.getVersion());
                                                                  } else {
                                                                      System.out.println("Executed!");
                                                                  }
                                                                  Thread.sleep(10000);
                                                              }
                                                          } catch (InterruptedException ex){
                                                              ex.printStackTrace();
                                                          }
                                                      }
                                                  },
                        5,
                        60,
                        TimeUnit.SECONDS);
    }
}
