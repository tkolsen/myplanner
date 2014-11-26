package MyPlanner.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ModuleItem {
    @JsonProperty("id")
    private int id;
    @JsonProperty("module_id")
    private int moduleId;
    @JsonProperty("position")
    private int position;
    @JsonProperty("title")
    private String title;
    @JsonProperty("completion_requirement")
    private CompletionRequirement completionRequirement;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getModuleId() {
        return moduleId;
    }

    public void setModuleId(int moduleId) {
        this.moduleId = moduleId;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public CompletionRequirement getCompletionRequirement() {
        return completionRequirement;
    }

    public void setCompletionRequirement(CompletionRequirement completionRequirement) {
        this.completionRequirement = completionRequirement;
    }

}
