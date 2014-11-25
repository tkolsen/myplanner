package MyPlanner.service;

import MyPlanner.exceptions.NotAuthorizedException;
import MyPlanner.exceptions.UserInfoNotSetException;
import MyPlanner.model.Course;
import MyPlanner.model.LoginInfo;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class CanvasApi {

    public List<Course> getCourses(HttpServletRequest request) throws NotAuthorizedException {
        LoginInfo loginInfo = (LoginInfo)request.getSession().getAttribute("loginInfo");

        if(loginInfo != null && loginInfo.hasValues() && loginInfo.getAccessToken() != null) {

            HttpEntity<Course[]> requestEntity = new HttpEntity<Course[]>(setAuthorizationHeader(loginInfo.getAccessToken()));
            String url = "https://canvas.instructure.com/api/v1/courses";

            ResponseEntity<Course[]> resp = getRestTemplate().exchange(url, HttpMethod.GET, requestEntity, Course[].class, new HashMap<String, String>());
            Course[] courses = resp.getBody();

            return Arrays.asList(courses);
        }else{
            throw new NotAuthorizedException("Cant access api without user info");
        }
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
