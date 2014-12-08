package MyPlanner.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class CalendarEvent {
    @JsonProperty("id")
    private int id;
    @JsonProperty("title")
    private String title;
    @JsonProperty("start_at")
    private String startAt;
    @JsonProperty("end_at")
    private String endAt;
    @JsonProperty("description")
    private String description;
    @JsonProperty("location_name")
    private String locationName;
    @JsonProperty("location_adress")
    private String locationAdress;
    @JsonProperty("context_code")
    private String contextCode;
    @JsonProperty("workflow_state")
    private String workflowState;
    @JsonProperty("url")
    private String url;
    @JsonProperty("all_day_date")
    private String allDayDate;
    @JsonProperty("all_day")
    private String allDay;
    @JsonProperty("created_at")
    private String createdAt;
    @JsonProperty("updated_at")
    private String updatedAt;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStartAt() {
        return startAt;
    }

    public void setStartAt(String startAt) {
        this.startAt = startAt;
    }

    public String getEndAt() {
        return endAt;
    }

    public void setEndAt(String endAt) {
        this.endAt = endAt;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getLocationAdress() {
        return locationAdress;
    }

    public void setLocationAdress(String locationAdress) {
        this.locationAdress = locationAdress;
    }

    public String getContextCode() {
        return contextCode;
    }

    public void setContextCode(String contextCode) {
        this.contextCode = contextCode;
    }

    public String getWorkflowState() {
        return workflowState;
    }

    public void setWorkflowState(String workflowState) {
        this.workflowState = workflowState;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAllDayDate() {
        return allDayDate;
    }

    public void setAllDayDate(String allDayDate) {
        this.allDayDate = allDayDate;
    }

    public String getAllDay() {
        return allDay;
    }

    public void setAllDay(String allDay) {
        this.allDay = allDay;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}


