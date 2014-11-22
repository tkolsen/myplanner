package MyPlanner.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class User {
    @JsonProperty("access_token")
    private String accessToken;
    private UserInfo userInfo;

    public User() {
    }

    public User(String accessToken, UserInfo userInfo) {
        this.accessToken = accessToken;
        this.userInfo = userInfo;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

}
