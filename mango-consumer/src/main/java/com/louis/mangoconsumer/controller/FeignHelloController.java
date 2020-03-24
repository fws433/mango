package com.louis.mangoconsumer.controller;

import com.louis.mangoconsumer.service.MangoProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FeignHelloController {
    @Autowired
    private MangoProducerService mangoProducerService;
    @RequestMapping("/feign/call")
    public String call(){
        return mangoProducerService.hello();
    }
}
