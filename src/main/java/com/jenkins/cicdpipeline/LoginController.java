package com.jenkins.cicdpipeline;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class LoginController {

    @GetMapping("/login")
    public String index(){
        return "index";
    }

    public int task1(int a, int b) {
    return a + b;
    }

public int task2(int a, int b) {
    int sum = a  + b;
    return sum;
}
	
}
