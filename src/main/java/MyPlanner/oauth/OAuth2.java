package MyPlanner.oauth;

import MyPlanner.model.LoginInfo;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by TomKolse on 24-Nov-14.
 */
public interface OAuth2 {

    public String askForUserInfoConfirmation() throws IOException;

    public LoginInfo exchangeCodeForUserInfo(String code);

    public String askForAccessTokenConfirmation() throws IOException;

    public String exchangeCodeForAccessToken(String code);
}
