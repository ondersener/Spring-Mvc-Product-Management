package com.works.controller;

import com.works.entities.Customer;
import com.works.services.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequiredArgsConstructor
public class LoginController {

    final CustomerService customerService;
    final HttpServletRequest request;
    final HttpServletResponse response;

    @GetMapping("/")
    public String login(){
        return "login";
    }

    @PostMapping("/customerLogin")
    public String customerLogin(Customer customer){

        Customer c = customerService.login(customer.getEmail(),customer.getPassword());
        request.getSession().setAttribute("customer",c);
        return "redirect:/dashboard";
    }

}
