package ai.swarmsolver.backend.infra;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import springfox.documentation.annotations.ApiIgnore;

@Controller
@ApiIgnore
public class RedirectToAngular {

    @RequestMapping({ "/fe/**"})
    public String redirect() {
        return "forward:/index.html";
    }

}