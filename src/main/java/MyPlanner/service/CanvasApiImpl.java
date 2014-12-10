package MyPlanner.service;

import MyPlanner.exceptions.NotAuthorizedException;
import MyPlanner.exceptions.UserInfoNotSetException;
import MyPlanner.model.*;
import org.springframework.http.*;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

public class CanvasApiImpl implements CanvasApi{
    private String baseUrl = "https://hist.instructure.com";

    /**
     *
     * @param request
     * @return
     * @throws NotAuthorizedException
     */
    public List<Course> getCourses(HttpServletRequest request) throws NotAuthorizedException {
        LoginInfo loginInfo = (LoginInfo)request.getSession().getAttribute("loginInfo");

        if(loginInfo != null && loginInfo.hasValues() && loginInfo.getAccessToken() != null && !loginInfo.getAccessToken().isEmpty()) {

            HttpEntity<Course[]> requestEntity = new HttpEntity<Course[]>(setAuthorizationHeader(loginInfo.getAccessToken()));
            String url = baseUrl + "/api/v1/courses";

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

    /**
     *
     * @param request
     * @param courseId
     * @return
     * @throws NotAuthorizedException
     */
    @Override
    public List<Module> getModules(HttpServletRequest request, int courseId) throws NotAuthorizedException {
        LoginInfo loginInfo = (LoginInfo)request.getSession().getAttribute("loginInfo");

        if(loginInfo != null && loginInfo.hasValues() && loginInfo.getAccessToken() != null){
            HttpEntity<Module[]> requestEntity = new HttpEntity<Module[]>(setAuthorizationHeader(loginInfo.getAccessToken()));
            String url = baseUrl + "/api/v1/courses/" + courseId + "/modules?per_page=50&include[]=items";
            Map<String,Integer> parameters = new HashMap<String, Integer>();
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

    @Override
    public List<CalendarEvent> getCalendarEvents(HttpServletRequest request, String startDate, String endDate) throws NotAuthorizedException {
        LoginInfo loginInfo = (LoginInfo)request.getSession().getAttribute("loginInfo");

        if(loginInfo != null && loginInfo.hasValues() && loginInfo.getAccessToken() != null){
            HttpEntity<CalendarEvent[]> requestEntity = new HttpEntity<CalendarEvent[]>(setAuthorizationHeader(loginInfo.getAccessToken()));
            String url = baseUrl + "/api/v1/calendar_events";
            Map<String,String> parameters = new HashMap<String, String>();
            // TODO: <String, Date> hashmap?
            parameters.put("start_date", "start");
            parameters.put("end_date", "end");
            ResponseEntity<CalendarEvent[]> resp = getRestTemplate().exchange(url, HttpMethod.GET, requestEntity, CalendarEvent[].class, parameters);
            CalendarEvent[] calendarEvents = resp.getBody();
            // TODO: Filter by startDate -> endDate
            /*for(CalendarEvent c : calendarEvents){
                if(startDate >= c.getStartAt() && endDate <= c.getEndAt()){
                    // add to list
                }
            }*/

            return Arrays.asList(calendarEvents);
        }else{
            throw new NotAuthorizedException("Can't access calendar events without user info");
        }
    }

    /**
     *
     * @param moduleId
     * @param courseId
     * @param studentId
     * @param accessToken
     * @return
     */
    public List<ModuleItem> getItems(int moduleId, int courseId, int studentId, String accessToken){
        HttpEntity<ModuleItem[]> requestEntity = new HttpEntity<ModuleItem[]>(setAuthorizationHeader(accessToken));
        String url = baseUrl + "/api/v1/courses/"+courseId+"/modules/"+moduleId+"/items";
        Map<String,String> params = new HashMap<String, String>();
        params.put("student_id", ""+studentId);
        ResponseEntity<ModuleItem[]> resp = getRestTemplate().exchange(url, HttpMethod.GET, requestEntity, ModuleItem[].class, params);
        ModuleItem[] moduleItems = resp.getBody();
        return Arrays.asList(moduleItems);
    }

    /**
     *
     * @return
     */
    private RestTemplate getRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());

        return restTemplate;
    }

    /**
     *
     * @param accessToken
     * @return
     */
    private HttpHeaders setAuthorizationHeader(String accessToken){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        return headers;
    }
}
