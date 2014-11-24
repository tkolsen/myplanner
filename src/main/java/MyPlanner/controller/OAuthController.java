package MyPlanner.controller;

import MyPlanner.exceptions.UserInfoNotSetException;
import MyPlanner.model.LoginInfo;
import MyPlanner.oauth.OAuth2;
import MyPlanner.service.LoginInfoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/oauth")
public class OAuthController {
    @Autowired
    Environment env;
    @Autowired
    OAuth2 oAuth;
    @Autowired
    LoginInfoRepo loginInfoRepo;

    @RequestMapping("/userInfo")
    public void login(HttpServletResponse response) throws IOException {
        String confirmationUrl = oAuth.askForUserInfoConfirmation();
        response.sendRedirect(confirmationUrl);
    }

    @RequestMapping("/redirectUserInfo")
    public String redirectUserInfo(HttpServletRequest request,
                           @RequestParam(value="code", required = true)String code) throws UserInfoNotSetException {
        LoginInfo loginInfo = oAuth.exchangeCodeForUserInfo(code);
        if(loginInfo.hasValues()){
            request.getSession().setAttribute("loginInfo", loginInfo);
            // TODO: dummy repo
            loginInfoRepo.saveUser(loginInfo);

            return "redirect:token";
        }else{
            throw new UserInfoNotSetException("User info was not set in /redirect");
        }
    }

    @RequestMapping("/token")
    public void getTokenConfirmation(HttpServletResponse response) throws IOException {
        String confirmationUrl = oAuth.askForAccessTokenConfirmation();
        response.sendRedirect(confirmationUrl);
    }

    @RequestMapping("/redirectAccessToken")
    public String redirectAccessToken(HttpServletResponse response,
                                    HttpServletRequest request,
                                    @RequestParam(value = "code", required = true)String code){
        String accessToken = oAuth.exchangeCodeForAccessToken(code);
        ((LoginInfo)request.getSession().getAttribute("loginInfo")).setAccessToken(accessToken);
        return "redirect:/user/profile";
    }

}
