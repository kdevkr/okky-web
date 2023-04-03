package kr.okky.web.page;

import kr.okky.web.api.OkkyWebApi;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@AllArgsConstructor
@Controller
public class IndexController {

    private final OkkyWebApi okkyWebApi;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("topWriters", okkyWebApi.getTopWriters());
        return "index";
    }
}
