package MyPlanner.controller;

import MyPlanner.exceptions.NotAuthorizedException;
import MyPlanner.model.LoginInfo;
import MyPlanner.oauth.OAuth;
import MyPlanner.service.LoginInfoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.GrantType;
import org.springframework.social.oauth2.OAuth2Parameters;
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
    @Autowired
    LoginInfoRepo loginInfoRepo;

    OAuth2Template oAuth2Template;

    @RequestMapping("/login")
    public void login(HttpServletResponse response) throws IOException, InstantiationException {
        oAuth.askForConfirmation(response);
    }

    @RequestMapping("/redirect")
    public String redirect(HttpServletRequest request) throws NotAuthorizedException, InstantiationException {
        LoginInfo userInfo = oAuth.exchangeCodeForToken(request.getParameter("code"), request);

        if(userInfo.hasValues()){
            request.getSession().setAttribute("loginInfo", userInfo);
            loginInfoRepo.saveUser(userInfo);

            return "redirect:/testing/token";
        }else {
            throw new NotAuthorizedException("Some info values where not set");
        }
    }

    @RequestMapping("/token")
    public void getToken(HttpServletResponse response) throws IOException {
        OAuth2Parameters oAuth2Parameters = new OAuth2Parameters();
        oAuth2Parameters.setRedirectUri("http://myplanner.aitel.hist.no/MyPlanner/testing/redirect2");

        oAuth2Template = new OAuth2Template(env.getProperty("client.id"), env.getProperty("client.secret"),
                env.getProperty("provider.authorizeUrl"), env.getProperty("provider.accessTokenUrl"));

        String codeUrl = oAuth2Template.buildAuthorizeUrl(GrantType.AUTHORIZATION_CODE, oAuth2Parameters);
        response.sendRedirect(codeUrl);
    }

    @RequestMapping("/redirect2")
    public String redirect2(HttpServletResponse response, HttpServletRequest request){
        AccessGrant accessGrant = oAuth2Template.exchangeForAccess(request.getParameter("code"), "http://myplanner.aitel.hist.no/MyPlanner/testing/redirect2", null);
        String accessToken = accessGrant.getAccessToken();

        LoginInfo loginInfo = (LoginInfo)request.getSession().getAttribute("loginInfo");
        loginInfo.setAccessToken(accessToken);
        request.getSession().setAttribute("loginInfo", loginInfoRepo);

        return "redirect:/user/profile";
    }

}
