package MyPlanner.exceptions;

/**
 * Created by TomKolse on 24-Nov-14.
 */
public class UserInfoNotSetException extends Exception{

    public UserInfoNotSetException(String message) {
        super(message);
    }

    public UserInfoNotSetException() {
        super();
    }
}
