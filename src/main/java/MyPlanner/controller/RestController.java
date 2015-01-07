package MyPlanner.controller;

import MyPlanner.exceptions.NotAuthorizedException;
import MyPlanner.model.Course;
import MyPlanner.model.LoginInfo;
import MyPlanner.model.User;
import MyPlanner.model.UserHasModule;
import MyPlanner.service.CanvasApi;
import MyPlanner.utils.DeadlineCheck;
import MyPlanner.model.ScheduleDetails;
import MyPlanner.utils.ScheduleGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
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

    @RequestMapping(value="/generateSchedule", method = RequestMethod.GET)
    public @ResponseBody List<UserHasModule> generateSchedule(HttpServletRequest request, @RequestBody ScheduleDetails details) throws NotAuthorizedException {
        LoginInfo loginInfo = (LoginInfo)request.getSession().getAttribute("loginInfo");

        if(loginInfo == null || !loginInfo.hasValues() || loginInfo.getAccessToken() == null)
            throw new NotAuthorizedException();

        // for testing:
        System.out.println("CourseName: " + details.getModules().get(0).getCourse().getName() + " WorkHours: " + details.getWorkHoursDaily());

        ScheduleGenerator sg = new ScheduleGenerator();
        User user = loginInfo.getUser();
        List<UserHasModule> schedule = sg.GenerateSchedule(user, details.getModules(), details.getWorkHoursDaily(), details.getStartDate());

        return schedule;

    }

    @RequestMapping("/checkAllDeadlines")
    public @ResponseBody List<UserHasModule> getAllDeadlines(HttpServletRequest request, List<UserHasModule> deadlines, Date date) throws NotAuthorizedException {
        LoginInfo loginInfo = (LoginInfo) request.getSession().getAttribute("loginInfo");

        if (loginInfo == null || !loginInfo.hasValues() || loginInfo.getAccessToken() == null)
            throw new NotAuthorizedException();

        DeadlineCheck dc = new DeadlineCheck();
        List<UserHasModule> allDeadlines = dc.ListAllUnmetDeadlines(deadlines, date);
        return allDeadlines;
    }

    @RequestMapping("/checkOldestDeadlines")
    public @ResponseBody List<UserHasModule> getOldestDeadlines(HttpServletRequest request, List<UserHasModule> deadlines, Date date) throws NotAuthorizedException {
        LoginInfo loginInfo = (LoginInfo)request.getSession().getAttribute("loginInfo");

        if(loginInfo == null || !loginInfo.hasValues() || loginInfo.getAccessToken() == null)
            throw new NotAuthorizedException();

        DeadlineCheck dc = new DeadlineCheck();
        List<UserHasModule> oldestDeadlines = dc.ListOldestUnmetDeadlines(deadlines, date);
        return oldestDeadlines;
    }
}
