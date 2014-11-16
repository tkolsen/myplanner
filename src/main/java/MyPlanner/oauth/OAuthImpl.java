package MyPlanner.oauth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.GrantType;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.social.oauth2.OAuth2Template;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// TODO: Maybe move all the property getters out
public class OAuthImpl implements OAuth {

    @Autowired
    Environment env;
    @Autowired
    AccessGrant accessGrant;

    OAuth2Template oAuth2Template;

    @Override
    public void exchangeCodeForToken(String code) throws InstantiationException {
        if(oAuth2Template != null){
            accessGrant = oAuth2Template.exchangeForAccess(code, getRedirectUrl(), new OAuth2Parameters());
            if(accessGrant.getAccessToken() == null) throw new IllegalStateException("access token not set");
        }else{
            throw new IllegalStateException("oAuthTemplate not set");
        }
    }

    @Override
    public void askForConfirmation(HttpServletResponse response) throws IOException, InstantiationException {
        oAuth2Template =
                new OAuth2Template(getClientID(), getClientSecret(), getAuthorizeUrl(), getAccessTokenUrl());
        String url = oAuth2Template.buildAuthorizeUrl(GrantType.AUTHORIZATION_CODE, new OAuth2Parameters());
        response.sendRedirect(url);
    }

    @Override
    public String getRedirectUrl() throws InstantiationException {
        String url = env.getProperty("client.redirect");
        if(url != null || !url.isEmpty()){
            return url;
        }else{
            throw new InstantiationException("Can't access client.redirect in properties file");
        }
    }

    @Override
    public String getClientID() throws InstantiationException {
        String clientId = env.getProperty("client.id");
        if(clientId != null || !clientId.isEmpty()){
            return clientId;
        }else{
            throw new InstantiationException("Can't access client.id in properties file");
        }
    }

    @Override
    public String getClientSecret() throws InstantiationException {
        String clientSecret = env.getProperty("client.secret");
        if(clientSecret != null || !clientSecret.isEmpty()){
            return clientSecret;
        }else{
            throw new InstantiationException("Can't access client.secret in properties file");
        }
    }

    @Override
    public String getAuthorizeUrl() throws InstantiationException {
        String url = env.getProperty("provider.authorizeUrl");
        if(url != null || !url.isEmpty()){
            return url;
        }else{
            throw new InstantiationException("Can't access provider.authorizeUrl in properties file");
        }
    }

    @Override
    public String getAccessTokenUrl() throws InstantiationException {
        String url = env.getProperty("provider.accessTokenUrl");
        if(url != null || !url.isEmpty()){
            return url;
        }else{
            throw new InstantiationException("Can't access provider.accessTokenUrl in properties file");
        }
    }

}
