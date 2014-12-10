package MyPlanner.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name="MODULE")
public class Module {
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

    private ModulePk modulePk = new ModulePk();
    @Embeddable
    private static final class ModulePk implements Serializable{
        private int id;
        private Course course;

        public ModulePk(){

        }

        public ModulePk(int id, Course course){
            this.id = id;
            this.course = course;
        }

        @Column(name="MODULE_ID")
        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        @ManyToOne(fetch = FetchType.EAGER)
        public Course getCourse() {
            return course;
        }

        public void setCourse(Course course) {
            this.course = course;
        }
    }

    @EmbeddedId
    public ModulePk getModulePk() {
        return modulePk;
    }

    public void setModulePk(ModulePk modulePk) {
        this.modulePk = modulePk;
    }

    @Transient
    public List<ModuleItem> getItems() {
        return items;
    }

    public void setItems(List<ModuleItem> items) {
        this.items = items;
    }

    @Transient
    @JsonProperty("id")
    public int getId() {
        return modulePk.getId();
    }

    @JsonProperty("id")
    public void setId(int id) {
        this.modulePk.setId(id);
    }

    @Transient
    public String getWorkflowState() {
        return workflowState;
    }

    public void setWorkflowState(String workflowState) {
        this.workflowState = workflowState;
    }

    @Column(name="POSITION")
    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @Column(name="NAME")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Transient
    public int getItemsCount() {
        return itemsCount;
    }

    public void setItemsCount(int itemsCount) {
        this.itemsCount = itemsCount;
    }

    @Transient
    public String getItemsUrl() {
        return itemsUrl;
    }

    public void setItemsUrl(String itemsUrl) {
        this.itemsUrl = itemsUrl;
    }
}
