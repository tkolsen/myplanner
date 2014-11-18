package MyPlanner.oauth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.GrantType;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.social.oauth2.OAuth2Template;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class OAuthTestImpl implements OAuth{
    @Autowired
    Environment env;

    OAuth2Template oAuth2Template;

    @Override
    public void exchangeCodeForToken(String code, HttpServletRequest request) throws InstantiationException {
        if(oAuth2Template != null){
            AccessGrant accessGrant = oAuth2Template.exchangeForAccess(code, getRedirectUrl(), new OAuth2Parameters());
            if(accessGrant.getAccessToken() != null){
                request.getSession().setAttribute("accessGrant", accessGrant);
            }else{
                throw new IllegalStateException("**** access token not set ****");
            }
        }
    }

    @Override
    public void askForConfirmation(HttpServletResponse response) throws IOException, InstantiationException {
        OAuth2Parameters parameters = new OAuth2Parameters();
        parameters.set("scopes", "/auth/userinfo");
        parameters.setRedirectUri(getRedirectUrl());

        oAuth2Template =
                new OAuth2Template(getClientID(), getClientSecret(), getAuthorizeUrl(), getAccessTokenUrl());
        String url = oAuth2Template.buildAuthenticateUrl(GrantType.AUTHORIZATION_CODE, parameters);
        response.sendRedirect(url);
    }

    @Override
    public String getRedirectUrl() throws InstantiationException {
        String url = env.getProperty("client.redirect");
        if(url != null){
            return url;
        }else{
            throw new InstantiationException("Can't access client.redirect in properties file");
        }
    }

    @Override
    public String getClientID() throws InstantiationException {
        String clientId = env.getProperty("client.id");
        if(clientId != null){
            return clientId;
        }else{
            throw new InstantiationException("Can't access client.id in properties file");
        }
    }

    @Override
    public String getClientSecret() throws InstantiationException {
        String clientSecret = env.getProperty("client.secret");
        if(clientSecret != null){
            return clientSecret;
        }else{
            throw new InstantiationException("Can't access client.secret in properties file");
        }
    }

    @Override
    public String getAuthorizeUrl() throws InstantiationException {
        String url = env.getProperty("provider.authorizeUrl");
        if(url != null){
            return url;
        }else{
            throw new InstantiationException("Can't access provider.authorizeUrl in properties file");
        }
    }

    @Override
    public String getAccessTokenUrl() throws InstantiationException {
        String url = env.getProperty("provider.accessTokenUrl");
        if(url != null){
            return url;
        }else{
            throw new InstantiationException("Can't access provider.accessTokenUrl in properties file");
        }
    }
}
