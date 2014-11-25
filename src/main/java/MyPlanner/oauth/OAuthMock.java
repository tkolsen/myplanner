package MyPlanner.oauth;

import MyPlanner.model.LoginInfo;
import MyPlanner.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import java.io.IOException;

public class OAuthMock implements OAuth2 {
    @Autowired
    Environment env;

    @Override
    public String askForUserInfoConfirmation() throws IOException {
        return "redirectUserInfo?code=test";
    }

    @Override
    public LoginInfo exchangeCodeForUserInfo(String code) {
        User user = new User("Tom Kristian Olsen", 12345);
        LoginInfo loginInfo = new LoginInfo();
        loginInfo.setUser(user);
        return loginInfo;
    }

    @Override
    public String askForAccessTokenConfirmation() throws IOException {
        return "redirectAccessToken?code=test";
    }

    @Override
    public String exchangeCodeForAccessToken(String code) {
        String accessToken = env.getProperty("testing.accesstoken");

        return accessToken;
    }
}
