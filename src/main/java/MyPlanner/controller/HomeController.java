package MyPlanner.controller;

import MyPlanner.exceptions.NotAuthorizedException;
import MyPlanner.model.Course;
import MyPlanner.model.LoginInfo;
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

@Controller
@RequestMapping("/user")
public class HomeController {
    @Autowired
    LoginInfoRepo loginInfoRepo;

    @RequestMapping("/profile")
    public ModelAndView profilePage(HttpServletRequest request) throws Exception {
        LoginInfo loginInfo = getLoginInfo(request);

        if(checkLogin(loginInfo)){
            ModelAndView model = new ModelAndView("profile");
            model.addObject("loginInfo", loginInfo);
            return model;
        }else{
            throw new NotAuthorizedException();
        }
    }

    @RequestMapping("/courses")
    public ModelAndView coursePage(HttpServletRequest request) throws NotAuthorizedException {
        LoginInfo loginInfo = getLoginInfo(request);

        if(checkLogin(loginInfo)){
            ModelAndView model = new ModelAndView("courses");

            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            restTemplate.getMessageConverters().add(new StringHttpMessageConverter());

            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", "Bearer: " + loginInfo.getAccessToken());

            HttpEntity<Course[]> requestEntity = new HttpEntity<Course[]>(headers);
            String url = "https://canvas.instructure.com/api/v1/courses";
            ResponseEntity<Course[]> resp = restTemplate.exchange(url, HttpMethod.GET, requestEntity, Course[].class, new HashMap<String, String>());
            Course[] courses = resp.getBody();

            model.addObject("courses", courses);
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
