package MyPlanner.controller;

import MyPlanner.api.CanvasApiHIST;
import MyPlanner.interfaces.CanvasApiInterface;
import MyPlanner.model.CanvasCourse;
import MyPlanner.model.CanvasUser;
import MyPlanner.utils.AngularjsHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.social.oauth2.OAuth2Template;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/canvas")
public class CanvasOauthController {
    @Autowired
    private CanvasApiInterface canvasApi;
    @Autowired
    private OAuth2Template oAuth2Template;
    @Autowired
    private Environment env;

    @RequestMapping("/login")
    public ModelAndView login(){
        Map<String, Object> model = new HashMap<String, Object>();

        return new ModelAndView("login", model);
    }

    @RequestMapping("/oauth")
    public void oauthStart(HttpServletResponse response) throws IOException{
        OAuth2Parameters oAuth2Parameters = new OAuth2Parameters();
        oAuth2Parameters.setRedirectUri(env.getProperty("client.redirect"));
        String codeUrl = oAuth2Template.buildAuthenticateUrl(oAuth2Parameters);

        response.sendRedirect(codeUrl);
    }

    @RequestMapping("/redirect")
    public void redirect(HttpServletRequest request, HttpServletResponse response) throws IOException{
        OAuth2Parameters oAuth2Parameters = new OAuth2Parameters();
        AccessGrant accessGrant = oAuth2Template.exchangeForAccess(request.getParameter("code"), env.getProperty("client.redirect"), oAuth2Parameters);

        response.addCookie(new Cookie("accessToken", accessGrant.getAccessToken()));
        response.sendRedirect("/MyPlanner/canvas/user");
    }

    // TODO: Se på andre muligheter en cookie
    @RequestMapping("/user")
    public ModelAndView profile(@CookieValue(value="accessToken", required = true) String accessToken) {

        Map<String, Object> model = new HashMap<String, Object>();

        // Henter brukerprofil.
        CanvasUser user = null;
        try {
            user = canvasApi.getProfile(accessToken);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Henter kurs brukeren er medlem i.
        CanvasCourse[] courses = null;
        try {
            courses = canvasApi.listCourses(accessToken);
        } catch (Exception e) {
            e.printStackTrace();
        }
        model.put("profile", user);
        model.put("courses", AngularjsHelper.printArray(courses, "courses"));

        return new ModelAndView("canvas-profile", model);
    }

    // TODO: Dette er kunn en testmetode. Fjern etter testing.
    @RequestMapping("/test")
    public @ResponseBody CanvasUser testUser(@CookieValue(value="accessToken", required = true) String accessToken){
        CanvasUser user = null;
        try {
            user = canvasApi.getProfile(accessToken);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return user;
    }

}
