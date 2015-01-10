package MyPlanner.controller;

import MyPlanner.dao.UserHasModuleDao;
import MyPlanner.exceptions.NotAuthorizedException;
import MyPlanner.model.LoginInfo;
import MyPlanner.model.UserHasModule;
import MyPlanner.service.CanvasApi;
import MyPlanner.service.LoginInfoRepo;
import MyPlanner.utils.DeadlineCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.util.List;

@Controller
@RequestMapping("/user")
public class HomeController {
    @Autowired
    LoginInfoRepo loginInfoRepo;
    @Autowired
    CanvasApi canvasApi;
    @Autowired
    private UserHasModuleDao userHasModuleDao;

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
    public ModelAndView teacherPage(){
        ModelAndView model = new ModelAndView("angular/teacher");

        List<UserHasModule> userHasModuleList = userHasModuleDao.list();
        for(UserHasModule u : userHasModuleList)
            System.out.println(u);
        boolean test = false; // TODO: koble denne til onlyOldestDate
        java.util.Date utilDate = new java.util.Date();
        Date date = new Date(utilDate.getTime());

        DeadlineCheck deadlineCheck = new DeadlineCheck();
        List<UserHasModule> result;
        if(test){
            result = deadlineCheck.ListOldestUnmetDeadlines(userHasModuleList, date);
        }else{
            result = deadlineCheck.ListAllUnmetDeadlines(userHasModuleList, date);
            for(UserHasModule u : result)
                System.out.println(u);
        }
        model.addObject("list", result);

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