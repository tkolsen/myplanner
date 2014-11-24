package MyPlanner.oauth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

public class CanvasProperties {

    @Autowired
    Environment env;

    private final String CLIENT_ID;
    private final String CLIENT_SECRET;
    private final String CLIENT_REDIRECT;
    private final String CLIENT_REDIRECT_2;
    private final String ACCESS_TOKEN_URL;
    private final String AUTHORIZATION_URL;
    private final String SCOPE_USER_INFO;

    public CanvasProperties(){
        CLIENT_ID = env.getProperty("client.id");
        CLIENT_SECRET = env.getProperty("client.secret");
        CLIENT_REDIRECT = env.getProperty("client.redirect");
        ACCESS_TOKEN_URL = env.getProperty("provider.accessTokenUrl");
        AUTHORIZATION_URL = env.getProperty("provider.authorizeUrl");
        SCOPE_USER_INFO = env.getProperty("scope.userinfo");
        CLIENT_REDIRECT_2 = env.getProperty("client.redirect2");
    }

    public String getClientID() {
        return CLIENT_ID;
    }

    public String getClientSecret() {
        return CLIENT_SECRET;
    }

    public String getClientRedirect() {
        return CLIENT_REDIRECT;
    }

    public String getAccessTokenUrl() {
        return ACCESS_TOKEN_URL;
    }

    public String getAuthorizeUrl() {
        return AUTHORIZATION_URL;
    }

    public String getScopeUserInfo() {
        return SCOPE_USER_INFO;
    }

    public String getClientRedirect2(){return CLIENT_REDIRECT_2;}
}
