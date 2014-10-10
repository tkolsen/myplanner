import org.apache.oltu.oauth2.client.OAuthClient;
import org.apache.oltu.oauth2.client.URLConnectionClient;
import org.apache.oltu.oauth2.client.request.OAuthBearerClientRequest;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.response.OAuthAuthzResponse;
import org.apache.oltu.oauth2.client.response.OAuthResourceResponse;
import org.apache.oltu.oauth2.common.OAuth;
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
    private static final String CLIENT_ID = "OAuthTestForLMS";
    private static final String CLIENT_SECRET = "78dd12e4aaa696ef31c32b8665be1122";
    private static final String REDIRECT_URL = "http://localhost:8080/redirect";
    private static final String TOKEN_LOCATION = "http://coop.apps.knpuniversity.com/token";
    private static final String API_COLLECT_EGGS = "http://coop.apps.knpuniversity.com/api/369/eggs-collec";
    private String accessToken = null;

    // Her kommer svaret fra canvas med parameteren code
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            OAuthAuthzResponse oar = OAuthAuthzResponse.oauthCodeAuthzResponse(req);
            String code = oar.getCode();
            OAuthClientRequest request = OAuthClientRequest
                    .tokenLocation(TOKEN_LOCATION)
                    .setGrantType(GrantType.AUTHORIZATION_CODE)
                    .setClientId(CLIENT_ID)
                    .setClientSecret(CLIENT_SECRET)
                    .setRedirectURI(REDIRECT_URL)
                    .setScope("eggs-collect eggs-count")
                    .setCode(code) // code from canvas. this is wrong for some reason.
                    .buildBodyMessage();
            OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());
            CanvasTokenResponse oAuthResponse = oAuthClient.accessToken(request, CanvasTokenResponse.class);
            accessToken = oAuthResponse.getAccessToken();

            request = new OAuthBearerClientRequest(API_COLLECT_EGGS)
                    .setAccessToken(accessToken)
                    .buildBodyMessage();
            OAuthResourceResponse resourceResponse = oAuthClient.resource(request, OAuth.HttpMethod.POST, OAuthResourceResponse.class);
            String body = resourceResponse.getBody().toString();
            resp.getWriter().write(body);
        } catch (OAuthProblemException e) {
            e.printStackTrace();
        } catch (OAuthSystemException e){
            e.printStackTrace();
        }
    }
    
}
