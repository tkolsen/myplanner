import org.apache.oltu.oauth2.client.OAuthClient;
import org.apache.oltu.oauth2.client.URLConnectionClient;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.response.GitHubTokenResponse;
import org.apache.oltu.oauth2.client.response.OAuthAuthzResponse;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.GrantType;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by TomKolse on 09-Oct-14.
 */
public class RedirectServlet extends HttpServlet {
    private static final String CLIENT_ID = "1105";
    private static final String CLIENT_SECRET = "d4b59dae33627a8a7569";
    private static final String REDIRECT_URL = "http://localhost:8080/redirect";
    private static final String TOKEN_LOCATION = "https://learn-lti.herokuapp.com/login/oauth2/token";

    // Her kommer svaret fra canvas med parameteren code
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().write("doGet()\n");
        try {
            OAuthAuthzResponse oar = OAuthAuthzResponse.oauthCodeAuthzResponse(req);
            resp.getWriter().write("Steg 1\n");
            String code = oar.getCode();
            resp.getWriter().write("Steg 2\n");
            OAuthClientRequest request = OAuthClientRequest
                    .tokenLocation(TOKEN_LOCATION)
                    .setGrantType(GrantType.AUTHORIZATION_CODE)
                    .setClientId(CLIENT_ID)
                    .setClientSecret(CLIENT_SECRET)
                    .setRedirectURI(REDIRECT_URL)
                    .setCode(code)
                    .buildQueryMessage();
            resp.getWriter().write("Steg 3\n");
            OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());
            resp.getWriter().write("Steg 4\n");
            CanvasTokenResponse oAuthResponse = oAuthClient.accessToken(request, CanvasTokenResponse.class);
            resp.getWriter().write("Steg 5\n");
            String accessToken = oAuthResponse.getAccessToken();
            resp.getWriter().write("Steg 6\n");
            resp.getWriter().write("AccessToken: " + accessToken);
        } catch (OAuthProblemException e) {
            e.printStackTrace();
        } catch (OAuthSystemException e){
            e.printStackTrace();
        }
    }
    
}
