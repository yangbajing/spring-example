package me.yangbajing.springreactive.web.controller.api;

import me.yangbajing.springreactive.data.Hello;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/hello")
public class HelloController {

    @GetMapping(path = "world")
    public Hello world(String hello, String world) {
        return Hello.builder().hello(hello).world(world).build();
    }

}
