package MyPlanner.controller;

import MyPlanner.dao.CourseDao;
import MyPlanner.dao.ModuleDao;
import MyPlanner.dao.UserHasModuleDao;
import MyPlanner.exceptions.NotAuthorizedException;
import MyPlanner.model.*;
import MyPlanner.service.CanvasApi;
import MyPlanner.utils.DeadlineCheck;
import MyPlanner.utils.ScheduleGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

import java.sql.Date;

import java.util.ArrayList;

import java.util.List;

@Controller
@RequestMapping("/rest")
public class RestController {

    @Autowired
    private CanvasApi canvasApi;
    @Autowired
    private CourseDao courseDao;
    @Autowired
    private ModuleDao moduleDao;
    @Autowired
    private UserHasModuleDao userHasModuleDao;

    @RequestMapping(value = "/updateDates", method = RequestMethod.PUT)
    public ResponseEntity<String> updateDates(@RequestBody UserHasModule userHasModule){
        userHasModuleDao.update(userHasModule);
        return new ResponseEntity<String>(HttpStatus.OK);
    }

    @RequestMapping(value = "/courses")
    public @ResponseBody List<Course> getCourses(HttpServletRequest request) throws NotAuthorizedException {
        LoginInfo loginInfo = (LoginInfo)request.getSession().getAttribute("loginInfo");

        if(loginInfo == null || !loginInfo.hasValues() || loginInfo.getAccessToken() == null)
            throw new NotAuthorizedException();

        List<Course> courseList = canvasApi.getCourses(request);
        request.getSession().setAttribute("courses", courseList);

        for(Course c : courseList){
            courseDao.save(c);
        }
        return courseList;
    }

    @RequestMapping(value = "/enrollments")
    public @ResponseBody List<Enrollment> getEnrollment(HttpServletRequest request) throws NotAuthorizedException {
        LoginInfo loginInfo = (LoginInfo)request.getSession().getAttribute("loginInfo");
        
        List<Enrollment> enrollmentList = canvasApi.getEnrollment(loginInfo.getUser(), loginInfo.getAccessToken());

        return enrollmentList;
    }

    @RequestMapping(value = "/modules")
    public @ResponseBody List<Module> getModules(HttpServletRequest request)throws NotAuthorizedException{
        LoginInfo loginInfo = (LoginInfo)request.getSession().getAttribute("loginInfo");

        if(loginInfo == null || !loginInfo.hasValues() || loginInfo.getAccessToken() == null)
            throw new NotAuthorizedException();

        List<Course> courseList = canvasApi.getCourses(request);
        ArrayList<Module> moduleList = null;
        for(Course c : courseList){
            ArrayList<Module> temp = canvasApi.getModulesAsArrayList(request, c);
            if(moduleList != null){
                moduleList.addAll(temp);
            }else{
                moduleList = temp;
            }
        }
        for(Module m : moduleList){
            moduleDao.save(m);
        }
        return moduleList;
    }

    @RequestMapping("/userHasModule")
    public @ResponseBody List<UserHasModule> getUserHasModuleList(HttpServletRequest request){
        // TODO: fix this. Is returning all userHasModules.
        LoginInfo loginInfo = (LoginInfo)request.getSession().getAttribute("loginInfo");
        List<UserHasModule> list = userHasModuleDao.list();
        List<UserHasModule> result = new ArrayList<UserHasModule>();
        for(UserHasModule u : list){
            if(u.getUser().getId()==loginInfo.getUser().getId()){
                result.add(u);
            }
        }
        return result;
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


    @RequestMapping(value="/generateSchedule", method = RequestMethod.POST)
    public @ResponseBody List<UserHasModule> generateSchedule(HttpServletRequest request, @RequestBody ScheduleDetails details) throws NotAuthorizedException {
       LoginInfo loginInfo = (LoginInfo)request.getSession().getAttribute("loginInfo");

        if(loginInfo == null || !loginInfo.hasValues() || loginInfo.getAccessToken() == null)
            throw new NotAuthorizedException();

        ScheduleGenerator sg = new ScheduleGenerator();
        User user = loginInfo.getUser();
        List<UserHasModule> schedule = sg.GenerateSchedule(user, details.getModules(), details.getWorkHoursDaily(), details.getStartDate());
        if(schedule!=null) {
            userHasModuleDao.updateList(schedule);
        }else {
            System.out.println("Schedule er NULL");
        }
        return schedule;
    }

    @RequestMapping(value="/checkAllDeadlines", method = RequestMethod.POST)
    public @ResponseBody List<UserHasModule> getAllDeadlines(HttpServletRequest request, @RequestBody DeadlineDetails details) throws NotAuthorizedException {
        LoginInfo loginInfo = (LoginInfo) request.getSession().getAttribute("loginInfo");

        if (loginInfo == null || !loginInfo.hasValues() || loginInfo.getAccessToken() == null)
            throw new NotAuthorizedException();

        DeadlineCheck dc = new DeadlineCheck();
        List<UserHasModule> allDeadlines = dc.ListAllUnmetDeadlines(details.getDeadlines(), details.getDate());
        return allDeadlines;
    }

    @RequestMapping(value="/checkOldestDeadlines", method = RequestMethod.POST)
    public @ResponseBody List<UserHasModule> getOldestDeadlines(HttpServletRequest request, @RequestBody DeadlineDetails details) throws NotAuthorizedException {
        LoginInfo loginInfo = (LoginInfo) request.getSession().getAttribute("loginInfo");

        if (loginInfo == null || !loginInfo.hasValues() || loginInfo.getAccessToken() == null)
            throw new NotAuthorizedException();

        DeadlineCheck dc = new DeadlineCheck();
        List<UserHasModule> oldestDeadlines = dc.ListOldestUnmetDeadlines(details.getDeadlines(), details.getDate());
        return oldestDeadlines;
    }
    
    private void updateCoursesAndModules(@RequestBody List<Course> courses){
        for(Course c : courses){
            courseDao.save(c);
        }
    }
}
