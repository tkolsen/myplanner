package MyPlanner.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserInfo {
    @JsonProperty("name")
    private String name;
    @JsonProperty("id")
    private String id;

    public UserInfo(){
        
    }

    public UserInfo(String name, String id) {
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
