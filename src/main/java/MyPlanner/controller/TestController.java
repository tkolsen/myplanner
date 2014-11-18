package MyPlanner.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.social.oauth2.GrantType;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.social.oauth2.OAuth2Template;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
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

    public ModelAndView start(){
        return new ModelAndView("test/start");
    }

    public void login(HttpServletResponse response) throws IOException {
        // OAuth2Template(clientId, clientSecret, authorizeUrl, accessTokenUrl)
        OAuth2Template oAuth2Template = new OAuth2Template(env.getProperty("client.id"),
                env.getProperty("client.secret"), env.getProperty("provider.authorizeUrl"), env.getProperty("provider.accessTokenUrl"));

        OAuth2Parameters oAuth2Parameters = new OAuth2Parameters();
        oAuth2Parameters.setRedirectUri(env.getProperty("client.redirect"));
        oAuth2Parameters.set("scopes", env.getProperty("scope.userinfo"));

        String url = oAuth2Template.buildAuthenticateUrl(GrantType.AUTHORIZATION_CODE, oAuth2Parameters);

        response.sendRedirect(url);
    }

    public ModelAndView redirect(HttpServletRequest request){
        ModelAndView model = new ModelAndView();
        List<String> list = Collections.list(request.getParameterNames());

        model.addObject("params", list);
        model.setViewName("test/params");
        return model;
    }

}
