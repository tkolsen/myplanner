package MyPlanner.model;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

/**
 * Help class for the RestController, for using the ScheduleGenerator
 */
public class ScheduleDetails implements Serializable{
    private List<Module> modules;
    private double workHoursDaily;
    private Date startDate;

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
