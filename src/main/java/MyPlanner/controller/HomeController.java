package MyPlanner.controller;

import MyPlanner.exceptions.NotAuthorizedException;
import MyPlanner.model.Course;
import MyPlanner.model.LoginInfo;
import MyPlanner.model.UserHasModule;
import MyPlanner.service.CanvasApi;
import MyPlanner.service.LoginInfoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/user")
public class HomeController {
    @Autowired
    LoginInfoRepo loginInfoRepo;
    @Autowired
    CanvasApi canvasApi;

    @RequestMapping("/login")
    public ModelAndView loginPage(){
        return new ModelAndView("login");
    }

    @RequestMapping("/profile")
    public ModelAndView profilePage(HttpServletRequest request) throws NotAuthorizedException {
        LoginInfo loginInfo = getLoginInfo(request);

        if(checkLogin(loginInfo)){
            ModelAndView model = new ModelAndView("angular/profile");
            return model;
        }else{
            throw new NotAuthorizedException();
        }
    }

    @RequestMapping("/teacher")
    public ModelAndView teacherPage(@RequestParam List<UserHasModule> userHasModuleList, @RequestParam boolean onlyOldestDate){
        ModelAndView model = new ModelAndView("angular/teacher");
        model.addObject("userHasModuleList", userHasModuleList);
        model.addObject("oldestDate", onlyOldestDate);
        return model;
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

    @RequestMapping("/profile/refresh")
    public String refresh(HttpServletRequest request){
        request.getSession().setAttribute("courses", null);
        return "redirect:../profile";
    }
}