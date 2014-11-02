package MyPlanner.api;

import MyPlanner.model.CanvasCourse;
import MyPlanner.model.CanvasUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 * Created by TomKolse on 02-Nov-14.
 */
public class CanvasApi {
    // TODO: BASE_URL funker kunn på HIST sitt instructure domene
    public static final String BASE_URL = "https://hist.instructure.com";
    public static final String PROFILE_ENDPOINT = "/api/v1/users/self/profile";
    public static final String COURSES_ENDPOINT = "/api/v1/courses";

    @Autowired
    private RestTemplate restTemplate;

    private HttpEntity<String> setAuthHeader(String accessToken){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        HttpEntity<String> entity = new HttpEntity<String>(headers);
        return entity;
    }

    public CanvasUser getProfile(String accessToken) throws Exception{
        ResponseEntity<CanvasUser> responseEntity = restTemplate.exchange(BASE_URL + PROFILE_ENDPOINT,
                HttpMethod.GET, setAuthHeader(accessToken), CanvasUser.class);

        CanvasUser user = responseEntity.getBody();

        return user;
    }

    public CanvasCourse[] getCourses(String accessToken) throws Exception{
        ResponseEntity<CanvasCourse[]> responseEntity = restTemplate.exchange(BASE_URL + COURSES_ENDPOINT,
                HttpMethod.GET, setAuthHeader(accessToken), CanvasCourse[].class);

        CanvasCourse[] courses = responseEntity.getBody();

        return courses;
    }
}
