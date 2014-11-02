package MyPlanner.controller;

import MyPlanner.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.social.oauth2.OAuth2Template;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/testing")
public class TestController {

    @Autowired
    RestTemplate restTemplate;
    @Autowired
    OAuth2Template oAuth2Template;
    @Autowired
    Environment env;

    @RequestMapping("/")
    public ModelAndView home(){
        return new ModelAndView("test-home");
    }

    @RequestMapping("/login")
    public void login(HttpServletResponse response,
                      @CookieValue(value="accessToken", required = false) String accessToken,
                      @CookieValue(value="id", required = false) String id) throws IOException{

        if(accessToken != null && id != null){
            response.sendRedirect("/testing/user");
        }else{
            OAuth2Parameters oAuth2Parameters = new OAuth2Parameters();
            oAuth2Parameters.setScope("profile");

            String codeUrl = oAuth2Template.buildAuthenticateUrl(oAuth2Parameters);

            response.sendRedirect(codeUrl);
        }
    }

    @RequestMapping("/redirect")
    public void redirect(HttpServletResponse response, HttpServletRequest request) throws IOException {
        OAuth2Parameters oAuth2Parameters = new OAuth2Parameters();
        AccessGrant accessGrant = oAuth2Template.exchangeForAccess(request.getParameter("code"), env.getProperty("client.redirect"), oAuth2Parameters);

        response.addCookie(new Cookie("accessToken", accessGrant.getAccessToken()));

        response.sendRedirect("/testing/user");
    }

    @RequestMapping("/user")
    public ModelAndView userProfile(HttpServletResponse response,
                                    @CookieValue(value="accessToken", required = false) String accessToken){

        Map<String, Object> model = new HashMap<String, Object>();
        User user = getUserObject(accessToken);
        model.put("accessToken", accessToken);
        model.put("user", user);

        // Id brukes på alle andre endpoint, så legger den til cookie
        // TODO: cookie er kanskje ikke smart. Heller bruk session objects
        response.addCookie(new Cookie("id", user.getId()));

        return new ModelAndView("user", model);
    }

    // TODO: Slike metoder kan være i en egen api-klasse
    private User getUserObject(String accessToken){
        String endpoint = "http://coop.apps.knpuniversity.com/api/me";
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        HttpEntity<String> entity = new HttpEntity<String>(headers);

        // Henter user fra knp og parser den til et User-objekt
        ResponseEntity<User> responseEntity = restTemplate.exchange(endpoint, HttpMethod.GET, entity, User.class);
        User user = responseEntity.getBody();

        return user;
    }

}
