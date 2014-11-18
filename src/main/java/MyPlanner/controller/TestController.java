package MyPlanner.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.GrantType;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.social.oauth2.OAuth2Template;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/testing")
public class TestController {
    @Autowired
    Environment env;
    OAuth2Template oAuth2Template;

    @RequestMapping("/start")
    public ModelAndView start(){
        return new ModelAndView("test/start");
    }

    @RequestMapping("/login")
    public void login(HttpServletResponse response) throws IOException {
        // OAuth2Template(clientId, clientSecret, authorizeUrl, accessTokenUrl)
        oAuth2Template = new OAuth2Template(env.getProperty("client.id"),
                env.getProperty("client.secret"), env.getProperty("provider.authorizeUrl"), env.getProperty("provider.accessTokenUrl"));

        OAuth2Parameters oAuth2Parameters = new OAuth2Parameters();
        oAuth2Parameters.setRedirectUri(env.getProperty("client.redirect"));
        oAuth2Parameters.set("scopes", env.getProperty("scope.userinfo"));

        String url = oAuth2Template.buildAuthenticateUrl(GrantType.AUTHORIZATION_CODE, oAuth2Parameters);

        response.sendRedirect(url);
    }

    @RequestMapping("/redirect")
    public ModelAndView redirect(HttpServletRequest request){
        ModelAndView model = new ModelAndView();
        List<String> list = Collections.list(request.getParameterNames());

        model.addObject("params", list);
        model.setViewName("test/params");

        RestTemplate restTemplate = new RestTemplate();
        String base = env.getProperty("provider.accessTokenUrl");
        HttpHeaders headers = new HttpHeaders();
        HttpEntity requestEntity = new HttpEntity(headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(base + "?code="+request.getParameter("code"), HttpMethod.POST, requestEntity, String.class);
        String body = responseEntity.getBody();
        model.addObject("body", body);

        return model;
    }

}
