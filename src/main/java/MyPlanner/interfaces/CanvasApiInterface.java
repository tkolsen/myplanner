package MyPlanner.interfaces;

import MyPlanner.model.CanvasCourse;
import MyPlanner.model.CanvasUser;

/**
 * Interface for canvas api.
 */
public interface CanvasApiInterface {

    public String PROFILE_ENDPOINT = "/api/v1/users/self/profile";
    public String COURSES_ENDPOINT = "/api/v1/courses";

    public CanvasUser getProfile(String accessToken) throws Exception;

    public CanvasCourse[] listCourses(String accessToken) throws Exception;

}
