package MyPlanner.utils;


import MyPlanner.model.UserHasModule;
import MyPlanner.model.Course;

import java.util.ArrayList;
import java.util.List;

public class DeadlineCheck {


    /**
     * Takes a list of deadlines in the form of UserHasModule objects, controls the
     * completion status of the module, and if it is incompleet, compares its scheduled
     * completion-date against the current date. If the scheduled date has passed, adds
     * the user to the list of expired deadlines to be returned.
     *
     * @param deadlines the list of all dates for which to control all deadlines
     * @return a list of UserHasModule objects where module is incomplete and deadlines has passed
     */
    public List<UserHasModule> CheckDeadlines(List<UserHasModule> deadlines){
        List<UserHasModule> expiredDeadlines = new ArrayList<UserHasModule>();


        return expiredDeadlines;
    }

}
