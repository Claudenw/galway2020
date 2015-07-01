package ie.galway2020.dashboard.webapp.controller;

import ie.galway2020.dashboard.model.SignupForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Top Influential Users page controller
 */
@Controller
public class TopInfluentialController {

    @RequestMapping(value = "/topInfluential", method = RequestMethod.GET)
    public ModelAndView topInfluentialUsers() {
        String message = "this is a message";
        return new ModelAndView("tables/topInfluential", "message", message);
    }

    @RequestMapping(value = "/getTopInfluentialData", method = RequestMethod.POST)
    public String create(Model model, SignupForm signupForm, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "index";
        }

        return "show";
    }

}


