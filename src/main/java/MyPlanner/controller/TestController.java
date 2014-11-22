package MyPlanner.controller;

import MyPlanner.model.LoginInfo;
import MyPlanner.oauth.OAuth;
import MyPlanner.service.LoginInfoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
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

    @RequestMapping("/login")
    public void login(HttpServletResponse response) throws IOException, InstantiationException {
        oAuth.askForConfirmation(response);
    }

    @RequestMapping("/redirect")
    public void redirect(HttpServletRequest request, HttpServletResponse response) throws InstantiationException, IOException, Exception {
        LoginInfo userInfo = oAuth.exchangeCodeForToken(request.getParameter("code"), request);

        if(userInfo.hasValues()){
            request.getSession().setAttribute("loginInfo", userInfo);
            loginInfoRepo.saveUser(userInfo);

            ModelAndView model = new ModelAndView("profile");
            model.addObject("loginInfo", userInfo);
            response.sendRedirect("user/profile");
        }else {
            throw new Exception("LoginInfo doesn't have some of its values.");
        }
    }

}
