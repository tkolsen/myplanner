package MyPlanner.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.social.oauth2.OAuth2Template;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {
    @Autowired
    Environment env;
    OAuth2Template template = null;

	@RequestMapping(value="/")
	public ModelAndView test(HttpServletResponse response) throws IOException{
		return new ModelAndView("home");
	}

    @RequestMapping(value="/oauth")
    public void oauthStart(HttpServletResponse response) throws IOException{
        template = new OAuth2Template(env.getProperty("client.id"), env.getProperty("client.secret"),
                env.getProperty("provider.authorizeUrl"), env.getProperty("provider.accessTokenUrl"));
        OAuth2Parameters params = new OAuth2Parameters();
        String authUrl = template.buildAuthenticateUrl(params);
        response.sendRedirect(authUrl);
    }

    @RequestMapping(value="/oauth/redirect")
    public ModelAndView oauthRedirect(HttpServletResponse response, HttpServletRequest request) throws IOException{


        return new ModelAndView("oauth");
    }

    @RequestMapping(value="/oauth/collect-eggs")
    public void collectEggs(HttpServletResponse response, @CookieValue("at") String token) throws IOException{
        RestTemplate restTemplate = new RestTemplate();

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("access_token", token);
        CustomRestResponse resp = restTemplate.getForObject("http://coop.apps.knpuniversity.com/api/me", CustomRestResponse.class, params);
        System.out.println(resp.toString());
    }
}
