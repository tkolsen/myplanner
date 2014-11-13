package MyPlanner.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TestJenkinsController {
    @RequestMapping("/*")
    public ModelAndView test(){
        return new ModelAndView("test");
    }
}
