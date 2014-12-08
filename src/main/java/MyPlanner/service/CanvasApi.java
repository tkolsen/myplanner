package MyPlanner.service;

import MyPlanner.exceptions.NotAuthorizedException;
import MyPlanner.model.CalendarEvent;
import MyPlanner.model.Course;
import MyPlanner.model.Module;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface CanvasApi {
    public List<Course> getCourses(HttpServletRequest request) throws NotAuthorizedException;
    public List<Module> getModules(HttpServletRequest request, int courseId) throws NotAuthorizedException;
    public List<CalendarEvent> getCalendarEvents(HttpServletRequest request, String startDate, String endDate) throws NotAuthorizedException;
}
