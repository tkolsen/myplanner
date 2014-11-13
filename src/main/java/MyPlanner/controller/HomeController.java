package MyPlanner.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@Controller
public class HomeController {

    @RequestMapping("/*")
    public ModelAndView home(){
        Map<String, Object> model = new HashMap<String, Object>();

        return new ModelAndView("home", model);
    }

}
