package dev.codeio.HelloWorld;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {
    @GetMapping("/amsha")
    String sayHello(){
        return "HelloAmsha!";
    }

}
