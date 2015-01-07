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
     * by the students expected hours available for work per day, rounded up. Modules where
     * all moduleItems are completed and modules without are not added to the schedule.
     *
     * @param startDate the date from which to start the schedule
     * @param user the user for which to generate a schedule for
     * @param modules the list of modules in the selected course to generate a schedule for
     * @param workHoursDaily the amount of hours the user expects to work with the course per day
     * @return an ArrayList containing the recommended schedule based on the given parameters
     * and the courses module time-estimates
     */
    // TODO: should workHoursDaily be moved to the user class? Probably not.
    public List<UserHasModule> GenerateSchedule(User user, List<Module> modules, double workHoursDaily, Date startDate){

        List<UserHasModule> recommendedSchedule = new ArrayList<UserHasModule>();

        for (int i = 0; i<modules.size(); i++){
            UserHasModule scheduleItem = new UserHasModule();

            scheduleItem.setUser(user);
            Module module = modules.get(i);
            scheduleItem.setModule(module);
            scheduleItem.getModule().setCourse(modules.get(i).getCourse());

            // Checks if the module is completed:
            boolean moduleIsComplete = true;
            for (int j = 0; j<module.getItems().size(); j++){
                if (!module.getItems().get(j).getCompletionRequirement().isCompleted()){
                    moduleIsComplete = false;
                }
            }

            // If the module is not complete and has requirements, adds it to the schedule
            if(!moduleIsComplete && module.getItemsCount() != 0) {
                Calendar c = Calendar.getInstance();
                if (recommendedSchedule.isEmpty()) {
                    scheduleItem.setStartDate(startDate);
                } else {
                    startDate = new Date(recommendedSchedule.get(recommendedSchedule.size() - 1).getEndDate().getTime()); // fetches the date the previous module ends
                    scheduleItem.setStartDate(startDate);
                }

                if (module.getModuleTimeEstimation() != 0) {
                    Double daysNeededForCompletion = Math.ceil(module.getModuleTimeEstimation() / workHoursDaily);
                    c.setTime(startDate);
                    c.add(Calendar.DATE, daysNeededForCompletion.intValue());
                    Date endDate = new Date(c.getTime().getTime());
                    scheduleItem.setEndDate(endDate);
                } else {
                    scheduleItem.setEndDate(startDate);
                }

                recommendedSchedule.add(scheduleItem);
            }
        }

        return recommendedSchedule;
    }

    public void UpdateSchedule(){
        /* TODO: To be added
         * this function needs to be able to look at an existing schedule,
         * let the user manually make changes to one or more dates, and automatically
         * postpone the following dates to match the changed dates.
         */

    }
}
