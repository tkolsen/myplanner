package MyPlanner.controller;

import MyPlanner.oauth.OAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.social.oauth2.OAuth2Template;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

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

    @RequestMapping("/start")
    public ModelAndView start(){
        return new ModelAndView("test/start");
    }

    @RequestMapping("/login")
    public void login(HttpServletResponse response) throws IOException, InstantiationException {
        oAuth.askForConfirmation(response);
    }

    @RequestMapping("/redirect")
    public ModelAndView redirect(HttpServletRequest request) throws InstantiationException {
        oAuth.exchangeCodeForToken(request.getParameter("code"), request);
        ModelAndView model = new ModelAndView();
        model.addObject("tokenResponse", request.getSession().getAttribute("tokenResponse"));
        model.setViewName("profile");
        return model;
    }

}
