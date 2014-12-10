package MyPlanner.model;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name="TEST_USERS")
public class DBUser {

    private int userId;
    private String role;
    private Date registered;
    private String name;

    @Id
    @GeneratedValue
    @Column(name="USER_ID")
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Column(name="ROLE")
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Column(name="REGISTERED")
    public Date getRegistered() {
        return registered;
    }

    public void setRegistered(Date registered) {
        this.registered = registered;
    }

    @Column(name="NAME")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
