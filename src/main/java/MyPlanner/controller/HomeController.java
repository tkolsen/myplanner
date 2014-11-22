package MyPlanner.controller;

import MyPlanner.model.LoginInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/user")
public class HomeController {

    @RequestMapping("/profile")
    public ModelAndView profilePage(HttpServletRequest request) throws Exception {
        if(checkLogin(request)){
            ModelAndView model = new ModelAndView("profile");
            model.addObject("loginInfo", getLoginInfo(request));
            return model;
        }else{
            throw new Exception("Not authorized");
        }
    }

    private boolean checkLogin(HttpServletRequest request){
        LoginInfo loginInfo = getLoginInfo(request);

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
