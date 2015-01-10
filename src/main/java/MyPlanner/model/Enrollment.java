package MyPlanner.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Enrollment {
    @JsonProperty("course_id")
    public int courseId;

    @JsonProperty("type")
    public String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }
}
