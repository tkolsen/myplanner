package MyPlanner.service;

import MyPlanner.exceptions.NotAuthorizedException;
import MyPlanner.model.Course;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface CanvasApi {
    public List<Course> getCourses(HttpServletRequest request) throws NotAuthorizedException;
    public List<Object> getModules(HttpServletRequest request) throws NotAuthorizedException;
}
