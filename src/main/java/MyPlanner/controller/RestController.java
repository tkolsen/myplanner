package MyPlanner.controller;

import MyPlanner.dao.CourseDao;
import MyPlanner.dao.ModuleDao;
import MyPlanner.dao.UserHasModuleDao;
import MyPlanner.exceptions.NotAuthorizedException;
import MyPlanner.model.*;
import MyPlanner.service.CanvasApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
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
    public @ResponseBody List<UserHasModule> getUserHasModuleList(){
        return userHasModuleDao.list();
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
