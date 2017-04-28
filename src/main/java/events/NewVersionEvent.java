package events;

import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;

/**
 * @author georgiy.hotiyan@masterdata.ru
 */
public class NewVersionEvent extends ApplicationEvent {
  private String applicationName;
  private String version;

  /**
   * Create a new ApplicationEvent.
   *
   * @param source the object on which the event initially occurred (never {@code null})
   */
  public NewVersionEvent(Object source) {
    super(source);
  }

  public String getApplicationName() {
    return applicationName;
  }

  public void setApplicationName(String applicationName) {
    this.applicationName = applicationName;
  }

  public String getVersion() {
    return version;
  }

  public void setVersion(String version) {
    this.version = version;
  }
}
