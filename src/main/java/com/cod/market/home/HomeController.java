package com.cod.market.home;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {

//    @GetMapping("/home")
//    public String index() {
//        return "home/main";
//    }
//

    @GetMapping("/")
    public String index() {
        return "redirect:/product/list";
    }
}
