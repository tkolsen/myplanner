package MyPlanner.utils;

import MyPlanner.model.Module;
import MyPlanner.model.User;
import MyPlanner.model.UserHasModule;
import org.junit.Before;
import org.junit.Test;


import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.junit.Assert.*;

public class DeadlineCheckTest {

    private Calendar calendar = Calendar.getInstance();
    private List<UserHasModule> deadlines;

    @Before
    public void createMockedDeadlines() throws Exception {

        deadlines = new ArrayList<UserHasModule>();

        User user1 = new User("Leonardo", 101);
        User user2 = new User("Raphael", 102);
        User user3 = new User("Michelangelo", 103);
        User user4 = new User("Donatello", 104);
        User user5 = new User("Splinter", 105);
        user5.setStatus(false);

        Module module1 = new Module();
        module1.setName("TestModule 1");
        Module module2 = new Module();
        module2.setName("TestModule 2");

        // UserHasModule-data for user1:
        UserHasModule User1Deadline1 = new UserHasModule();
        User1Deadline1.setUser(user1);
        User1Deadline1.setModule(module1);
        calendar.set(2013, calendar.NOVEMBER, 20);
        User1Deadline1.setEndDate(new Date(calendar.getTime().getTime()));
        User1Deadline1.setCompletedAt(calendar.getTime().getTime() + "");

        UserHasModule User1Deadline2 = new UserHasModule();
        User1Deadline2.setUser(user1);
        User1Deadline2.setModule(module2);
        calendar.set(2013, calendar.OCTOBER, 20);
        User1Deadline2.setEndDate(new Date(calendar.getTime().getTime()));

        // One completed module, one module behind schedule
        deadlines.add(User1Deadline1);
        deadlines.add(User1Deadline2);

        // UserHasModule-data for user2:
        UserHasModule User2Deadline1 = new UserHasModule();
        User2Deadline1.setUser(user2);
        User2Deadline1.setModule(module1);
        calendar.set(2013, calendar.JULY, 20);
        User2Deadline1.setEndDate(new Date(calendar.getTime().getTime()));

        UserHasModule User2Deadline2 = new UserHasModule();
        User2Deadline2.setUser(user2);
        User2Deadline2.setModule(module2);
        calendar.set(2013, calendar.JUNE, 20);
        User2Deadline2.setEndDate(new Date(calendar.getTime().getTime()));

        // Two modules behind schedule
        deadlines.add(User2Deadline1);
        deadlines.add(User2Deadline2);

        // UserHasModule-data for user3:
        UserHasModule User3Deadline1 = new UserHasModule();
        User3Deadline1.setUser(user3);
        User3Deadline1.setModule(module1);
        calendar.set(2014, calendar.JANUARY, 20);
        User3Deadline1.setEndDate(new Date(calendar.getTime().getTime()));

        UserHasModule User3Deadline2 = new UserHasModule();
        User3Deadline2.setUser(user3);
        User3Deadline2.setModule(module2);
        calendar.set(2014, calendar.JUNE, 20);
        User3Deadline2.setEndDate(new Date(calendar.getTime().getTime()));

        // Two incomplete modules not behind schedule
        deadlines.add(User3Deadline1);
        deadlines.add(User3Deadline2);

        // UserHasModule-data for user4:
        UserHasModule User4Deadline1 = new UserHasModule();
        User4Deadline1.setUser(user4);
        User4Deadline1.setModule(module1);
        calendar.set(2014, calendar.JANUARY, 20);
        User4Deadline1.setEndDate(new Date(calendar.getTime().getTime()));
        User4Deadline1.setCompletedAt(calendar.getTime().getTime() + "");

        UserHasModule User4Deadline2 = new UserHasModule();
        User4Deadline2.setUser(user4);
        User4Deadline2.setModule(module2);
        calendar.set(2014, calendar.JUNE, 20);
        User4Deadline2.setEndDate(new Date(calendar.getTime().getTime()));
        User4Deadline1.setCompletedAt(calendar.getTime().getTime() + "");

        // Two completed modules
        deadlines.add(User4Deadline1);
        deadlines.add(User4Deadline2);


        // UserHasModule-data for user5, identical to user1, but marked as inactive:
        UserHasModule User5Deadline1 = new UserHasModule();
        User5Deadline1.setUser(user5);
        User5Deadline1.setModule(module1);
        calendar.set(2013, calendar.NOVEMBER, 20);
        User5Deadline1.setEndDate(new Date(calendar.getTime().getTime()));
        User5Deadline1.setCompletedAt(calendar.getTime().getTime() + "");

        UserHasModule User5Deadline2 = new UserHasModule();
        User5Deadline2.setUser(user5);
        User5Deadline2.setModule(module2);
        calendar.set(2013, calendar.OCTOBER, 20);
        User5Deadline2.setEndDate(new Date(calendar.getTime().getTime()));

        // One completed module, one module behind schedule
        deadlines.add(User5Deadline1);
        deadlines.add(User5Deadline2);
    }

