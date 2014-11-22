package MyPlanner.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LoginInfo {
    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("user")
    private User user;

    public LoginInfo() {
    }

    public LoginInfo(String accessToken, User user) {
        this.accessToken = accessToken;
        this.user = user;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean hasValues(){
        if(user.getId() != null && user.getName() != null)
            return true;
        else
            return false;
    }

    @Override
    public String toString(){
        return "User:{name: " + user.getName() + ", id: " + user.getId() + "}";
    }

}
