package MyPlanner.utils;


import MyPlanner.model.UserHasModule;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Might be implemented better using HashMaps with user-id as the index, and date-object as value.
 *
 * When the ListOldestUnmetDeadlines method is called, users who have fallen behind their schedule
 * are added to a list of users, while the oldest date of an incomplete module is added to
 * the same index of a list of expiredDates.
 */
public class DeadlineCheck {

    /**
     * Takes a list of deadlines in the form of UserHasModule objects, checks the
     * completion status of the module, and if it is incomplete, compares its scheduled
     * completion-date against the current date. If the scheduled date has passed, adds the
     * UserModuleObject to a list of Users behind schedule. If the user is already in the list,
     * the oldest unmet deadline is kept in the list, and more recent, unmet deadlines are ignored.
     *
     * @param deadlines the list of UserHasModule objects for which to control all deadlines
     * @return list of users behind their schedule and the oldest unmet deadline
     */
    public List<UserHasModule> ListOldestUnmetDeadlines(List<UserHasModule> deadlines){
        List<UserHasModule> UsersBehindSchedule = new ArrayList<UserHasModule>();
        Calendar calendar = Calendar.getInstance();
        Date today = new Date(calendar.getTimeInMillis());

        for (int i=0; i<deadlines.size(); i++){
            if (deadlines.get(i).getCompletedAt() == null){                         // If the module is not completed
                if(today.getTime() > deadlines.get(i).getEndDate().getTime()) {     // If the timestamp for today is after the deadline

                    int counter = 0;
                    while(counter < UsersBehindSchedule.size()) {                          // Loops through the list of users already added and checks if the current user is there
                        if(UsersBehindSchedule.get(counter).getUser().getId() != deadlines.get(i).getUser().getId()){       // if not the same user, skip to next
                            counter++;
                        }

                        else if(UsersBehindSchedule.get(counter).getUser().getId() == deadlines.get(i).getUser().getId()){  // if it is the same user
                            if (UsersBehindSchedule.get(counter).getEndDate().getTime() > deadlines.get(i).getEndDate().getTime()){    // if the new endDate is longer in the past
                                UsersBehindSchedule.set(counter, deadlines.get(i));

                            }
                            counter = UsersBehindSchedule.size()+1;
                        }
                    }
                    if(counter == UsersBehindSchedule.size()){             // If this is exactly equal, the user is not in the list
                        UsersBehindSchedule.add(deadlines.get(i));
                    }
                }
            }
        }
        return UsersBehindSchedule;
    }

    /**
     * Takes a list of deadlines in the form of UserHasModule objects, checks the
     * completion status of the module, and if it is incomplete, compares its scheduled
     * completion-date against the current date. If the scheduled date has passed, adds the
     * UserModuleObject to a list of Users behind schedule. Unlike ListOldestUnmetDeadlines(),
     * this method lists ALL unmet deadlines, so users with multiple unmet deadlines over several
     * modules, will have all modules listed.
     *
     * @param deadlines the list of UserHasModule objects for which to control all deadlines
     * @return list of all users' modules behind schedule
     */
    public List<UserHasModule> ListAllUnmetDeadlines(List<UserHasModule> deadlines){
        List<UserHasModule> UsersBehindSchedule = new ArrayList<UserHasModule>();
        Calendar calendar = Calendar.getInstance();
        Date today = new Date(calendar.getTimeInMillis());

        for (int i=0; i<deadlines.size(); i++){
            if (deadlines.get(i).getCompletedAt() == null) {                         // If the module is not completed
                if (today.getTime() > deadlines.get(i).getEndDate().getTime()) {     // If the timestamp for today is after the deadline
                    UsersBehindSchedule.add(deadlines.get(i));
                }
            }
        }
        return UsersBehindSchedule;
    }

    public static void main(String[] args){
        DeadlineCheck test = new DeadlineCheck();
        ArrayList<UserHasModule> list = new ArrayList<UserHasModule>();
        test.ListOldestUnmetDeadlines(list);
    }

}
