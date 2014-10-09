import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by TomKolse on 09-Oct-14.
 */
public class RedirectServlet extends HttpServlet {

    // Her kommer svaret fra canvas med parameteren code
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().write("doGet()");
    }
    
}
