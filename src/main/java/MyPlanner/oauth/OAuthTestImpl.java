package MyPlanner.oauth;

import MyPlanner.model.LoginInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.GrantType;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.social.oauth2.OAuth2Template;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
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
    public void askForConfirmation(HttpServletResponse response) throws IOException, InstantiationException {
        OAuth2Parameters oAuth2Parameters = new OAuth2Parameters();
        oAuth2Parameters.setRedirectUri(getRedirectUrl());
        oAuth2Parameters.set("scopes", getScope());
        //oAuth2Parameters.set("force_login", "1");

        oAuth2Template = new OAuth2Template(getClientID(), getClientSecret(), getAuthorizeUrl(), getAccessTokenUrl());

        String codeUrl = oAuth2Template.buildAuthorizeUrl(GrantType.AUTHORIZATION_CODE, oAuth2Parameters);
        response.sendRedirect(codeUrl);
    }

    @Override
    public LoginInfo exchangeCodeForToken(String code, HttpServletRequest request) throws InstantiationException {
        // TODO: Move this to a more logical place
        // Initializing parameter values
        String CLIENT_ID = env.getProperty("client.id");
        String CLIENT_SECRET = env.getProperty("client.secret");
        String CLIENT_REDIRECT = env.getProperty("client.redirect");
        String PROVIDER_ACCESS_TOKEN_URL = env.getProperty("provider.accessTokenUrl");

        // Setting up the RestTemplate
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        // Setting up request headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        // Adding parameters to request body
        MultiValueMap<String,String> body = new LinkedMultiValueMap<String, String>();
        body.add("client_id", CLIENT_ID);
        body.add("client_secret", CLIENT_SECRET);
        body.add("redirect_uri", CLIENT_REDIRECT);
        body.add("code", code);

        // Making the request entity
        HttpEntity requestEntity = new HttpEntity(body, headers);

        // Posting for login info
        ResponseEntity<LoginInfo> result = restTemplate.exchange(PROVIDER_ACCESS_TOKEN_URL, HttpMethod.POST, requestEntity, LoginInfo.class);

        // Extracting and returning login info.
        LoginInfo loginInfo = result.getBody();
        return loginInfo;
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

    public String getScope() throws InstantiationException {
        String url = env.getProperty("scope.userinfo");
        if(url != null){
            return url;
        }else{
            throw new InstantiationException("Can't access provider.accessTokenUrl in properties file");
        }
    }
}
