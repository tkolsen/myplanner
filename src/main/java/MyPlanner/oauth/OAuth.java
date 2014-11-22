package MyPlanner.oauth;

import MyPlanner.model.LoginInfo;
import org.springframework.social.oauth2.AccessGrant;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface OAuth {

    /**
     * Exchanges code for final access token.
     *
     * @param code - Code parameter return from step 1 i oauth flow.
     * @return
     */
    public LoginInfo exchangeCodeForToken(String code, HttpServletRequest request) throws InstantiationException;

    /**
     * Request Canvas accsess
     *
     * @param response - HttpServletResponse for redirecting to canvas
     */
    public void askForConfirmation(HttpServletResponse response) throws IOException, InstantiationException;

    public String getRedirectUrl() throws InstantiationException;

    public String getClientID() throws InstantiationException;

    public String getClientSecret() throws InstantiationException;

    public String getAuthorizeUrl() throws InstantiationException;

    public String getAccessTokenUrl() throws InstantiationException;
}
