package MyPlanner.utils;

import MyPlanner.model.Module;
import MyPlanner.model.User;
import MyPlanner.model.UserHasModule;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.junit.Assert.*;

public class DeadlineCheckTest {

    private Calendar calendar = Calendar.getInstance();
    private List<UserHasModule> deadlines;

    @Before
    public void setUp() throws Exception {

        deadlines = new ArrayList<UserHasModule>();

        User user1 = new User("Leonardo", 101);
        User user2 = new User("Raphael", 102);
        User user3 = new User("Michelangelo", 103);
        User user4 = new User("Donatello", 104);

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
        User1Deadline1.setCompletedAt(new Date(calendar.getTime().getTime()));

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
        User4Deadline1.setCompletedAt(new Date(calendar.getTime().getTime()));

        UserHasModule User4Deadline2 = new UserHasModule();
        User4Deadline2.setUser(user4);
        User4Deadline2.setModule(module2);
        calendar.set(2014, calendar.JUNE, 20);
        User4Deadline2.setEndDate(new Date(calendar.getTime().getTime()));
        User4Deadline1.setCompletedAt(new Date(calendar.getTime().getTime()));

        // Two completed modules
        deadlines.add(User4Deadline1);
        deadlines.add(User4Deadline2);
    }


    @Test
    public void testGetExpiredUsers() throws Exception {

    }

    @Test
    public void testGetExpiredDates() throws Exception {

    }

    @Test
    public void testGetExpiredUsersAsString() throws Exception {

    }

    @Test
    public void ListOldestUnmetDeadlines() throws Exception {
        calendar.set(2013, calendar.NOVEMBER, 15);
        Date todayMock = new Date(calendar.getTime().getTime());
        DeadlineCheck dc = new DeadlineCheck();
        List<UserHasModule> checkedDeadlines = dc.ListOldestUnmetDeadlines(deadlines, todayMock);

        assertEquals(2, checkedDeadlines.size());
        assertEquals()
    }

    @Test
    public void ListAllUnmetDeadlines() throws Exception {

    }
}