    @Test
    public void ListOldestUnmetDeadlines() throws Exception {
        calendar.set(2013, calendar.NOVEMBER, 15);
        Date todayMock = new Date(calendar.getTime().getTime());
        DeadlineCheck dc = new DeadlineCheck();
        List<UserHasModule> checkedDeadlines = dc.ListOldestUnmetDeadlines(deadlines, todayMock);

        assertEquals(2, checkedDeadlines.size());

        assertEquals("Leonardo", checkedDeadlines.get(0).getUser().getName());
        assertEquals("TestModule 2", checkedDeadlines.get(0).getModule().getName());
        calendar.set(2013, calendar.OCTOBER, 20);
        Date expected = new Date(calendar.getTime().getTime());
        Date actual = checkedDeadlines.get(0).getEndDate();
        assertEquals(expected, actual);

        assertEquals("Raphael", checkedDeadlines.get(1).getUser().getName());
        assertEquals("TestModule 2", checkedDeadlines.get(1).getModule().getName());
        calendar.set(2013, calendar.JUNE, 20);
        expected = new Date(calendar.getTime().getTime());
        actual = checkedDeadlines.get(1).getEndDate();
        assertEquals(expected, actual);
    }

    @Test
    public void ListAllUnmetDeadlines() throws Exception {
        calendar.set(2013, calendar.NOVEMBER, 15);
        Date todayMock = new Date(calendar.getTime().getTime());
        DeadlineCheck dc = new DeadlineCheck();
        List<UserHasModule> checkedDeadlines = dc.ListAllUnmetDeadlines(deadlines, todayMock);

        assertEquals(3, checkedDeadlines.size());

        assertEquals("Leonardo", checkedDeadlines.get(0).getUser().getName());
        assertEquals("TestModule 2", checkedDeadlines.get(0).getModule().getName());
        calendar.set(2013, calendar.OCTOBER, 20);
        Date expected = new Date(calendar.getTime().getTime());
        Date actual = checkedDeadlines.get(0).getEndDate();
        assertEquals(expected, actual);

        assertEquals("Raphael", checkedDeadlines.get(1).getUser().getName());
        assertEquals("TestModule 1", checkedDeadlines.get(1).getModule().getName());
        calendar.set(2013, calendar.JULY, 20);
        expected = new Date(calendar.getTime().getTime());
        actual = checkedDeadlines.get(1).getEndDate();
        assertEquals(expected, actual);

        assertEquals("Raphael", checkedDeadlines.get(2).getUser().getName());
        assertEquals("TestModule 2", checkedDeadlines.get(2).getModule().getName());
        calendar.set(2013, calendar.JUNE, 20);
        expected = new Date(calendar.getTime().getTime());
        actual = checkedDeadlines.get(2).getEndDate();
        assertEquals(expected, actual);
    }
}