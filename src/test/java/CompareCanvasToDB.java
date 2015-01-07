import MyPlanner.exceptions.NotAuthorizedException;
import MyPlanner.model.Course;
import MyPlanner.model.Module;
import MyPlanner.service.CanvasApi;
import MyPlanner.service.CanvasApiImpl;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import java.util.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CompareCanvasToDB {
    Course course;
    List<Course> courses;
    @Before
    public void setUp(){
        course = new Course();
        course.setId(1);
        course.setCourseCode("abc");
        course.setName("Mock Course");

        Module module1 = new Module();
        module1.setName("Mock module 1");
        module1.setId(2);

        Module module2 = new Module();
        module2.setName("Mock module 2");
        module2.setId(5);

        List<Module> modules = new ArrayList<Module>();
        modules.add(module1);
        modules.add(module2);

        /*course.setModules(modules);
        courses = new ArrayList<Course>();
        courses.add(course);*/
    }

    @Test
    public void sammenlign_db_modul_og_canvas_modul() throws NotAuthorizedException {
        CanvasApi api = mock(CanvasApiImpl.class);
        when(api.getCourses(new MockHttpServletRequest())).thenReturn(courses);


    }
}
