package ag.pinguin.issuetracker.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Index Controller
 *
 * @author Mehdi Chitforoosh
 */
@Controller
public class IndexController {

    /**
     * Index endpoint to show the index page
     *
     * @return index html page
     */
    @GetMapping({"/", "/developers/**", "/issues/**", "/planner"})
    public String index() {
        return "index";
    }

}
