package MyPlanner.controller;

import MyPlanner.oauth.OAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/oauth")
public class OauthController {
    @Autowired
    OAuth oAuth;

    /**
     * Step 1: Redirect users to request Canvas access.
     */
    @RequestMapping("/start")
    public void start(HttpServletResponse response) throws IOException {
        try {
            oAuth.askForConfirmation(response);
        } catch (InstantiationException e) {
            // TODO: Poor error handling.
            e.printStackTrace();
            System.out.println("***************" + e.getMessage() + "***************");
        }
    }

    /**
     * Step 2: Redirected back to /redirect with code or error parameter.
     * Step 3: Exchange code for final access token.
     *
     * @param code - Confirmation code from Canvas.
     * @param error - If the user doesn't accept Canvas return an error parameter.
     * @throws IOException
     */
    @RequestMapping("/redirect")
    public void redirect(@RequestParam(value="code", required = false) String code,
                         @RequestParam(value="error", required = false) String error,
                         HttpServletResponse response) throws IOException, InstantiationException {
        if(error == null && code != null){
            try{
                oAuth.exchangeCodeForToken(code);
                response.sendRedirect("/MyPlanner/profile");
            } catch(IllegalStateException e){
                response.sendRedirect("/oauth/error");
            }
        }else{
            response.sendRedirect("/oauth/error");
        }
        // TODO: Check the exception handling in this method.
    }

    @RequestMapping("/error")
    public ModelAndView loginError(Model model){
        // TODO: Fix error message. Maybe delegate error messages to another class
        model.addAttribute("message", "Error message");
        return new ModelAndView("login-error");
    }

}
