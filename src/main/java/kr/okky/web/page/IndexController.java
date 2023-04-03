package kr.okky.web.page;

import kr.okky.web.api.OkkyArticleList;
import kr.okky.web.api.OkkyWebApi;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@AllArgsConstructor
@Controller
public class IndexController {

    private final OkkyWebApi okkyWebApi;

    @GetMapping("/")
    public String index(Model model, @ModelAttribute OkkyArticleList.Params params) {
        model.addAttribute("topWriters", okkyWebApi.getTopWriters());
        model.addAttribute("life", okkyWebApi.getLife(params));
        return "index";
    }
}
