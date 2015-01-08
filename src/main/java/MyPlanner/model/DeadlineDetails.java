package MyPlanner.model;

import java.sql.Date;
import java.util.List;

/**
 * Help class for the RestController, for using the DeadlineCheck
 */
public class DeadlineDetails {
    private List<UserHasModule> deadlines;
    private Date date;

    public List<UserHasModule> getDeadlines() {
        return deadlines;
    }

    public void setDeadlines(List<UserHasModule> deadlines) {
        this.deadlines = deadlines;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
