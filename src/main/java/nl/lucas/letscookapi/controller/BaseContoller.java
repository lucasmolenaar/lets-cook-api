package nl.lucas.letscookapi.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class BaseContoller {

    public String hello() {
        return "Hello world!";
    }
}
