package MyPlanner.service;

import MyPlanner.exceptions.NotAuthorizedException;
import MyPlanner.exceptions.UserInfoNotSetException;
import MyPlanner.model.Course;
import MyPlanner.model.LoginInfo;
import MyPlanner.model.Module;
import MyPlanner.model.ModuleItem;
import org.springframework.http.*;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CanvasApiImpl implements CanvasApi{

    public List<Course> getCourses(HttpServletRequest request) throws NotAuthorizedException {
        LoginInfo loginInfo = (LoginInfo)request.getSession().getAttribute("loginInfo");

        if(loginInfo != null && loginInfo.hasValues() && loginInfo.getAccessToken() != null && !loginInfo.getAccessToken().isEmpty()) {

            HttpEntity<Course[]> requestEntity = new HttpEntity<Course[]>(setAuthorizationHeader(loginInfo.getAccessToken()));
            String url = "https://canvas.instructure.com/api/v1/courses";

            ResponseEntity<Course[]> resp = getRestTemplate().exchange(url, HttpMethod.GET, requestEntity, Course[].class, new HashMap<String, String>());
            Course[] courses = resp.getBody();

            for(Course c : courses){
                c.setModules(getModules(request, c.getId()));
            }

            return Arrays.asList(courses);
        }else{
            throw new NotAuthorizedException("Cant access api without user info");
        }
    }

    @Override
    public List<Module> getModules(HttpServletRequest request, int courseId) throws NotAuthorizedException {
        LoginInfo loginInfo = (LoginInfo)request.getSession().getAttribute("loginInfo");

        if(loginInfo != null && loginInfo.hasValues() && loginInfo.getAccessToken() != null){
            HttpEntity<Module[]> requestEntity = new HttpEntity<Module[]>(setAuthorizationHeader(loginInfo.getAccessToken()));
            String url = "https://canvas.instructure.com/api/v1/courses/" + courseId + "/modules";
            Map<String,String> parameters = new HashMap<String, String>();
            parameters.put("include[]", "items");
            ResponseEntity<Module[]> resp = getRestTemplate().exchange(url, HttpMethod.GET, requestEntity, Module[].class, parameters);
            Module[] modules = resp.getBody();
            for(Module m : modules){
                if(m.getItems() == null || m.getItems().size() == 0){
                    m.setItems(getItems(m.getId(), courseId, loginInfo.getUser().getId(), loginInfo.getAccessToken()));
                }
            }

            return Arrays.asList(modules);
        }else{
            throw new NotAuthorizedException("Can't access modules without user info");
        }
    }

    // /api/v1/courses/:course_id/modules/:module_id/items
    public List<ModuleItem> getItems(int moduleId, int courseId, int studentId, String accessToken){
        HttpEntity<ModuleItem[]> requestEntity = new HttpEntity<ModuleItem[]>(setAuthorizationHeader(accessToken));
        String url = "https://canvas.instructure.com/api/v1/courses/"+courseId+"/modules/"+moduleId+"/items";
        Map<String,String> params = new HashMap<String, String>();
        params.put("student_id", ""+studentId);
        ResponseEntity<ModuleItem[]> resp = getRestTemplate().exchange(url, HttpMethod.GET, requestEntity, ModuleItem[].class, params);
        ModuleItem[] moduleItems = resp.getBody();

        return Arrays.asList(moduleItems);
    }

    private RestTemplate getRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());

        return restTemplate;
    }

    private HttpHeaders setAuthorizationHeader(String accessToken){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        return headers;
    }
}
