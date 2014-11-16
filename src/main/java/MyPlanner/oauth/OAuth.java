package MyPlanner.oauth;

import org.springframework.social.oauth2.AccessGrant;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface OAuth {

    /**
     * Exchanges code for final access token.
     *
     * @param code - Code parameter return from step 1 i oauth flow.
     * @return
     */
    public void exchangeCodeForToken(String code) throws InstantiationException;

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
