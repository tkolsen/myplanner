package MyPlanner.controller;

import MyPlanner.exceptions.NotAuthorizedException;
import MyPlanner.model.LoginInfo;
import MyPlanner.service.LoginInfoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/user")
public class HomeController {
    @Autowired
    LoginInfoRepo loginInfoRepo;

    @RequestMapping("/profile")
    public ModelAndView profilePage(HttpServletRequest request) throws Exception {
        LoginInfo loginInfo = getLoginInfo(request);

        if(checkLogin(loginInfo)){
            ModelAndView model = new ModelAndView("profile");
            model.addObject("loginInfo", loginInfo);
            return model;
        }else{
            throw new NotAuthorizedException();
        }
    }

    private boolean checkLogin(LoginInfo loginInfo){
        if(loginInfo != null && loginInfo.hasValues()){
            return true;
        }else{
            return false;
        }
    }

    private LoginInfo getLoginInfo(HttpServletRequest request){
        LoginInfo loginInfo = (LoginInfo)request.getSession().getAttribute("loginInfo");
        return loginInfo;
    }
}
