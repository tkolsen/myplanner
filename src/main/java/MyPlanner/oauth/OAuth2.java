package MyPlanner.oauth;

import MyPlanner.model.LoginInfo;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Interface for the oauth2 flow.
 */
public interface OAuth2 {

    /**
     * Handles the first step in the flow,
     * asking for confirmation.
     * @return String - the url with parameters to send the user to for confirmation.
     * @throws IOException
     */
    public String askForUserInfoConfirmation() throws IOException;

    /**
     * Handles the second step in the flow,
     * exchanging the code from step 1 for canvas user info
     * @param code - the code that is sent back from canvas after the first step
     * @return
     */
    public LoginInfo exchangeCodeForUserInfo(String code);

    /**
     * Handles the third step in the flow,
     * ask the user for access token confirmation.
     * @return String - the url with parameters to send the user to for confirmation.
     * @throws IOException
     */
    public String askForAccessTokenConfirmation() throws IOException;

    /**
     * Handles the last step in the flow,
     * exchanging code for access token.
     * @param code - the code that is sent back after confirmation in the third step
     * @return String - the access token
     */
    public String exchangeCodeForAccessToken(String code);
}
