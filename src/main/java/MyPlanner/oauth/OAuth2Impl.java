package MyPlanner.oauth;

import MyPlanner.model.LoginInfo;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.io.IOException;

public class OAuth2Impl implements OAuth2 {
    //@Autowired
    private CanvasProperties props = new CanvasProperties();

    private OAuth2Template oAuth2Template;

    @Override
    public String askForUserInfoConfirmation() throws IOException {
        OAuth2Parameters oAuth2Parameters = new OAuth2Parameters();
        oAuth2Parameters.setRedirectUri(props.getClientRedirect());
        oAuth2Parameters.set("scopes", props.getScopeUserInfo());

        oAuth2Template = new OAuth2Template(props.getClientID(), props.getClientSecret(), props.getAuthorizeUrl(), props.getAccessTokenUrl());

        String codeUrl = oAuth2Template.buildAuthorizeUrl(GrantType.AUTHORIZATION_CODE, oAuth2Parameters);
        return codeUrl;
    }

    @Override
    public LoginInfo exchangeCodeForUserInfo(String code){
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String,String> body = new LinkedMultiValueMap<String, String>();
        body.add("client_id", props.getClientID());
        body.add("client_secret", props.getClientSecret());
        body.add("redirect_uri", props.getClientRedirect());
        body.add("code", code);
        HttpEntity requestEntity = new HttpEntity(body, headers);

        ResponseEntity<LoginInfo> result = restTemplate.exchange(props.getAccessTokenUrl(), HttpMethod.POST, requestEntity, LoginInfo.class);
        LoginInfo loginInfo = result.getBody();

        return loginInfo;
    }

    @Override
    public String askForAccessTokenConfirmation() throws IOException {
        OAuth2Parameters oAuth2Parameters = new OAuth2Parameters();
        // TODO: legg denne til i properties fila
        oAuth2Parameters.setRedirectUri(props.getClientRedirect2());

        oAuth2Template = new OAuth2Template(props.getClientID(), props.getClientSecret(),
                props.getAuthorizeUrl(), props.getAccessTokenUrl());

        String codeUrl = oAuth2Template.buildAuthorizeUrl(GrantType.AUTHORIZATION_CODE, oAuth2Parameters);

        return codeUrl;
    }

    @Override
    public String exchangeCodeForAccessToken(String code){
        // TODO: legg denne til i properties fila
        AccessGrant accessGrant = oAuth2Template.exchangeForAccess(code, props.getClientRedirect2(), null);
        String accessToken = accessGrant.getAccessToken();

        return accessToken;
    }

}
