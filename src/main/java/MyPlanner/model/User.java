package MyPlanner.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name="USER")
public class User{
    @JsonProperty("name")
    private String name;
    @JsonProperty("id")
    private int id;

    private boolean status = true;

    public User(){

    }

    public User(String name, int id) {
        this.name = name;
        this.id = id;
        this.status = true;
    }

    @Column(name="NAME")
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @Id
    @Column(name="USER_ID")
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Column(name="USER_STATUS")
    public boolean getStatus(){return status;}
    public void setStatus(boolean status){this.status = status;}

    @Override
    public boolean equals(Object obj){
        if(obj == this) return true;
        if(obj != null && obj instanceof User){
            User other = (User) obj;
            if(other.getId() == this.getId()) return true;
        }
        return false;
    }

}
