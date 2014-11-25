package MyPlanner.controller;

import MyPlanner.exceptions.NotAuthorizedException;
import MyPlanner.exceptions.UserInfoNotSetException;
import MyPlanner.model.Course;
import MyPlanner.model.LoginInfo;
import MyPlanner.service.CanvasApi;
import MyPlanner.service.LoginInfoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/user")
public class HomeController {
    @Autowired
    LoginInfoRepo loginInfoRepo;
    @Autowired
    CanvasApi canvasApi;

    @RequestMapping("/profile")
    public ModelAndView profilePage(HttpServletRequest request) throws NotAuthorizedException {
        LoginInfo loginInfo = getLoginInfo(request);

        if(checkLogin(loginInfo)){
            ModelAndView model = new ModelAndView("profile");
            model.addObject("loginInfo", loginInfo);
            model.addObject("courses", canvasApi.getCourses(request));

            return model;
        }else{
            throw new NotAuthorizedException();
        }
    }

    private boolean checkLogin(LoginInfo loginInfo){
        if(loginInfo == null || !loginInfo.hasValues()){
            return false;
        }else{
            return true;
        }
    }

    private LoginInfo getLoginInfo(HttpServletRequest request){
        LoginInfo loginInfo = (LoginInfo)request.getSession().getAttribute("loginInfo");
        return loginInfo;
    }
}
