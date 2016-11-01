package db.entities;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "application_info")
public class AppStoreApplicationInfo extends BaseApplicationInfo{
}
