package MyPlanner.controller;

import MyPlanner.dao.CourseDao;
import MyPlanner.dao.ModuleDao;
import MyPlanner.exceptions.NotAuthorizedException;
import MyPlanner.model.*;
import MyPlanner.service.CanvasApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/rest")
public class RestController {

    @Autowired
    CanvasApi canvasApi;

    @Autowired
    private CourseDao courseDao;
    @Autowired
    private ModuleDao moduleDao;

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

    private void updateCoursesAndModules(@RequestBody List<Course> courses){
        for(Course c : courses){
            courseDao.save(c);
        }
    }
}
