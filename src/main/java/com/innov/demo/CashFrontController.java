package com.innov.demo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import javax.validation.Valid;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;

@Controller
class CashFrontController {

    private static final Logger logger = Logger.getLogger(CashFrontController.class.getName());

    @Autowired
    RestTemplate restTemplate;

    @GetMapping({"/", "/hello"})
    public String hello(Model model, @RequestParam(value="name", required=false, defaultValue="World") String name) {
        model.addAttribute("name", name);
        return "hello";
    }

    @GetMapping({"/new"})
    public String initCash(Model model) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<String>(headers);
        List<Payment> pymnts = restTemplate.exchange("http://cash-back:8080/payments", HttpMethod.GET, entity, List.class).getBody();
        model.addAttribute("payments", pymnts);
        System.out.println("The result is ->" + pymnts);  
        return "payment";
    }

    @PostMapping ({"/postPayment"})
    public String postPayment(Model model, @Valid Payment payment) {
        System.out.println(payment.toString());
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<Payment> entity = new HttpEntity<Payment>(payment,headers);
        restTemplate.exchange("http://cash-back:8080/payment", HttpMethod.POST, entity, String.class).getBody();
        return initCash(model);
    }
}