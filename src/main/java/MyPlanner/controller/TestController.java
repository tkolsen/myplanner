package MyPlanner.controller;

import MyPlanner.model.UserInfo;
import MyPlanner.oauth.OAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.*;

import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/testing")
public class TestController {
    @Autowired
    Environment env;
    @Autowired
    OAuth oAuth;

    @RequestMapping("/login")
    public void login(HttpServletResponse response) throws IOException, InstantiationException {
        oAuth.askForConfirmation(response);
    }

    @RequestMapping("/redirect")
    public void redirect(HttpServletRequest request, HttpServletResponse response) throws InstantiationException, IOException {
        String CLIENT_ID = env.getProperty("client.id");
        String CLIENT_SECRET = env.getProperty("client.secret");
        String CLIENT_REDIRECT = env.getProperty("client.redirect");
        String PROVIDER_ACCESS_TOKEN_URL = env.getProperty("provider.accessTokenUrl");

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String,String> body = new LinkedMultiValueMap<String, String>();
        body.add("client_id", CLIENT_ID);
        body.add("client_secret", CLIENT_SECRET);
        body.add("redirect_uri", CLIENT_REDIRECT);
        body.add("code", request.getParameter("code"));

        HttpEntity requestEntity = new HttpEntity(body, headers);

        ResponseEntity<UserInfo> result = restTemplate.exchange(PROVIDER_ACCESS_TOKEN_URL, HttpMethod.POST, requestEntity, UserInfo.class);
        userInfo = result.getBody();
        response.sendRedirect("ok");
    }
    UserInfo userInfo;

    @RequestMapping("/ok")
    public @ResponseBody UserInfo ok(HttpServletRequest request){
        return userInfo;
    }

}
