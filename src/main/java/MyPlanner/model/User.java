package MyPlanner.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class User {
    @JsonProperty("name")
    private String name;
    @JsonProperty("id")
    private String id;

    public User(){

    }

    public User(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
