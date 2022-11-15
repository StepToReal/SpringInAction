package tacos;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "home"; // 뷰의 논리적인 이름 반환 - resources/templates/ 아래 "이름".html 페이지를 호출하게 되어 있다.
    }
}
