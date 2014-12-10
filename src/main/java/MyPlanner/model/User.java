package MyPlanner.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="USER")
public class User {
    @JsonProperty("name")
    private String name;
    @JsonProperty("id")
    private int id;

    public User(){

    }

    public User(String name, int id) {
        this.name = name;
        this.id = id;
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
}
