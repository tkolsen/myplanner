package MyPlanner.utils;

import MyPlanner.model.Course;

import java.sql.Date;


public class ScheduleDetails {
    Course course;
    double workHoursDaily;
    Date startDate;

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
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
