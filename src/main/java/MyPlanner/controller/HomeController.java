package MyPlanner.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {
    @Autowired
    AccessGrant accessGrant;

    @RequestMapping("/*")
    public ModelAndView home(Model model){
        model.addAttribute("wisdom", "Minimal XML!");
        return new ModelAndView("home");
    }

    @RequestMapping("/profile")
    public ModelAndView profile(Model model){
        if(accessGrant != null)
            if(accessGrant.getAccessToken() != null)
                if(!accessGrant.getAccessToken().isEmpty())
                    model.addAttribute("test", accessGrant.getAccessToken());

        return new ModelAndView("profile");
    }

}
