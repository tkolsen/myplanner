package MyPlanner.model;

public class User {
    private String id;
    private String email;
    private String firstName;
    private String lastName;

    public User() {
    }

    public User(String id, String email, String firstName, String lastName) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return "CustomUserPrint={ \n" +
                "id: " + id + "\n" +
                "email: " + email + "\n" +
                "firstName: " + firstName + "\n" +
                "lastName: " + lastName + "\n" +
                "}";
    }
}
