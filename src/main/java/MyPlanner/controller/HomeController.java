package MyPlanner.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class HomeController {

    @RequestMapping("/*")
    public ModelAndView home(Model model){
        model.addAttribute("wisdom", "Minimal XML!");
        return new ModelAndView("home");
    }

    @RequestMapping("/profile")
    public ModelAndView profile(Model model, HttpServletRequest request){
        AccessGrant accessGrant = (AccessGrant)(request.getSession().getAttribute("accessGrant"));
        model.addAttribute("accessToken", accessGrant.getAccessToken());

        return new ModelAndView("profile");
    }

}
