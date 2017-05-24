package events;

import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @author georgiy.hotiyan@masterdata.ru
 */
@Component
@Async
public class NewVersionEventListener implements ApplicationListener<NewVersionEvent> {
  public void onApplicationEvent(NewVersionEvent event) {
    System.out.println(event.toString());
    throw new IllegalArgumentException("hi");
  }
}
