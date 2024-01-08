package org.example.shoppingapp.controller;

import org.example.shoppingapp.domain.User;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
@PreAuthorize("hasAuthority('Ultra')")
public class HelloWorldController {

    @GetMapping
    public String getHelloWorld(){
        return "Hello, World!";
    }

    @GetMapping("/test")
    public Object getAuthUserDetail(){
        System.out.println(SecurityContextHolder.getContext().getAuthentication().getAuthorities());
        return SecurityContextHolder.getContext().getAuthentication();
    }

}
