package model;

import java.io.Serializable;

/**
 * Created by Emil Makovac on 15/03/2015.
 */
public class Post implements Serializable {

    String id, title, date, description;
    Location location;

    public Post() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Post(String id, String title, String date, String description, Location location) {
        this.id = id;
        this.title = title;

        this.date = date;
        this.description = description;
        this.location = location;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
