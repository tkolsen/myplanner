package MyPlanner.service;

import MyPlanner.exceptions.NotAuthorizedException;
import MyPlanner.model.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public interface CanvasApi {
    public List<Course> getCourses(HttpServletRequest request) throws NotAuthorizedException;
    public List<Module> getModules(HttpServletRequest request, int courseId) throws NotAuthorizedException;
    public List<CalendarEvent> getCalendarEvents(HttpServletRequest request, String startDate, String endDate) throws NotAuthorizedException;
    public ArrayList<Module> getModulesAsArrayList(HttpServletRequest request, Course course) throws NotAuthorizedException;
    public List<Enrollment> getEnrollment(User user, String accessToken);
}
