package MyPlanner.exceptions;

public class NotAuthorizedException extends Exception {
    public NotAuthorizedException() {
        super("Not authorized");
    }

    public NotAuthorizedException(String message) {
        super("Not authorized: " + message);
    }
}
