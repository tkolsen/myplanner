package MyPlanner.controller;

import MyPlanner.exceptions.NotAuthorizedException;
import MyPlanner.model.Course;
import MyPlanner.model.LoginInfo;
import MyPlanner.model.User;
import MyPlanner.model.UserHasModule;
import MyPlanner.service.CanvasApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/rest")
public class RestController {

    @Autowired
    CanvasApi canvasApi;
    @RequestMapping(value = "/updateDates", method = RequestMethod.POST)
    public void updateDates(@RequestBody UserHasModule userHasModule){
        System.out.println("userID: " + userHasModule.getUser().getId() + ", moduleId: " + userHasModule.getModule().getId() + ", startDate: " + userHasModule.getStartDate() + ", endDate: " + userHasModule.getEndDate());

    }

    @RequestMapping(value = "/courses")
    public @ResponseBody List<Course> getCourses(HttpServletRequest request) throws NotAuthorizedException {
        LoginInfo loginInfo = (LoginInfo)request.getSession().getAttribute("loginInfo");

        if(loginInfo == null || !loginInfo.hasValues() || loginInfo.getAccessToken() == null)
            throw new NotAuthorizedException();

        List<Course> courseList = (List<Course>)request.getSession().getAttribute("courses");
        if(courseList == null) {
            courseList = canvasApi.getCourses(request);
            request.getSession().setAttribute("courses", courseList);
            System.out.println("Fetching courses from instructure.");
        }
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
