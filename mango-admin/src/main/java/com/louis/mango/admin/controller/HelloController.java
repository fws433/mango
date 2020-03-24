package com.louis.mango.admin.controller;

import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.GetMapping;
@RestController
public class HelloController {
    @GetMapping(value = "/hello")
    public Object hello(){
        return "hello world";
    }
}
