package MyPlanner.model;

import MyPlanner.model.Module;

import java.sql.Date;
import java.util.List;

/**
 * Help class for the RestController
 */

public class ScheduleDetails {
    List<Module> modules;
    double workHoursDaily;
    Date startDate;

    public List<Module> getModules() {
        return modules;
    }

    public void setModules(List<Module> modules) {
        this.modules = modules;
    }

    public double getWorkHoursDaily() {
        return workHoursDaily;
    }

    public void setWorkHoursDaily(double workHoursDaily) {
        this.workHoursDaily = workHoursDaily;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
}
