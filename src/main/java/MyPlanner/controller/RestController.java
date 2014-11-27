package MyPlanner.controller;

import MyPlanner.exceptions.NotAuthorizedException;
import MyPlanner.model.Course;
import MyPlanner.model.LoginInfo;
import MyPlanner.model.User;
import MyPlanner.service.CanvasApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/rest")
public class RestController {

    @Autowired
    CanvasApi canvasApi;

    @RequestMapping("/courses")
    public @ResponseBody List<Course> getCourses(HttpServletRequest request) throws NotAuthorizedException {
        LoginInfo loginInfo = (LoginInfo)request.getSession().getAttribute("loginInfo");

        if(loginInfo == null || !loginInfo.hasValues() || loginInfo.getAccessToken() == null)
            throw new NotAuthorizedException();
        List<Course> courseList = canvasApi.getCourses(request);

        return courseList;
    }

    @RequestMapping("/userName")
    public @ResponseBody LoginInfo getUserName(HttpServletRequest request) throws NotAuthorizedException {
        LoginInfo loginInfo = (LoginInfo)request.getSession().getAttribute("loginInfo");

        if(loginInfo == null || !loginInfo.hasValues() || loginInfo.getAccessToken() == null)
            throw new NotAuthorizedException();
        LoginInfo returnInfo = new LoginInfo();
        User returnUser = new User(loginInfo.getUser().getName(), loginInfo.getUser().getId());
        returnInfo.setUser(returnUser);

        return returnInfo;
    }
}
