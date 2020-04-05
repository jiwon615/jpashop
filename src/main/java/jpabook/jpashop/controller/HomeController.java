package jpabook.jpashop.controller;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j      // Logger log = LoggerFactory.getLogger(getClass()); 을 어노테이션으로 한번에 대체함 !!
public class HomeController {
   // Logger log = LoggerFactory.getLogger(getClass());
    @RequestMapping("/")
    public String home() {
        log.info("home controller~!!!!");
        return "home";
    }
}
