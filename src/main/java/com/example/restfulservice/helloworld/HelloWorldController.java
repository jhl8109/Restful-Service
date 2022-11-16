package com.example.restfulservice.helloworld;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
public class HelloWorldController {

    @Autowired
    private MessageSource messageSource;

    // @RequestMapping(method=RequestMethod.GET, path = "/hello-world")
    @GetMapping(path = "/hello-world")
    public String HelloWorld() {
        return "Hello World";
    }

    //bean 형태를 사용함으로써 단순 text, string 형태가 아닌 json 형태로 반환해주게 된다.
    @GetMapping(path = "/hello-world-bean")
    public HelloWorldBean HelloWorldBean() {
        return new HelloWorldBean("Hello World");
    }

    @GetMapping(path = "/hello-world-bean/path-variable/{name}")
    public HelloWorldBean HelloWorldBean(@PathVariable String name) {
        return new HelloWorldBean(String.format("Hello World %s",name));
    }

    @GetMapping(path = "/hello-world-internationalized")
    public String helloWorldInternationalized(@RequestHeader(name="Accept-Language", required = false) Locale locale) {
        return messageSource.getMessage("greeting.message",null,locale);
    }
}
