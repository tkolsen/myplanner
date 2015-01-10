package MyPlanner.model;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Entity
@Table(name="USER_HAS_MODULE")
public class UserHasModule {
    private Date startDate;
    private Date endDate;
    private String completedAt = null;

    @Column(name="START_DATE")
    public Date getStartDate() {
        return startDate;
    }
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @Column(name="END_DATE")
    public Date getEndDate() {
        return endDate;
    }
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    //@Column(name="COMPLETED_AT")
    public String getCompletedAt() { return completedAt; }
    public void setCompletedAt(String completedAt) { this.completedAt = completedAt; }


    private UserHasModulePK userHasModulePK = new UserHasModulePK();
    @Embeddable
    private static final class UserHasModulePK implements Serializable{
        private Module module;
        private User user;

        public UserHasModulePK(){}
        public UserHasModulePK(Module module, User user){
            this.module = module;
            this.user = user;
        }

        @ManyToOne(fetch = FetchType.EAGER)
        public Module getModule() {
            return module;
        }
        public void setModule(Module module) {
            this.module = module;
        }

        @ManyToOne(fetch = FetchType.EAGER)
        public User getUser() {
            return user;
        }
        public void setUser(User user) {
            this.user = user;
        }

        @Override
        public boolean equals(Object obj){
            if(obj == this) return true;
            if(obj != null && obj instanceof UserHasModulePK){
                UserHasModulePK other = (UserHasModulePK)obj;
                if(other.getModule().equals(this.getModule()) && other.getUser().equals(this.getUser())) return true;
            }
            return false;
        }
    }

    @EmbeddedId
    public UserHasModulePK getUserHasModulePK() {
        return userHasModulePK;
    }
    public void setUserHasModulePK(UserHasModulePK userHasModulePK) {
        this.userHasModulePK = userHasModulePK;
    }

    @Transient
    public User getUser(){
        return getUserHasModulePK().getUser();
    }
    public void setUser(User user){
        getUserHasModulePK().setUser(user);
    }

    @Transient
    public Module getModule(){
        return getUserHasModulePK().getModule();
    }
    public void setModule(Module module){
        getUserHasModulePK().setModule(module);
    }

    @Override
    public boolean equals(Object obj){
        if(obj == this)return true;
        if(obj != null && obj instanceof UserHasModule){
            UserHasModule other = (UserHasModule)obj;
            if(other.getUserHasModulePK().equals(this.getUserHasModulePK()))return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "UserHasModule{" +
                "startDate=" + startDate +
                ", endDate=" + endDate +
                ", completedAt='" + completedAt + '\'' +
                ", userHasModulePK=" + userHasModulePK +
                '}';
    }
}
