package db.entities;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "application_info_android")
public class PlayStoreApplicationInfo extends BaseApplicationInfo{
}
