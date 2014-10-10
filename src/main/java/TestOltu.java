import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by TomKolse on 09-Oct-14.
 */
public class TestOltu extends HttpServlet {

    // Brukeren går inn på MyPlanner
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        OAuthClientRequest request = null;
        try {
            request = OAuthClientRequest
                    .authorizationLocation("http://coop.apps.knpuniversity.com/authorize")
                    .setClientId("OAuthTestForLMS")
                    .setRedirectURI("http://localhost:8080/redirect")
                    .setScope("eggs-collect eggs-count")
                    .setResponseType("code")
                    .buildQueryMessage();
            resp.sendRedirect(request.getLocationUri()); // Sender bruker videre til canvas for å spørre om tillatelse
        } catch (OAuthSystemException e) {
            e.printStackTrace();
        }
    }
}
