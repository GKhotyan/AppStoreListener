package db.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "simpleEntities")
public class SimpleEntity {
    @Id
    private
    int id;
    private String text;

    public SimpleEntity(){}

    public SimpleEntity(int id, String text){
        this.setId(id);
        this.setText(text);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
