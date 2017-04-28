package events;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

/**
 * @author georgiy.hotiyan@masterdata.ru
 */
@Component
public class NewVersionEventProducer {
  private final ApplicationEventPublisher publisher;

  @Autowired
  public NewVersionEventProducer(ApplicationEventPublisher publisher) {
    this.publisher = publisher;
  }

  public void createNewVersionEvent(String applicationName, String version){
    NewVersionEvent newVersionEvent = new NewVersionEvent(this);
    newVersionEvent.setApplicationName(applicationName);
    newVersionEvent.setVersion(version);
    publisher.publishEvent(newVersionEvent);
  }
}
