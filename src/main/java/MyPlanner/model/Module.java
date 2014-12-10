package MyPlanner.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Module {
    @JsonProperty("id")
    private int id;
    @JsonProperty("workflow_state")
    private String workflowState;
    @JsonProperty("position")
    private int position;
    @JsonProperty("name")
    private String name;
    @JsonProperty("items_count")
    private int itemsCount;
    @JsonProperty("items_url")
    private String itemsUrl;
    @JsonProperty("items")
    private List<ModuleItem> items;
    private double moduleTimeEstimation; // Must be manually set by the MyPlanner administrator, is not fetched through Canvas


    public double getModuleTimeEstimation() {
        return moduleTimeEstimation;
    }

    public void setModuleTimeEstimation(double moduleTimeEstimation) {
        this.moduleTimeEstimation = moduleTimeEstimation;
    }

    public List<ModuleItem> getItems() {
        return items;
    }

    public void setItems(List<ModuleItem> items) {
        this.items = items;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWorkflowState() {
        return workflowState;
    }

    public void setWorkflowState(String workflowState) {
        this.workflowState = workflowState;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getItemsCount() {
        return itemsCount;
    }

    public void setItemsCount(int itemsCount) {
        this.itemsCount = itemsCount;
    }

    public String getItemsUrl() {
        return itemsUrl;
    }

    public void setItemsUrl(String itemsUrl) {
        this.itemsUrl = itemsUrl;
    }
}
