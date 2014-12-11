package MyPlanner.utils;

import MyPlanner.model.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.sql.Date;
import java.util.List;

public class ScheduleGenerator {

    /**
     * Generates a recommended schedule for completing each individual module in the given
     * course based on the modules preset estimated time in hours needed to complete, divided
     * by the students expected hours available for work per day, rounded up.
     *
     * @param startDate the date from which to start the schedule
     * @param user the user for which to generate a schedule for
     * @param course the selected course to generate a schedule for
     * @param workHoursDaily the amount of hours the user expects to work with the course per day
     * @return an arraylist containing the recommended schedule based on the given parameters
     * and the courses module time-estimates
     */
    // TODO: should workHoursDaily be moved to the user class?
    public List<UserHasModule> GenerateSchedule(User user, Course course, double workHoursDaily, Date startDate){

        List<UserHasModule> recommendedSchedule = new ArrayList<UserHasModule>();

        for (int i = 0; i<course.getModules().size(); i++){
            UserHasModule scheduleItem = new UserHasModule();

            scheduleItem.setUser(user);
            Module module = course.getModules().get(i);
            scheduleItem.setModule(module);
            scheduleItem.getModule().setCourse(course);

            Calendar c = Calendar.getInstance();
            if(recommendedSchedule.isEmpty()) {
                scheduleItem.setStartDate(startDate);
            }else{
                startDate = new Date(recommendedSchedule.get(recommendedSchedule.size()-1).getEndDate().getTime()); // fetches the date the previous module ends
                scheduleItem.setStartDate(startDate);
            }

            if (module.getModuleTimeEstimation() != 0) {
                Double daysNeededForCompletion = Math.ceil(module.getModuleTimeEstimation() / workHoursDaily);
                c.setTime(startDate);
                c.add(Calendar.DATE, daysNeededForCompletion.intValue());
                Date endDate = new Date(c.getTime().getTime());
                scheduleItem.setEndDate(endDate);
            }else{
                scheduleItem.setEndDate(startDate);
            }

            recommendedSchedule.add(scheduleItem);
        }

        return recommendedSchedule;
    }

    public void UpdateSchedule(List<UserModuleSchedule> schedule){
        //TODO: Implement me
    }
}
