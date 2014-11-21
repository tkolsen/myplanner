package MyPlanner.controller;

import MyPlanner.oauth.OAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
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
    @Autowired
    OAuth oAuth;

    @RequestMapping("/login")
    public void login(HttpServletResponse response) throws IOException, InstantiationException {
        oAuth.askForConfirmation(response);
    }

    @RequestMapping("/redirect")
    public void redirect(HttpServletRequest request, HttpServletResponse response) throws InstantiationException, IOException {
        /*String code = request.getParameter("code");
        oAuth.exchangeCodeForToken(code, request);

        response.sendRedirect("/ok");*/

        String code = request.getParameter("code");
        RestTemplate restTemplate = new RestTemplate();
        final List<HttpMessageConverter<?>> converterList = new ArrayList<HttpMessageConverter<?>>();
        converterList.add(new FormHttpMessageConverter());
        converterList.add(new StringHttpMessageConverter());
        converterList.add(new MappingJackson2HttpMessageConverter());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<String> entity = new HttpEntity<String>(headers);

        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put("client_id", env.getProperty("client.id"));
        parameters.put("client_secret", env.getProperty("client.secret"));
        parameters.put("redirect_uri", env.getProperty("client.redirect"));
        parameters.put("code", code);
        ResponseEntity test = restTemplate.exchange(env.getProperty("provider.accessTokenUrl"), HttpMethod.POST, entity, String.class, parameters);
        String body = (String)test.getBody();
        System.out.println(body);

        //response.sendRedirect("/testing/ok");
    }

    @RequestMapping("/ok")
    public ModelAndView ok(){
        return new ModelAndView("test/params");
    }

}
