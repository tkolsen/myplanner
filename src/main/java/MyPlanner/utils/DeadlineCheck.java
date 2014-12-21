package MyPlanner.utils;


import MyPlanner.model.User;
import MyPlanner.model.UserHasModule;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Very hacky solution, might be implemented better using HashMaps with user-id as the
 * index, and date-object as value.
 *
 * When the CheckDeadlines method is called, users who have fallen behind their schedule
 * are added to a list of users, while the oldest date of an incomplete module is added to
 * the same index of a list of expiredDates.
 */
public class DeadlineCheck {

    private List<User> expiredUsers;
    private List<Date> expiredDates;

    public List<User> getExpiredUsers(){ return expiredUsers; }
    public List<Date> getExpiredDates(){ return expiredDates; }


    /**
     * @return a list of strings containing a simple visualization of users and deadlines not met.
     */
    public List<String> getExpiredUsersAsString(){
        ArrayList<String> SimpleExpiredUsers = new ArrayList<String>();
        for (int i=0; i<expiredUsers.size(); i++){
            SimpleExpiredUsers.add("User: " + expiredUsers.get(i).getName() + ", oldest deadline: " + expiredDates.get(i));
        }
        return SimpleExpiredUsers;
    }

    /**
     * Takes a list of deadlines in the form of UserHasModule objects, checks the
     * completion status of the module, and if it is incomplete, compares its scheduled
     * completion-date against the current date. If the scheduled date has passed, adds
     * the user to the expiredUsers and the oldest date missed to the list of expiredDates.
     *
     * @param deadlines the list of UserHasModule objects for which to control all deadlines
     */
    public void CheckDeadlines(List<UserHasModule> deadlines){
        List<UserHasModule> expiredDeadlines = new ArrayList<UserHasModule>();
        Calendar calendar = Calendar.getInstance();
        Date today = new Date(calendar.getTimeInMillis());

        for (int i=0; i<deadlines.size(); i++){
            if (deadlines.get(i).getCompletedAt() == null){                         // If the module is not completed
                if(today.getTime() > deadlines.get(i).getEndDate().getTime()) {     // If the timestamp for today is after the deadline

                    int counter = 0;
                    while(counter < expiredUsers.size()) {                          // Loops through the list of users already added and checks if the current user is there
                        if(expiredUsers.get(counter).getId() != deadlines.get(i).getUser().getId()){       // if not the same user, skip to next
                            counter++;
                        }

                        else if(expiredUsers.get(counter).getId() == deadlines.get(i).getUser().getId()){  // if it is the same user
                            if (expiredDeadlines.get(counter).getEndDate().getTime() > deadlines.get(i).getEndDate().getTime()){    // if the new endDate is longer in the past
                                expiredDates.set(counter, deadlines.get(i).getEndDate());
                            }
                            counter = expiredUsers.size()+1;
                        }
                    }
                    if(counter == expiredUsers.size()){             // If this is exactly equal, the user is not in the list
                        expiredUsers.add(deadlines.get(i).getUser());
                        expiredDates.add(deadlines.get(i).getEndDate());
                    }
                }
            }
        }
    }

    public static void main(String[] args){
        DeadlineCheck test = new DeadlineCheck();
        ArrayList<UserHasModule> list = new ArrayList<UserHasModule>();
        test.CheckDeadlines(list);
    }

}
