package MyPlanner.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name="MODULE")
public class Module implements Serializable{

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
    @JsonProperty("completed_at")
    private String completedAt;
    private double moduleTimeEstimation; // Must be manually set by the MyPlanner administrator, is not fetched through Canvas

    @Column(name="TIME_ESTIMATION")
    public double getModuleTimeEstimation() { return moduleTimeEstimation;}
    public void setModuleTimeEstimation(double moduleTimeEstimation) { this.moduleTimeEstimation = moduleTimeEstimation; }

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

        @Override
        public boolean equals(Object obj){
            if(this == obj) return true;
            if(obj != null && obj instanceof ModulePk){
                ModulePk other = (ModulePk) obj;
                if(other.getId() == this.getId() && other.getCourse().equals(this.getCourse())){
                    return true;
                }
            }
            return false;
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
    public int getItemsCount() { return itemsCount; }
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

    @Transient
    public Course getCourse(){ return getModulePk().getCourse(); }
    public void setCourse(Course course){this.getModulePk().course = course; }

    @Override
    public boolean equals(Object obj){
        if(obj == this) return true;
        if(obj != null && obj instanceof Module){
            Module other = (Module)obj;
            if(other.getModulePk().equals(this.getModulePk()))return true;
        }
        return false;
    }
}
