package MyPlanner.utils;

import MyPlanner.model.Course;
import MyPlanner.model.Module;
import MyPlanner.model.User;
import MyPlanner.model.UserHasModule;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.junit.Assert.*;

public class ScheduleGeneratorTest {

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testGenerateSchedule() throws Exception {
        User user = new User();
        Course course = new Course();

        Calendar cal = Calendar.getInstance();
        cal.set(2013, cal.NOVEMBER,15);
        Date date = new Date(cal.getTime().getTime());

        ArrayList<Module> modules = new ArrayList<Module>();
        Module module1 = new Module(); module1.setModuleTimeEstimation(20);
        Module module2 = new Module(); module2.setModuleTimeEstimation(30);
        Module module3 = new Module(); module3.setModuleTimeEstimation(5);
        Module module4 = new Module(); module4.setModuleTimeEstimation(0);
        Module module5 = new Module(); module5.setModuleTimeEstimation(40);
        modules.add(module1); modules.add(module2); modules.add(module3); modules.add(module4); modules.add(module5);

        course.setModules(modules);

        ScheduleGenerator generator = new ScheduleGenerator();
        List<UserHasModule> schedule = generator.GenerateSchedule(user, course, 10, date);

        /*
        System.out.println("Module 1 startDate: " + schedule.get(0).getStartDate() + ", endDate: " + schedule.get(0).getEndDate());
        System.out.println("Module 2 startDate: " + schedule.get(1).getStartDate() + ", endDate: " + schedule.get(1).getEndDate());
        System.out.println("Module 3 startDate: " + schedule.get(2).getStartDate() + ", endDate: " + schedule.get(2).getEndDate());
        System.out.println("Module 4 startDate: " + schedule.get(3).getStartDate() + ", endDate: " + schedule.get(3).getEndDate());
        System.out.println("Module 5 startDate: " + schedule.get(4).getStartDate() + ", endDate: " + schedule.get(4).getEndDate());
        */

        assertEquals(schedule.get(0).getStartDate(), date);
        Date actual = schedule.get(0).getEndDate();
        cal.set(2013, cal.NOVEMBER, 17);
        Date expected = new Date(cal.getTime().getTime());
        assertEquals(expected, actual);

        assertEquals(schedule.get(1).getStartDate(), schedule.get(0).getEndDate());
        actual = schedule.get(1).getEndDate();
        cal.set(2013, cal.NOVEMBER, 20);
        expected = new Date(cal.getTime().getTime());
        assertEquals(expected, actual);

        assertEquals(schedule.get(2).getStartDate(), schedule.get(1).getEndDate());
        actual = schedule.get(2).getEndDate();
        cal.set(2013, cal.NOVEMBER, 21);
        expected = new Date(cal.getTime().getTime());
        assertEquals(expected, actual);

        assertEquals(schedule.get(3).getStartDate(), schedule.get(2).getEndDate());
        actual = schedule.get(3).getEndDate();
        cal.set(2013, cal.NOVEMBER, 21);
        expected = new Date(cal.getTime().getTime());
        assertEquals(expected, actual);

        assertEquals(schedule.get(4).getStartDate(), schedule.get(3).getEndDate());
        actual = schedule.get(4).getEndDate();
        cal.set(2013, cal.NOVEMBER, 25);
        expected = new Date(cal.getTime().getTime());
        assertEquals(expected, actual);
    }

    @Test
    public void testUpdateSchedule() throws Exception {

    }
}