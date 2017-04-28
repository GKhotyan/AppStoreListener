package events;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * @author georgiy.hotiyan@masterdata.ru
 */
@Component
public class NewVersionEventListener implements ApplicationListener<NewVersionEvent> {
  public void onApplicationEvent(NewVersionEvent event) {
    System.out.println(event.toString());
  }
}
