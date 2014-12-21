package MyPlanner.utils;

import MyPlanner.model.Module;
import MyPlanner.model.User;
import MyPlanner.model.UserHasModule;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;

import static org.junit.Assert.*;

public class DeadlineCheckTest {

    List<UserHasModule> deadlines;

    @Before
    public void setUp() throws Exception {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2013, calendar.NOVEMBER,15);
        Date todayMock = new Date(calendar.getTime().getTime());    // creates a fictional "today"

        User user1 = new User("Leonardo", 101);
        User user2 = new User("Raphael", 102);
        User user3 = new User("Michelangelo", 103);
        User user4 = new User("Donatello", 104);

        Module module1 = new Module();
        Module module2 = new Module();

        // UserHasModule-data for user1:
        UserHasModule deadlineItem1 = new UserHasModule();
        deadlineItem1.setUser(user1);
        deadlineItem1.setModule(module1);


        deadlines.add(deadlineItem1);
    }

    @After
    public void tearDown() throws Exception {

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
    public void testCheckDeadlines() throws Exception {

    }
}