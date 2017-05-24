package events;

import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;

import db.entities.AppStoreApplicationInfo;

/**
 * @author georgiy.hotiyan@masterdata.ru
 */
@Component
@RepositoryEventHandler(AppStoreApplicationInfo.class)
public class AppStoreRepositoryHandler {
  @HandleBeforeSave
  public void handleBeforeSave(AppStoreApplicationInfo appStoreApplicationInfo) {

    System.out.println("Saving AppStoreApplicationInfo " + appStoreApplicationInfo.toString());

  }
}
