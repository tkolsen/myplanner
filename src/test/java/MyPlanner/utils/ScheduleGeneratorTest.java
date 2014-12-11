package MyPlanner.utils;

import MyPlanner.model.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
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

        // Mocked modules with content necessary for testing - wtf nightmare
        ArrayList<Module> modules = new ArrayList<Module>();

        // Mocked module 1
        Module module1 = new Module();
        module1.setName("Module 1");
        module1.setModuleTimeEstimation(20);
        ModuleItem module1Item1 = new ModuleItem();
        CompletionRequirement module1Item1Req = new CompletionRequirement(); module1Item1Req.setCompleted(true);
        module1Item1.setCompletionRequirement(module1Item1Req);
        ModuleItem module1Item2 = new ModuleItem();
        CompletionRequirement module1Item2Req = new CompletionRequirement(); module1Item2Req.setCompleted(false);
        module1Item2.setCompletionRequirement(module1Item2Req);
        List<ModuleItem> module1Items = new ArrayList<ModuleItem>();
        module1Items.add(module1Item1);
        module1Items.add(module1Item2);
        module1.setItems(module1Items);
        module1.setItemsCount(module1Items.size());

        // Mocked module 2
        Module module2 = new Module();
        module2.setName("Module 2");
        module2.setModuleTimeEstimation(30);
        ModuleItem module2Item1 = new ModuleItem();
        CompletionRequirement module2Item1Req = new CompletionRequirement(); module2Item1Req.setCompleted(true);
        module2Item1.setCompletionRequirement(module2Item1Req);
        ModuleItem module2Item2 = new ModuleItem();
        CompletionRequirement module2Item2Req = new CompletionRequirement(); module2Item2Req.setCompleted(false);
        module2Item2.setCompletionRequirement(module2Item2Req);
        List<ModuleItem> module2Items = new ArrayList<ModuleItem>();
        module2Items.add(module2Item1);
        module2Items.add(module2Item2);
        module2.setItems(module2Items);
        module2.setItemsCount(module2Items.size());

        // Mocked module 3
        Module module3 = new Module();
        module3.setName("Module 3");
        module3.setModuleTimeEstimation(5);
        ModuleItem module3Item1 = new ModuleItem();
        CompletionRequirement module3Item1Req = new CompletionRequirement(); module3Item1Req.setCompleted(true);
        module3Item1.setCompletionRequirement(module3Item1Req);
        ModuleItem module3Item2 = new ModuleItem();
        CompletionRequirement module3Item2Req = new CompletionRequirement(); module3Item2Req.setCompleted(false);
        module3Item2.setCompletionRequirement(module3Item2Req);
        List<ModuleItem> module3Items = new ArrayList<ModuleItem>();
        module3Items.add(module3Item1);
        module3Items.add(module3Item2);
        module3.setItems(module3Items);
        module3.setItemsCount(module3Items.size());

        // Mocked module 4 - module without requirements
        Module module4 = new Module();
        module4.setName("Module 4");
        module4.setModuleTimeEstimation(0);
        List<ModuleItem> module4Items = new ArrayList<ModuleItem>();
        module4.setItems(module4Items);
        module4.setItemsCount(module4Items.size());

        // Mocked Module 5 - module with all requirements completed
        Module module5 = new Module();
        module5.setName("Module 5");
        module5.setModuleTimeEstimation(40);
        ModuleItem module5Item1 = new ModuleItem();
        CompletionRequirement module5Item1Req = new CompletionRequirement(); module5Item1Req.setCompleted(true);
        module5Item1.setCompletionRequirement(module5Item1Req);
        ModuleItem module5Item2 = new ModuleItem();
        CompletionRequirement module5Item2Req = new CompletionRequirement(); module5Item2Req.setCompleted(true);
        module5Item2.setCompletionRequirement(module5Item2Req);
        List<ModuleItem> module5Items = new ArrayList<ModuleItem>();
        module5Items.add(module5Item1);
        module5Items.add(module5Item2);
        module5.setItems(module5Items);
        module5.setItemsCount(module5Items.size());

        // Mocked module 6
        Module module6 = new Module();
        module6.setName("Module 6");
        module6.setModuleTimeEstimation(40);
        ModuleItem module6Item1 = new ModuleItem();
        CompletionRequirement module6Item1Req = new CompletionRequirement(); module6Item1Req.setCompleted(true);
        module6Item1.setCompletionRequirement(module6Item1Req);
        ModuleItem module6Item2 = new ModuleItem();
        CompletionRequirement module6Item2Req = new CompletionRequirement(); module6Item2Req.setCompleted(false);
        module6Item2.setCompletionRequirement(module6Item2Req);
        List<ModuleItem> module6Items = new ArrayList<ModuleItem>();
        module6Items.add(module6Item1);
        module6Items.add(module6Item2);
        module6.setItems(module6Items);
        module6.setItemsCount(module6Items.size());

        modules.add(module1); modules.add(module2); modules.add(module3); modules.add(module4); modules.add(module5); modules.add(module6);

        course.setModules(modules);

        ScheduleGenerator generator = new ScheduleGenerator();
        List<UserHasModule> schedule = generator.GenerateSchedule(user, course, 10, date);


        System.out.println("Module 1 startDate: " + schedule.get(0).getStartDate() + ", endDate: " + schedule.get(0).getEndDate());
        System.out.println("Module 2 startDate: " + schedule.get(1).getStartDate() + ", endDate: " + schedule.get(1).getEndDate());
        System.out.println("Module 3 startDate: " + schedule.get(2).getStartDate() + ", endDate: " + schedule.get(2).getEndDate());
        System.out.println("Module 4 startDate: " + schedule.get(3).getStartDate() + ", endDate: " + schedule.get(3).getEndDate());


        assertEquals(4, schedule.size());
        assertEquals("Module 1", schedule.get(0).getModule().getName());
        assertEquals("Module 2", schedule.get(1).getModule().getName());
        assertEquals("Module 3", schedule.get(2).getModule().getName());
        assertEquals("Module 6", schedule.get(3).getModule().getName());

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
        cal.set(2013, cal.NOVEMBER, 25);
        expected = new Date(cal.getTime().getTime());
        assertEquals(expected, actual);
    }

    @Test
    public void testUpdateSchedule() throws Exception {

    }
}