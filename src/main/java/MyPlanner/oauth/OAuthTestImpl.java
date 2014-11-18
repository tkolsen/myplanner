package MyPlanner.oauth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.GrantType;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.social.oauth2.OAuth2Template;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class OAuthTestImpl implements OAuth{
    @Autowired
    Environment env;

    OAuth2Template oAuth2Template;

    @Override
    public void exchangeCodeForToken(String code, HttpServletRequest request) throws InstantiationException {
        String base = env.getProperty("provider.accessTokenUrl");
        RestTemplate restTemplate = new RestTemplate();
        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put("client_id", getClientID());
        parameters.put("redirect_uri", getRedirectUrl());
        parameters.put("client_secret", getClientSecret());
        parameters.put("code", code);
        String tokenResponse = restTemplate.postForObject(base, request, String.class, parameters);
        request.getSession().setAttribute("tokenResponse", tokenResponse);
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
