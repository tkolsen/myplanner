package MyPlanner.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

    @RequestMapping("/*")
    public ModelAndView home(Model model){
        model.addAttribute("wisdom", "Minimal XML!");
        return new ModelAndView("home");
    }

}
