import org.apache.oltu.oauth2.client.response.OAuthAccessTokenResponse;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.token.OAuthToken;
import org.apache.oltu.oauth2.common.utils.JSONUtils;

/**
 * Created by Tom on 10.10.2014.
 *
 * Custom implementation of OAuthAccessTokenResponse made for Instructure Canvas
 * Uses JSONUtils.parseJSON because the response from canvas is a json object.
 */
public class CanvasTokenResponse extends OAuthAccessTokenResponse {

    /**
     * Gets the access_token parameter from the canvas response.
     * @return - access_token param from canvas
     */
    @Override
    public String getAccessToken() {
        return getParam("access_token");
    }

    @Override
    public Long getExpiresIn() {
        return null;
    }

    @Override
    public String getRefreshToken() {
        return null;
    }

    @Override
    public String getScope() {
        return null;
    }

    @Override
    public OAuthToken getOAuthToken() {
        return null;
    }

    /**
     * Tis method parses the json response from canvas and sets all parameters.
     *
     * @param body - response from authentication uri
     * @throws OAuthProblemException
     */
    @Override
    protected void setBody(String body) throws OAuthProblemException {
        this.body = body;
        parameters = JSONUtils.parseJSON(body);
    }

    @Override
    protected void setContentType(String contentType) {
        this.contentType = contentType;
    }

    @Override
    protected void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }
}
