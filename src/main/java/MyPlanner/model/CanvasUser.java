package MyPlanner.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CanvasUser {
    @JsonProperty("id")
    private long id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("login_id")
    private String loginId;

    public CanvasUser(){}

    public CanvasUser(long id, String name, String loginId) {
        this.id = id;
        this.name = name;
        this.loginId = loginId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }
}
