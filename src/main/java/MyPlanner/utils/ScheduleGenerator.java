package MyPlanner.utils;

import MyPlanner.model.Course;
import MyPlanner.model.Module;
import MyPlanner.model.User;
import MyPlanner.model.UserModuleSchedule;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ScheduleGenerator {

    /**
     * Generates a recommended schedule for completing each individual module in the given
     * course based on the modules preset estimated time in hours needed to complete, divided
     * by the students expected hours available for work per day, rounded up.
     *
     * @param user the user for which to generate a schedule for
     * @param course the selected course to generate a schedule for
     * @param workHoursDaily the amount of hours the user expects to work with the course per day
     * @return an arraylist containing the recommended schedule based on the given parameters
     * and the courses module time-estimates
     */
    // TODO: should workHoursDaily be moved to the user class?
    public List<UserModuleSchedule> GenerateSchedule(User user, Course course, double workHoursDaily){

        List<UserModuleSchedule> recommendedSchedule = new ArrayList<UserModuleSchedule>();

        for (int i = 0; i<course.getModules().size(); i++){
            UserModuleSchedule scheduleItem = new UserModuleSchedule();

            scheduleItem.setUser(user);
            scheduleItem.setCourse(course);
            Module module = course.getModules().get(i);
            scheduleItem.setModule(module);

            Date startDate = new Date();
            scheduleItem.setStartDate(startDate);

            Double daysNeededForCompletion = Math.ceil(module.getModuleTimeEstimation() / workHoursDaily);

            Calendar c = Calendar.getInstance();
            c.setTime(startDate);
            c.add(Calendar.DATE, daysNeededForCompletion.intValue());
            Date endDate = c.getTime();
            scheduleItem.setEndDate(endDate);

            recommendedSchedule.add(scheduleItem);
        }

        return recommendedSchedule;
    }

    /**
     * Generates a recommended schedule for completing each individual module in the given
     * course based on the modules preset estimated time in hours needed to complete, divided
     * by the students expected hours available for work per day, rounded up.
     *
     * @param user the user for which to generate a schedule for
     * @param course the selected course to generate a schedule for
     * @param workHoursDaily the amount of hours the user expects to work with the course per day
     * @param startDate the date from which the scheduler will plan the recommended schedule
     * @return an arraylist containing the recommended schedule for module progression based on the given parameters and the module time-estimates
     */
    public List<UserModuleSchedule> GenerateSchedule(User user, Course course, double workHoursDaily, Date startDate){
        List<UserModuleSchedule> recommendedSchedule = new ArrayList<UserModuleSchedule>();

        for (int i = 0; i<course.getModules().size(); i++){
            UserModuleSchedule scheduleItem = new UserModuleSchedule();

            scheduleItem.setUser(user);
            scheduleItem.setCourse(course);
            Module module = course.getModules().get(i);
            scheduleItem.setModule(module);
            scheduleItem.setStartDate(startDate);

            Double daysNeededForCompletion = Math.ceil(module.getModuleTimeEstimation() / workHoursDaily);

            Calendar c = Calendar.getInstance();
            c.setTime(startDate);
            c.add(Calendar.DATE, daysNeededForCompletion.intValue());
            Date endDate = c.getTime();
            scheduleItem.setEndDate(endDate);

            recommendedSchedule.add(scheduleItem);
        }

        return recommendedSchedule;
    }

    public void UpdateSchedule(List<UserModuleSchedule> schedule){
        //TODO: Implement me
    }
}
