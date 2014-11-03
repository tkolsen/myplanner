package MyPlanner.model;

import MyPlanner.interfaces.AngularjsPrintable;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CanvasCourse implements AngularjsPrintable {

    @JsonProperty("id")
    private long id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("course_code")
    private String courseCode;
    @JsonProperty("start_at")
    private String startAt;

    public CanvasCourse(){}

    public CanvasCourse(long id, String name, String courseCode, String startAt) {
        this.id = id;
        this.name = name;
        this.courseCode = courseCode;
        this.startAt = startAt;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getStartAt() {
        return startAt;
    }

    public void setStartAt(String startAt) {
        this.startAt = startAt;
    }

    @Override
    public String angularPrint() {
        return "{id:'"+id+"',name:'"+name+"',courseCode:'"+courseCode+"'}";
    }

}